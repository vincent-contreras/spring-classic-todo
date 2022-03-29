CREATE TABLE reply
(
    reply_no SERIAL,
    board_no BIGINT UNSIGNED,
    reply_writer VARCHAR(100),
    reply_content VARCHAR (100),
    reply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(board_no, reply_no)
)

ALTER TABLE reply ADD CONSTRAINT reply_no FOREIGN KEY(board_no) REFERENCES board(board_no)