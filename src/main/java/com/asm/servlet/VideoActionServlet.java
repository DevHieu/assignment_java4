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

@WebServlet({ "/like-video", "/share-video" })
@MultipartConfig
public class VideoActionServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getServletPath();
    String videoId = request.getParameter("videoId");

    switch (action) {
      case "/like-video":

        String isLiked = request.getParameter("action");
        System.out.println(isLiked);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (isLiked.equals("like")) {
          // Thực hiện logic lưu lượt thích vào cơ sở dữ liệu
          System.out.println("Video " + videoId + " đã được thích.");
        } else if (isLiked.equals("unlike")) {
          // Thực hiện logic xóa lượt thích khỏi cơ sở dữ liệu
          System.out.println("Video " + videoId + " đã bị bỏ thích.");
        }

        String jsonResponse = "{ \"status\": \"success\", \"action\": \"" + isLiked + "\" }";

        try (PrintWriter out = response.getWriter()) {
          out.print(jsonResponse);
          out.flush();
        }
        break;

      case "/share-video":
        String redirectUrl = request.getParameter("redirectUrl");
        String section = request.getParameter("currentSection");
        System.out.println(videoId);

        HttpSession session = request.getSession();
        session.setAttribute("message", "Gửi video thành công!");

        String url = redirectUrl + (section != null ? section : "");

        response.sendRedirect(url);
        break;
      default:
        break;
    }
  }
}