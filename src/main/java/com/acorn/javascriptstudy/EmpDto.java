package com.acorn.javascriptstudy;
/*  desc emp : emp 테이블 구조보기
    +----------+-------------+------+-----+---------+-------+
        | Field    | Type        | Null | Key | Default | Extra |
        +----------+-------------+------+-----+---------+-------+
        | EMPNO    | int         | NO   | PRI | NULL    |       |
        | ENAME    | varchar(10) | YES  |     | NULL    |       |
        | JOB      | varchar(9)  | YES  |     | NULL    |       |
        | MGR      | int         | YES  | MUL | NULL    |       |
        | HIREDATE | date        | YES  |     | NULL    |       |
        | SAL      | float(7,2)  | YES  |     | NULL    |       |
        | COMM     | float(7,2)  | YES  |     | NULL    |       |
        | DEPTNO   | int         | YES  | MUL | NULL    |       |
        +----------+-------------+------+-----+---------+-------+*/

import java.util.Date;

public class EmpDto {
    // Dto : 디비와 데이터통신할떄 가져오는 자료들을 정리
    // 데이터 전송용 // DTO Data Transfer Object
    // 👉DB 의 자료를 java 에 처리하기 위해 받아오는 역할
    // 👉이때 DB 의 타입과 유사한 java 의 type 을 선택해야 한다.
    // 👉=> 데이터를 불러올때 타입을 맞춰주는 역할
    // 테이블 내역을 private 하게. 타입을 자바타입으로. getter,setter => DTO
    // 👉파라미터의 타입을 명확하게 하므로써 sql injection 해킹을 막는다.
    private  int empno;   // pk
    private String ename; // varchar 문자열 타입
    private String job;
    private int mgr;      // fk / Emp.empno 를 참조
    private Date hiredate;
    private Double sal;
    private Double comm;   // is null(null 일 수 있다) // 기본형은 null 을 참조할 수 없다.
    private int deptno;   // is null / fk / Dept.deptno 를 참조

    // dept 테이블
//    private DeptDto DEPT;
//
//    public DeptDto getDEPT() {
//        return DEPT;
//    }
//
//    public void setDEPT(DeptDto DEPT) {
//        this.DEPT = DEPT;
//    }

        DeptDto d = new DeptDto();

    public DeptDto getD() {
        return d;
    }

    public void setD(DeptDto d) {
        this.d = d;
    }

    // alt + insert 자동생성
    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public double getComm() {
        return comm;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    // alt + insert 자동생성
    // 미리 정의된 출력구문 toString
    @Override
    public String toString() { // toString() 출력구문 정의해 놓은 것 : 문자열 출력  -> JSON 으로 바꿔서 출력해야한다.
        return "EmpDto{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", mgr=" + mgr +
                ", hiredate=" + hiredate +
                ", sal=" + sal +
                ", comm=" + comm +
                ", deptno=" + deptno +
                '}';
    }

}
