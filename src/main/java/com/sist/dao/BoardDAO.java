package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sist.vo.BoardVO;

public class BoardDAO {
	
	public int deleteBoard(int no, String pwd) {
		int re = -1;
		String sql = "delete board where no=? and pwd=?";
		try {
			Context context
				= new InitialContext();
			DataSource ds=(DataSource) context.lookup("java:/comp/env/sist");
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, pwd);
			re = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return re;
	}
	
	
	
	//게시물 번호를 매개변수로 전달받아 해당 게시물을 조회하여 
	//BoardVO로 반환하는 메소드를 정의 해 봅니다.
	public BoardVO findByNo(int no) {
		BoardVO b = null;
		String sql = "select * from board where no=?";
		try {
			Context context = 
					new InitialContext();
			DataSource ds= (DataSource)context.lookup("java:/comp/env/sist");
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				b = new BoardVO();
				b.setNo(rs.getInt(1));
				b.setTitle(rs.getString(2));
				b.setWriter(rs.getString(3));
				b.setPwd(rs.getString(4));
				b.setContent(rs.getString(5));
				b.setRegdate(rs.getDate(6));
				b.setHit(rs.getInt(7));
				b.setFname(rs.getString(8));
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return b;
	}
	
	
	
	
	//모든 게시물목록을 반환하는 메소드를 만드시오.
	public ArrayList<BoardVO> findAll(){
		System.out.println("2. 모델동작함.");
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		String sql = "select * from board";
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:/comp/env/sist");
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				BoardVO b = new BoardVO();
				b.setNo(rs.getInt(1));
				b.setTitle(rs.getString(2));
				b.setWriter(rs.getString(3));
				b.setPwd(rs.getString(4));
				b.setContent(rs.getString(5));
				b.setRegdate(rs.getDate(6));
				b.setHit(rs.getInt(7));
				b.setFname(rs.getString(8));	
				list.add(b);
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return list;
	}
	
	
	
	
	
	
	
	public int insertBoard(BoardVO b) {
		int re = -1;
		try {
			String sql = "insert into board values(seq_board.nextval,?,?,?,?,sysdate,0,?)";
			Context context = new InitialContext();
			DataSource ds=(DataSource) context.lookup("java:/comp/env/sist");
			Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getWriter());
			pstmt.setString(3, b.getPwd());
			pstmt.setString(4, b.getContent());
			pstmt.setString(5, b.getFname());
			re = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		
		return re;
	}
}






