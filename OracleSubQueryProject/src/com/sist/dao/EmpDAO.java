package com.sist.dao;
import java.util.*;
import java.sql.*;
public class EmpDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	public EmpDAO()
	{
		try
		{
			Class.forName("orcle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
		
	}
	
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	//기능 => 이름을 입력받아서 => 같은 부서에서 근무하는 사원들의 정보 출력
	//단일행 서브쿼리 (부서번호)
	public List <EmpVO> empListData(String ename)
	{
		List<EmpVO> list=
				new ArrayList<EmpVO>();
		try
		{
			getConnection();
			String sql="SELECT empno,ename,deptno,job FROM emp "
					+"WHERE deptno=(SELECT deptno "
					+"FROM emp WHERE ename=?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, ename);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setDeptno(rs.getInt(3));
				vo.setJob(rs.getString(4));
				list.add(vo);
				
			}
			rs.close();
		}catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	//다중행 서브쿼리
	public List<EmpVO> empListData2(String ename)
	{
		List<EmpVO> list=new ArrayList<EmpVO>();
		try
		{
			getConnection();
			String sql="SELECT empno,ename,job,hiredate,sal "
					+"FROM emp "
					+"WHERE job IN(SELECT job "
					+"             FROM emp "
					+"             WHERE deptno=("
					+"                       SELECT deptno "
					+"                       FROM emp "
					+"                       WHERE ename=?))";
			ps=conn.prepareStatement(sql);
			ps.setString(1, ename);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				EmpVO vo=new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
}
