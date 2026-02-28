package unit_test;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.servlet.admin.UserManagerServlet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserManagerUnitTest extends UserManagerServlet {

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
    }

    @Override
    protected UserDAO getUserDAO() {
        return mockDAO;
    }

    // UM-02
    @Test
    public void testPagination_Page2() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users");
        when(request.getParameter("page")).thenReturn("2");

        when(request.getRequestDispatcher("/views/admin/userManager.jsp"))
                .thenReturn(dispatcher);

        doNothing().when(dispatcher).forward(request, response);

        doGet(request, response);

        verify(request).setAttribute(eq("currentPage"), eq(2));
    }

    // UM-03
    @Test
    public void testPagination_InvalidPage() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users");
        when(request.getParameter("page")).thenReturn("0");

        when(request.getRequestDispatcher("/views/admin/userManager.jsp"))
                .thenReturn(dispatcher);

        doNothing().when(dispatcher).forward(request, response);

        doGet(request, response);

        verify(request).setAttribute(eq("currentPage"), eq(1));
    }

    // UM-04
    @Test
    public void testSearchUser_ByKeywordAndRole() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users");
        when(request.getParameter("q")).thenReturn("an");
        when(request.getParameter("role")).thenReturn("ADMIN");
        when(request.getParameter("page")).thenReturn(null);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/views/admin/userManager.jsp"))
                .thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(request, response);

        doGet(request, response);

        verify(request).setAttribute("paramQ", "an");
        verify(request).setAttribute("paramRole", "ADMIN");
        verify(request).setAttribute("currentPage", 1);
    }

    // UM-05
    @Test
    public void testLoadUserEdit() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/edit");

        when(request.getParameter("id")).thenReturn("user01");

        User user = new User();
        user.setId("user01");

        when(mockDAO.findById("user01")).thenReturn(user);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/views/admin/userManager.jsp"))
                .thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(request, response);

        doGet(request, response);

        verify(mockDAO).findById("user01");
        verify(request).setAttribute("userEdit", user);
    }

    // UM-06
    @Test
    public void testCreateUser_Success() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/save");
        when(request.getParameter("id")).thenReturn("newuser");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("confirmPassword")).thenReturn("123");
        when(request.getParameter("fullname")).thenReturn("New User");
        when(request.getParameter("email")).thenReturn("new@gmail.com");
        when(request.getParameter("role")).thenReturn("USER");

        when(mockDAO.findById("newuser")).thenReturn(null);

        doPost(request, response);

        verify(mockDAO).create(argThat(user -> user.getId().equals("newuser")));

        verify(session).setAttribute(eq("message"), contains("Thêm"));
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

    // UM-10
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

    // UM-11
    @Test
    public void testDeleteUser_Success() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/delete");
        when(request.getParameter("id")).thenReturn("user01");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(request.getContextPath()).thenReturn("");

        doPost(request, response);

        verify(mockDAO).deleteById("user01");
        verify(session).setAttribute("message", "Xóa thành công!");
        verify(response).sendRedirect("/admin/users");
    }

    // UM-12
    @Test
    public void testDeleteAdmin_MainAdmin() throws Exception {

        when(request.getRequestURI()).thenReturn("/admin/users/delete");
        when(request.getParameter("id")).thenReturn("admin");

        doPost(request, response);

        verify(session).setAttribute(eq("error"), contains("Không được xóa"));
        verify(mockDAO, never()).deleteById(anyString());
    }
}