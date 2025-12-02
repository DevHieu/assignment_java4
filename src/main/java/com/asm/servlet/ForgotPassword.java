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
import com.asm.utils.XMailer;

@WebServlet("/forgot_pw")
public class ForgotPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        try {
            UserDAO userDAO = new UserDAOImpl();

            User user = userDAO.findById(username);

            if (user == null) {
                request.setAttribute("message", "Tài khoản không tồn tại!");
                request.getRequestDispatcher("/views/forgotPassword.jsp").forward(request, response);
                return;
            }

            if (!user.getEmail().equals(email)) {
                request.setAttribute("message", "Email không khớp với tài khoản!");
                request.getRequestDispatcher("/views/forgotPassword.jsp").forward(request, response);
                return;
            }

            if (user != null && user.getEmail().equals(email)) {
                String to = email;
                String subject = "Khôi phục mật khẩu";
                String body = "Xin chào " + username + ". Mật khẩu của bạn là: " + user.getPassword();

                XMailer.send(to, subject, body);
                request.setAttribute("message", "Mật khẩu đã được gửi về email!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Không thể gửi email!");
        }
        request.getRequestDispatcher("/views/forgotPassword.jsp").forward(request, response);
    }
}