package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClassificationDAO;
import entity.Classification;
import util.PageBean;

public class ClassificationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public ClassificationServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//���ñ���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//��ȡ����������
		String method = request.getParameter("method");
		
		//�ж�
		if ("add".equals(method)){
			//���
			add(request, response);
		}
		
		else if ("list".equals(method)){
			//�б�չʾ
			list(request, response);
		}
		
		else if ("search".equals(method)){
			//����
			search(request, response);
		}
		
		else if ("delete".equals(method)){
			//ɾ��
			delete(request, response);
		}
		
		else if ("Jumpup".equals(method)){
			//��ת����ҳ
			Jumpup(request, response);
		}
		
		else if ("update".equals(method)){
			//����
			update(request, response);
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//a.�����Ʒ����
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//1. ��ȡ�������ݷ�װ
			String classificationTitle = request.getParameter("title");
			
			Classification cc = new Classification();
			cc.setTitle(classificationTitle);

			//2. ����daoִ�����
			ClassificationDAO dao= new ClassificationDAO();
			dao.add(cc);
			
			//3. ��ת
			uri = "/servlet/ClassificationServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ת��
		request.getRequestDispatcher(uri).forward(request, response);
			
	}
		
	
	//b.��Ʒ����չʾ
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
			
			ClassificationDAO dao = new ClassificationDAO();
			// ����dao�����getAll�������õ����
			Map<String, Object> result = dao.getAll(pageBean);
			
			//����
			request.setAttribute("result", result);
			//��ת��Ʒ����ҳ
			uri = "/sys/classify/classification.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ת��
		request.getRequestDispatcher(uri).forward(request, response);
		
	}
	
	//c.ɾ����Ʒ����
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//1. ��ȡ�������ݷ�װ
			int classificationId = Integer.parseInt(request.getParameter("id"));
			
			Classification cc = new Classification();
			cc.setId(classificationId);

			
			//2. ����daoִ��ɾ��
			ClassificationDAO dao= new ClassificationDAO();
			dao.delete(cc);
			
			//3. ��ת
			uri = "/servlet/ClassificationServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ת��
		request.getRequestDispatcher(uri).forward(request, response);
			
	}
	
	//d.������Ʒ����
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//��ȡ����ǰҳ��������������һ�η��ʵ�ǰҳΪnull��
			String currPage = request.getParameter("currentPage");
			//��ȡ���ҵ���Ʒ��������
			String classificationTitle = request.getParameter("title");
			//�ж� 
			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1";       //��һ�η��ʣ����õ�ǰҳΪ1��
			}
			//ת��
			int currentPage = Integer.parseInt(currPage);
			
			//����dao�������õ�ǰҳ������
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);
			
			Classification title = new Classification();
			title.setTitle(classificationTitle);
			
			// ����dao�����search�������õ����
			ClassificationDAO dao= new ClassificationDAO();
			Map<String, Object> result = dao.search(pageBean, title);
			
			//����
			request.setAttribute("result", result);
			//��ת��Ʒ����ҳ
			uri = "/sys/classify/classification.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ת��
		request.getRequestDispatcher(uri).forward(request, response);
		
	}
	
	//e.��Ʒ���������תչʾ
	public void Jumpup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			//1. ��ȡ�������ݷ�װ
			int classificationId = Integer.parseInt(request.getParameter("id"));
			
			Classification cc = new Classification();
			cc.setId(classificationId);

			
			//2. ����daoִ�����
			ClassificationDAO dao= new ClassificationDAO();
			
			//����dao�����getone�������õ����
			Classification result = dao.getone(cc);
			
			//����
			request.setAttribute("listClass", result);
			//��ת��Ʒ����ҳ
			uri = "/sys/classify/update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ת��
		request.getRequestDispatcher(uri).forward(request, response);
		
	}
	
	//f.������Ʒ����
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//1. ��ȡ�������ݷ�װ
			int classificationId = Integer.parseInt(request.getParameter("id"));
			String classificationTitle = request.getParameter("title");
			
			Classification cc = new Classification();
			cc.setId(classificationId);
			cc.setTitle(classificationTitle);

			
			//2. ����daoִ�����
			ClassificationDAO dao= new ClassificationDAO();
			dao.update(cc);
			
			//3. ��ת
			uri = "/servlet/ClassificationServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ת��
		request.getRequestDispatcher(uri).forward(request, response);
			
	}
		
	

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
