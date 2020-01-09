package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Administrators;
import util.DBHelper;
import util.PageBean;

//����Ա
public class AdministratorsDAO {

	// ��ȡ���й���Ա����Ϣ
	public Map<String, Object> getAll(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		ArrayList<Administrators> list = new ArrayList<Administrators>(); // ����Ա����

		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.getTotalCount();
		pb.setTotalCount(totalCount);
		// �жϣ�Ԥ����ǰҳΪ��ҳ��ĩҳʱ�������һҳ����һҳ����
		if (pb.getCurrentPage() <= 0) {
			pb.setCurrentPage(1); // �ѵ�ǰҳ����Ϊ1
		} else if (pb.getCurrentPage() > pb.getTotalPage()) {
			pb.setCurrentPage(pb.getTotalPage()); // �ѵ�ǰҳ����Ϊ���ҳ��
		}

		// ��ȡ��ǰҳ�������ѯ����ʼ�С����ص�����
		int currentPage = pb.getCurrentPage(); // ��ǰҳ
		int index = (currentPage - 1) * pb.getPageCount(); // ��ѯ����ʼ��
		int count = pb.getPageCount(); // ��ѯ���ص�����

		try {
			String sql = "select * from administrators limit ?,? ;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, index);
			stmt.setInt(2, count);
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Administrators lists = new Administrators();
				lists.setId(rs.getInt("id"));
				lists.setName(rs.getString("name"));
				lists.setPassword(rs.getString("password"));
				list.add(lists); // ��һ������Ա��Ϣ���뼯��
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PageDate", pb);
			map.put("list", list);
			return map;
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

	// ��ȡ����
	public int getTotalCount() throws Exception {
		PreparedStatement pStatement = null; // ������
		ResultSet rSet = null; // ���ݼ�
		String sql = "select count(*) from administrators"; // SQL���
		int count = 0;
		try {
			pStatement = DBHelper.getConnection().prepareStatement(sql);
			rSet = pStatement.executeQuery();
			rSet.next();
			count = rSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ͷ����ݼ�����
			if (rSet != null) {
				try {
					rSet.close();
					rSet = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			// �ͷ�������
			if (pStatement != null) {
				try {
					pStatement.close();
					pStatement = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return count;
	}

	// ���
	public void add(Administrators administrators) {
		String sql = "insert into administrators(name,password) value(?,?);"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, administrators.getName());
			pstmt.setString(2, administrators.getPassword());
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
	public void delete(Administrators administrators) {
		String sql = "delete from administrators where id=?;"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setInt(1, administrators.getId());
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

	// ��ȡ��������Ա����Ϣ
	public Administrators getone(Administrators administrators) {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		try {
			String sql = "select * from administrators where id=? ;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, administrators.getId());
			rs = stmt.executeQuery(); // ������ݼ�

			if (rs.next()) {
				Administrators lists = new Administrators();
				lists.setId(rs.getInt("id"));
				lists.setName(rs.getString("name"));
				lists.setPassword(rs.getString("password"));
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

	// ��ѯ����Ա����Ϣ
	public Map<String, Object> search(PageBean pb, Administrators administrators) throws Exception {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		ArrayList<Administrators> list = new ArrayList<Administrators>(); // ����Ա����

		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.getTotalCount();
		pb.setTotalCount(totalCount);

		// �жϣ�Ԥ����ǰҳΪ��ҳ��ĩҳʱ�������һҳ����һҳ����
		if (pb.getCurrentPage() <= 0) {
			pb.setCurrentPage(1); // �ѵ�ǰҳ����Ϊ1
		} else if (pb.getCurrentPage() > pb.getTotalPage()) {
			pb.setCurrentPage(pb.getTotalPage()); // �ѵ�ǰҳ����Ϊ���ҳ��
		}
		// ��ȡ��ǰҳ�������ѯ����ʼ�С����ص�����
		int currentPage = pb.getCurrentPage(); // ��ǰҳ
		int index = (currentPage - 1) * pb.getPageCount(); // ��ѯ����ʼ��
		int count = pb.getPageCount(); // ��ѯ���ص�����
		try {
			String sql = "select * from administrators where name like concat('%',?,'%') limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setString(1, administrators.getName());
			stmt.setInt(2, index);
			stmt.setInt(3, count);
			rs = stmt.executeQuery(); // ������ݼ�

			while (rs.next()) {
				Administrators lists = new Administrators();
				lists.setId(rs.getInt("id"));
				lists.setName(rs.getString("name"));
				lists.setPassword(rs.getString("password"));
				list.add(lists); // ��һ������Ա���뼯��
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PageDate", pb);
			map.put("list", list);
			return map;
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
	public void update(Administrators administrators) {
		String sql = "update administrators set name=?,password=? where id=? ;"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, administrators.getName());
			pstmt.setString(2, administrators.getPassword());
			pstmt.setInt(3, administrators.getId());
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