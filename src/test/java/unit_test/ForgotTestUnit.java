package unit_test;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.servlet.ForgotPassword;
import com.asm.utils.IMailer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgotTestUnit {
    @InjectMocks
    private ForgotPassword servlet;

    @Mock private IMailer xMailer;
    @Mock private UserDAO userDAO;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher dispatcher;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
        servlet.setUserDAO(userDAO);
        when(request.getRequestDispatcher("/views/forgotPassword.jsp"))
                .thenReturn(dispatcher);
    }

    @Test
    public void UT_FP_01_UsernameNotExist() throws Exception {

        when(request.getParameter("username")).thenReturn("abc");
        when(request.getParameter("email")).thenReturn("a@gmail.com");

        when(userDAO.findById("abc")).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).setAttribute("message",
                "Tài khoản không tồn tại!");
    }

    @Test
    public void UT_FP_02_EmailMismatch() throws Exception {

        User user = new User();
        user.setId("test1");
        user.setEmail("correct@gmail.com");

        when(request.getParameter("username")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("wrong@gmail.com");

        when(userDAO.findById("test1")).thenReturn(user);

        servlet.doPost(request, response);

        verify(request).setAttribute("message",
                "Email không khớp với tài khoản!");
    }

    @Test
    public void UT_FP_03_Success() throws Exception {

        User user = new User();
        user.setId("test1");
        user.setEmail("correct@gmail.com");

        when(request.getParameter("username")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("correct@gmail.com");

        when(userDAO.findById("test1")).thenReturn(user);

        servlet.doPost(request, response);

        verify(request).setAttribute("message",
                "Mật khẩu đã được gửi về email!");
    }

    @Test
    public void UT_FP_04_SendMailException() throws Exception {

        User user = new User();
        user.setId("test1");
        user.setEmail("test@gmail.com");
        user.setPassword("123");

        when(request.getParameter("username")).thenReturn("test1");
        when(request.getParameter("email")).thenReturn("test@gmail.com");

        when(userDAO.findById("test1")).thenReturn(user);

        when(xMailer.send(anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Mail error"));

        servlet.doPost(request, response);

        verify(request).setAttribute(
                eq("message"),
                contains("Không thể gửi email!")
        );
    }
}
