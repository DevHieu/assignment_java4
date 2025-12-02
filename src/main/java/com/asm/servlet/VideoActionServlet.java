package com.asm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asm.dao.FavoriteDAO;
import com.asm.dao.ShareDAO;
import com.asm.dao.VideoDAO;
import com.asm.dao.impl.FavoriteDAOImpl;
import com.asm.dao.impl.ShareDAOImpl;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Favorite;
import com.asm.entity.Share;
import com.asm.entity.User;
import com.asm.entity.Video;
import com.asm.utils.XMailer;

@WebServlet({ "/like-video", "/share-video" })
@MultipartConfig
public class VideoActionServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private VideoDAO videoDAO = new VideoDAOImpl();
  private ShareDAO shareDAO = new ShareDAOImpl();
  private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    String action = request.getServletPath();

    User user = (User) session.getAttribute("user");
    String videoId = request.getParameter("videoId");
    Video video = videoId != null ? videoDAO.findById(videoId) : null;

    switch (action) {

      case "/like-video":
        handleLikeVideo(request, response, user, video);
        break;

      case "/share-video":
        handleShareVideo(request, response, session, user, video);
        break;

      default:
        break;
    }
  }

  private void handleLikeVideo(
      HttpServletRequest request,
      HttpServletResponse response,
      User user,
      Video video) throws IOException {

    String action = request.getParameter("action");

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if ("like".equals(action)) {
      Favorite f = new Favorite();
      f.setUser(user);
      f.setVideo(video);
      favoriteDAO.create(f);
      System.out.println("Liked video: " + video.getTitle());
    } else if ("unlike".equals(action)) {
      Favorite f = favoriteDAO.findByUserAndVideo(user.getId(), video.getId());
      if (f != null) {
        favoriteDAO.deleteById(f.getId());
        System.out.println("Unliked video: " + video.getTitle());
      }
    }

    try (PrintWriter out = response.getWriter()) {
      out.print("{\"status\":\"success\",\"action\":\"" + action + "\"}");
    }
  }

  private void handleShareVideo(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpSession session,
      User user,
      Video video) throws IOException {

    String redirectUrl = request.getParameter("redirectUrl");
    String section = request.getParameter("currentSection");
    String emails = request.getParameter("emailTo");

    try {
      Share share = new Share();
      share.setUser(user);
      share.setVideo(video);
      share.setEmails(emails);
      shareDAO.create(share);

      String link = "http://localhost:9090"
          + request.getContextPath()
          + "/watch?id=" + video.getId();

      String content = "<h3>Video được chia sẻ từ The HAHA Factory</h3>" +
          "<p>Bạn vừa nhận được một video được chia sẻ.</p>" +
          "<p>👉 <a href='" + link + "' target='_blank' " +
          "style='color:#0d6efd;text-decoration:none;font-weight:bold;'>" +
          "Xem video tại đây</a></p>";

      XMailer.send(emails, "Chia sẻ video từ Ứng dụng Video", content);

      session.setAttribute("message", "Gửi video thành công!");

    } catch (Exception e) {
      e.printStackTrace();
      session.setAttribute("message", "Gửi video thất bại!");
    }

    response.sendRedirect(redirectUrl + (section != null ? section : ""));
  }
}