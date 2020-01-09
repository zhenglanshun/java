package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Knowledge;
import util.DBHelper;
import util.PageBean;

//���֪ʶ
public class KnowledgeDAO {
	// ��ȡ���к��֪ʶ����Ϣ
	public Map<String, Object> getAll(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Knowledge> list = new ArrayList<Knowledge>(); // ���֪ʶ����

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
			String sql = "select * from knowledge limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, index);
			stmt.setInt(2, count);
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Knowledge lists = new Knowledge();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setAuthor(rs.getString("author"));
				lists.setContent(rs.getString("content"));
				lists.setDate(rs.getString("date"));
				list.add(lists); // ��һ�����֪ʶ���뼯��
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
		String sql = "select count(*) from knowledge"; // SQL���
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
	public void add(Knowledge knowledge) {
		String sql = "insert into knowledge(title,author,content,date) values(?,?,?,?);"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, knowledge.getTitle());
			pstmt.setString(2, knowledge.getAuthor());
			pstmt.setString(3, knowledge.getContent());
			pstmt.setString(4, knowledge.getDate());
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
	public void delete(Knowledge knowledge) {
		String sql = "delete from knowledge where id=?;"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setInt(1, knowledge.getId());
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

	// ��ȡ�������֪ʶ����Ϣ
	public Knowledge getone(Knowledge knowledge) {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		try {
			String sql = "select * from knowledge where id=? "; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, knowledge.getId());
			rs = stmt.executeQuery(); // ������ݼ�

			if (rs.next()) {
				Knowledge lists = new Knowledge();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setAuthor(rs.getString("author"));
				lists.setContent(rs.getString("content"));
				lists.setDate(rs.getString("date"));
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

	// ��ѯ���֪ʶ����Ϣ
	public Map<String, Object> search(PageBean pb, Knowledge knowledge) throws Exception {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Knowledge> list = new ArrayList<Knowledge>(); // ���֪ʶ����

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
			String sql = "select * from knowledge where title like concat('%',?,'%') limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setString(1, knowledge.getTitle());
			stmt.setInt(2, index);
			stmt.setInt(3, count);
			rs = stmt.executeQuery(); // ������ݼ�

			while (rs.next()) {
				Knowledge lists = new Knowledge();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setAuthor(rs.getString("author"));
				lists.setContent(rs.getString("content"));
				lists.setDate(rs.getString("date"));
				list.add(lists); // ��һ�����֪ʶ���뼯��
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
	public void update(Knowledge knowledge) {
		String sql = "update knowledge set title=?,author=?,content=?,date=? where id=? ;"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, knowledge.getTitle());
			pstmt.setString(2, knowledge.getAuthor());
			pstmt.setString(3, knowledge.getContent());
			pstmt.setString(4, knowledge.getDate());
			pstmt.setInt(5, knowledge.getId());
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