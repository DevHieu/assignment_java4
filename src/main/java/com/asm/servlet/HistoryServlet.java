package com.asm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asm.dao.VideoDetailDAO;
import com.asm.dao.impl.VideoDetailDAOImpl;
import com.asm.dto.VideoDetailDTO; 
import com.asm.entity.User; 

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private VideoDetailDAO videoDetailDAO = new VideoDetailDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login"); 
            return;
        }

        User user = (User) session.getAttribute("user");
        String userId = user.getId();
        
        int page = 1; 
        int offset = 10;
        
        try {
            List<VideoDetailDTO> historyVideos = videoDetailDAO.findHistoryByUserId(userId, page, offset);
            
            request.setAttribute("historyVideos", historyVideos);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/views/history.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}