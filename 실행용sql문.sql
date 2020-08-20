-- 사용자의 요청에 따라 실행해야할 sql문

-- 회원가입 요청시 실행해야될 sql문

INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XX', 'XX', 'XX', 'X', 'XX', 'XX', 'XX',SYSDATE);

-- 회원 전체 조회 요청시 실행해야될  sql문
SELECT * FROM MEMBER;
