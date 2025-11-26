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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        UserDAO userDAO = new UserDAOImpl();
        String error = null;
        try {
            if(fullname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                error = "Vui lòng điền đầy đủ thông tin!";
            } else if(!password.equals(confirmPassword)){
                error = "Mật khẩu xác nhận không khớp!";
            } else if(userDAO.checkUsernameExist(username)){
                error = "Tên đăng nhập đã tồn tại!";
            } else if(userDAO.checkEmailExist(email)){
                error = "Email đã tồn tại!";
            } else {
                User user = new User();
                user.setFullname(fullname);
                user.setId(username);
                user.setEmail(email);
                user.setPassword(password);

                userDAO.create(user);

                request.setAttribute("success", "Đăng ký thành công! Bạn có thể đăng nhập ngay.");
                request.getRequestDispatcher("/views/register.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("error", error);
            request.setAttribute("fullname", fullname);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra, vui lòng thử lại!");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        }
    }
}
