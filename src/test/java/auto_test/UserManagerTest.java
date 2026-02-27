package auto_test;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class UserManagerTest extends BaseTest {

    @BeforeMethod
    public void setupPage() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("http://localhost:9090/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        // driver.findElement(By.name("password")).sendKeys("new123");
        driver.findElement(By.id("btnLogin")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("home"));

        driver.get("http://localhost:9090/admin/users");
    }

    // UM-01
    @Test
    public void testDisplayUserList() {
        Assert.assertTrue(driver.getPageSource().contains("User Management"));
    }

    // UM-02
    @Test
    public void testPaginationValid() {
        driver.get("http://localhost:9090/admin/users?page=2");
        Assert.assertTrue(driver.getCurrentUrl().contains("page=2"));
    }

    // UM-03
    @Test
    public void testPageLessThanOne() {
        driver.get("http://localhost:9090/admin/users?page=0");
        Assert.assertTrue(driver.getCurrentUrl().contains("page=1")
                || driver.getPageSource().contains("User Management"));
    }

    // UM-04
    @Test
    public void testPageNotNumber() {
        driver.get("http://localhost:9090/admin/users?page=abc");
        Assert.assertTrue(driver.getPageSource().contains("User Management"));
    }

    // UM-05
    @Test
    public void testSearchByKeyword() {

        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("an");

        driver.findElement(By.id("btnSearch")).click();

        Assert.assertTrue(driver.getPageSource().contains("an"));
    }

    // UM-06
    @Test
    public void testSearchByRole() {
        driver.findElement(By.id("role")).click();
        driver.findElement(By.xpath("//option[text()='Admin']")).click();

        driver.findElement(By.id("btnSearch")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("role=ADMIN"));
    }

    // UM-10
    @Test
    public void testAddUserMissingId() {
        driver.findElement(By.id("btnAddUser")).click();

        driver.findElement(By.name("fullname")).sendKeys("Test User");
        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.name("confirmPassword")).sendKeys("123");

        driver.findElement(By.id("btnSave")).click();

        Assert.assertTrue(driver.getPageSource().contains("ID không được để trống"));
    }

    // UM-12
    @Test
    public void testUpdateUserNoPassword() {
        driver.findElement(By.linkText("edit")).click();

        WebElement pw = driver.findElement(By.name("password"));
        pw.clear();

        driver.findElement(By.id("btnSave")).click();

        Assert.assertTrue(driver.getPageSource().contains("Cập nhật người dùng thành công"));
    }

    // UM-14
    @Test
    public void testUploadAvatar() {

        driver.findElement(By.linkText("edit")).click();

        WebElement fileInput = driver.findElement(By.name("avatarFile"));

        // Lấy đường dẫn tuyệt đối tới file trong project
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "\\src\\main\\webapp\\images\\baner3.jpg";

        fileInput.sendKeys(filePath);

        driver.findElement(By.id("btnSave")).click();

        Assert.assertTrue(driver.getPageSource()
                .contains("Cập nhật người dùng thành công"));
    }

    // UM-15
    @Test
    public void testFullnameNull() {
        driver.findElement(By.linkText("edit")).click();

        WebElement fullname = driver.findElement(By.name("fullname"));
        fullname.clear();

        driver.findElement(By.id("btnSave")).click();

        Assert.assertTrue(driver.getPageSource().contains("Fullname không được để trống"));
    }

    // UM-16
    @Test
    public void testDeleteUserValid() {

        driver.get("http://localhost:9090/admin/users?q=test");

        driver.findElement(By.id("btnDelete")).click();

        driver.switchTo().alert().accept();

        Assert.assertFalse(driver.getPageSource().contains("user999"));
    }

    // UM-17
    @Test
    public void testDeleteMainAdmin() {
        driver.findElement(By.id("role")).click();
        driver.findElement(By.xpath("//option[text()='Admin']")).click();

        driver.findElement(By.id("btnSearch")).click();
        driver.findElement(By.id("btnDelete")).click();
        driver.switchTo().alert().accept();

        Assert.assertTrue(driver.getPageSource()
                .contains("Không được xóa tài khoản admin chính"));
    }
}

// UM-07
// @Test
// public void testSearchKeywordAndRole() {
// driver.findElement(By.name("q")).sendKeys("an");
//
// driver.findElement(By.id("role")).click();
// driver.findElement(By.xpath("//option[text()='Admin']")).click();
//
// driver.findElement(By.id("btnSearch")).click();
// Assert.assertTrue(driver.getCurrentUrl().contains("q=an&role=ADMIN"));
// }

// UM-08
// @Test
// public void testLoadUserEdit() {
// driver.findElement(By.linkText("edit")).click();
// Assert.assertTrue(driver.getPageSource().contains("Edit User"));
// }

// UM-09
// @Test
// public void testAddUserValid() {
// driver.findElement(By.id("btnAddUser")).click();
//
// driver.findElement(By.name("id")).sendKeys("user999");
// driver.findElement(By.name("fullname")).sendKeys("Test User");
// driver.findElement(By.name("email")).sendKeys("test999@gmail.com");
// driver.findElement(By.name("password")).sendKeys("123");
// driver.findElement(By.name("confirmPassword")).sendKeys("123");
//
// driver.findElement(By.cssSelector("#userModal
// button[type='submit']")).click();
//
// Assert.assertTrue(driver.getPageSource().contains("Thêm người dùng thành
// công"));
// }

// UM-11
// @Test
// public void testPasswordNotMatch() {
// driver.findElement(By.id("btnAddUser")).click();
//
// driver.findElement(By.name("id")).sendKeys("user998");
// driver.findElement(By.name("fullname")).sendKeys("Test User");
// driver.findElement(By.name("email")).sendKeys("test@gmail.com");
// driver.findElement(By.name("password")).sendKeys("123");
// driver.findElement(By.name("confirmPassword")).sendKeys("456");
//
// driver.findElement(By.id("btnSave")).click();
//
// Assert.assertTrue(driver.getPageSource().contains("Mật khẩu không khớp"));
// }

// UM-13
// @Test
// public void testUpdateUserChangePassword() {
// driver.findElement(By.linkText("edit")).click();
//
// driver.findElement(By.name("password")).sendKeys("admin123");
// driver.findElement(By.name("confirmPassword")).sendKeys("admin123");
//
// driver.findElement(By.id("btnSave")).click();
//
// Assert.assertTrue(driver.getPageSource().contains("Cập nhật người dùng thành
// công"));
// }
