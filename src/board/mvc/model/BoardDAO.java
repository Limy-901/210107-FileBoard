package board.mvc.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import mvc.domain.FileBoard;
import static board.mvc.model.BoardSQL.*;

class BoardDAO {
	// DAO는 노출되면 안되기때문에 default 접근제한자 사용.
	private DataSource ds;
	BoardDAO(){
		try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		}catch(NamingException ne) {}
	}
	ArrayList<FileBoard> list(){
		ArrayList<FileBoard> list = new ArrayList<FileBoard>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;		
		String sql = LIST;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				long seq = rs.getLong("SEQ");
				String writer = rs.getString("WRITER");
				String email = rs.getString("EMAIL");
				String subject = rs.getString("SUBJECT");
				String content = rs.getString("CONTENT");
				Date rdate = rs.getDate("RDATE");
				String fname = rs.getString("FNAME");
				String ofname = rs.getString("OFNAME");
				long fsize = rs.getLong("FSIZE");
				
				FileBoard b = new FileBoard(seq,writer,email,subject,content,rdate,fname,ofname,fsize);
				list.add(b);
			}
		}catch(SQLException se) {
			System.out.println("BoardDAO list() se: " + se);
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			}catch(SQLException se) {}
		}
		
		return list;
	}
	ArrayList<FileBoard> list(int cp, int ps){
		ArrayList<FileBoard> list = new ArrayList<FileBoard>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		String sql = LIST;
		
		int startRow = (cp-1) * ps; //0, 3, 6
		int endRow = cp * ps;       //3, 6, 9
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				long seq = rs.getLong("SEQ");
				String writer = rs.getString("WRITER");
				String email = rs.getString("EMAIL");
				String subject = rs.getString("SUBJECT");
				String content = rs.getString("CONTENT");
				Date rdate = rs.getDate("RDATE");
				String fname = rs.getString("FNAME");
				String ofname = rs.getString("OFNAME");
				long fsize = rs.getLong("FSIZE");
				
				FileBoard b = new FileBoard(seq,writer,email,subject,content,rdate,fname,ofname,fsize);
				list.add(b);
			}
		}catch(SQLException se) {
			System.out.println("BoardDAO list(cp, ps) se: " + se);
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se) {}
		}
		return list;
	}
	long getTotalCount() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;		
		String sql = COUNT;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				long count = rs.getLong(1);
				return count;
			}else {
				return -1L;
			}
		}catch(SQLException se) {
			System.out.println("getTotalCount() se: " + se);
			return -1L;
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			}catch(SQLException se) {}
		}
	}
	void insert(FileBoard board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = INSERT;
		if(board.getWriter() != null) {
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, board.getWriter());
				pstmt.setString(2, board.getEmail());
				pstmt.setString(3, board.getSubject());
				pstmt.setString(4, board.getContent());
				pstmt.setString(5, board.getFname());
				pstmt.setString(6, board.getOfname());
				pstmt.setLong(7, board.getFsize());
				pstmt.executeUpdate();
			}catch(SQLException se) {
				System.out.println("BoardDAO insert() se: "+se);
			}finally {
				try {
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				}catch(SQLException se) {}
			}
		}
	}
	FileBoard getBoard(long seq) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = CONTENT;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, seq);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String writer = rs.getString("WRITER");
				String email = rs.getString("EMAIL");
				String subject = rs.getString("SUBJECT");
				String content = rs.getString("CONTENT");
				Date rdate = rs.getDate("RDATE");
				String fname = rs.getString("FNAME");
				String ofname = rs.getString("OFNAME");
				Long fsize = rs.getLong("FSIZE");
				System.out.println("fname:"+fname);
				
				FileBoard b = new FileBoard(seq,writer,email,subject,content,rdate,fname,ofname,fsize);
				return b;
			}else {
				return null;
			}
		}catch(SQLException se) {
			System.out.println("BoardDAO getBoard() se: " + se);
			return null;
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se) {}
		}
	}
	void update(FileBoard board) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println("update 들어왔음."+board.getFname());
			con = ds.getConnection();
			if(board.getFname()==null) {
				String sql = UPDATE;
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getContent());
				pstmt.setLong(3, board.getSeq());
				pstmt.executeUpdate();
			}else {
				System.out.println("");
				String sql = FILE_UPDATE;
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getContent());
				pstmt.setString(3, board.getFname());
				pstmt.setString(4, board.getOfname());
				pstmt.setLong(5, board.getFsize());
				pstmt.setLong(6, board.getSeq());
				pstmt.executeUpdate();
			}
		}catch(SQLException se) {
			System.out.println("BoardDAO update() se: " + se);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se) {}
		}
	}
	void fileDbDel(long seq) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = FILEDB_DEL;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, seq);
			pstmt.executeUpdate();
		}catch(SQLException se) {
			System.out.println("BoardDAO filedel() se: "+se);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se) {}
		}
	}
	void del(long seq) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = DEL;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, seq);
			pstmt.executeUpdate();
		}catch(SQLException se) {
			System.out.println("BoardDAO del() se: "+se);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException se) {}
		}
	}
}
