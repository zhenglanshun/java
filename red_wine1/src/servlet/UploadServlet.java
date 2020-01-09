package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.CommodityDAO;
import entity.Commodity;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// ��ת��Դ
	private String uri;

	/**
	 * Constructor of the object.
	 */
	public UploadServlet() {
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

	// a.�����Ʒ
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/********* �ļ��ϴ������ �����ļ��ϴ� ************/

		try {
			// 1. �ļ��ϴ�����
			FileItemFactory factory = new DiskFileItemFactory();
			// 2. �����ļ��ϴ����Ĺ�����
			ServletFileUpload upload = new ServletFileUpload(factory);

			// ���ô�С���Ʋ���
			upload.setFileSizeMax(10 * 1024 * 1024); // �����ļ���С����
			upload.setSizeMax(50 * 1024 * 1024); // ���ļ���С����
			upload.setHeaderEncoding("utf-8"); // �������ļ����봦��
			
			// 3. �жϣ� ��ǰ���Ƿ�Ϊ�ļ��ϴ���
			if (upload.isMultipartContent(request)) {
				// 4. ����������ת��Ϊһ����FileItem�������ü��Ϸ�װ
				List<FileItem> list = upload.parseRequest(request);
				Commodity result = new Commodity();
				// ������ �õ�ÿһ���ϴ�������
				for (FileItem item : list) {
					// �жϣ���ͨ�ı�����
					if (item.isFormField()) {
						// ��ȡ����
						String name = item.getFieldName();
						// ��ȡֵ
						String value = item.getString("utf-8");   //�ر�ע���������

						// �жϻ�ȡ�ı�����
						if ("class_id".equals(name)) {
							result.setClass_id(Integer.parseInt(value));
						} else if ("title".equals(name)) {
							result.setTitle(value);
						} else if ("price".equals(name)) {
							result.setPrice(Float.parseFloat(value));
						} else if ("specifications".equals(name)) {
							result.setSpecifications(value);
						} else if ("place".equals(name)) {
							result.setPlace(value);
						} else if ("alcohol".equals(name)) {
							result.setAlcohol(value);
						} else if ("content".equals(name)) {
							result.setContent(value);
						}

					}
					// �ļ�����
					else {
						/******** �ļ��ϴ� ***********/
						// a. ��ȡ�ļ�����
						String name = item.getName();
						// ----�����ϴ��ļ�����������----
						// a1. �ȵõ�Ψһ���
						String id = UUID.randomUUID().toString();
						// a2. ƴ���ļ���
						name = id + name;
						result.setPhoto(name);
						// b. �õ��ϴ�Ŀ¼
						//String basePath = getServletContext().getRealPath("/upload");  //tomcat������·��
						String basePath = "D:/MyEclipse2017/red_wine/WebRoot/upload";   
						// c. ����Ҫ�ϴ����ļ�����
						File file = new File(basePath, name); // �ļ�������·��
						// d. �ļ��в����ھ��Զ�����
						if (!new File(basePath).isDirectory())
							new File(basePath).mkdirs();
						// e. �ϴ�
						item.write(file);
						item.delete(); // ɾ���������ʱ��������ʱ�ļ�
					}

				}

				// ����daoִ�����
				CommodityDAO dao = new CommodityDAO();
				dao.add(result);

			} else {
				System.out.println("��ǰ�������ļ��ϴ���������ʧ�ܣ�");
			}
			// 3. ��ת
			uri = "/servlet/CommodityServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ת��
		request.getRequestDispatcher(uri).forward(request, response);

	}

	// f.������Ʒ
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// 1. �ļ��ϴ�����
			FileItemFactory factory = new DiskFileItemFactory();
			// 2. �����ļ��ϴ����Ĺ�����
			ServletFileUpload upload = new ServletFileUpload(factory);

			// ���ô�С���Ʋ���
			upload.setFileSizeMax(10 * 1024 * 1024); // �����ļ���С����
			upload.setSizeMax(50 * 1024 * 1024); // ���ļ���С����
			upload.setHeaderEncoding("UTF-8"); // �������ļ����봦��
			// 3. �жϣ� ��ǰ���Ƿ�Ϊ�ļ��ϴ���
			if (upload.isMultipartContent(request)) {
				// 4. ����������ת��Ϊһ����FileItem�������ü��Ϸ�װ
				List<FileItem> list = upload.parseRequest(request);
				Commodity result = new Commodity();
				// ������ �õ�ÿһ���ϴ�������
				for (FileItem item : list) {
					// �жϣ���ͨ�ı�����
					if (item.isFormField()) {
						// ��ȡ����
						String name = item.getFieldName();
						// ��ȡֵ
						String value = item.getString("utf-8");   //�ر�ע���������

						// �жϻ�ȡ�ı�����
						if ("class_id".equals(name)) {
							result.setClass_id(Integer.parseInt(value));
						} else if ("id".equals(name)) {
							result.setId(Integer.parseInt(value));
						} else if ("title".equals(name)) {
							result.setTitle(value);
						} else if ("price".equals(name)) {
							result.setPrice(Float.parseFloat(value));
						} else if ("specifications".equals(name)) {
							result.setSpecifications(value);
						} else if ("place".equals(name)) {
							result.setPlace(value);
						} else if ("alcohol".equals(name)) {
							result.setAlcohol(value);
						} else if ("content".equals(name)) {
							result.setContent(value);
						}

					}
					// �ļ�����
					else {
						/******** �ļ��ϴ� ***********/
						// a. ��ȡ�ļ�����
						String name = item.getName();
						// a2. ƴ���ļ���
						if (name == null || "".equals(name.trim())) {
							result.setPhoto(name);
						}else{
							// ----�����ϴ��ļ�����������----
							// a1. �ȵõ�Ψһ���
							String id = UUID.randomUUID().toString();
							name = id + name;
							result.setPhoto(name);
							// b. �õ��ϴ�Ŀ¼
							String basePath = getServletContext().getRealPath("/upload");  //tomcat������·��
							//String basePath = "D:\\eclipse\\apache-tomcat-9.0.22\\wtpwebapps\\red_wine1\\upload";   
							// c. ����Ҫ�ϴ����ļ�����
							File file = new File(basePath, name); // �ļ�������·��
							// d. �ļ��в����ھ��Զ�����
							if (!new File(basePath).isDirectory())
								new File(basePath).mkdirs();
							// e. �ϴ�
							item.write(file);
							item.delete(); // ɾ���������ʱ��������ʱ�ļ�
						}						
					}
				}

				// ����daoִ�����
				CommodityDAO dao = new CommodityDAO();
				dao.update(result);

			} else {
				System.out.println("��ǰ�������ļ��ϴ���������ʧ�ܣ�");
			}
			// 3. ��ת
			uri = "/servlet/CommodityServlet?method=list";
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