package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Classification;
import entity.Commodity;
import entity.Information;
import entity.Knowledge;
import entity.Recruit;
import util.DBHelper;
import util.PageBean;

/**
 * ǰ�˼���
 */
public class HomeDAO {

	// ��Ʒչʾ
	public Map<String, Object> getCommodity(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ��Ʒ������
		ResultSet rs = null; // ��Ʒ���ݼ�
		List<Commodity> list = new ArrayList<Commodity>(); // ��Ʒ����

		PreparedStatement cstmt = null; // ����������
		ResultSet crs = null; // �������ݼ�
		List<Classification> clist = new ArrayList<Classification>(); // ���༯��

		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.getTotalCount("commodity");
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
			// ��Ʒ
			String sql = "select * from commodity limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, index);
			stmt.setInt(2, count);
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Commodity lists = new Commodity();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setPrice(rs.getFloat("price"));
				lists.setPhoto(rs.getString("photo"));
				list.add(lists); // ��һ����Ʒ��Ϣ���뼯��
			}

			// ����
			String sqls = "select * from commodity_class"; // SQL���
			cstmt = DBHelper.getConnection().prepareStatement(sqls); // �������Ӷ���
			crs = cstmt.executeQuery(); // ������ݼ�
			while (crs.next()) {
				Classification lists = new Classification();
				lists.setId(crs.getInt("id"));
				lists.setTitle(crs.getString("title"));
				clist.add(lists); // ��һ��������Ϣ���뼯��
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PageDate", pb);
			map.put("list", list);
			map.put("clist", clist);
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
			if (crs != null) {
				try {
					crs.close();
					crs = null;
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
			if (cstmt != null) {
				try {
					cstmt.close();
					cstmt = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��Ʒ����
	 * 
	 * @param pb
	 *            ��ҳ����
	 * @param cid
	 *            ����ID
	 * @param title
	 *            ��Ʒ��
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> searchCommodity(PageBean pb, int cid, String title) throws Exception {
		PreparedStatement stmt = null; // ��Ʒ������
		ResultSet rs = null; // ��Ʒ���ݼ�
		List<Commodity> list = new ArrayList<Commodity>(); // ��Ʒ����

		PreparedStatement cstmt = null; // ����������
		ResultSet crs = null; // �������ݼ�
		List<Classification> clist = new ArrayList<Classification>(); // ���༯��
		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.searchTotalCount(cid, title);
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
			// ��Ʒ

			// �ж� ��������Ϊ����
			if (title == null || "".equals(title.trim())) {
				String sql = "select * from commodity where class_id=? limit ?,?;"; // SQL���
				stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
				stmt.setInt(1, cid);
				stmt.setInt(2, index);
				stmt.setInt(3, count);
			} else { // ��������Ϊ����������
				String sql = "select * from commodity where title like concat('%',?,'%') and class_id=? limit ?,?;"; // SQL���
				stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
				stmt.setString(1, title);
				stmt.setInt(2, cid);
				stmt.setInt(3, index);
				stmt.setInt(4, count);
			}
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Commodity lists = new Commodity();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setPrice(rs.getFloat("price"));
				lists.setPhoto(rs.getString("photo"));
				list.add(lists); // ��һ����Ʒ��Ϣ���뼯��
			}

			// ����
			String sqls = "select * from commodity_class"; // SQL���
			cstmt = DBHelper.getConnection().prepareStatement(sqls); // �������Ӷ���
			crs = cstmt.executeQuery(); // ������ݼ�
			while (crs.next()) {
				Classification lists = new Classification();
				lists.setId(crs.getInt("id"));
				lists.setTitle(crs.getString("title"));
				clist.add(lists); // ��һ��������Ϣ���뼯��
			}

			String url = "search_commodity&cid=" + cid + "&title=" + title;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PageDate", pb);
			map.put("list", list);
			map.put("clist", clist);
			map.put("cid", cid); // ���� �����жϷ�ҳ����ת
			map.put("url", url); // ������Ʒ�� ���ڷ�ҳ��ת
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
			if (crs != null) {
				try {
					crs.close();
					crs = null;
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
			if (cstmt != null) {
				try {
					cstmt.close();
					cstmt = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// ���֪ʶ
	public Map<String, Object> getKnowledge(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Knowledge> list = new ArrayList<Knowledge>(); // ���֪ʶ����

		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.getTotalCount("knowledge");
		// System.out.println(totalCount);
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

	// ��˾��Ѷ
	public Map<String, Object> getNews(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Information> list = new ArrayList<Information>(); // ��˾��Ѷ����

		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.getTotalCount("information");
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
			stmt.setInt(1, index);
			stmt.setInt(2, count);
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Information lists = new Information();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setDate(rs.getString("date"));
				list.add(lists); // ��һ����˾��Ӎ���뼯��
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

	/**
	 * ��˾��Ѷ ����
	 * @param id    ����ID
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public Information informationDetail(int id) throws SQLException, Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�

		String sql = "select * from Information where id=? ;";  // SQL���
		stmt = DBHelper.getConnection().prepareStatement(sql);  // �������Ӷ���
		stmt.setInt(1, id);
		rs = stmt.executeQuery(); // ������ݼ�
			
		try{
			if (rs.next()) {
				Information lists = new Information();
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
	
	/**
	 * ���֪ʶ ����
	 * @param id    ����ID
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public Knowledge knowledgeDetail(int id) throws SQLException, Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		
		String sql = "select * from knowledge where id=? ;";  // SQL���
		stmt = DBHelper.getConnection().prepareStatement(sql);  // �������Ӷ���
		stmt.setInt(1, id);
		rs = stmt.executeQuery(); // ������ݼ�
			
		try{
			if (rs.next()) {
				Knowledge lists = new Knowledge();
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

	/**
	 * ��Ʒ ����
	 * @param id    ����ID
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public Map<String, Object> commodityDetail(int id) throws SQLException, Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		
		PreparedStatement cstmt = null; // ����������
		ResultSet crs = null; // �������ݼ�
		List<Classification> clist = new ArrayList<Classification>(); // ���༯��
		Map<String, Object> map = new HashMap<String, Object>();
			
		try{
			// ����
			String sqls = "select * from commodity_class"; // SQL���
			cstmt = DBHelper.getConnection().prepareStatement(sqls); // �������Ӷ���
			crs = cstmt.executeQuery(); // ������ݼ�
			while (crs.next()) {
				Classification list = new Classification();
				list.setId(crs.getInt("id"));
				list.setTitle(crs.getString("title"));
				clist.add(list); // ��һ��������Ϣ���뼯��
				map.put("clist", clist);
			}
			// ��Ʒ
			String sql = "select * from commodity where id=? ;";  // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql);  // �������Ӷ���
			stmt.setInt(1, id);
			rs = stmt.executeQuery(); // ������ݼ�
			while(rs.next()) {
				Commodity lists = new Commodity();
				lists.setTitle(rs.getString("title"));
				lists.setPrice(rs.getFloat("price"));
				lists.setSpecifications(rs.getString("specifications"));
				lists.setPlace(rs.getString("place"));
				lists.setAlcohol(rs.getString("alcohol"));
				lists.setPhoto(rs.getString("photo"));
				lists.setContent(rs.getString("content"));
				map.put("list", lists);
			} 
			
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
	
	// ��ҳչʾ���¶�̬����˾��Ѷ��
	public ArrayList<Information> indexNews() {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		ArrayList<Information> list = new ArrayList<Information>(); // ��˾��Ѷ����

		try {
			String sql = "select * from information order by id desc limit 0,5 "; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Information lists = new Information();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				list.add(lists); 
			}
			return list;
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
	
	// ��ҳչʾ���֪ʶ
	public ArrayList<Knowledge> indexKnowledge() {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		ArrayList<Knowledge> list = new ArrayList<Knowledge>(); // ��˾��Ѷ����

		try {
			String sql = "select * from knowledge order by id desc limit 0,5 "; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Knowledge lists = new Knowledge();
				lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				list.add(lists); 
			}
			return list;
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
	
	// ������ʿ
	public ArrayList<Recruit> getRecruit() {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		ArrayList<Recruit> list = new ArrayList<Recruit>(); // ��˾��Ѷ����

		try {
			String sql = "select * from recruit "; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			rs = stmt.executeQuery(); // ������ݼ�
			while (rs.next()) {
				Recruit lists = new Recruit();
				// lists.setId(rs.getInt("id"));
				lists.setTitle(rs.getString("title"));
				lists.setJob_describe(rs.getString("job_describe"));
				lists.setRequirement(rs.getString("requirement"));
				list.add(lists); // ��һ����Ƹ��Ϣ���뼯��
			}
			return list;
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

	/**
	 * ��Ʒչʾ���� ��ȡ����
	 * 
	 * @param cid
	 *            ����ID
	 * @param title
	 *            ��Ʒ��
	 * @return
	 * @throws Exception
	 */
	public int searchTotalCount(int cid, String title) throws Exception {
		PreparedStatement pStatement = null; // ������
		ResultSet rSet = null; // ���ݼ�
		int count = 0;
		try {
			// �ж� ��������Ϊ����
			if (title == null || "".equals(title.trim())) {
				String sql = "select * from commodity where class_id=? "; // SQL���
				pStatement = DBHelper.getConnection().prepareStatement(sql);
				pStatement.setInt(1, cid);
			} else { // ��������Ϊ����������
				String sql = "select * from commodity where title like concat('%',?,'%') and class_id=? "; // SQL���
				pStatement = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
				pStatement.setString(1, title);
				pStatement.setInt(2, cid);
			}
			rSet = pStatement.executeQuery();
			rSet.last(); // �Ƶ����һ��
			count = rSet.getRow(); // �õ���ǰ�кţ�Ҳ���Ǽ�¼��
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

	// ��ȡ����
	public int getTotalCount(String table) throws Exception {
		PreparedStatement pStatement = null; // ������
		ResultSet rSet = null; // ���ݼ�
		String sql = "select count(*) from " + table; // SQL���
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
}