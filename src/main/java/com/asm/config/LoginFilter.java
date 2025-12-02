package com.asm.config;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.asm.dao.impl.UserDAOImpl;
import com.asm.entity.User;

@WebFilter("/*") // chạy cho mọi request
public class LoginFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // Nếu cần khởi tạo gì đó thì viết ở đây
    System.out.println("LoginFilter initialized");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;

    HttpSession session = req.getSession(false); // không tạo mới
    User user = (session != null) ? (User) session.getAttribute("user") : null;

    // Nếu chưa có session user thì thử lấy cookie
    if (user == null) {
      Cookie[] cookies = req.getCookies();
      if (cookies != null) {
        for (Cookie c : cookies) {
          if ("user".equals(c.getName())) {
            try {
              String decoded = new String(Base64.getDecoder().decode(c.getValue()));
              String[] parts = decoded.split("\\|");
              String username = parts[0];

              User u = new UserDAOImpl().findById(username);

              // tạo session mới
              HttpSession newSession = req.getSession(true);
              newSession.setAttribute("user", u);

            } catch (Exception e) {
              e.printStackTrace();
            }
            break;
          }
        }
      }
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // cleanup
  }
}
