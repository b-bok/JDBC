-- ������� ��û�� ���� �����ؾ��� sql��

-- ȸ������ ��û�� �����ؾߵ� sql��

INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XX', 'XX', 'XX', 'X', 'XX', 'XX', 'XX',SYSDATE);
INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?,?,?,?,?,?,?,SYSDATE);

-- ȸ�� ��ü ��ȸ ��û�� �����ؾߵ�  sql��
SELECT * FROM MEMBER;

-- ȸ�� ���̵�� �˻� ��û�� �����ؾߵ� sql�� (��ȸ�� �Ǹ� ������ ����)
SELECT * FROM MEMBER WHERE USERID = 'user01'; 

-- ȸ�� �̸����� Ű���� �˻� ��û�� �����ؾߵ� sql��
SELECT * FROM MEMBER WHERE USERNAME LIKE '%XX%';

select '%'||'����'||'%' from dual;


-- ����ڰ� ȸ�� ���� ����(��й�ȣ, �̸���, ��ȭ��ȣ, �ּ�) ��û�� �� �� �����ؾ��� sql��
UPDATE MEMBER 
SET USERPWD = 'XXX',
    EMAIL = 'XXX',
    PHONE = 'XXX',
    ADDRESS = 'XXX'
WHERE USERID = 'XXX';    
    

-- �����  ȸ�� ���� ����
DELETE FROM MEMBER WHERE USERID = '';