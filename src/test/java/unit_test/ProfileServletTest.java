package unit_test;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.servlet.ProfileServlet;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import static org.mockito.Mockito.*;

public class ProfileServletTest {

    @InjectMocks
    private ProfileServlet servlet;

    @Mock private UserDAO userDAO;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
        servlet.setUserDAO(userDAO);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    // UT-PRO-01: Chưa login -> redirect về /login
    @Test
    public void UT_PRO_01_NotLoggedIn() throws Exception {
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getContextPath()).thenReturn("");

        servlet.doPost(request, response);

        verify(response).sendRedirect(contains("/login"));
        verify(userDAO, never()).update(any());
    }

    // UT-PRO-02: Update profile thành công
    @Test
    public void UT_PRO_02_UpdateProfile_Success() throws Exception {
        User user = new User();
        user.setId("test1");
        user.setEmail("old@gmail.com");

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("updateProfile");
        when(request.getParameter("fullname")).thenReturn("Test");
        when(request.getParameter("email")).thenReturn("test@gmail.com");
        when(userDAO.findById("test1")).thenReturn(user);

        servlet.doPost(request, response);

        verify(userDAO, times(1)).update(any());
        verify(request).setAttribute("message", "Cập nhật thông tin hồ sơ thành công!");
    }

    // UT-PRO-03: Đổi mật khẩu thành công
    @Test
    public void UT_PRO_03_UpdatePassword_Success() throws Exception {
        User user = new User();
        user.setId("test1");
        user.setPassword("123");

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("updatePassword");
        when(request.getParameter("current-password")).thenReturn("123");
        when(request.getParameter("new-password")).thenReturn("456");
        when(userDAO.findById("test1")).thenReturn(user);

        servlet.doPost(request, response);

        verify(userDAO, times(1)).update(any());
        verify(request).setAttribute("message", "Đổi mật khẩu thành công!");
    }

    // UT-PRO-04: Sai mật khẩu hiện tại
    @Test
    public void UT_PRO_04_UpdatePassword_WrongCurrent() throws Exception {
        User user = new User();
        user.setId("test1");
        user.setPassword("123");

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("updatePassword");
        when(request.getParameter("current-password")).thenReturn("wrong");
        when(request.getParameter("new-password")).thenReturn("456");
        when(userDAO.findById("test1")).thenReturn(user);

        servlet.doPost(request, response);

        verify(userDAO, never()).update(any());
        verify(request).setAttribute("message", "Mật khẩu hiện tại không đúng.");
    }

    // UT-PRO-05: Update avatar thành công
    @Test
    public void UT_PRO_05_UpdateAvatar_Success() throws Exception {
        User user = new User();
        user.setId("test1");

        Part mockPart = mock(Part.class);
        when(mockPart.getSize()).thenReturn(1024L);
        when(mockPart.getSubmittedFileName()).thenReturn("avatar.png");
        when(mockPart.getInputStream()).thenReturn(
                new java.io.ByteArrayInputStream("fake-image".getBytes())
        );

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("updateAvatar");
        when(request.getPart("avatar-file")).thenReturn(mockPart);
        when(userDAO.findById("test1")).thenReturn(user);

        // Mock servletContext để tránh NPE khi uploadFile() chạy
        javax.servlet.ServletContext ctx = mock(javax.servlet.ServletContext.class);
        when(request.getServletContext()).thenReturn(ctx);
        when(ctx.getRealPath("/")).thenReturn(System.getProperty("java.io.tmpdir"));

        servlet.doPost(request, response);

        verify(userDAO, times(1)).update(any());
        verify(request).setAttribute("message", "Cập nhật avatar thành công!");
    }

    // UT-PRO-06: Không chọn file avatar
    @Test
    public void UT_PRO_06_UpdateAvatar_NoFile() throws Exception {
        User user = new User();
        user.setId("test1");

        Part mockPart = mock(Part.class);
        when(mockPart.getSize()).thenReturn(0L);

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("updateAvatar");
        when(request.getPart("avatar-file")).thenReturn(mockPart);
        when(userDAO.findById("test1")).thenReturn(user);

        servlet.doPost(request, response);

        verify(userDAO, never()).update(any());
        verify(request).setAttribute("message", "Vui lòng chọn file ảnh.");
    }

    // UT-PRO-07: Xóa avatar thành công
    @Test
    public void UT_PRO_07_DeleteAvatar_Success() throws Exception {
        User user = new User();
        user.setId("test1");
        user.setAvatar("/uploads/avatars/old.png");

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("deleteAvatar");
        when(userDAO.findById("test1")).thenReturn(user);

        servlet.doPost(request, response);

        verify(userDAO, times(1)).update(any());
        verify(request).setAttribute("message", "Xóa avatar thành công!");
    }
}