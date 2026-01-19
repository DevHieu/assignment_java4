package com.asm.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asm.dao.FavoriteDAO;
import com.asm.dao.ShareDAO;
import com.asm.dao.impl.FavoriteDAOImpl;
import com.asm.dao.impl.ShareDAOImpl;

@WebServlet("/admin/statistics")
public class StatisticServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
    private ShareDAO shareDAO = new ShareDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tab = request.getParameter("tab");

        if (tab != null) {
            switch (tab) {
                case "favorite-users": {
                    String favTitle = request.getParameter("favTitle");
                    if (favTitle != null) {
                        request.setAttribute("favoriteUsers",
                                favoriteDAO.getFavoriteUserStatisByTitle(favTitle));
                    } else {
                        request.setAttribute("favoriteUsers", null);
                    }
                    request.setAttribute("activeTab", "tab2");
                    break;
                }

                case "shared-friends": {
                    String shareTitle = request.getParameter("shareTitle");
                    if (shareTitle != null) {
                        request.setAttribute("sharedFriends",
                                shareDAO.getShareStatisByTitle(shareTitle));
                    } else {
                        request.setAttribute("sharedFriends", null);
                    }
                    request.setAttribute("activeTab", "tab3");
                    break;
                }
            }
        } else {
            request.setAttribute("activeTab", "tab1");
        }

        request.setAttribute("favoriteStatis", favoriteDAO.getFavoriteStatis());
        request.getRequestDispatcher("/views/admin/statistics.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}