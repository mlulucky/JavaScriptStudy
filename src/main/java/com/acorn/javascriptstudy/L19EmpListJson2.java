package com.acorn.javascriptstudy;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/empListJson2.do") // 동적페이지 주소
public class L19EmpListJson2 extends HelloServlet {
    // 😃동적페이지 만들기!
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 데이터 요청하는 통신 방식이 Get 이라서 doGet 실행
        // 😃html 파일 불러오기(반환하기) - 응답하는 컨텐츠 타입 html
//        response.setContentType("text/html;charset=UTF-8;");
//        out.println("<h1>안녕!</h1>");


        // 😃json 페이지 반환하기(응답하는 컨텐츠타입 json /문자열인코딩)
        response.setContentType("application/json;charset=UTF-8"); // 📍json 페이지 반환 + 문자열 인코딩!
        PrintWriter out=response.getWriter(); // 응답으로 내보낼 출력 스트림
        // 😃파라미터로 페이지 불러오기
        String deptnoStr=request.getParameter("deptno"); // url 에서 파라미터 deptno 값 가져오기. 요청정보에서 deptno 파라미터 가져오기
        // getParameter(파라미터의 key) : 파라미터의 key의 value 를 가져온다.
        // out.println("안녕하세요");
        // out.println("{\"name\": \"경민\"}"); // 👉JSON 문자열 형식
        // String a=request.getParameter("a"); // 파라미터 a 불러오기
        // String b=request.getParameter("b"); // 파라미터 b 불러오기
        // out.println("{\"sum\":"+(a+b)+"}"); // 서비스 콘솔에 출력

        // JDBC 통신
        String url="jdbc:mysql://localhost:3306/SCOTT"; // DB 서버주소
        String user="root"; // 유저
        String pw="mysql123"; // 패스워드
        String driver="com.mysql.cj.jdbc.Driver"; // 동적로딩 역할 (mysql DB 사용을 위한 드라이버)

        // JDBC 객체(테이블/자료구조)
        Connection conn=null; // 접속하는 객체
        PreparedStatement pstmt=null; // 준비된 쿼리 // ? 뒤 파라미터를 셋팅하는 쿼리
        ResultSet rs=null; // 결과
        List<EmpDto> empList=null; // EmpDto DB를 리스트 타입으로 선언 // 테이블

        try { // 📍DB 통신에 성공했을때
            Class.forName(driver); // db 드라이버 로드 // 클래스가 없을 예외처리
            conn = DriverManager.getConnection(url,user,pw); // db 접속
            // 접속에 문제가 있을 예외처리
            try{
                // 🍋url 파라미터가 있을때 (파라미터가 없는경우 - 에러 발생가능)
                int deptno=Integer.parseInt(deptnoStr); // 파라미터 문자열 -> 정수로 형변환
                String sql="SELECT * FROM EMP WHERE DEPTNO=?";
                pstmt=conn.prepareStatement(sql);
                pstmt.setInt(1,deptno); // 파라미터 정수 deptno
            }catch(NumberFormatException e){
                // 🍋url 파라미터가 없는 경우 -> 전체 테이블 출력
                e.printStackTrace();
                pstmt=conn.prepareStatement("SELECT * FROM EMP"); // 데이터베이스 담기 // 데이터베이스 없을 예외처리
            }
            // 🍋url 파라미터 있을때,없을때 공통!
            // 통신에 성공했을 때
            rs=pstmt.executeQuery(); // 쿼리 결과물(SELECT) // 데이터베이스 없을 예외처리
            empList=new ArrayList<>(); // 💎통신 성공 시 EmpDto DB를 리스트로 저장
             while(rs.next()) { // 테이블 한줄 == emp 객체 (사원1명 정보)블러오기
                EmpDto emp=new EmpDto();// rs 에서 가져온 내역을 emp 에 셋팅하기
                 // DB 테이블에서 가져온 내역(데이터)이 자바와 타입이 다르다
                 // ResultSet : 자바의 타입과 유사하게 반환해준다!
                 emp.setEmpno(rs.getInt("empno"));  //이름이 "empno"인 컬럼의 데이터 받아옴
                 emp.setEname(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setSal(rs.getDouble("sal"));
                emp.setDeptno(rs.getInt("deptno"));
                empList.add(emp);// EmpDto 정보 셋팅 후  list 에 추가하기
             }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // out.println(empList.get(1));
        // EmpDto{empno=7499, ename='ALLEN', job='SALESMAN', mgr=0, hiredate=null, sal=1600.0, comm=null, deptno=30}

        // out.println(empList);
        // 👉미리 정의된 출력구문이 출력된다. => EmpDto 파일의 toString
        // 👉출력형태를 JSON 문자열로 만들어서 반환하기! // JSON : 오브젝트를 문자열로 표현한 것.
        // 💎JSON 문자열 => "[{"empno":7788, "ename":"scott", "sal":1200.00}]"
        String empJson="[";
        for(int i=0; i<empList.size(); i++) { // empList 변수는 전역에 선언
            EmpDto emp=empList.get(i); // empList 배열에 저장된 emp를 변수상자 emp에 담는다 // EmpDto 타입 emp 변수상자 선언 // 변수 범위 if문
            empJson+="{";
            empJson+="\"empno\":"+emp.getEmpno()+",";
            empJson+="\"ename\":\""+emp.getEname()+"\","; // 📍문자열은 \" 으로 감싸기!
            empJson+="\"job\":\""+emp.getJob()+"\",";
            empJson+="\"sal\":"+emp.getSal()+",";
            empJson+="\"deptno\":"+emp.getDeptno()+"";
            empJson+="}";
            empJson+=(i!=empList.size()-1)?",":""; // 반복횟수가 empList의 마지막인덱스와 다른가? true 쉼표(,)추가 // false 쉼표없음
        }
        empJson+="]";



        // 😃동기식으로 동적 페이지 불러오기
        out.println(empJson);

    }
}
