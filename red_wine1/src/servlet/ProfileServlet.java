package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProfileDAO;
import entity.Profile;

public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// ��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public ProfileServlet() {
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
			// չʾ
			list(request, response);
		} else if ("add".equals(method)) {
			// ���
			add(request, response);
		} else if ("delete".equals(method)) {
			// ɾ��
			delete(request, response);
		} else if ("Jump".equals(method)) {
			// ��ת����ҳ
			Jump(request, response);
		} else if ("update".equals(method)) {
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

	// ��˾���չʾ
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ����dao����ķ������õ����
			ProfileDAO dao = new ProfileDAO();
			Profile result = dao.getAll();
			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/sys/profile/profile.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// ���
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. ��ȡ�������ݷ�װ
			String Content = request.getParameter("content");
			Profile profile = new Profile();
			profile.setContent(Content);
			// 2. ����daoִ�����
			ProfileDAO dao = new ProfileDAO();
			dao.add(profile);
			// 3. ��ת
			uri = "/servlet/ProfileServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// ɾ��
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. ��ȡ�������ݷ�װ
			int Id = Integer.parseInt(request.getParameter("id"));
			Profile profile = new Profile();
			profile.setId(Id);
			// 2. ����daoִ��ɾ��
			ProfileDAO dao = new ProfileDAO();
			dao.delete(profile);
			// 3. ��ת
			uri = "/servlet/ProfileServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// ������ת
	public void Jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ��ȡ�������ݷ�װ
			int Id = Integer.parseInt(request.getParameter("id"));
			Profile profile = new Profile();
			profile.setId(Id);
			// ����dao����ķ������õ����
			ProfileDAO dao = new ProfileDAO();
			Profile result = dao.getone(profile);
			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/sys/profile/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// ����
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. ��ȡ�������ݷ�װ
			int Id = Integer.parseInt(request.getParameter("id"));
			String Content = request.getParameter("content");
			Profile profile = new Profile();
			profile.setId(Id);
			profile.setContent(Content);
			// 2. ����daoִ�и���
			ProfileDAO dao = new ProfileDAO();
			dao.update(profile);
			// 3. ��ת
			uri = "/servlet/ProfileServlet?method=list";
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