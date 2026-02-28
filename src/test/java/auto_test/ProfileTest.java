package auto_test;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LoginPage;
import page.ProfilePage;
import java.io.File;

public class ProfileTest extends BaseTest {
    ProfilePage profilePage;

    @BeforeMethod
    public void loginAndGoToProfile() {
        // Giả định đã có LoginPage để thực hiện đăng nhập trước khi vào Profile
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login("admin", "123"); // Thay bằng user thật trong DB của bạn

        profilePage = new ProfilePage(driver);
        profilePage.open();
    }

    @Test // PRO-03
    public void SEL_PRO_01_UpdateProfileSuccess() {
        profilePage.updateProfile("Nguyen Van Test", "test@gmail.com");
        Assert.assertTrue(profilePage.getAlertMessage().contains("Cập nhật thông tin hồ sơ thành công"));
    }

    @Test // PRO-09
    public void SEL_PRO_02_ChangePasswordSuccess() {
        profilePage.updatePassword("123", "456", "456");
        Assert.assertTrue(profilePage.getAlertMessage().contains("Đổi mật khẩu thành công"));
    }

    @Test // PRO-10
    public void SEL_PRO_03_ChangePasswordWrongCurrent() {
        profilePage.updatePassword("wrong_pass", "456", "456");
        Assert.assertTrue(profilePage.getAlertMessage().contains("Mật khẩu hiện tại không đúng"));
    }

    @Test // PRO-06 - Sửa lỗi File Not Found
    public void SEL_PRO_04_UploadAvatarSuccess() {
        // Tạo file tạm nếu chưa có để Selenium không báo lỗi "File not found"
        File file = new File("src/test/resources/avatar.jpg");
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try { file.createNewFile(); } catch (Exception e) {}
        }

        profilePage.uploadAvatar(file.getAbsolutePath());
        Assert.assertTrue(profilePage.getAlertMessage().contains("Cập nhật avatar thành công!"));
    }

    @Test // PRO-08
    public void PRO_08_DeleteAvatarSuccess() {
        profilePage.deleteAvatar();
        Assert.assertTrue(profilePage.getAlertMessage().contains("Xóa avatar thành công"));
    }

    @AfterMethod
    public void resetPassword() {
        try {
            profilePage.open();
            profilePage.updatePassword("456", "123", "123");
        } catch (Exception e) {
        }
    }

}