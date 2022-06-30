package com.sist.dao;
import java.util.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmpDAO dao=new EmpDAO();
		Scanner scan=new Scanner(System.in);
		System.out.print("이름 입력 : ");
		String ename=scan.next();
		List<EmpVO> list=dao.empListData2(ename.toUpperCase());
		
		for(EmpVO vo:list)
		{
			System.out.println(vo.getEmpno()+" "
					+vo.getEname()+" "
					+vo.getJob()+" "
					+vo.getHiredate().toString()+" "
					+vo.getSal());
		}
	}

}
