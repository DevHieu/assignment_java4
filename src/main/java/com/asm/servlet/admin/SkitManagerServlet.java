package com.asm.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;

@WebServlet("/admin/skit/*")
public class SkitManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private VideoDAO videoDAO = new VideoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/skitManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getPathInfo();
        System.out.println("type: " + type);
        switch (type) {
            case "/removeBanner":
                String videoId = request.getParameter("videoId");
                videoDAO.removeBanner(videoId);
                System.out.println("Removed banner for video ID: " + videoId);
                break;

        }

        response.sendRedirect("/admin/home");
    }
}