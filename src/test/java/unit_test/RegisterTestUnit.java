package unit_test;

import com.asm.dao.UserDAO;
import com.asm.servlet.RegisterServlet;
import com.asm.utils.IMailer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterTestUnit {

    @InjectMocks
    private RegisterServlet servlet;

    @Mock private UserDAO userDAO;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;
    @Mock private IMailer xMailer;

    private AutoCloseable closeable;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        servlet.setUserDAO(userDAO);
        when(request.getRequestDispatcher("/views/register.jsp"))
                .thenReturn(dispatcher);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void TC01_EmptyFields() throws Exception {

        when(request.getParameter("fullname")).thenReturn("");
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("confirmPassword")).thenReturn("");

        servlet.doPost(request, response);

        verify(userDAO, never()).create(any());
        verify(request).setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void TC02_PasswordMismatch() throws Exception {

        when(request.getParameter("fullname")).thenReturn("Test");
        when(request.getParameter("username")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("a@gmail.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("confirmPassword")).thenReturn("456");

        servlet.doPost(request, response);

        verify(userDAO, never()).create(any());
        verify(request).setAttribute("message", "Mật khẩu xác nhận không khớp!");
    }

    @Test
    public void TC03_UsernameExist() throws Exception {

        when(request.getParameter("fullname")).thenReturn("Test");
        when(request.getParameter("username")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("a@gmail.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("confirmPassword")).thenReturn("123");

        when(userDAO.checkUsernameExist("test1")).thenReturn(true);

        servlet.doPost(request, response);

        verify(userDAO, never()).create(any());
        verify(request).setAttribute("message", "Tên đăng nhập đã tồn tại!");
    }

    @Test
    public void TC04_EmailExist() throws Exception {

        when(request.getParameter("fullname")).thenReturn("Test");
        when(request.getParameter("username")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("a@gmail.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("confirmPassword")).thenReturn("123");

        when(userDAO.checkUsernameExist("test1")).thenReturn(false);
        when(userDAO.checkEmailExist("a@gmail.com")).thenReturn(true);

        servlet.doPost(request, response);

        verify(userDAO, never()).create(any());
        verify(request).setAttribute("message", "Email đã tồn tại!");
    }

    @Test
    public void TC05_RegisterSuccess() throws Exception {

        when(request.getParameter("fullname")).thenReturn("Test");
        when(request.getParameter("username")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("a@gmail.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("confirmPassword")).thenReturn("123");

        when(userDAO.checkUsernameExist("test1")).thenReturn(false);
        when(userDAO.checkEmailExist("a@gmail.com")).thenReturn(false);

        servlet.doPost(request, response);

        verify(userDAO, times(1)).create(any());
        verify(request).setAttribute("message",
                "Đăng ký thành công! Bạn có thể đăng nhập ngay.");
    }
}