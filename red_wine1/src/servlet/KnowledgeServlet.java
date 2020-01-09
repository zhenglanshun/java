package servlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.KnowledgeDAO;
import entity.Knowledge;
import util.PageBean;

public class KnowledgeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// ��ת��Դ
	private String uri;


	
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

	// a.��Ӻ��֪ʶ
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			String knowledgeTitle = request.getParameter("title");
			String knowledgeAuthor = request.getParameter("author");
			String knowledgeContent = request.getParameter("content");
			String knowledgeDate = request.getParameter("date");

			Knowledge cc = new Knowledge();
			cc.setTitle(knowledgeTitle);
			cc.setAuthor(knowledgeAuthor);
			cc.setContent(knowledgeContent);
			cc.setDate(knowledgeDate);

			// 2. ����daoִ�����
			KnowledgeDAO dao = new KnowledgeDAO();
			dao.add(cc);

			// 3. ��ת
			uri = "/servlet/KnowledgeServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// b.���֪ʶչʾ
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			// �ж�
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1"; // ��һ�η��ʣ����õ�ǰҳΪ1��
			}
			// ת��
			int currentPage = Integer.parseInt(currPage);

			// ����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);

			// ����dao�����getAll�������õ����
			KnowledgeDAO dao = new KnowledgeDAO();
			Map<String, Object> result = dao.getAll(pageBean);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/sys/knowledge/knowledge.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// c.ɾ�����֪ʶ
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			int knowledgeId = Integer.parseInt(request.getParameter("id"));

			Knowledge cc = new Knowledge();
			cc.setId(knowledgeId);

			// 2. ����daoִ��ɾ��
			KnowledgeDAO dao = new KnowledgeDAO();
			dao.delete(cc);

			// 3. ��ת
			uri = "/servlet/KnowledgeServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// d.���ҵ����t��֪ʶ
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// ��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			// ��ȡ���ҵı���
			String KnowledgeTitle = request.getParameter("title");
			// �ж�
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1"; // ��һ�η��ʣ����õ�ǰҳΪ1��
			}
			// ת��
			int currentPage = Integer.parseInt(currPage);

			// ����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);

			Knowledge title = new Knowledge();
			title.setTitle(KnowledgeTitle);

			// ����dao�����search�������õ����
			KnowledgeDAO dao = new KnowledgeDAO();
			Map<String, Object> result = dao.search(pageBean, title);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/sys/knowledge/knowledge.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// e.���֪ʶ������תչʾ
	public void Jumpup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// 1. ��ȡ�������ݷ�װ
			int KnowledgeId = Integer.parseInt(request.getParameter("id"));

			Knowledge cc = new Knowledge();
			cc.setId(KnowledgeId);

			// 2. ����daoִ�����
			KnowledgeDAO dao = new KnowledgeDAO();

			// ����dao�����getone�������õ����
			Knowledge result = dao.getone(cc);

			// ����
			request.setAttribute("listClass", result);
			// ��ת
			uri = "/sys/knowledge/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// f.���º��֪ʶ
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			int knowledgeId = Integer.parseInt(request.getParameter("id"));
			String knowledgeTitle = request.getParameter("title");
			String knowledgeAuthor = request.getParameter("author");
			String knowledgeContent = request.getParameter("content");
			String knowledgeDate = request.getParameter("date");

			Knowledge cc = new Knowledge();
			cc.setId(knowledgeId);
			cc.setTitle(knowledgeTitle);
			cc.setAuthor(knowledgeAuthor);
			cc.setContent(knowledgeContent);
			cc.setDate(knowledgeDate);

			// 2. ����daoִ�����
			KnowledgeDAO dao = new KnowledgeDAO();
			dao.update(cc);

			// 3. ��ת
			uri = "/servlet/KnowledgeServlet?method=list";
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