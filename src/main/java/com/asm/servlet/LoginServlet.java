package com.asm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asm.dao.UserDAO;
import com.asm.dao.impl.UserDAOImpl;
import com.asm.entity.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("forgot_pw".equals(action)) {
        request.getRequestDispatcher("/views/forgotPassword.jsp").forward(request,
        response);
        return;
        }

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserDAO userDAO = new UserDAOImpl();

            User user = userDAO.findById(username);

            if (username != null && user.getPassword().equals(password)) {
                // đăng nhập thành công
                response.sendRedirect("home");
            } else {
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // TODO: handle exception
            request.setAttribute("error", "Lỗi hệ thống. Vui lòng thử lại!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}