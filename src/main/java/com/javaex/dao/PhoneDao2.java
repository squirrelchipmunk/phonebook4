package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao2 {

	@Autowired
	private DataSource dataSource;
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs= null;
	
	
	private void getConnection(){
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	private void close() {
		try {               
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public void personInsert(PersonVo vo) {

		try {
			getConnection();

			String query ="";
			query += " insert into person ";
			query += " values(seq_person_id.nextval, ?, ?, ?) " ;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getName());    
			pstmt.setString(2, vo.getHp());
			pstmt.setString(3, vo.getCompany());
			
			int count = pstmt.executeUpdate();  
			System.out.println("["+count + " 건이 등록되었습니다.]");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
	}

	public void personUpdate(PersonVo vo) {

		try {
			getConnection();

			String query ="";
			query += " update person ";
		    query += " set name = ?, ";
		    query += " 	   hp = ?, " ;
		    query += " 	   company = ? " ;
		    query += " where person_id = ? " ;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getName());    
			pstmt.setString(2, vo.getHp());
			pstmt.setString(3, vo.getCompany());
			pstmt.setInt(4, vo.getPersonId());
			
			int count = pstmt.executeUpdate();  
			System.out.println("["+count + " 건이 수정되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
	
		close();
	}
	

	public void personDelete(int personId) {

		try {
			getConnection();

			String query ="";
			query += " delete from person ";
		    query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);      
			
			int count = pstmt.executeUpdate();  
			System.out.println("["+count + " 건이 삭제되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		close();
	}

	public List<PersonVo> getPersonList() {
		return searchPerson("");
	}
	
	public List<PersonVo> searchPerson(String key) {
		List<PersonVo> personList = new ArrayList<>();
		
		try {
			getConnection();

			String query ="";
			query += " select person_id, "; // as 사용 가능
			query += " 	 	  name, ";
			query += " 		  hp, ";
			query += " 		  company ";
			query += " from person ";
			query += " where name like ? or ";
			query += " 		 hp like ? or ";
			query += " 		 company like ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+key+"%");
			pstmt.setString(2, "%"+key+"%");
			pstmt.setString(3, "%"+key+"%");
			rs = pstmt.executeQuery();  
			
			while(rs.next()) { //			  person_id       name               hp            company
				PersonVo vo = new PersonVo( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4) );
				personList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
			
		return personList;
	}
	
	public PersonVo getPerson(int index) {
		PersonVo vo = null;
		try {
			getConnection();

			String query ="";
			query += " select person_id, "; // as 사용 가능
			query += " 	 	  name, ";
			query += " 		  hp, ";
			query += " 		  company ";
			query += " from person ";
			query += " where person_id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();  
			
			rs.next(); //			  person_id       name               hp            company
			vo = new PersonVo( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4) );	

		} 
		catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		close();
			
		return vo;
	}

}
