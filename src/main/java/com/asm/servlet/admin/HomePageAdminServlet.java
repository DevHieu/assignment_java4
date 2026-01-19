package com.asm.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asm.dao.AdminDAO;
import com.asm.dao.FavoriteDAO;
import com.asm.dao.VideoDAO;
import com.asm.dao.impl.AdminDAOImpl;
import com.asm.dao.impl.FavoriteDAOImpl;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.dto.AdminStatDTO;
import com.asm.dto.VideoDetailDTO;
import com.asm.entity.Video;

@WebServlet("/admin/home")
public class HomePageAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AdminDAO adminDAO = new AdminDAOImpl();
    private VideoDAO videoDAO = new VideoDAOImpl();
    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminStatDTO stats = adminDAO.getAdminStat();
        List<VideoDetailDTO> top10Videos = favoriteDAO.get10MostLikeVideo();
        List<Video> bannerVideos = videoDAO.getBannerVideo();

        request.setAttribute("top10Videos", top10Videos);
        request.setAttribute("bannerVideos", bannerVideos);
        request.setAttribute("totalVideo", stats.getVideoCount());
        request.setAttribute("totalUser", stats.getUserCount());
        request.setAttribute("totalLike", stats.getLikeCount());
        request.setAttribute("totalShare", stats.getShareCount());
        request.getRequestDispatcher("/views/admin/homePageAdmin.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
