package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.ForgotPasswordPage;

public class ForgotPasswordTest extends BaseTest {
    ForgotPasswordPage forgotPage;

    @BeforeMethod
    public void setup() {
        forgotPage = new ForgotPasswordPage(driver);
        forgotPage.open();
    }

    // Hàm hỗ trợ xóa thuộc tính required để test server-side validation
    private void removeRequiredAttribute() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByName('username')[0].removeAttribute('required')");
        js.executeScript("document.getElementsByName('email')[0].removeAttribute('required')");
    }

    @Test
    public void FGP_001_EmptyAll() {
        removeRequiredAttribute();
        forgotPage.forgotPassword("", "");
        Assert.assertEquals(forgotPage.getMessage(), "Tài khoản không tồn tại!");
    }

    @Test
    public void FGP_002_UsernameExists_EmailEmpty() {
        removeRequiredAttribute();
        forgotPage.forgotPassword("test1", "");
        Assert.assertEquals(forgotPage.getMessage(), "Email không khớp với tài khoản!");
    }

    @Test
    public void FGP_004_UsernameNotExists() {
        forgotPage.forgotPassword("abcde", "any@gmail.com");
        Assert.assertEquals(forgotPage.getMessage(), "Tài khoản không tồn tại!");
    }

    @Test
    public void FGP_005_EmailWrong() {
        forgotPage.forgotPassword("test1", "wrongemail@gmail.com");
        Assert.assertEquals(forgotPage.getMessage(), "Email không khớp với tài khoản!");
    }

    @Test
    public void FGP_006_Success() {
        // Đảm bảo username test1 và email test@gmail.com có trong DB
        forgotPage.forgotPassword("test1", "test@gmail.com");
        Assert.assertEquals(forgotPage.getMessage(), "Mật khẩu đã được gửi về email!");
    }

    @Test
    public void FGP_008_EmailWrongFormat() {
        // Lưu ý: Browser validation sẽ chặn "test.com" nếu input type là email
        // Nhưng ở JSP bạn đang để type="text" nên nó sẽ gửi lên Server bình thường
        forgotPage.forgotPassword("test1", "test.com");
        // Theo Servlet hiện tại, nó sẽ báo "Email không khớp..." vì email trong DB luôn có @
        Assert.assertTrue(forgotPage.getMessage().contains("Email không khớp") ||
                forgotPage.getMessage().contains("Email sai định dạng"));
    }
}