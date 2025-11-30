package com.asm.servlet.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 2 * 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
@WebServlet("/admin/skit/*")
public class SkitManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private VideoDAO videoDAO = new VideoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();

        if (uri.contains("/edit")) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                request.setAttribute("videoEdit", videoDAO.findById(id));
            }
        }

        String q = request.getParameter("q");
        String pageStr = request.getParameter("page");
        int page = 1, size = 10;

        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1)
                    page = 1;
            } catch (Exception ignored) {
            }
        }

        List<Video> videos;
        int totalPages = 1;

        if (q != null && !q.trim().isEmpty()) {
            videos = videoDAO.searchByTitle(q.trim());
            totalPages = 1;
            page = 1;
        } else {
            videos = videoDAO.findPage((page - 1) * size, size);
            int total = videoDAO.countAll();
            totalPages = (int) Math.ceil(total * 1.0 / size);
        }

        request.setAttribute("videos", videos);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageCount", totalPages > 0 ? totalPages : 1);
        request.setAttribute("paramQ", q);

        request.getRequestDispatcher("/views/admin/skitManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String type = request.getPathInfo();
        System.out.println("Action: " + type);

        try {
            if ("/save".equals(type)) {
                String youtubeId = request.getParameter("youtubeId");
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                System.out.println("Youtube ID: " + youtubeId);
                String status = request.getParameter("status");
                boolean active = "ACTIVE".equalsIgnoreCase(status);
                boolean isBanner = "true".equals(request.getParameter("isBanner"));

                Part posterPart = request.getPart("posterFile");

                if (youtubeId == null || youtubeId.trim().isEmpty() || title == null || title.trim().isEmpty()) {
                    request.getSession().setAttribute("error", "Vui lòng nhập ID và tiêu đề!");
                    response.sendRedirect(request.getContextPath() + "/admin/skit");
                    return;
                }

                Video video = videoDAO.findById(youtubeId);
                boolean isUpdate = (video != null);

                if (!isUpdate && videoDAO.findById(youtubeId) != null) {
                    request.getSession().setAttribute("error", "ID video đã tồn tại!");
                    response.sendRedirect(request.getContextPath() + "/admin/skit");
                    return;
                }

                if (video == null) {
                    video = new Video();
                }

                video.setId(youtubeId.trim());
                video.setTitle(title);
                video.setDescription(description != null ? description : "");
                video.setVideo(youtubeId);
                video.setActive(active);
                video.setBanner(isBanner);

                if (posterPart != null && posterPart.getSize() > 0) {
                    String fileName = uploadFile(posterPart, request, "posters");
                    video.setPoster("/uploads/posters/" + fileName);
                } else if (video != null) {
                    // giữ poster cũ
                    video.setPoster(video.getPoster());
                }

                if (isUpdate) {
                    videoDAO.update(video);
                    request.getSession().setAttribute("message", "Cập nhật video thành công!");
                } else {
                    videoDAO.create(video);
                    request.getSession().setAttribute("message", "Thêm video thành công!");
                }

            } else if ("/delete".equals(type)) {
                String id = request.getParameter("id");
                if (id != null && !id.trim().isEmpty()) {
                    videoDAO.deleteById(id);
                    request.getSession().setAttribute("message", "Xóa video thành công!");
                }

            } else if ("/removeBanner".equals(type)) {
                String videoId = request.getParameter("videoId");
                videoDAO.removeBanner(videoId);
                response.sendRedirect(request.getContextPath() + "/admin/home");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/skit?t=" + System.currentTimeMillis());
    }

    private String uploadFile(Part part, HttpServletRequest request, String folder) throws IOException {
        java.io.File webappDir = new java.io.File(request.getServletContext().getRealPath("/"));

        java.io.File uploadsDir = new java.io.File(webappDir, "uploads/" + folder);
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