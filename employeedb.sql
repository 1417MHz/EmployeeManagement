DROP SCHEMA IF EXISTS employeedb;
CREATE SCHEMA employeedb;
USE employeedb;

DROP TABLE IF EXISTS emp;
CREATE TABLE emp (
    empid INT NOT NULL,                 -- 사원번호
    name VARCHAR(20),                   -- 이름
    age INT,                            -- 나이
    address VARCHAR(30),                -- 주소
    dept VARCHAR(10),                   -- 소속부서
    position VARCHAR(10),               -- 직위
    salary INT,                         -- 연봉
    joindate VARCHAR(20),               -- 입사일(년-월-일)
    leavedate VARCHAR(20) DEFAULT NULL, -- 퇴사일(년-월-일)
    PRIMARY KEY(empid)
);

-- empid, name, age, address, dept, position, salary, joindate, leavedate
-- INSERT INTO emp VALUES(?, '?', ?, '?', '?', '?', ?,'????-??-??', NULL);

SELECT * FROM emp;

-- empid를 세자릿수로 표시하기 위해 LPAD()사용
SELECT LPAD(empid, 3, '0') AS empid, name, age, address, dept, position, salary, joindate, leavedate FROM emp;