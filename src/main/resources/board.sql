CREATE TABLE board
(
    board_no SERIAL PRIMARY KEY,
    board_title VARCHAR(100) NOT NULL,
    board_content TEXT,
    user_id VARCHAR(100) NOT NULL,
    board_count INTEGER UNSIGNED DEFAULT 0,
    board_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    board_file1 VARCHAR(200),
    board_file2 VARCHAR(200)
)

INSERT INTO board
	(board_title,board_content,user_id)
VALUES('샘플1', '샘플입니다', 'toco')