package com.asm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asm.dao.VideoDAO;
import com.asm.dao.VideoDetailDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.dao.impl.VideoDetailDAOImpl;
import com.asm.dto.VideoDetailDTO;
import com.asm.entity.User;

@WebServlet({ "/home", "logout" })
public class HomePageServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  VideoDetailDAO videoDetailDAO = new VideoDetailDAOImpl();
  VideoDAO videoDAO = new VideoDAOImpl();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    // fake session user -> giả lập đăng nhập
    User user = new User();
    user.setId("hieu01");
    user.setFullname("Admin");
    user.setAdmin(true);
    session.setAttribute("user", user);

    if (request.getRequestURI().contains("logout")) {
      session.setAttribute("user", null);
    }

    String userId = session.getAttribute("user") != null
        ? ((User) session.getAttribute("user")).getId()
        : null;

    int offset = 6;
    int maxPage = videoDAO.countAllVideos() / offset;
    String prevPage = request.getParameter("page");
    int page = 1;

    if (prevPage != null) {
      page = Integer.parseInt(prevPage);
      if (page < 1) {
        page = 1;
      } else if (page > maxPage) {
        page = maxPage;
      }

      request.setAttribute("targetScrollId", "#skit"); // Khi nào có đổi trang thì mởi để jsp nhảy lên id này
    }

    List<VideoDetailDTO> videos = videoDetailDAO.findByPage(userId, page, offset);

    request.setAttribute("videos", videos);
    request.setAttribute("currentUrl", request.getRequestURI());
    request.setAttribute("currentPage", page);
    request.setAttribute("maxPage", maxPage);

    request.getRequestDispatcher("/views/homePage.jsp").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
