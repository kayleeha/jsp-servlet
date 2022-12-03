package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.sun.glass.ui.Pixels.Format;

public class NoticeService {

	public int removeNoticeAll(int[] ids) {

		return 0;
	}
	public int pubNoticeAll(int[] oids, int[] cids) {

		List<String> oidsList = new ArrayList<>(); 
		for(int i=0; i<oids.length; i++)
			oidsList.add(String.valueOf(oids[i])); //문자열로 바꾸기 위해 valueOf

		List<String> cidsList = new ArrayList<>();
		for(int i=0; i<cids.length; i++)
			cidsList.add(String.valueOf(cids[i]));


		return pubNoticeAll(oidsList, cidsList);
	} //배열, 리스트 원하는 자료 형식 사용 할 수 있게 하기 위해서 두 개로 나눔

	public int pubNoticeAll(List<String> oids, List<String> cids) {

		String oidsCSV = String.join(",", oids);
		String cidsCSV = String.join(",", cids);

		return pubNoticeAll(oidsCSV, cidsCSV);
	} //배열, 리스트 원하는ㄴㄴ 자료 형식 사용 할 수 있게 하기 위해서 두 개로 나눔

	public int pubNoticeAll(String oidsCSV, String cidsCSV) {

		int result = 0;
		
		String sqlOpen =String.format("UPDATE NOTICE SET PUB=1 WHERE ID IN (%s)", oidsCSV);
		String sqlClose =String.format("UPDATE NOTICE SET PUB=0 WHERE ID IN (%s)", cidsCSV);
				
		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			Statement stOpen = con.createStatement();
			int resultOpen = stOpen.executeUpdate(sqlOpen); //executeUpdate는 insert, update, delete와 같은 query문 실행
			
			Statement stClose = con.createStatement();
			int resultClose = stClose.executeUpdate(sqlClose); //executeUpdate는 insert, update, delete와 같은 query문 실행


			stOpen.close();
			stClose.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

				
	} // 오버로드를 사용함으로써 자유롭게...

	public int insertNotice(Notice notice){
		int result = 0;


		String sql = "INSERT INTO NOTICE(TITLE, CONTENT, WRITER_ID, PUB, FILES) VALUES(?,?,?,?,?)";

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, notice.getTitle());
			st.setString(2, notice.getContent());
			st.setString(3, notice.getWriterId());
			st.setBoolean(4, notice.getPub());
			st.setString(5, notice.getFiles());

			result = st.executeUpdate(); //executeUpdate는 insert, update, delete와 같은 query문 실행


			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;


	}

	public int deleteNotice(int id){

		return 0;
	}
	public int updateNotice(Notice notice){

		return 0;
	}

	List<Notice> getNoticeNewestList(){

		return null;
	}


	public List<NoticeView> getNoticeList() {


		return getNoticeList("title", "", 1);

	}
	public List<NoticeView> getNoticeList(int page) {

		return getNoticeList("title", "", page);
	}
	public List<NoticeView> getNoticeList(String field, String query, int page) {

		List<NoticeView> list = new ArrayList<>();


		String sql = "SELECT * FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ " )"
				+ " WHERE NUM BETWEEN ? AND ?";   //view를 사용해서 목록을 만든다!! join 문을 쓰면 복잡해짐

		// 1, 11, 21, 31 -> an = 1+(page-1)*10
		// 10, 20, 30, 40 -> page*10

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,"%" + query + "%");
			st.setInt(2,1+(page-1)*10);
			st.setInt(3,page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");  // 변수가 model이 됨 //view로 넘거야 함(detail.jsp로 전달)
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT"); 
				String files = rs.getString("FILES"); 
				//String content = rs.getString("CONTENT"); //view에서 데이터 크기 문제로 content를 삭제했기 때문에 여기서도 삭제
				int cmtCount = rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");


				NoticeView notice = new NoticeView(
						id,
						title,
						writerId,
						regDate,
						hit,
						files,
						pub,
						//content
						cmtCount

						);

				list.add(notice);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return list;

	}

	public List<NoticeView> getNoticePubList(String field, String query, int page) {
		List<NoticeView> list = new ArrayList<>();


		String sql = "SELECT * FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ " )"
				+ " WHERE PUB=1 NUM BETWEEN ? AND ?";   //view를 사용해서 목록을 만든다!! join 문을 쓰면 복잡해짐
		//PUB=1(공개된 글 중에서 몇 개를 공지로 보여주겠다)

		// 1, 11, 21, 31 -> an = 1+(page-1)*10
		// 10, 20, 30, 40 -> page*10

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,"%" + query + "%");
			st.setInt(2,1+(page-1)*10);
			st.setInt(3,page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");  // 변수가 model이 됨 //view로 넘거야 함(detail.jsp로 전달)
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT"); 
				String files = rs.getString("FILES"); 
				//String content = rs.getString("CONTENT"); //view에서 데이터 크기 문제로 content를 삭제했기 때문에 여기서도 삭제
				int cmtCount = rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");


				NoticeView notice = new NoticeView(
						id,
						title,
						writerId,
						regDate,
						hit,
						files,
						pub,
						//content
						cmtCount

						);

				list.add(notice);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return list;
	}


	public int getNoticeCount() {

		return getNoticeCount("title", "");
	}
	public int getNoticeCount(String field, String query) {

		int count = 0;

		String sql = "SELECT COUNT(ID) COUNT FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N" +
				")";

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,"%" + query + "%");
			ResultSet rs = st.executeQuery();

			if(rs.next())
				count = rs.getInt("count");

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public Notice getNotice(int id) {

		Notice notice = null;

		String sql = "SELECT * FROM NOTICE WHERE ID=?";

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1,id);
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");  // 변수가 model이 됨 //view로 넘거야 함(detail.jsp로 전달)
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT"); 
				String files = rs.getString("FILES"); 
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");


				notice = new Notice(
						nid,
						title,
						writerId,
						regDate,
						hit,
						files,
						content,
						pub

						);


			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return notice;
	}
	public Notice getNextNotice(int id) {

		Notice notice = null;

		String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE ASC)"
				+ " WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=?)"
				+ " AND ROWNUM = 1)";

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1,id);
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");  // 변수가 model이 됨 //view로 넘거야 함(detail.jsp로 전달)
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT"); 
				String files = rs.getString("FILES"); 
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");


				notice = new Notice(
						nid,
						title,
						writerId,
						regDate,
						hit,
						files,
						content,
						pub

						);


			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return notice;

	}

	public Notice getPrevNotice(int id) {

		Notice notice = null;

		String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC)"
				+ " WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=?)"
				+ " AND ROWNUM = 1";

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1,id);
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");  // 변수가 model이 됨 //view로 넘거야 함(detail.jsp로 전달)
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT"); 
				String files = rs.getString("FILES"); 
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");


				notice = new Notice(
						nid,
						title,
						writerId,
						regDate,
						hit,
						files,
						content,
						pub

						);


			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notice;

	}

	public int deleteNoticeAll(int[] ids1) {

		int result = 0;

		String params = "";

		for(int i=0; i<ids1.length; i++) {
			params += ids1[i];
			if(i < ids1.length-1) //마지막 값 뒤에는 ';'가 붙지 않게 하기 위해서
				params += ",";
		}

		String sql = "DELETE NOTICE WHERE ID IN ("+params+")";

		String url = "jdbc:oracle:thin:@localhost:1521:XE";


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			Statement st = con.createStatement();
			result = st.executeUpdate(sql); //executeUpdate는 insert, update, delete와 같은 query문 실행


			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}





}
