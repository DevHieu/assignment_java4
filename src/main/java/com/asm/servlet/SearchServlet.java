package com.asm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;

@WebServlet({ "/search", "/search/viewLtoH", "/search/viewHtoL", "/search/likeLtoH", "/search/likeHtoL", "/search/AZ",
		"/search/ZA" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//    String query = request.getParameter("query");
//    System.out.println(query);

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String searchTestString = "sql";
		VideoDAO daoVid = new VideoDAOImpl();

		String pathString = request.getServletPath();
		if (pathString.contains("viewLtoH")) {
			String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
					+ "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
					+ "WHERE v.title LIKE :text "
					+ "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active " + "ORDER BY v.views  ASC";
			List<Object[]> listVideoSearchList = daoVid.searchVideo(searchTestString, JPQL);
			request.setAttribute("listVideoSearchList", listVideoSearchList);

		} else if (pathString.contains("viewHtoL")) {
			String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
					+ "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
					+ "WHERE v.title LIKE :text "
					+ "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active " + "ORDER BY v.views DESC";
			List<Object[]> listVideoSearchList = daoVid.searchVideo(searchTestString, JPQL);
			request.setAttribute("listVideoSearchList", listVideoSearchList);

		} else if (pathString.contains("likeLtoH")) {
			String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
					+ "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
					+ "WHERE v.title LIKE :text "
					+ "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
					+ "ORDER BY COUNT(f.video.id) ASC ";
			List<Object[]> listVideoSearchList = daoVid.searchVideo(searchTestString, JPQL);
			request.setAttribute("listVideoSearchList", listVideoSearchList);

		} else if (pathString.contains("likeHtoL")) {
			String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
					+ "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
					+ "WHERE v.title LIKE :text "
					+ "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
					+ "ORDER BY COUNT(f.video.id) DESC";
			List<Object[]> listVideoSearchList = daoVid.searchVideo(searchTestString, JPQL);
			request.setAttribute("listVideoSearchList", listVideoSearchList);
		} else if (pathString.contains("AZ")) {

			String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
					+ "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
					+ "WHERE v.title LIKE :text "
					+ "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active " + "ORDER BY v.title ASC";
			List<Object[]> listVideoSearchList = daoVid.searchVideo(searchTestString, JPQL);
			request.setAttribute("listVideoSearchList", listVideoSearchList);

		} else if (pathString.contains("ZA")) {
			String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
					+ "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id "
					+ "WHERE v.title LIKE :text "
					+ "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active " + "ORDER BY v.title DESC ";
			List<Object[]> listVideoSearchList = daoVid.searchVideo(searchTestString, JPQL);
			request.setAttribute("listVideoSearchList", listVideoSearchList);

		} else {
			String JQPL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
					+ "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id left join Share s on v.id = s.video.id WHERE v.title LIKE :text GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active";
			List<Object[]> listVideoSearchList = daoVid.searchVideo(searchTestString, JQPL);
			request.setAttribute("listVideoSearchList", listVideoSearchList);
		}

		request.getRequestDispatcher("/views/searchPage.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
