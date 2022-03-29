package com.vincent.todo.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vincent.todo.main.vo.BoardVO;
import com.vincent.todo.main.vo.MemberVO;
import com.vincent.todo.main.vo.PageVO;
import com.vincent.todo.main.vo.ReplyVO;

@Mapper
public interface MainMapper {
	
	public void joinProcess(MemberVO vo);
	
	public int loginProcess(String userId, String password);
	
	public List<BoardVO> viewBoard(PageVO pagevo);
	
	public void writeProcess(BoardVO vo);
	
	public BoardVO viewContent(int boardNo);
	
	public int cntContent();
	
	public void updateProcess(BoardVO vo);
	
	public void deleteProcess(int boardNo);
	
	public void writeReply(ReplyVO vo);
	
	public List<ReplyVO> viewReply(int boardNo);
	
	public void updateReply(ReplyVO vo);
	
	public void deleteReply(int replyNo);
	
	public MemberVO idCheck(String userId);

}
