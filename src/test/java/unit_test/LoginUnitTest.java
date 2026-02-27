package unit_test;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.servlet.LoginServlet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.util.Base64;

public class LoginUnitTest extends LoginServlet {

    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;
    HttpSession session;

    UserDAO mockDAO;

    @BeforeMethod
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);

        mockDAO = mock(UserDAO.class);

        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");

        when(request.getRequestDispatcher("/views/login.jsp"))
                .thenReturn(dispatcher);
    }

    @Override
    protected UserDAO getUserDAO() {
        return mockDAO;
    }

    // LOG-004
    @Test
    public void testUserNotExists() throws Exception {

        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("123");

        when(mockDAO.findById("test")).thenReturn(null);

        doPost(request, response);

        verify(request).setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
        verify(dispatcher).forward(request, response);
    }

    // LOG-005
    @Test
    public void testEmptyUsername() throws Exception {

        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("123");

        doPost(request, response);

        verify(request).setAttribute("message", "Username không được để trống!");
        verify(dispatcher).forward(request, response);
    }

    // LOG-006
    @Test
    public void testEmptyPassword() throws Exception {

        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("");

        doPost(request, response);

        verify(request).setAttribute("message", "Password không được để trống!");
        verify(dispatcher).forward(request, response);
    }

    // LOG-007
    @Test
    public void testRememberMe() throws Exception {

        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin123");
        when(request.getParameter("remember")).thenReturn("on");

        User user = new User();
        user.setId("admin");
        user.setPassword("admin123");

        when(mockDAO.findById("admin")).thenReturn(user);

        doPost(request, response);

        verify(session).setAttribute("user", user);

        String encoded = Base64.getEncoder()
                .encodeToString("admin".getBytes());

        verify(response).addCookie(argThat(cookie -> cookie.getName().equals("user") &&
                cookie.getValue().equals(encoded)));

        verify(response).sendRedirect("home");
    }

    // LOG-008
    @Test
    public void testSessionCreated() throws Exception {

        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin123");
        when(request.getParameter("remember")).thenReturn("on");

        User user = new User();
        user.setId("admin");
        user.setPassword("admin123");

        when(mockDAO.findById("admin")).thenReturn(user);

        doPost(request, response);

        verify(request).getSession();
        verify(session).setAttribute("user", user);
        verify(response).sendRedirect("home");
    }
}