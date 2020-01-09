package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;
import util.DBHelper;

//��˾���
public class ProfileDAO {

	// ��ȡ��˾�����Ϣ
	public Profile getAll() {
		Connection conn = null; // ���Ӷ���
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		try {
			conn = DBHelper.getConnection(); // ������Ӷ���
			String sql = "select * from company_profile;"; // SQL���
			stmt = conn.prepareStatement(sql); // �������Ӷ���
			rs = stmt.executeQuery(); // ������ݼ�
			if (rs.next()) {
				Profile lists = new Profile();
				lists.setId(rs.getInt("id"));
				lists.setContent(rs.getString("content"));
				return lists;
			} else {
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			// �ͷ����ݼ�����
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			// �ͷ�������
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// ���
	public void add(Profile profile) {
		String sql = "insert into company_profile(content) values(?);"; // ����sql���
		PreparedStatement pstmt = null;
		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, profile.getContent());
			pstmt.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ɾ��
	public void delete(Profile profile) {
		String sql = "delete from company_profile where id=?;"; // ����sql���
		PreparedStatement pstmt = null;
		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setInt(1, profile.getId());
			pstmt.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// ��ȡ������Ϣ
	public Profile getone(Profile profile) {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		try {
			String sql = "select * from company_profile where id=? ;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, profile.getId());
			rs = stmt.executeQuery(); // ������ݼ�
			if (rs.next()) {
				Profile lists = new Profile();
				lists.setId(rs.getInt("id"));
				lists.setContent(rs.getString("content"));
				return lists;
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// �ͷ����ݼ�����
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			// �ͷ�������
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return null;
	}

	// ����
	public void update(Profile profile) {
		String sql = "update company_profile set content=? where id=? ;";  // ����sql���
		PreparedStatement pstmt = null;
		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, profile.getContent());
			pstmt.setInt(2, profile.getId());
			pstmt.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}