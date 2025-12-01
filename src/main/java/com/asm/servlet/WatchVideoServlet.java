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
import com.asm.entity.Video;

@WebServlet({ "/watch/*" })
public class WatchVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		VideoDAO daoVid = new VideoDAOImpl();
		List<Video> listAllVid = daoVid.findAll();
		String pathInfo = request.getPathInfo().substring(1);
		Video video = daoVid.findById(pathInfo);
		request.setAttribute("video", video);

		request.setAttribute("ListVideoAll", listAllVid);
		request.getRequestDispatcher("/views/videoPage.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(id);
		request.getRequestDispatcher("/views/videoPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}