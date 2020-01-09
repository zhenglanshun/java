package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Information;
import util.DBHelper;
import util.PageBean;

//��˾��Ѷ
public class InformationDAO {
	// ��ȡ���й�˾��Ѷ�����Ϣ
	public Map<String, Object> getAll(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Information> list = new ArrayList<Information>(); // ��˾��Ѷ����
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
			String sql = "select * from information limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, index);  //����SQL����һ��������ʼƫ����
			stmt.setInt(2, count);  //����SQL���ڶ����������ؼ�¼�������Ŀ
			rs = stmt.executeQuery(); //��ù�˾��Ѷ���ݼ�
			while (rs.next()) { //ѭ����˾��Ѷ���ݼ�
				Information lists = new Information(); //����һ��Information����
				lists.setId(rs.getInt("id")); //����˾��ѶID��ֵ����lists
				lists.setTitle(rs.getString("title")); //����˾��Ѷ�����ֵ����lists
				lists.setAuthor(rs.getString("author")); //����˾��Ѷ���ߵ�ֵ����lists
				lists.setContent(rs.getString("content")); //����˾��Ѷ���ݵ�ֵ����lists
				lists.setDate(rs.getString("date")); //����˾��Ѷ����ʱ���ֵ����lists
				list.add(lists); // ��һ����˾��Ѷ���뼯��
			}
			//����һ��hashMap��key��String���ͣ�value��Object���ͣ��������ͣ�
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PageDate", pb); //����ҳ����pb��ӵ�map������
			map.put("list", list); //����˾��Ѷ���϶���list��ӵ�map������
			return map;	//����map����
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
		String sql = "select count(*) from information"; // SQL���
		int count = 0;  //����
		try {
			pStatement = DBHelper.getConnection().prepareStatement(sql); //�������Ӷ���
			rSet = pStatement.executeQuery(); //��ù�˾��Ѷ���ݼ�
			rSet.next();
			count = rSet.getInt(1);  //��������ֵ��������count
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
		return count;  //��������
	}

	// ���
	public void add(Information information) {
		String sql = "insert into information(title,author,content,date) values(?,?,?,?);"; // ����SQL���
		PreparedStatement pstmt = null;  // ������
		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setString(1, information.getTitle());	//����SQL���ĵ�һ�����������ֵ
			pstmt.setString(2, information.getAuthor()); //����SQL���ĵڶ����������ߵ�ֵ
			pstmt.setString(3, information.getContent()); //����SQL���ĵ������������ݵ�ֵ
			pstmt.setString(4, information.getDate()); //����SQL���ĵ��ĸ���������ʱ���ֵ
			pstmt.execute(); //ִ��SQL���
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pstmt.close(); //�ͷ�������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ɾ��
	public void delete(Information information) {
		String sql = "delete from information where id=?;"; // ����SQL���
		PreparedStatement pstmt = null;	//������
		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setInt(1, information.getId()); //����SQL���Ĳ���ID��ֵ
			pstmt.execute();	//ִ��SQL���
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pstmt.close();  //�ͷ�������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ��ȡ������˾��Ѷ����Ϣ
	public Information getone(Information Information) {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		try {
			String sql = "select * from Information where id=? ;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, Information.getId()); //����SQL���Ĳ���ID��ֵ
			rs = stmt.executeQuery(); //������ݼ�
			if (rs.next()) {
				Information lists = new Information(); //����һ��Information����
				lists.setId(rs.getInt("id")); //����˾��ѶID��ֵ����lists
				lists.setTitle(rs.getString("title")); //����˾��Ѷ�����ֵ����lists
				lists.setAuthor(rs.getString("author")); //����˾��Ѷ���ߵ�ֵ����lists
				lists.setContent(rs.getString("content")); //����˾��Ѷ���ݵ�ֵ����lists
				lists.setDate(rs.getString("date")); //����˾��Ѷ����ʱ���ֵ����lists
				return lists;  //����lists����
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

	// ��ѯ��˾��Ѷ����Ϣ
	public Map<String, Object> search(PageBean pb, Information Information) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		ArrayList<Information> list = new ArrayList<Information>(); // ��˾��Ѷ����

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
			String sql = "select * from Information where title like concat('%',?,'%') limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setString(1, Information.getTitle()); //����SQL����һ�����������ֵ
			stmt.setInt(1, index);  //����SQL����һ��������ʼƫ����
			stmt.setInt(2, count);  //����SQL���ڶ����������ؼ�¼�������Ŀ
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Information lists = new Information();  //����һ��Information����
				lists.setId(rs.getInt("id"));		//����˾��ѶID��ֵ����lists
				lists.setTitle(rs.getString("title"));	//����˾��Ѷ�����ֵ����lists
				lists.setAuthor(rs.getString("author"));	//����˾��Ѷ���ߵ�ֵ����lists
				lists.setContent(rs.getString("content"));	//����˾��Ѷ���ݵ�ֵ����lists
				lists.setDate(rs.getString("date"));	//����˾��Ѷ����ʱ���ֵ����lists
				list.add(lists); // ��һ����˾��Ѷ���뼯��
			}
			//����һ��hashMap��key��String���ͣ�value��Object���ͣ��������ͣ�
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PageDate", pb); //����ҳ����pb��ӵ�map������
			map.put("list", list); //����˾��Ѷ���϶���list��ӵ�map������
			return map;	//����map����
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
	public void update(Information Information) {
		String sql = "update Information set title=?,author=?,content=?,date=? where id=? ;"; // ����SQL���
		PreparedStatement pstmt = null; // ������
		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); //�������Ӷ���
			pstmt.setString(1, Information.getTitle()); //����SQL���ĵ�һ�����������ֵ
			pstmt.setString(2, Information.getAuthor()); //����SQL���ĵڶ����������ߵ�ֵ
			pstmt.setString(3, Information.getContent()); //����SQL���ĵ������������ݵ�ֵ
			pstmt.setString(4, Information.getDate()); //����SQL���ĵ��ĸ���������ʱ���ֵ
			pstmt.setInt(5, Information.getId()); //����SQL���ĵ��������ID��ֵ
			pstmt.execute(); //ִ��SQL���
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pstmt.close();  //�ͷ�������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}