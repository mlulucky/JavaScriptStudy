package com.acorn.javascriptstudy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// 톰캣에서 동적페이지(서블릿)을 생성하기 위한 2가지 약속
// 서블릿으로 정의할 타입을 HttpServlet 으로 정의
// 서블릿을 리소스로 등록하기 위해 DD(web.xml)에 주소를 등록(어노테이션 @WebServlet) // DD(Deploy Descriptor) : 배포설명자
@WebServlet("/l14_emp_list.do")
public class L14EmpList extends HttpServlet {
    // 클라이언트(브라우저)에서 리소스를 요청하는 2가지 방식(GET: url, POST: 양식제출)
    // doGet 클라이언트(브라우저)가 /l14_emp_list.do 를 url 호출하면 톰캣이 해당 리소스의 doGet()함수를 실행

    //📍서블릿 : 통신에 요청에 응답 하는것
    // HttpServletRequset, HttpServletResponse
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // java.sql.* : JDBC  java에서 제공하는 db 접속 객체들 (java에서 database를 connect 한다.)(자바와 DB를 연결해준다.)
        // JDBC 가 mysql 에 접속하기 접속하기 위해서는 mysql-j-connector.jar 가 필요하다
        // maven 이 의존성을 관리해준다.

        // 📍JDBC : 서블릿 안에서 DB 통신하는 것
        String user="root";   // mysql 계정
        String pw="mysql123"; // mysql 비밀번호
        String driver="com.mysql.cj.jdbc.Driver"; // maven 파일에서 종속성 mysql 라이브러리 다운받은거

        // 자바에서 DriverManager 로부터 connection 객체를 받아와 jdbc 와 연결, SELECT 쿼리를 돌린다
        Connection conn=null; // Connection : 네트워크 연결
        PreparedStatement pstmt=null; // Preparedstatement : sql문을 db에 보내기위한 인터페이스
        ResultSet rs = null;  // ResultSet : SELECT 결과를 저장하는 인터페이스

        String empListStr="";
        List<EmpDto> empList=null;

        try { // try catch 오류가 발생해도 시스템이 멈추지 않게 하는 예외처리
            // 1. JDBC 드라이버 로딩 => Class.forName() 메소드를 이용한 DrivaerManager 생성
            Class.forName("com.mysql.cj.jdbc.Driver"); // 동적로딩의 대상 // jdbc.Driver는 패키지명.클래스명
            // *동적로딩 : 특정 객체(DriverManager)가 작업을 수행할 때 객체를 생성하는 행위 (제어의 역전)
            // *일반적인 객체지향 언어는 객체를 프로그래머가 직접 생성 후 매개변수로 전달 <-> 동적로딩

            // 2. DriverManager 클래스를 통해 DB 와의 연결을 생성한다. => DriverManager.getConnection(연결문자열, DB_id,DB_pw) // 연결문자열 - “jdbc:Driver 종류://IP:포트번호/DB명”
            // * DriverManager 가 db에 접속할 때 능동적으로 주소를 보고 필요한 라이브러리(Class.forName 으로 정의한)를 찾아서 생성 후 접속한다(동적로딩)
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scott",user,pw);// 접속할때
            // getConnection - 어떤 url에 접속할거냐(DB가 있는 url, DB의 user, password)를 입력해주면 연결
            // DB 접속 성공시 Connection(접속을 유지) 객체를 반환

            // 3. Statement 생성 및 질의 수행
//            pstmt=conn.prepareStatement("SELECT * FROM EMP"); // 쿼리(명령문)를 사용하기 위한 객체
            pstmt=conn.prepareStatement("SELECT * FROM EMP WHERE DEPTNO=?"); // 쿼리(명령문)를 사용하기 위한 객체
            int deptno=Integer.parseInt(req.getParameter("deptno")); // url에서 deptno 파라미터 값을 가져온다. + 정수로변환 // url 뒤쪽 물음표 뒤에 // 자바코드를 실행해서 디비내역이 바껴서 파라미터 가 변경되서 바뀌는게 동적인 이유
            pstmt.setInt(1,deptno); // sql 에 첫번째 ?물음표 파라미터에 deptno 를 넣겠다
            // setInt 는 파라미터 타입에 따라서 setString setDouble 등.. // parameterIndex: 1 물음표 첫번째라는 뜻

            // rs에 쿼리 결과값 담기 // 질의어를 실행
            rs=pstmt.executeQuery(); // executeQuery() : db의 데이터를 조회할 때 사용한다.(select, show 등의 명령어)
            // executeQuery() => ResultSet 객체를 반환한다. (조회한 정보들이 담겨있다)

            empList=new ArrayList<EmpDto>();
//            deptList=new ArrayList<DeptDto>();

            // 4. ResultSet 결과 받기
            while(rs.next()) { // 레코드상에 다음 요소가 있는 동안에 // rs == table row : 레코드, 튜플, 객체  //  rs가 iterator 이므로 next()함수 사용가능 next() 다음요소 있으면 출력!
                /* 👉문자열로 db의 데이터를 받으면 제어할 방법이 없다. 그래서 *DTO 를 정의해서 사용한다.(DTO 를 정의하는 이유) => EmpDto.java 파일 생성 */
                // emp 테이블
                EmpDto emp=new EmpDto(); // rs 는 emp 테이블 한줄(row)이 들어간다. => rs 는 emp 객체 1개 -> 객체생성가능
                // emp 테이블에서 데이터 받아오기 - getInt()
                emp.setEmpno(rs.getInt("empno")); // getInt : result set 함수.
                emp.setEname(rs.getString("ename")); // 칼럼명이 ename 인 값을 가져와라
                emp.setJob(rs.getString("job"));
                emp.setSal(rs.getDouble("sal"));
                emp.setComm(rs.getDouble("comm"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setDeptno(rs.getInt("deptno"));
                emp.setHiredate(rs.getDate("hiredate"));
                empList.add(emp);


               /* 문자열로 db 데이터를 받으면 제어할 방법이 없다.
                empListStr+=rs.getInt("empno");
                empListStr+=rs.getInt("ename");
                empListStr+=rs.getInt("job");*/
            }
        }catch (Exception e) { // 부모타입의 예외를 작성할 수록 더 많은 예외 처리를 한다.(Exception 은 모든 예외처리가능)
            e.printStackTrace();
        }

        // 이부분.. 다시 영상보고 이해하기
        resp.setContentType("text/html;charset=UTF-8;"); // 반환하는 컨텐츠의 타입
        PrintWriter out = resp.getWriter(); // resp.getWriter() : 리소스로 반환될 문자열 작성
        // 해당 문자열을 리소스로 반환하면 브라우저가 자동으로 문서의 형식을 html(문서형식x) 로 인지하는데 문서에 인코딩이 적용되지 않아 한글이 깨짐

        // 2/13 문제풀기!
        out.println("<h1>Scott.emp list출력</h1>");
        out.println("<h2>문제1: 부서번호를 파라미터로 보내면 쿼리로 부서번호에 해당하는 사원만 출력하세요</h2>");
        out.println("<h2>문제2: dept와 emp 를 조인해서 부서이름과 상사이름을 출력하세요</h2>");
        out.println("<h2><a href='./l14_dept_list.do'>문제3: 부서 리스트를 출력하는 동적 페이지를 만드세요</a></h2>");

        // emp 테이블 => 화면출력
        empListStr+="<table>";
        empListStr+="<tr><th>사번</th><th>이름</th><th>부서번호</th><th>부서이름</th><th>상사이름</th></tr>";
        for(EmpDto emp : empList) { // 이 한줄이 tr
            empListStr+="<tr>";
            empListStr+="<td>"+emp.getEmpno()+"</td>";
            empListStr+="<td>"+emp.getEname()+"</td>";
            empListStr+="<td>"+emp.getDeptno()+"</td>";
            empListStr+="<td>"+emp.getD().getDname()+"</td>";
            empListStr+="<td>Dept 조인</td>"; // 부서이름. 상사이름 조인하기 // dname. 테이블 dept
            empListStr+="<td>Emp 조인</td>";
            empListStr+="</tr>";
        }
        empListStr+="</table>";
        out.println(empListStr);




        // java 문서가 바뀌면 class 로 컴파일 하고 톰캣에 배포된 webapp 바꿔야 한다. (배포 꼭! 해야한다.)

    }
}
