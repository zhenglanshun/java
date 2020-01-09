package demoweb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	static Statement sta;
	static Connection con;
	/**
	 * ��ȡStatement����
	 * @return
	 */
	public static Statement getStatement(){
		
		
		try {
			//1����������
			Class.forName("com.mysql.cj.jdbc.Driver");
			//2����������
			String url="jdbc:mysql://192.168.0.16:3306/demo?serverTimezone=UTC";
			String username="root";
			String pwd="123456";
			con = DriverManager.getConnection(url, username, pwd);
			sta = con.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sta;
	}
	/**
	 * �ر���Դ
	 * @param res
	 */
	public static void close(ResultSet res) {
		try {
			res.close();
			sta.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �ر���Դ�ķ���
	 */
	public static void close() {
		try {
			sta.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
