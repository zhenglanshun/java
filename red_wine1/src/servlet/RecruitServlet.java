package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RecruitDAO;
import entity.Recruit;
import util.PageBean;

public class RecruitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// ��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public RecruitServlet() {
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
			String recruitTitle = request.getParameter("title");
			String recruitJob_describe = request.getParameter("job_describe");
			String recruitRequirement = request.getParameter("requirement");

			Recruit cc = new Recruit();
			cc.setTitle(recruitTitle);
			cc.setJob_describe(recruitJob_describe);
			cc.setRequirement(recruitRequirement);

			// 2. ����daoִ�����
			RecruitDAO dao = new RecruitDAO();
			dao.add(cc);

			// 3. ��ת
			uri = "/servlet/RecruitServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// b.������ʿչʾ
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			//�ж� 
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1";       //��һ�η��ʣ����õ�ǰҳΪ1��
			}
			//ת��
			int currentPage = Integer.parseInt(currPage);
			
			//����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			
			// ����dao�����getAll�������õ����
			RecruitDAO dao = new RecruitDAO();
			Map<String, Object> result = dao.getAll(pageBean);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/sys/recruit/recruit.jsp";
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
			int RecruitId = Integer.parseInt(request.getParameter("id"));

			Recruit cc = new Recruit();
			cc.setId(RecruitId);

			// 2. ����daoִ��ɾ��
			RecruitDAO dao = new RecruitDAO();
			dao.delete(cc);

			// 3. ��ת
			uri = "/servlet/RecruitServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// d.���ҵ�����Ϣ
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			//��ȡ���ҵ�ְλ����
			String RecruitTitle = request.getParameter("title");
			//�ж� 
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1";       //��һ�η��ʣ����õ�ǰҳΪ1��
			}
			//ת��
			int currentPage = Integer.parseInt(currPage);
			
			//����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			
			Recruit title = new Recruit();
			title.setTitle(RecruitTitle);
			
			//����dao�����search�������õ����
			RecruitDAO dao = new RecruitDAO();
			Map<String, Object> result = dao.search(pageBean, title);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/sys/recruit/recruit.jsp";
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
			int RecruitId = Integer.parseInt(request.getParameter("id"));

			Recruit cc = new Recruit();
			cc.setId(RecruitId);

			// 2. ����daoִ�����
			RecruitDAO dao = new RecruitDAO();

			// ����dao�����getone�������õ����
			Recruit result = dao.getone(cc);

			// ����
			request.setAttribute("listClass", result);
			// ��ת
			uri = "/sys/recruit/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// f.����������ʿ
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			int recruitId = Integer.parseInt(request.getParameter("id"));
			String recruitTitle = request.getParameter("title");
			String recruitJob_describe = request.getParameter("job_describe");
			String recruitRequirement = request.getParameter("requirement");

			Recruit cc = new Recruit();
			cc.setId(recruitId);
			cc.setTitle(recruitTitle);
			cc.setJob_describe(recruitJob_describe);
			cc.setRequirement(recruitRequirement);

			// 2. ����daoִ�����
			RecruitDAO dao = new RecruitDAO();
			dao.update(cc);

			// 3. ��ת
			uri = "/servlet/RecruitServlet?method=list";
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
