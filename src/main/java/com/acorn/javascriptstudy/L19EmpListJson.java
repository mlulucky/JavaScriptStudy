package com.acorn.javascriptstudy;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.mysql.cj.MysqlType.JSON;
import static java.lang.Class.forName;

@WebServlet("/empListJson.do") // do 는 서블릿 동적페이지 확장자명 // 주소에 / 슬러시 반드시 쓰기. 안쓰면 톰캣이 주소 오류로 처리해서 실행이 안된다
public class L19EmpListJson extends HelloServlet { // 동적페이지가 주소가 된다.
    // 요청하는 방식이 Get 방식이라서 doGet 실행 // jdbc 지겹도록 하면서 외울게요
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 1. 동적 페이지는 서버에서 한번 실행하는 문서
    // 2. 동적 페이지는 호출할때 마다 내용(DB 에서 가져온)이 갱신될 수 있다.
    // 3. 동적 페이지는 호출할 때 마다 내용을 바꿀 수 있는 파라미터(쿼리스트링)를 전달 받을 수 있어야 한다.
    // url?queryString  // 물음표 다음에 오는것이 쿼리스트링
    // queryString(질의하는 문자열) : 요청정보, request 에 존재하고 파라미터는 무조건 문자열(String)이다. => 쿼리를 문자열 변수에 담는다.
    // 쿼리스트링의 규칙 (?key=value&key=value&....) // key=value

    // 📍Integer.parseInt("as11as"); // 정수로 변환불가 - 파싱오류
    // => 오류가 발생하면 밑에 코드 실행x,
    // => 톰캣이 예외 처리해서 500이라는 상태를 반환(서버내부오류! - 개발자가 예외처리를 하지 않은 경우 발생)
    // response.setContentType("text/html;charset=UTF-8"); // html 페이지 반환 + 캐릭터 인코딩
    // JSON : println("{\"name\": \"경민\"}")  <- JSON 규칙 key:value
    // 파라미터 전달받기 // 파라미터는 문자열이기 때문에 무조건 parseInt 로 바꿔줘야 한다.
   /* String a=request.getParameter("a"); // a라는 key 에 있는 value 를 받아온다.
    String b=request.getParameter("b");
    */

    // 서블릿 수정 후 톰캣서버 배포다시 하기!
    // JSON 으로 동적페이지 불러오기
    // 📍파라미터를 불러오는 함수
    String deptnoStr=request.getParameter("deptno");  // return val // key에 있는 값을 가져올 수 있다. // queryString(파라미터) : 요청정보, request 에 존재하고 파라미터는 무조건 문자열(String)이다. => 쿼리를 문자열 변수에 담는다.
    // response.setContentType("text/html;charset=UTF-8"); // 📍text/html 페이지 반환 + 문자열 인코딩!
    response.setContentType("application/json;charset=UTF-8"); // 📍application/jason 페이지 반환 + 문자열 인코딩!
    PrintWriter out=response.getWriter(); // 응답으로 내보낼 출력 스트림
    // out.println("{\"name\":\"경민\"}"); // JSON 문자열표기

    // JDBC 통신
    String url="jdbc:mysql://localhost:3306/SCOTT"; // DB 서버 주소
    String user="root";   // 유저
    String pw="mysql123"; // 패스워드
    String driver="com.mysql.cj.jdbc.Driver"; // 동적로딩 역할 (mysql DB 사용을 위한 드라이버)
    // JDBC 객체(테이블/자료구조)
    Connection conn=null; // 접속하는 객체
    PreparedStatement pstmt=null;// 쿼리 // sqlInjection : 해킹을 막는다 // 물음표에 어떤값을 담을 수 있으므로 ? 물음표 뒤에 파라미터에 📍타입을 명확하게 넣으면 해킹을 막을 수 있다 // EmpDto 에서 파라미터들의 타입을 명확하게 해준다. 문자형, 정수형, 실수형 ...
    ResultSet rs=null; // 결과 // 테이블의 자료구조형
    List<EmpDto> empList=null;  // 리스트 타입의 변수선언 및 초기화 // EmpDto 타입만 들어올 수 있는 // == 테이블 // EmpDto 객체(DB)를 리스트로 저장한게 테이블
        try {
            Class.forName(driver); // db 드라이버 로드
            conn = DriverManager.getConnection(url,user,pw); // 접속하면 커넥션 객체 반환
            try{ // 🍋파라미터가 있을 때 실행되는 코드들!
                // 📍파라미터가 있을때 // 파라미터 문자열 -> 정수로 형변환 하기!
                int deptno=Integer.parseInt(deptnoStr);  // 파라미터 value 값 // deptnoStr : url 파라미터 deptno
                // 파라미터 문자열 "?10&&" => 파싱오류 NumberFormatException 이 발생. 오류가 발생안하면 아래 코드 실행
                String sql="SELECT * FROM EMP WHERE DEPTNO=?"; // sql에 db(데이터베이스) 테이블 담기
                pstmt=conn.prepareStatement(sql); // 파라미터의 타입을 명확하게 해서 하는것 // sql Injection 해킹을 막는다 // 파리미터를 주입하는 건 prepared // create 타입이 문자이면 ''을 붙이는거
                pstmt.setInt(1,deptno); // 첫번째 ? 물음표 뒤에 (파라미터) deptno를 넣겠다 는 뜻! => 파라미터에 DEPTNO가 없으면 오류가 발생한다.
            }catch(NumberFormatException e) { //🍋파라미터가 없을때 실행되는 코드
                // deptnoStr 파라미터가 없을때. 오류가 나타날때 => 전체 테이블 출력
                e.printStackTrace();
                // 물음표 파라미터가 없는 쿼리 / 있는 쿼리 // 쿼리를 실행하는거. 파라미터를 셋팅하는객체. 질의하는 객체(DB)
                pstmt=conn.prepareStatement("SELECT * FROM EMP"); // 오류가 발생하면 전체 테이블
            }
            rs=pstmt.executeQuery(); // 명령어를 처리하고 그 결과값을 가져온다 // 결과물이 들어온다. // ResultSet 객체의 값을 반환 // SELECT 문 실행
           // sql 에 쿼리를 실행한 결과값 ex) 부서번호 10인 테이블, 20인 테이블, 30인 테이블, 테이블전체
            empList=new ArrayList<>(); // 통신 성공하면 객체로 만든다 // 새로운 리스트 주소 할당 // 리스트 배열 참조
            while(rs.next()) { // rs.next() 메소드는 다음행이 없는 경우 false 리턴 // 테이블 한줄 불러오기
                // ex) rs.next() : 부서번호 10인 테이블 (deptno 파라미터 10). 테이블의 요소. row 객체 한줄한줄을 불러온다. 반복
                EmpDto emp=new EmpDto(); // rs(테이블 한줄) 가져온 내역을 emp에 셋팅
                emp.setEmpno(rs.getInt("empno")); //이름이 "empno"인 컬럼의 데이터 받아옴 // getInt : 테이블에서 가져온 데이터가 자바와 타입이 다르기 때문에(ex) varchar타입 -> String타입). resultSet 이라는 객체가 자바 타입과 유사하게 반환해주는 것.                                                         거?
                emp.setEname(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setSal(rs.getDouble("sal"));
                emp.setDeptno(rs.getInt("deptno"));
                // 부서번호 10인 총 3명 -> 각 1명씩의 row(가로,튜플,객체) 정보를 emp참조변수에 저장 하고
                // 1명을 전부 new EmpDto 객체에 담는다. 그 객체 하나를 empList(ArrayList)에 담는다.
                // ArrayList - 객체를 담는 배열. (array 배열은 기본형만을 담는다)
                empList.add(emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // out.println(empList); // EmpDto 의 toString 출력

        // 📍JSON 문자열로 만들어서 반환하세요 // JSON 은 오브젝트가 아니라 오브젝트를 문자열로 표현한 것
        // 📍[{"empno":7788,"ename":"scott","sal":1200.00}]
        // out.println("{\name\":\"경민\"}"); // 문자열 -> JSON => Jackson 라이브러리로 한방에 해결할 수 도 있다.
        // 👉위에 코드 객체 new EmpDto() 를 문자열 JSON 으로 변환하는 과정!!!
        //  person {name : "경민"} (객체)
        //  => person.json "{\"name\":\"\경민"}" (JSON 문자열로 된 문서)
        String empJson="[";
        if(empList!=null) { // empList 에 객체가 있는 경우만 // empList 객체가 비어있지 않으면 // 객체가 비어있으면 오류
            for(int i=0; i<empList.size(); i++) { // empList 변수는 전역에 선언
                EmpDto emp=empList.get(i); // 🍋 EmpDto 타입 emp 변수상자 새로 선언 // 변수범위 if문
                empJson+="{";
                empJson+="\"empno\":" + emp.getEmpno()+",";
                empJson+="\"ename\":\"" + emp.getEname()+"\",";
                empJson+="\"job\":\"" + emp.getJob()+"\",";
                empJson+="\"sal\":" + emp.getSal()+",";
                empJson+="\"depno\":" + emp.getDeptno()+"";
                empJson+="}";
                empJson+=(i!=empList.size()-1)?",":""; // 반복횟수가 empList의 마지막인덱스와 다른가? true 쉼표(,)추가 // false 쉼표없음
            }
        }
        empJson+="]";
        out.println(empJson); // 출력!!! // 동기식 출력 (페이지 전환)

        // escape 문자 : \"	큰따옴표 출력
        // print("\"hello world\"")
        // >>"hello world"
        // 쿼리 구문에서 특수 기능을 갖는 특수 문자("")를 검색하려면 특수 문자 앞에 백슬래시를 추가하여 특수 문자를 이스케이프 처리해야 합니다

    }
}
