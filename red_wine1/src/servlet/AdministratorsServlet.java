package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdministratorsDAO;
import entity.Administrators;
import util.PageBean;

public class AdministratorsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// ��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public AdministratorsServlet() {
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
		if ("add".equals(method)) {
			// ���
			add(request, response);
		}

		else if ("list".equals(method)) {
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

		else if ("update".equals(method)) {
			// ����
			update(request, response);
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

	// a.���
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			String administratorsName = request.getParameter("name");
			String administratorsPassword = request.getParameter("password");

			Administrators cc = new Administrators();
			cc.setName(administratorsName);
			cc.setPassword(administratorsPassword);

			// 2. ����daoִ�����
			AdministratorsDAO dao = new AdministratorsDAO();
			dao.add(cc);

			// 3. ��ת
			uri = "/servlet/AdministratorsServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// b.չʾ
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
			
			AdministratorsDAO dao = new AdministratorsDAO();
			// ����dao�����getAll�������õ����
			Map<String, Object> result = dao.getAll(pageBean);
			
			//����
			request.setAttribute("result", result);

			// ��תҳ
			uri = "/sys/administrators/administrators.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// c.ɾ��
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			int administratorsId = Integer.parseInt(request.getParameter("id"));

			Administrators cc = new Administrators();
			cc.setId(administratorsId);

			// 2. ����daoִ��ɾ��
			AdministratorsDAO dao = new AdministratorsDAO();
			dao.delete(cc);

			// 3. ��ת
			uri = "/servlet/AdministratorsServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// d.����
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			//��ȡ���ҵĹ���Ա�û���
			String administratorsName = request.getParameter("name");
			//�ж� 
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1";       //��һ�η��ʣ����õ�ǰҳΪ1��
			}
			//ת��
			int currentPage = Integer.parseInt(currPage);
			
			//����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			
			Administrators title = new Administrators();
			title.setName(administratorsName);
			// ����dao�����search�������õ����
			AdministratorsDAO dao = new AdministratorsDAO();
			Map<String, Object> result = dao.search(pageBean, title);
			
			//����
			request.setAttribute("result", result);
			
			// ��תҳ
			uri = "/sys/administrators/administrators.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// e.������תչʾ
	public void Jumpup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// 1. ��ȡ�������ݷ�װ
			int administratorsId = Integer.parseInt(request.getParameter("id"));

			Administrators cc = new Administrators();
			cc.setId(administratorsId);

			// 2. ����daoִ�����
			AdministratorsDAO dao = new AdministratorsDAO();

			// ����dao�����getone�������õ����
			Administrators result = dao.getone(cc);

			// ����
			request.setAttribute("listClass", result);
			// ��תҳ
			uri = "/sys/administrators/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// f.���¹���Ա
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			int administratorsId = Integer.parseInt(request.getParameter("id"));
			String administratorsName = request.getParameter("name");
			String administratorsPassword = request.getParameter("password");

			Administrators cc = new Administrators();
			cc.setId(administratorsId);
			cc.setName(administratorsName);
			cc.setPassword(administratorsPassword);

			// 2. ����daoִ�����
			AdministratorsDAO dao = new AdministratorsDAO();
			dao.update(cc);

			// 3. ��ת
			uri = "/servlet/AdministratorsServlet?method=list";
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
