package com.asm.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asm.dao.HistoryDAO;
import com.asm.dao.VideoDAO;
import com.asm.dao.impl.HistoryDAOImpl;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.History;
import com.asm.entity.User;
import com.asm.entity.Video;

@WebServlet("/watch")
public class WatchVideoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private HistoryDAO historyDAO = new HistoryDAOImpl();

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(true);

    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");

    VideoDAO daoVid = new VideoDAOImpl();
    List<Video> list10Vid = daoVid.find10RandomVideo();
    String id = request.getParameter("id");

    Video video = daoVid.findById(id);
    daoVid.increaseViews(id);

    boolean isLiked = false;
    User user = (User) session.getAttribute("user");
    if (user != null) {
      History history = historyDAO.existsByUserIdAndVideoId(user.getId(), id);
      if (history == null) {
        history = new History();
        history.setUserId(user.getId());
        history.setVideoId(id);
        historyDAO.create(history);
      } else {
        history.setViewDate(new Date());
        historyDAO.update(history);
      }

      isLiked = daoVid.isLiked(id, user.getId());
    }

    request.setAttribute("video", video);
    request.setAttribute("list10Vid", list10Vid);
    request.setAttribute("isLiked", isLiked);
    request.getRequestDispatcher("/views/videoPage.jsp").forward(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    request.getRequestDispatcher("/views/videoPage.jsp").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}