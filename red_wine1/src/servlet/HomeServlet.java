package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HomeDAO;
import entity.Information;
import entity.Knowledge;
import entity.Recruit;
import util.PageBean;

public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// ��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public HomeServlet() {
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
		if ("knowledge".equals(method)) {
			// ���֪ʶ
			knowledge(request, response);
		} else if ("news".equals(method)) {
			// ��˾��Ѷ
			news(request, response);
		} else if ("recruit".equals(method)) {
			// ������ʿ
			recruit(request, response);
		} else if ("commodity".equals(method)) {
			// ��Ʒչʾ
			commodity(request, response);
		} else if ("search_commodity".equals(method)) {
			// ��Ʒչʾ����
			search_commodity(request, response);
		} else if ("commodity_detail".equals(method)) {
			// ��Ʒ����
			commodity_detail(request, response);
		} else if ("knowledge_detail".equals(method)) {
			// ���֪ʶ����
			knowledge_detail(request, response);
		} else if ("information_detail".equals(method)) {
			// ��˾��Ѷ����
			information_detail(request, response);
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

	// ��Ʒչʾ
	public void commodity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

			// ����dao���󣬻�ȡ���
			HomeDAO dao = new HomeDAO();
			Map<String, Object> result = dao.getCommodity(pageBean);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/Home/product_display.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// ��Ʒ����
	public void search_commodity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// ��ȡ��ѯ����
			String search_name = request.getParameter("search_name");
			String cids = request.getParameter("cid");
			// ��ȡ����ǰҳ������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			// �ж�
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1"; // ��һ�η��ʣ����õ�ǰҳΪ1��
			}
			// ת��
			int currentPage = Integer.parseInt(currPage);
			int cid = Integer.parseInt(cids);

			// ����dao�������õ�ǰҳ����
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);

			// ����dao���󣬻�ȡ���
			HomeDAO dao = new HomeDAO();
			Map<String, Object> result = dao.searchCommodity(pageBean,cid,search_name);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/Home/product_display.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// ���֪ʶչʾ
	public void knowledge(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

			// ����dao���󣬻�ȡ���
			HomeDAO dao = new HomeDAO();
			Map<String, Object> result = dao.getKnowledge(pageBean);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/Home/knowledge.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// ��˾��Ѷ
	public void news(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

			// ����dao���󣬻�ȡ���
			HomeDAO dao = new HomeDAO();
			Map<String, Object> result = dao.getNews(pageBean);

			// ����
			request.setAttribute("result", result);

			// ��ת
			uri = "/Home/news.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// ������ʿ
	public void recruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// ����dao���󣬻�ȡ���
			HomeDAO dao = new HomeDAO();
			ArrayList<Recruit> result = dao.getRecruit();

			// ����
			request.setAttribute("result", result);

			// ��ת
			uri = "/Home/recruit.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}
	
	//��Ʒ����
	public void commodity_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// ��ȡ�������ݷ�װ
			int commodityId = Integer.parseInt(request.getParameter("id"));
			
			// ����dao����ķ������õ����
			HomeDAO dao = new HomeDAO();
			Map<String, Object> result = dao.commodityDetail(commodityId);

			// ����
			request.setAttribute("result", result);
			// ��ת��Ʒ����ҳ
			uri = "/Home/goods_detail.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}
	
	//���֪ʶ����
	public void knowledge_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ��ȡ�������ݷ�װ
			int KnowledgeId = Integer.parseInt(request.getParameter("id"));
			
			// ����dao���󷽷����õ����
			HomeDAO dao = new HomeDAO();
			Knowledge result = dao.knowledgeDetail(KnowledgeId);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/Home/knowledge_detail.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}
	
	//��˾��Ѷ����
	public void information_detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ��ȡ�������ݷ�װ
			int Id = Integer.parseInt(request.getParameter("id"));
			
			// ����dao���󷽷����õ����
			HomeDAO dao = new HomeDAO();
			Information result = dao.informationDetail(Id);

			// ����
			request.setAttribute("result", result);
			// ��ת
			uri = "/Home/news_detail.jsp";
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
