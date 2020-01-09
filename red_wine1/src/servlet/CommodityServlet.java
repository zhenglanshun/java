package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CommodityDAO;
import entity.Commodity;
import util.PageBean;

public class CommodityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// ��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public CommodityServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ���ñ���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// ��ȡ����������
		String method = request.getParameter("method");

		// �ж�
		if ("list".equals(method)) {
			// �б�չʾ
			list(request, response);
		}

		else if ("search".equals(method)) {
			// ����
			search(request, response);
		}

		else if ("delete".equals(method)) {
			// ɾ��
			delete(request, response);
		}

		else if ("Jumpup".equals(method)) {
			// ��ת����ҳ
			Jumpup(request, response);
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// b.��Ʒչʾ
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//1. ��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			//�ж� 
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1";       //��һ�η��ʣ����õ�ǰҳΪ1��
			}
			//ת��
			int currentPage = Integer.parseInt(currPage);
			
			//2. ����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			
			CommodityDAO dao = new CommodityDAO();
			// ����dao�����getAll�������õ����
			Map<String, Object> result = dao.getAll(pageBean);
			// ����
			request.setAttribute("result", result); // ���
			// ��ת
			uri = "/sys/goods/goods.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// c.ɾ����Ʒ
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			int CommodityId = Integer.parseInt(request.getParameter("id"));

			Commodity cc = new Commodity();
			cc.setId(CommodityId);

			// 2. ����daoִ��ɾ��
			CommodityDAO dao = new CommodityDAO();
			dao.delete(cc);

			// 3. ��ת
			uri = "/servlet/CommodityServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// d.������Ʒ
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			//��ȡ���ҵ���Ʒ����
			String CommodityTitle = request.getParameter("title");
			//�ж� 
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1";       //��һ�η��ʣ����õ�ǰҳΪ1��
			}
			//ת��
			int currentPage = Integer.parseInt(currPage);
			
			//2����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			
			Commodity title = new Commodity();
			title.setTitle(CommodityTitle);
			// ����dao�����search�������õ����
			CommodityDAO dao = new CommodityDAO();
			Map<String, Object> result = dao.search(pageBean, title);
			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/sys/goods/goods.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// e.��Ʒ������תչʾ
	public void Jumpup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// 1. ��ȡ�������ݷ�װ
			int CommodityId = Integer.parseInt(request.getParameter("id"));

			Commodity cc = new Commodity();
			cc.setId(CommodityId);

			// 2. ����daoִ�����
			CommodityDAO dao = new CommodityDAO();

			// ����dao�����getone�������õ����
			Commodity result = dao.getone(cc);

			// ����
			request.setAttribute("listClass", result);
			// ��ת
			uri = "/sys/goods/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}


	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}