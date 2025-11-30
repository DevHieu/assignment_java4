package com.asm.servlet.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.asm.dao.UserDAO;
import com.asm.dao.impl.UserDAOImpl;
import com.asm.entity.User;

@WebServlet({
    "/admin/users",
    "/admin/users/save",
    "/admin/users/delete",
    "/admin/users/edit"
})
@MultipartConfig(fileSizeThreshold = 2 * 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 50 * 1024 * 1024)
public class UserManagerServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();

        if (uri.contains("/edit")) {
            String id = req.getParameter("id");
            if (id != null && !id.isEmpty()) {
                req.setAttribute("userEdit", userDAO.findById(id));
            }
        }

        String q = req.getParameter("q");
        String roleStr = req.getParameter("role");
        String pageStr = req.getParameter("page");
        int page = 1, size = 10;

        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (Exception ignored) {}
        }

        List<User> users;
        int totalPages = 1;

        if (q != null && !q.trim().isEmpty()) {
            users = userDAO.searchByKeyword(q.trim());
        } else if (roleStr != null && !roleStr.isEmpty()) {
            boolean isAdmin = "ADMIN".equalsIgnoreCase(roleStr) || "true".equalsIgnoreCase(roleStr);
            users = userDAO.findByRole(isAdmin);
        } else {
            users = userDAO.findPage((page - 1) * size, size);
            int total = userDAO.countAll();
            totalPages = (int) Math.ceil(total * 1.0 / size);
        }

        req.setAttribute("users", users);
        req.setAttribute("currentPage", page);
        req.setAttribute("pageCount", totalPages > 0 ? totalPages : 1);
        req.setAttribute("paramQ", q);
        req.setAttribute("paramRole", roleStr);

        req.getRequestDispatcher("/views/admin/userManager.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();

        try {
            if (uri.contains("/save")) {

                String id = req.getParameter("id");

                User u = userDAO.findById(id); 
                boolean isCreate = (u == null);

                if (isCreate) {
                    if (id == null || id.trim().isEmpty())
                        throw new Exception("ID không được để trống");

                    if (!req.getParameter("password").equals(req.getParameter("confirmPassword")))
                        throw new Exception("Mật khẩu không khớp!");

                    u = new User();
                    u.setId(id);
                    u.setPassword(req.getParameter("password"));
                } else {
                    String pw = req.getParameter("password");
                    String cpw = req.getParameter("confirmPassword");

                    if (pw != null && !pw.isEmpty()) {
                        if (!pw.equals(cpw))
                            throw new Exception("Xác nhận mật khẩu không khớp!");
                        u.setPassword(pw);
                    }
                }

                u.setFullname(req.getParameter("fullname"));
                u.setEmail(req.getParameter("email"));
                u.setAdmin("ADMIN".equals(req.getParameter("role")));

                Part part = req.getPart("avatarFile");
                if (part != null && part.getSize() > 0) {
                    String fileName = uploadFile(part, req);
                    u.setAvatar("/uploads/avatars/" + fileName);
                }
                if (isCreate) {
                    userDAO.create(u);
                    req.getSession().setAttribute("message", "Thêm người dùng thành công!");
                } else {
                    userDAO.update(u);
                    req.getSession().setAttribute("message", "Cập nhật người dùng thành công!");
                }
            }

            else if (uri.contains("/delete")) {
                String id = req.getParameter("id");

                if ("admin".equalsIgnoreCase(id))
                    throw new Exception("Không được xóa tài khoản admin chính!");

                userDAO.deleteById(id);
                req.getSession().setAttribute("message", "Xóa thành công!");
            }

        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }

    private String uploadFile(Part part, HttpServletRequest req) throws IOException {
        java.io.File webappDir = new java.io.File(req.getServletContext().getRealPath("/"));
        java.io.File uploadsDir = new java.io.File(webappDir, "uploads/avatars");
        
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }

        String originalName = java.nio.file.Paths.get(part.getSubmittedFileName())
                .getFileName().toString();

        String ext = originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf("."))
                : "";

        String name = originalName.contains(".")
                ? originalName.substring(0, originalName.lastIndexOf("."))
                : originalName;

        String fileName = name + "_" + System.currentTimeMillis() + ext;

        java.io.File uploadFile = new java.io.File(uploadsDir, fileName);

        try (InputStream input = part.getInputStream()) {
            Files.copy(input, uploadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }
}
