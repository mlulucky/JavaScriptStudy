package com.acorn.javascriptstudy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/l14_dept_list.do")
public class L14DeptList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String user="root";
    String pw="mysql123";
    String driver="com.mysql.cj.jdbc.Driver";

    Connection conn=null;
    PreparedStatement pstmt=null;
    ResultSet rs=null;

    String deptListStr="";
    List<DeptDto> deptList=null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/scott",user,pw);
            pstmt=conn.prepareStatement("SELECT * FROM DEPT");
            rs=pstmt.executeQuery();

            deptList=new ArrayList<DeptDto>();

            while(rs.next()) {
                // dept 테이블
                DeptDto dept = new DeptDto();
                dept.setDeptno(rs.getInt("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
                deptList.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }
        resp.setContentType("text/html;charset=UTF-8;");
        PrintWriter out = resp.getWriter();

        // dept 테이블 => 화면출력
        deptListStr+="<table>";
        deptListStr+="<tr><th>부서번호</th><th>부서이름</th><th>지역</th></tr>";
        for(DeptDto dept : deptList) {
            deptListStr+="<tr>";
            deptListStr+="<td>"+dept.getDeptno()+"</td>";
            deptListStr+="<td>"+dept.getDname()+"</td>";
            deptListStr+="<td>"+dept.getLoc()+"</td>";
            deptListStr+="</tr>";
        }
        deptListStr+="</table>";
        out.println(deptListStr);



    }
}
