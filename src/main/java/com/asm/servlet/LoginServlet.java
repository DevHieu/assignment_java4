package com.asm.servlet;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asm.dao.UserDAO;
import com.asm.dao.impl.UserDAOImpl;
import com.asm.entity.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        System.out.println(remember);

        try {
            UserDAO userDAO = new UserDAOImpl();

            User user = userDAO.findById(username);

            if (user != null && user.getPassword().equals(password)) {
                // đăng nhập thành công
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // if(remember != null){
                // byte[] bytes = (username+","+password).getBytes();
                // String userInfo = Base64.getEncoder().encodeToString(bytes);
                // Cookie cookie = new Cookie("user", userInfo);
                // cookie.setMaxAge(30*24*60*60);
                // cookie.setPath("/"); //hịệu ứng từng ứng dụng

                // response.addCookie(cookie); //gửi trình duyệt
                // }

                if (remember != null) {
                    byte[] bytes = (username).getBytes();
                    String userInfo = Base64.getEncoder().encodeToString(bytes);
                    Cookie cookie = new Cookie("user", userInfo);
                    cookie.setMaxAge(30 * 24 * 60 * 60); // hiệu lực 30 ngày
                    cookie.setPath("/"); // hiệu lực toàn ứng dụng
                    // Gửi về trình duyệt
                    response.addCookie(cookie);
                }
                response.sendRedirect("home");
            } else {
                request.setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("message", "Lỗi hệ thống. Vui lòng thử lại!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}