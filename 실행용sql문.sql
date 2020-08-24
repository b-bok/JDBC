-- 사용자의 요청에 따라 실행해야할 sql문

-- 회원가입 요청시 실행해야될 sql문

INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XX', 'XX', 'XX', 'X', 'XX', 'XX', 'XX',SYSDATE);
INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?,?,?,?,?,?,?,SYSDATE);

-- 회원 전체 조회 요청시 실행해야될  sql문
SELECT * FROM MEMBER;

-- 회원 아이디로 검색 요청시 실행해야될 sql문 (조회가 되면 무조건 한행)
SELECT * FROM MEMBER WHERE USERID = 'user01'; 

-- 회원 이름으로 키워드 검색 요청시 실행해야될 sql문
SELECT * FROM MEMBER WHERE USERNAME LIKE '%XX%';

select '%'||'한자'||'%' from dual;


-- 사용자가 회원 정보 변경(비밀번호, 이메일, 전화번호, 주소) 요청시 그 때 실행해야할 sql문
UPDATE MEMBER 
SET USERPWD = 'XXX',
    EMAIL = 'XXX',
    PHONE = 'XXX',
    ADDRESS = 'XXX'
WHERE USERID = 'XXX';    
    

-- 사용자  회원 정보 삭제
DELETE FROM MEMBER WHERE USERID = '';