package com.vincent.todo.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vincent.todo.main.service.MainService;
import com.vincent.todo.main.vo.BoardVO;
import com.vincent.todo.main.vo.MemberVO;
import com.vincent.todo.main.vo.PageVO;
import com.vincent.todo.main.vo.ReplyVO;

@Controller
public class MainController {

	@Resource
	private MainService mainService;

	public static void date(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
	}

	public void alert(String notice, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript'>");
		out.println("alert('" + notice + "');");
		out.println("</script>");
		out.flush();
		return;
	}

	public boolean loginCheck(HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = { "/", "home.do" })
	public String home(Locale locale, Model model) {
		date(locale, model);
		return "index";
	}

	@RequestMapping("login.do")
	public String login() {
		return "login";
	}

	@PostMapping("loginProcess.do")
	public String loginProcess(HttpServletResponse response, HttpSession session, HttpServletRequest req, Locale locale,
			Model model) throws IOException {
		date(locale, model);
		response.setCharacterEncoding("UTF-8");
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");

		int loginCheck = mainService.loginProcess(userId, password);

		if (loginCheck == 1) {
			session.setAttribute("userId", userId);
			return "redirect:/main.do";
		} else {
			alert("Login Credential is incorrect.", response);
			return "login";
		}
	}

	@GetMapping("logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();

		return "redirect:/home.do";
	}

	@RequestMapping("join.do")
	public String join() {
		return "join";
	}

	@PostMapping("joinProcess.do")
	public String joinProcess(HttpServletRequest req, HttpServletResponse response) throws Exception {
		try {
			response.setCharacterEncoding("UTF-8");
			MemberVO vo = new MemberVO();

			vo.setName(req.getParameter("name"));
			vo.setUserId(req.getParameter("userId"));
			vo.setPassword(req.getParameter("password"));

			mainService.joinProcess(vo);

			alert("User successfully registered.", response);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "login";
	}

	@ResponseBody
	@PostMapping("idCheck.do")
	public int idCheck(HttpServletRequest req) throws Exception {
		String userId = req.getParameter("userId");
		MemberVO vo = mainService.idCheck(userId);

		int result = 0;

		if (vo != null) {
			result = 1;
		}

		return result;
	}

	@GetMapping("main.do")
	public String boardList(PageVO pagevo, Locale locale, Model model, String nowPage, String cntPerPage,
			HttpSession session) throws Exception {
		if (!loginCheck(session)) {
			return "login";
		}
		date(locale, model);

		int total = mainService.cntContent();

		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "10";
		}

		pagevo = new PageVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));

		model.addAttribute("paging", pagevo);
		model.addAttribute("board", mainService.viewBoard(pagevo));

		return "main";
	}

	@RequestMapping("writePage.do")
	public String writePage(HttpSession session) {
		if (!loginCheck(session)) {
			return "login";
		}
		return "write";
	}

	@PostMapping("writeProcess.do")
	public String writeProcess(HttpServletRequest req, HttpSession session,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBoardTitle(req.getParameter("boardTitle"));
		vo.setBoardContent(req.getParameter("boardContent"));
		vo.setUserId((String) session.getAttribute("userId"));

		mainService.writeProcess(vo);

		return "redirect:/main.do";
	}

	@GetMapping("viewContent.do")
	public String viewContent(HttpSession session, Locale locale, Model model, int boardNo) {
		if (!loginCheck(session)) {
			return "login";
		}
		date(locale, model);
		model.addAttribute("content", mainService.viewContent(boardNo));
		model.addAttribute("reply", mainService.viewReply(boardNo));
		return "viewContent";
	}

	@GetMapping("updateContent.do")
	public String updateContent(Locale locale, Model model, int boardNo) {
		date(locale, model);
		model.addAttribute("content", mainService.viewContent(boardNo));
		return "update";
	}

	@PostMapping("updateProcess.do")
	public String updateProcess(HttpServletRequest req, HttpSession session, int boardNo,
			MultipartHttpServletRequest multipartRequest) throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBoardTitle(req.getParameter("boardTitle"));
		vo.setBoardContent(req.getParameter("boardContent"));
		vo.setBoardNo(Integer.parseInt(req.getParameter("boardNo")));

		String fileName1, fileName2 = null;

		MultipartFile file1 = multipartRequest.getFile("file1");
		MultipartFile file2 = multipartRequest.getFile("file2");

		if (!file1.isEmpty()) {
			String originalFileName = file1.getOriginalFilename();
			String ext = FilenameUtils.getExtension(originalFileName);

			UUID uuid = UUID.randomUUID();
			fileName1 = uuid + "." + ext;
			file1.transferTo(new File("upload/" + fileName1));
			vo.setBoardFile1(fileName1);
		}

		if (!file2.isEmpty()) {
			String originalFileName = file2.getOriginalFilename();
			String ext = FilenameUtils.getExtension(originalFileName);

			UUID uuid = UUID.randomUUID();
			fileName2 = uuid + "." + ext;
			file1.transferTo(new File("upload/" + fileName2));
			vo.setBoardFile2(fileName2);
		}

		mainService.updateProcess(vo);
		return "redirect:/main.do";
	}

	@GetMapping("deleteProcess.do")
	public String deleteProcess(HttpServletResponse response, Locale locale, Model model, int boardNo)
			throws IOException {
		date(locale, model);
		mainService.deleteProcess(boardNo);
		return "redirect:/main.do";
	}

	/*
	 * 댓글(reply) 관리 부분
	 */

	@PostMapping("writeReply.do")
	public String writeReply(RedirectAttributes redirectAttributes, HttpServletRequest req, HttpSession session,
			HttpServletResponse response, int boardNo) throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setBoardNo(boardNo);
		vo.setReplyWriter((String) session.getAttribute("userId"));
		vo.setReplyContent(req.getParameter("replyContent"));

		mainService.writeReply(vo);
		redirectAttributes.addAttribute("boardNo", boardNo);

		return "redirect:/viewContent.do";
	}

	@GetMapping("updateReply.do")
	public String updateReply(Locale locale, Model model, HttpSession session, HttpServletRequest req, int boardNo) {
		if (!loginCheck(session)) {
			return "login";
		}

		int replyNo = Integer.parseInt(req.getParameter("replyNo"));

		model.addAttribute("content", mainService.viewContent(boardNo));
		model.addAttribute("reply", mainService.viewReply(boardNo));
		model.addAttribute("update", replyNo);

		return "updateReply";
	}

	@PostMapping("updateReplyPro.do")
	public String updateReplyPro(RedirectAttributes redirectAttributes, HttpServletRequest req,
			HttpServletResponse response, Locale locale, Model model, int replyNo) throws IOException {
		ReplyVO vo = new ReplyVO();
		vo.setReplyNo(Integer.parseInt(req.getParameter("replyNo")));
		vo.setReplyContent(req.getParameter("updateReply"));
		int boardNo = Integer.parseInt(req.getParameter("boardNo"));

		mainService.updateReply(vo);
		redirectAttributes.addAttribute("boardNo", boardNo);

		return "redirect:/viewContent.do";
	}

	@GetMapping("deleteReply.do")
	public String deleteReply(RedirectAttributes redirectAttributes, HttpServletResponse response,
			HttpServletRequest req, Locale locale, Model model, int boardNo) {
		date(locale, model);

		redirectAttributes.addAttribute("boardNo", boardNo);

		int replyNo = Integer.parseInt(req.getParameter("replyNo"));

		mainService.deleteReply(replyNo);
		return "redirect:/viewContent.do";
	}
}
