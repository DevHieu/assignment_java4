package com.asm.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asm.dao.HistoryDAO;
import com.asm.dao.impl.HistoryDAOImpl;
import com.asm.entity.History;
import com.asm.entity.User;

@WebServlet("/watch")
public class WatchVideoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private HistoryDAO historyDAO = new HistoryDAOImpl();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    String id = request.getParameter("id");
    User user = (User) session.getAttribute("user");

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

    request.getRequestDispatcher("/views/videoPage.jsp").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}