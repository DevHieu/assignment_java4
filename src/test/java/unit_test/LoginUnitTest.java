package unit_test;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.servlet.LoginServlet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class LoginUnitTest extends LoginServlet {

    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;

    UserDAO mockDAO;

    @BeforeMethod
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        mockDAO = mock(UserDAO.class);

        when(request.getRequestDispatcher("/views/login.jsp"))
                .thenReturn(dispatcher);
    }

    @Override
    protected UserDAO getUserDAO() {
        return mockDAO;
    }

    // LOG-003 
    @Test
    public void testWrongPassword() throws Exception {

        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("abc");

        User user = new User();
        user.setId("admin");
        user.setPassword("admin123");

        when(mockDAO.findById("admin")).thenReturn(user);

        doPost(request, response);

        verify(request).setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
        verify(dispatcher).forward(request, response);
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
}