package com.asm.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.asm.dao.impl.UserDAOImpl;
import com.asm.entity.User;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAOImpl userDAO;

    @Override
    public void init() throws ServletException {
        this.userDAO = new UserDAOImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = userDAO.findById(currentUser.getId());
        request.setAttribute("user", user);

        request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String message = "";
        String alertType = "success";

        try {
            User user = userDAO.findById(currentUser.getId());

            if ("updateProfile".equals(action)) {
                user.setFullname(request.getParameter("fullname"));
                user.setEmail(request.getParameter("email"));
                userDAO.update(user);
                message = "Cập nhật thông tin hồ sơ thành công!";
            } else if ("updatePassword".equals(action)) {
                String currentPwd = request.getParameter("current-password");
                String newPwd = request.getParameter("new-password");

                if (user.getPassword().equals(currentPwd)) {
                    user.setPassword(newPwd);
                    userDAO.update(user);
                    message = "Đổi mật khẩu thành công!";
                } else {
                    message = "Mật khẩu hiện tại không đúng.";
                    alertType = "danger";
                }
            } else if ("updateAvatar".equals(action)) {
                Part part = request.getPart("avatar-file");

                if (part != null && part.getSize() > 0) {
                    String avatarUrl = "/uploads/avatars/" + uploadFile(part, request);
                    user.setAvatar(avatarUrl);
                    userDAO.update(user);
                    message = "Cập nhật avatar thành công!";
                } else {
                    message = "Vui lòng chọn file ảnh.";
                    alertType = "warning";
                }
            } else if ("deleteAvatar".equals(action)) {
                user.setAvatar(null);
                userDAO.update(user);
                message = "Xóa avatar thành công!";
            } else {
                message = "Hành động không hợp lệ!";
                alertType = "warning";
            }

            session.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Lỗi khi xử lý: " + e.getMessage();
            alertType = "danger";
        }

        request.setAttribute("message", message);
        request.setAttribute("alertType", alertType);
        doGet(request, response);
    }
}
