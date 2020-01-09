package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.Commodity;
import util.DBHelper;
import util.PageBean;

//��Ʒ
public class CommodityDAO {

	// ��ȡ������Ʒ����Ϣ
	public Map<String, Object> getAll(PageBean pb) throws Exception {
		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Commodity> list = new ArrayList<Commodity>(); // ��Ʒ����
		
		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.getTotalCount();
		pb.setTotalCount(totalCount);

		//�жϣ�Ԥ����ǰҳΪ��ҳ��ĩҳʱ�������һҳ����һҳ����
		if (pb.getCurrentPage() <= 0) {
			pb.setCurrentPage(1); 						//�ѵ�ǰҳ����Ϊ1
		} else if (pb.getCurrentPage() > pb.getTotalPage()){
			pb.setCurrentPage(pb.getTotalPage());       //�ѵ�ǰҳ����Ϊ���ҳ��
		}
		
		// ��ȡ��ǰҳ�������ѯ����ʼ�С����ص�����
		int currentPage = pb.getCurrentPage(); // ��ǰҳ
		int index = (currentPage - 1) * pb.getPageCount(); // ��ѯ����ʼ��
		int count = pb.getPageCount(); // ��ѯ���ص�����
		
		try {
			String sql = "select * from commodity limit ?,?;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, index);
			stmt.setInt(2, count);
			rs = stmt.executeQuery(); // ������ݼ�

			while (rs.next()) {
				Commodity lists = new Commodity();
				lists.setId(rs.getInt("id"));
				lists.setClass_id(rs.getInt("class_id"));
				lists.setTitle(rs.getString("title"));
				lists.setPrice(rs.getFloat("price"));
				lists.setSpecifications(rs.getString("specifications"));
				lists.setPlace(rs.getString("place"));
				lists.setAlcohol(rs.getString("alcohol"));
				lists.setPhoto(rs.getString("photo"));
				lists.setContent(rs.getString("content"));
				list.add(lists); // ��һ����Ʒ��Ϣ���뼯��
			}
			
			Map<String,Object> map = new HashMap<String, Object>();
		    map.put("PageDate",pb);
			map.put("list",list);
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
		String sql = "select count(*) from commodity"; // SQL���
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
	public void add(Commodity commodity) {
		String sql = "insert into commodity(class_id,title,price,specifications,place,alcohol,photo,content) values(?,?,?,?,?,?,?,?);"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setInt(1, commodity.getClass_id());
			pstmt.setString(2, commodity.getTitle());
			pstmt.setFloat(3, commodity.getPrice());
			pstmt.setString(4, commodity.getSpecifications());
			pstmt.setString(5, commodity.getPlace());
			pstmt.setString(6, commodity.getAlcohol());
			pstmt.setString(7, commodity.getPhoto());
			pstmt.setString(8, commodity.getContent());
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
	public void delete(Commodity commodity) {
		String sql = "delete from commodity where id=?;"; // ����sql���
		PreparedStatement pstmt = null;

		try {
			pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			pstmt.setInt(1, commodity.getId());
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

	// ��ȡ������Ʒ����Ϣ
	public Commodity getone(Commodity commodity) {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		try {
			String sql = "select * from commodity where id=? ;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setInt(1, commodity.getId());
			rs = stmt.executeQuery(); // ������ݼ�

			if (rs.next()) {
				Commodity lists = new Commodity();
				lists.setId(rs.getInt("id"));
				lists.setClass_id(rs.getInt("class_id"));
				lists.setTitle(rs.getString("title"));
				lists.setPrice(rs.getFloat("price"));
				lists.setSpecifications(rs.getString("specifications"));
				lists.setPlace(rs.getString("place"));
				lists.setAlcohol(rs.getString("alcohol"));
				lists.setPhoto(rs.getString("photo"));
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

	// ��ѯ��Ʒ����Ϣ
	public Map<String, Object> search(PageBean pb,Commodity commodity) throws Exception {

		PreparedStatement stmt = null; // ������
		ResultSet rs = null; // ���ݼ�
		List<Commodity> list = new ArrayList<Commodity>(); // ��Ʒ����
		
		// ��ѯ�ܼ�¼�������õ�pb������
		int totalCount = this.getTotalCount();
		pb.setTotalCount(totalCount);

		//�жϣ�Ԥ����ǰҳΪ��ҳ��ĩҳʱ�������һҳ����һҳ����
		if (pb.getCurrentPage() <= 0) {
			pb.setCurrentPage(1); 						//�ѵ�ǰҳ����Ϊ1
		} else if (pb.getCurrentPage() > pb.getTotalPage()){
			pb.setCurrentPage(pb.getTotalPage());       //�ѵ�ǰҳ����Ϊ���ҳ��
		}
		// ��ȡ��ǰҳ�������ѯ����ʼ�С����ص�����
		int currentPage = pb.getCurrentPage(); // ��ǰҳ
		int index = (currentPage - 1) * pb.getPageCount(); // ��ѯ����ʼ��
		int count = pb.getPageCount(); // ��ѯ���ص�����
		try {
			String sql = "select * from commodity where title like concat('%',?,'%') limit ?,? ;"; // SQL���
			stmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
			stmt.setString(1, commodity.getTitle());
			stmt.setInt(2, index);
			stmt.setInt(3, count);
			rs = stmt.executeQuery(); // ������ݼ�
			
			while (rs.next()) {
				Commodity lists = new Commodity();
				lists.setId(rs.getInt("id"));
				lists.setClass_id(rs.getInt("class_id"));
				lists.setTitle(rs.getString("title"));
				lists.setPrice(rs.getFloat("price"));
				lists.setSpecifications(rs.getString("specifications"));
				lists.setPlace(rs.getString("place"));
				lists.setAlcohol(rs.getString("alcohol"));
				lists.setPhoto(rs.getString("photo"));
				lists.setContent(rs.getString("content"));
				list.add(lists); // ��һ����Ʒ��Ϣ���뼯��
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
	public void update(Commodity commodity) {
		
		PreparedStatement pstmt = null;
		try {
			String photo = commodity.getPhoto();
			if (photo == null || "".equals(photo.trim())) {
				String sql = "update commodity set class_id=?,title=?,price=?,specifications=?,place=?,alcohol=?,content=? where id=? ;"; // ����sql���
				pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
				pstmt.setInt(1, commodity.getClass_id());
				pstmt.setString(2, commodity.getTitle());
				pstmt.setFloat(3, commodity.getPrice());
				pstmt.setString(4, commodity.getSpecifications());
				pstmt.setString(5, commodity.getPlace());
				pstmt.setString(6, commodity.getAlcohol());
				pstmt.setString(7, commodity.getContent());
				pstmt.setInt(8, commodity.getId());
				pstmt.execute();
			} else {
				String sql = "update commodity set class_id=?,title=?,price=?,specifications=?,place=?,alcohol=?,photo=?,content=? where id=? ;"; // ����sql���
				pstmt = DBHelper.getConnection().prepareStatement(sql); // �������Ӷ���
				pstmt.setInt(1, commodity.getClass_id());
				pstmt.setString(2, commodity.getTitle());
				pstmt.setFloat(3, commodity.getPrice());
				pstmt.setString(4, commodity.getSpecifications());
				pstmt.setString(5, commodity.getPlace());
				pstmt.setString(6, commodity.getAlcohol());
				pstmt.setString(7, photo);
				pstmt.setString(8, commodity.getContent());
				pstmt.setInt(9, commodity.getId());
				pstmt.execute();
			}
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
