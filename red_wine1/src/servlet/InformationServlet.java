package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InformationDAO;
import entity.Information;
import util.PageBean;

public class InformationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// ��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public InformationServlet() {
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

	// a.��ӹ�˾��Ѷ
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. ��ȡ�������ݷ�װ
			String informationTitle = request.getParameter("title");  //��ȡ�����ֵ����ֵ��informationTitle����
			String informationAuthor = request.getParameter("author"); //��ȡ���ߵ�ֵ����ֵ��informationAuthor����
			String informationContent = request.getParameter("content"); //��ȡ���ݵ�ֵ����ֵ��informationContent����
			String informationDate = request.getParameter("date"); //��ȡ����ʱ���ֵ����ֵ��informationDate����

			Information information = new Information(); //����һ��Information����
			information.setTitle(informationTitle);	//�������ֵ����Information
			information.setAuthor(informationAuthor); //�����ߵ�ֵ����Information
			information.setContent(informationContent);	//�����ݵ�ֵ����Information
			information.setDate(informationDate); //������ʱ���ֵ����Information

			// 2. ����daoִ�����
			InformationDAO dao = new InformationDAO();
			dao.add(information);
			// 3. ��ת·��
			uri = "/servlet/InformationServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// b.��˾��Ѷչʾ
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			// �жϵ�ǰҳ�Ƿ�Ϊ��
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1"; // ��һ�η��ʣ����õ�ǰҳΪ1��
			}
			// ת��ҳ����Ϊint����
			int currentPage = Integer.parseInt(currPage);
			// ����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);

			// ����dao�����getAll�������õ����
			InformationDAO dao = new InformationDAO();
			Map<String, Object> result = dao.getAll(pageBean);

			// ����
			request.setAttribute("result", result);

			// ��ת·��
			uri = "/sys/information/information.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// c.ɾ����˾��Ѷ
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ��ȡID��ֵ��ת��Ϊint���Ͳ���ֵ��informationId����
			int informationId = Integer.parseInt(request.getParameter("id"));

			Information information = new Information(); //����һ��Information����
			information.setId(informationId);  //��ID��ֵ����information

			// ����daoִ��ɾ��
			InformationDAO dao = new InformationDAO(); 
			dao.delete(information);

			// ��ת·��
			uri = "/servlet/InformationServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);
	}

	// d.���ҵ�����˾��Ѷ
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// ��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			// ��ȡ���ҵı���
			String InformationTitle = request.getParameter("title");
			// �ж�
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1"; // ��һ�η��ʣ����õ�ǰҳΪ1��
			}
			// ת��ҳ����Ϊint����
			int currentPage = Integer.parseInt(currPage);

			// ����PageBean�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			// ����Information���󣬽������ֵ����title
			Information title = new Information();
			title.setTitle(InformationTitle);

			// ����dao�����search�������õ����
			InformationDAO dao = new InformationDAO();
			Map<String, Object> result = dao.search(pageBean, title);

			// ����
			request.setAttribute("result", result);
			// ��ת·��
			uri = "/sys/information/information.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// e.��˾��Ѷ������תչʾ
	public void Jumpup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ��ȡID��ֵ��ת��Ϊint���Ͳ���ֵ��informationId����
			int informationId = Integer.parseInt(request.getParameter("id"));
			// ����һ��Information���󣬲���ID��ֵ����information
			Information information = new Information();
			information.setId(informationId);

			// ����daoִ��getone�������õ����
			InformationDAO dao = new InformationDAO();
			Information result = dao.getone(information);

			// ����
			request.setAttribute("listClass", result);
			// ��ת·��
			uri = "/sys/information/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// f.���¹�˾��Ѷ
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			int informationId = Integer.parseInt(request.getParameter("id"));  //��ȡID��ֵ��תΪint���Ͳ���ֵ��informationId����
			String informationTitle = request.getParameter("title");	//��ȡ�����ֵ����ֵ��informationTitle����
			String informationAuthor = request.getParameter("author");	//��ȡ���ߵ�ֵ����ֵ��informationAuthor����
			String informationContent = request.getParameter("content"); //��ȡ���ݵ�ֵ����ֵ��informationContent����
			String informationDate = request.getParameter("date"); //��ȡ����ʱ���ֵ����ֵ��informationDate����

			//����Information���󣬲���������ݼ���information
			Information information = new Information();
			information.setId(informationId);
			information.setTitle(informationTitle);
			information.setAuthor(informationAuthor);
			information.setContent(informationContent);
			information.setDate(informationDate);

			// ����daoִ�и���
			InformationDAO dao = new InformationDAO();
			dao.update(information);

			// ��ת·��
			uri = "/servlet/InformationServlet?method=list";
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
