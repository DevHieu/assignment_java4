package unit_test;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.servlet.admin.UserManagerServlet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserManagerUnitTest extends UserManagerServlet {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    UserDAO mockDAO;

    @BeforeMethod
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        mockDAO = mock(UserDAO.class);

        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("");
    }

    @Override
    protected UserDAO getUserDAO() {
        return mockDAO;
    }

    // UM-07
    @Test
    public void testCreateUser_IdNull() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/save");
        when(request.getParameter("id")).thenReturn("");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("confirmPassword")).thenReturn("123");

        doPost(request, response);

        verify(session).setAttribute(eq("error"), contains("ID không được để trống"));
    }

    // UM-08
    @Test
    public void testCreateUser_PasswordMismatch() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/save");
        when(request.getParameter("id")).thenReturn("user01");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("confirmPassword")).thenReturn("456");

        when(mockDAO.findById("user01")).thenReturn(null);

        doPost(request, response);

        verify(session).setAttribute(eq("error"), contains("Mật khẩu không khớp"));
    }

    // UM-09
    @Test
    public void testUpdateUser_UpdatePasswordSuccess() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/save");
        when(request.getParameter("id")).thenReturn("user01");
        when(request.getParameter("password")).thenReturn("newpass");
        when(request.getParameter("confirmPassword")).thenReturn("newpass");
        when(request.getParameter("fullname")).thenReturn("Test User");
        when(request.getParameter("email")).thenReturn("test@gmail.com");
        when(request.getParameter("role")).thenReturn("USER");

        User existing = new User();
        existing.setId("user01");
        existing.setPassword("oldpass");

        when(mockDAO.findById("user01")).thenReturn(existing);

        doPost(request, response);

        assert existing.getPassword().equals("newpass");

        verify(mockDAO).update(existing);
        verify(session).setAttribute(eq("message"), contains("Cập nhật"));
    }

    // UM-11
    @Test
    public void testUpdateUser_FullnameNull() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/save");
        when(request.getParameter("id")).thenReturn("user01");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("confirmPassword")).thenReturn("");
        when(request.getParameter("fullname")).thenReturn("");

        User existing = new User();
        existing.setId("user01");

        when(mockDAO.findById("user01")).thenReturn(existing);

        doPost(request, response);

        verify(session).setAttribute(eq("error"), contains("Fullname"));
    }

    // UM-13
    @Test
    public void testDeleteAdmin_MainAdmin() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/delete");
        when(request.getParameter("id")).thenReturn("admin");

        doPost(request, response);

        verify(session).setAttribute(eq("error"), contains("Không được xóa"));
        verify(mockDAO, never()).deleteById(anyString());
    }
}