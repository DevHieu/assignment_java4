package com.asm.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asm.dao.UserDAO;
import com.asm.dao.impl.UserDAOImpl;
import com.asm.entity.User;

@WebServlet("/admin/users")
public class UserManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String q = request.getParameter("q");
        String roleStr = request.getParameter("role");
        String pageStr = request.getParameter("page");

        int page = 1;
        int size = 10;

        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<User> users;
        int totalPages = 1;

        // 2. Xử lý tìm kiếm hoặc lọc
        if (q != null && !q.trim().isEmpty()) {
            // Tìm kiếm theo tên hoặc email
            users = userDAO.searchByKeyword(q.trim());
        } else if (roleStr != null && !roleStr.isEmpty()) {
            // Lọc theo vai trò
            boolean isAdmin = "true".equalsIgnoreCase(roleStr) || "ADMIN".equalsIgnoreCase(roleStr);
            users = userDAO.findByRole(isAdmin);
        } else {
            // Lấy theo trang (mặc định)
            users = userDAO.findPage(page - 1, size); // page - 1 vì DAO bắt đầu từ 0
            int totalUsers = userDAO.count();
            totalPages = (int) Math.ceil((double) totalUsers / size);
            if (totalPages == 0) totalPages = 1;
        }

        // 3. Đưa dữ liệu vào request
        request.setAttribute("users", users);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageCount", totalPages);
        
        // Giữ lại giá trị đã tìm kiếm để hiển thị lại trên form
        request.setAttribute("paramQ", q);
        request.setAttribute("paramRole", roleStr);

        // 4. Forward sang JSP
        request.getRequestDispatcher("/views/admin/userManager.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}