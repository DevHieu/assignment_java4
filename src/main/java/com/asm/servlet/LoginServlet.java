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

    protected UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        try {

            // ===== 1. Validate trước =====
            if (username == null || username.trim().isEmpty()) {
                request.setAttribute("message", "Username không được để trống!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("message", "Password không được để trống!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }

            // ===== 2. Kiểm tra tài khoản =====
            UserDAO userDAO = getUserDAO();
            User user = userDAO.findById(username);

            if (user == null || !user.getPassword().equals(password)) {
                request.setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }

            // ===== 3. Đăng nhập thành công =====
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (remember != null) {
                String userInfo = Base64.getEncoder()
                        .encodeToString(username.getBytes());

                Cookie cookie = new Cookie("user", userInfo);
                cookie.setMaxAge(30 * 24 * 60 * 60);
                cookie.setPath("/");
                response.addCookie(cookie);
            }

            response.sendRedirect("home");

        } catch (Exception e) {
            request.setAttribute("message", "Lỗi hệ thống. Vui lòng thử lại!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}