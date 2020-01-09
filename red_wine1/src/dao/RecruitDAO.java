package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Recruit;
import util.DBHelper;
import util.PageBean;

//������ʿ
public class RecruitDAO {
	// ��ȡ����������ʿ����Ϣ
	public Map<String, Object> getAll(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Recruit> list = new ArrayList<Recruit>(); // ������ʿ����

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
			String sql = "select * from recruit limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, index);
			stmt.setInt(2, count);
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Recruit lists = new Recruit();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setJob_describe(rs.getString("job_describe"));
				lists.setRequirement(rs.getString("requirement"));
				list.add(lists); // ��һ�l������ʿ��Ϣ���뼯��
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
		String sql = "select count(*) from recruit"; // SQL���
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
	public void add(Recruit recruit) {
		String sql = "insert into recruit(title, job_describe, requirement) values(?,?,?);"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, recruit.getTitle());
			pstmt.setString(2, recruit.getJob_describe());
			pstmt.setString(3, recruit.getRequirement());
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
	public void delete(Recruit recruit) {
		String sql = "delete from recruit where id=?;"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setInt(1, recruit.getId());
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

	// ��ȡ����������ʿ��Ϣ
	public Recruit getone(Recruit recruit) {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		try {
			String sql = "select * from recruit where id=? ;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, recruit.getId());
			rs = stmt.executeQuery(); // ������ݼ�

			if (rs.next()) {
				Recruit lists = new Recruit();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setJob_describe(rs.getString("job_describe"));
				lists.setRequirement(rs.getString("requirement"));
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

	// ��ѯ������ʿ����Ϣ
	public Map<String, Object> search(PageBean pb, Recruit recruit) throws Exception {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Recruit> list = new ArrayList<Recruit>(); // ������ʿ����

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
			String sql = "select * from recruit where title like concat('%',?,'%') limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setString(1, recruit.getTitle());
			stmt.setInt(2, index);
			stmt.setInt(3, count);
			rs = stmt.executeQuery(); // ������ݼ�

			while (rs.next()) {
				Recruit lists = new Recruit();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setJob_describe(rs.getString("job_describe"));
				lists.setRequirement(rs.getString("requirement"));
				list.add(lists); // ��һ��������ʿ��Ϣ���뼯��
			}
			Map<String,Object> map = new HashMap<String, Object>();
		    map.put("PageDate",pb);
			map.put("list",list);
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
	public void update(Recruit recruit) {
		String sql = "update recruit set title=?,job_describe=?,requirement=? where id=? ;"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, recruit.getTitle());
			pstmt.setString(2, recruit.getJob_describe());
			pstmt.setString(3, recruit.getRequirement());
			pstmt.setInt(4, recruit.getId());
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