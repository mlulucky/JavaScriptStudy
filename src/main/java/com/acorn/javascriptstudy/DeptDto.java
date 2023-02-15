package com.acorn.javascriptstudy;

/* desc dept : dept 테이블 구조보기
+--------+-------------+------+-----+---------+-------+
        | Field  | Type        | Null | Key | Default | Extra |
        +--------+-------------+------+-----+---------+-------+
        | DEPTNO | int         | NO   | PRI | NULL    |       |
        | DNAME  | varchar(14) | YES  |     | NULL    |       |
        | LOC    | varchar(13) | YES  |     | NULL    |       |
        +--------+-------------+------+-----+---------+-------+
*/


public class DeptDto {
    // 디비와 데이터 통신할때 가져오는 자료
    // DB 의 자료를 java 에 처리하기 위해 받아온다
    // DB 의 타입과 유사한 java 의 type을 선택

    private int deptno;   // pk
    private String dname; // varchar 문자열타입
    private String loc;

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "DeptDto{" +
                "deptno=" + deptno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
