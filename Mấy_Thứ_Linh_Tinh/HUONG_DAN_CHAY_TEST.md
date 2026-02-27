# 📋 Hướng Dẫn Chạy Kiểm Thử Trên IntelliJ IDEA

> **Stack:** Maven · TestNG 7.11 · Selenium 4.40 · Jetty 9 (port 9090) · Java 8+

---

## Phần 1 — Hướng dẫn chạy dự án và test

### 1.1 Yêu cầu môi trường

| Phần mềm | Phiên bản |
|---|---|
| JDK | 8 trở lên |
| IntelliJ IDEA | 2022+ (Community hoặc Ultimate) |
| Google Chrome | Phiên bản mới nhất |
| ChromeDriver | **Cùng phiên bản** với Chrome |
| MySQL Server | 8.x (đang chạy) |
| Maven | Tích hợp sẵn trong IntelliJ |

> 💡 **Kiểm tra phiên bản Chrome:** Chrome → ⋮ → Trợ giúp → Giới thiệu về Google Chrome.
> 💡 **Tải ChromeDriver:** https://googlechromelabs.github.io/chrome-for-testing/

---

### 1.2 Cấu trúc file test

```
src/test/java/
├── page/
│   ├── RegisterPage.java    ← Page Object đăng ký
│   ├── SearchPage.java      ← Page Object tìm kiếm & sắp xếp
│   └── WatchPage.java       ← Page Object xem video & tương tác
└── tests/
    ├── BaseTest.java         ← Khởi tạo / đóng WebDriver
    ├── RegisterTest.java     ← Test đăng ký
    ├── SearchTest.java       ← Test tìm kiếm & sắp xếp
    └── WatchTest.java        ← Test xem video & like/share
```

---

### 1.3 Mở dự án trong IntelliJ

1. Khởi động **IntelliJ IDEA**.
2. **File → Open…** → chọn thư mục `assignment_java4`.
3. Đợi IntelliJ import Maven xong (thanh tiến trình góc dưới phải).

---

### 1.4 Cài đặt ChromeDriver

**Cách 1 — Copy vào thư mục hệ thống:**
- Tải `chromedriver.exe` đúng version → copy vào `C:\Windows\`.

**Cách 2 — Thêm vào System PATH:**
1. Giải nén `chromedriver.exe` vào `C:\chromedriver\`.
2. **Control Panel → System → Advanced → Environment Variables**.
3. Sửa biến `Path` → thêm dòng `C:\chromedriver\`.
4. Restart IntelliJ.

---

### 1.5 Khởi động Web Server (Jetty)

Ứng dụng phải chạy tại `http://localhost:9090` trước khi test.

1. Mở tab **Maven** (góc phải) → `assignment_java4 → Plugins → jetty`.
2. Double-click **`jetty:run`**.
3. Đợi console hiện:
   ```
   [INFO] Started ServerConnector@... {HTTP/1.1,[http/1.1]}{0.0.0.0:9090}
   ```
4. Mở browser truy cập `http://localhost:9090` để xác nhận.

---

### 1.6 Load Maven Dependencies

1. Mở `pom.xml`.
2. Nhấn biểu tượng **🔄 Load Maven Changes** (hoặc `Ctrl+Shift+O`).
3. Đợi IntelliJ tải xong thư viện.

---

### 1.7 Chạy Test

**▶ Chạy 1 method:**
- Mở file test → click **▶ xanh** bên trái method → **Run**.

**▶ Chạy toàn bộ 1 class:**
- Chuột phải vào tên class → **Run 'ClassName'**.

**▶ Chạy bằng Maven (Terminal `Alt+F12`):**

```bash
mvn test                                          # Toàn bộ
mvn test -Dtest=RegisterTest                      # Chỉ đăng ký
mvn test -Dtest=SearchTest                        # Chỉ tìm kiếm
mvn test -Dtest=WatchTest                         # Chỉ xem video
mvn test -Dtest=SearchTest#testSearchWithValidKeyword  # 1 method cụ thể
```

---

### 1.8 Xem kết quả

- **Cửa sổ Run** ở dưới IntelliJ: ✅ Green = PASS, ❌ Red = FAIL.
- **Báo cáo HTML:** `target/surefire-reports/index.html` (mở bằng trình duyệt).

---

### 1.9 Danh sách Test Case

**Đăng ký (`RegisterTest.java`)**

| ID | Method | Mô tả |
|---|---|---|
| REG-001 | `testAllEmptyFields` | Bỏ trống tất cả |
| REG-002 | `testMissingFullnameFields` | Thiếu họ tên |
| REG-003 | `testMissingUsernameFields` | Thiếu username |
| REG-004 | `testMissingEmailFields` | Thiếu email |
| REG-005 | `testMissingPasswordFields` | Thiếu password |
| REG-006 | `testWrongPasswordRepeat` | Mật khẩu xác nhận sai |
| REG-007 | `testUsernameExisted` | Username đã tồn tại |
| REG-008 | `testEmailExisted` | Email đã tồn tại |
| REG-009 | `testRegisterSuccess` | Đăng ký thành công |

**Tìm kiếm & Sắp xếp (`SearchTest.java`)**

| ID | Method | Mô tả |
|---|---|---|
| SEA-001 | `testSearchWithValidKeyword` | Tìm từ khóa "Hài" |
| SEA-002 | `testSearchWithEmptyKeyword` | Tìm chuỗi rỗng → load all |
| SEA-003 | `testSearchWithNonExistentKeyword` | Tìm "XYZ123" → 0 kết quả |
| SRT-001 | `testSortByMostViews` | Sắp xếp views giảm dần |
| SRT-002 | `testSortByLeastViews` | Sắp xếp views tăng dần |
| SRT-003 | `testSortByMostLikes` | Sắp xếp likes cao nhất |
| SRT-004 | `testSortByAZ` | Sắp xếp A → Z |
| SRT-005 | `testFilterMaintainedOnPageTwo` | Giữ lọc khi sang trang 2 |

**Xem video & Tương tác (`WatchTest.java`)**

| ID | Method | Mô tả |
|---|---|---|
| WAT-001 | `testVideoDetailDisplay` | Hiển thị chi tiết video V001 |
| WAT-002 | `testYoutubeIframeLoaded` | Kiểm tra Iframe YouTube |
| WAT-003 | `testViewCountAutoIncrement` | Tự động tăng lượt xem |
| WAT-004 | `testRecommendedVideosDisplayed` | Hiển thị video đề xuất |
| WAT-005 | `testWatchHistorySaved` | Lưu lịch sử xem (đã đăng nhập) |
| ACT-001 | `testLikeVideo` | Thích video |
| ACT-002 | `testUnlikeVideo` | Bỏ thích video |
| ACT-003 | `testShareVideoByEmail` | Chia sẻ video qua email |

---

### 1.10 Lưu ý quan trọng

| Vấn đề | Giải pháp |
|---|---|
| `SessionNotCreatedException` | ChromeDriver không khớp Chrome → tải đúng bản |
| `Connection refused` port 9090 | Chưa khởi động Jetty → chạy lại Bước 1.5 |
| Test WAT-005, ACT-* FAIL | Tài khoản `admin / 123` chưa có trong DB |
| `NoSuchElementException` | Locator chưa khớp HTML → kiểm tra JSP và cập nhật Page Object |
| Maven không nhận TestNG | Reload Maven (`Ctrl+Shift+O`) |

---
---

## Phần 2 — Hướng dẫn viết Test Case

### 2.1 Kiến trúc tổng quan: Page Object Model (POM)

Dự án sử dụng kiến trúc **Page Object Model** — tách biệt hoàn toàn giữa **code tương tác giao diện** (Page) và **code kiểm thử** (Test):

```
┌──────────────┐      sử dụng       ┌──────────────┐      kế thừa      ┌──────────────┐
│  SearchPage  │◄────────────────────│  SearchTest  │─────────────────►│   BaseTest   │
│  WatchPage   │                     │  WatchTest   │                   │ (WebDriver)  │
│  RegisterPage│                     │  RegisterTest│                   └──────────────┘
└──────────────┘                     └──────────────┘
     page/                                tests/                            tests/
```

- **Page Object** (`page/`): Chứa các locator (CSS/XPath) và các method tương tác (click, nhập text, lấy text…).
- **Test Class** (`tests/`): Kế thừa `BaseTest`, gọi các method của Page Object, dùng `Assert` để kiểm tra kết quả.
- **BaseTest**: Khởi tạo WebDriver (`@BeforeTest`) và đóng trình duyệt (`@AfterTest`).

---

### 2.2 Bước 1 — Tạo Page Object

Mỗi trang web cần 1 file Page Object trong thư mục `src/test/java/page/`. Cấu trúc chuẩn:

```java
package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class TenTrangPage {

    WebDriver driver;
    WebDriverWait wait;

    // === KHAI BÁO LOCATOR ===
    By txtInput = By.name("ten-input");
    By btnSubmit = By.cssSelector("button[type='submit']");
    By alertMessage = By.cssSelector(".alert");

    // === CONSTRUCTOR ===
    public TenTrangPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // === ĐIỀU HƯỚNG ===
    public void open() {
        driver.get("http://localhost:9090/ten-trang");
    }

    // === HÀNH ĐỘNG (Actions) ===
    public void enterInput(String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtInput))
                .sendKeys(value);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit))
                .click();
    }

    // === LẤY DỮ LIỆU (Getters) ===
    public String getAlertMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage))
                .getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
```

**Quy tắc quan trọng khi viết Page Object:**

| Quy tắc | Giải thích |
|---|---|
| Mỗi trang = 1 file | `RegisterPage.java`, `SearchPage.java`, `WatchPage.java`… |
| Locator khai báo ở đầu class | Dùng `By.name()`, `By.id()`, `By.cssSelector()`, `By.xpath()` |
| Luôn dùng `WebDriverWait` | Tránh lỗi `NoSuchElementException` do trang chưa load xong |
| Không viết `Assert` trong Page | Page chỉ tương tác, Test mới kiểm tra |

---

### 2.3 Cách tìm đúng Locator

Đây là bước **quan trọng nhất**. Locator sai = test FAIL.

**Bước 1:** Mở trang web trên Chrome → nhấn `F12` (DevTools) → tab **Elements**.

**Bước 2:** Click vào biểu tượng 🔍 (Select element) → click vào phần tử trên trang.

**Bước 3:** Xem HTML của phần tử, chọn locator theo thứ tự ưu tiên:

| Ưu tiên | Loại | Ví dụ | Khi nào dùng |
|---|---|---|---|
| 1 | `By.id()` | `By.id("emailInput")` | Phần tử có thuộc tính `id` |
| 2 | `By.name()` | `By.name("query")` | Phần tử có thuộc tính `name` |
| 3 | `By.cssSelector()` | `By.cssSelector(".like-btn")` | Tìm theo class/attribute CSS |
| 4 | `By.xpath()` | `By.xpath("//span[contains(.,'text')]")` | Tìm theo nội dung text hoặc cấu trúc phức tạp |

**Ví dụ thực tế từ dự án:**

```html
<!-- HTML trong navbar.jsp -->
<input type="text" name="query" placeholder="Tìm kiếm...">
```
→ Locator: `By.name("query")`

```html
<!-- HTML trong videoPage.jsp -->
<button class="btn btn-outline-light btn-sm border-0 like-btn" onclick="likeVideo(...)">
```
→ Locator: `By.cssSelector(".like-btn")`

```html
<!-- HTML trong shareBox.jsp -->
<input type="email" id="emailInput" name="emailTo">
```
→ Locator: `By.id("emailInput")`

**Mẹo kiểm tra locator nhanh:** Trong DevTools Console, gõ:
```javascript
document.querySelector(".like-btn")        // CSS Selector
document.querySelectorAll(".video-card")   // Kiểm tra có bao nhiêu phần tử khớp
```

---

### 2.4 Bước 2 — Tạo Test Class

Test class nằm trong `src/test/java/tests/`, **kế thừa** `BaseTest`:

```java
package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.TenTrangPage;

public class TenTrangTest extends BaseTest {

    private TenTrangPage tenTrangPage;

    @BeforeMethod
    public void initPage() {
        tenTrangPage = new TenTrangPage(driver);
    }

    @Test
    public void testChucNangABC() {
        tenTrangPage.open();
        tenTrangPage.enterInput("giá trị test");
        tenTrangPage.clickSubmit();

        String result = tenTrangPage.getAlertMessage();
        Assert.assertTrue(result.contains("Thành công"));
    }
}
```

**Quy tắc quan trọng:**

| Quy tắc | Giải thích |
|---|---|
| `extends BaseTest` | Để kế thừa `driver` đã được khởi tạo |
| `@BeforeMethod` khởi tạo Page | Tạo mới Page Object trước mỗi test method |
| Tên method `@BeforeMethod` **KHÔNG** trùng `setUp` | Vì `BaseTest` đã có `setUp()` → trùng tên sẽ ghi đè và `driver` sẽ bị null |
| Mỗi `@Test` method là 1 test case độc lập | Có thể chạy riêng hoặc chạy cả class |

---

### 2.5 Bước 3 — Viết Assert (Kiểm tra kết quả)

Các phương thức Assert hay dùng:

```java
// Kiểm tra điều kiện đúng
Assert.assertTrue(condition, "Thông báo lỗi nếu sai");

// Kiểm tra điều kiện sai
Assert.assertFalse(condition, "Thông báo lỗi nếu đúng");

// Kiểm tra giá trị bằng nhau
Assert.assertEquals(actual, expected, "Thông báo lỗi");

// Kiểm tra giá trị KHÔNG bằng nhau
Assert.assertNotEquals(actual, notExpected, "Thông báo lỗi");

// Kiểm tra chuỗi chứa từ khóa
Assert.assertTrue(text.contains("từ khóa"), "Phải chứa 'từ khóa'");

// Kiểm tra danh sách không rỗng
Assert.assertFalse(list.isEmpty(), "Danh sách không được rỗng");
```

---

### 2.6 Bước 4 — Xử lý các tình huống đặc biệt

**a) Test cần đăng nhập trước:**

```java
@Test
public void testChucNangCanLogin() {
    driver.get("http://localhost:9090/login");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("123");
    driver.findElement(By.cssSelector("button[type='submit']")).click();

    // Tiếp tục test...
    tenTrangPage.open();
}
```

**b) Test phần tử bị ẩn (Dropdown, Modal):**

Nếu phần tử nằm trong dropdown hoặc modal chưa mở, dùng `driver.get()` để điều hướng thẳng:

```java
public void goToHistory() {
    driver.get("http://localhost:9090/history");
}
```

**c) Test chức năng AJAX (Like, Share):**

Sau khi click AJAX request, cần chờ response xử lý xong:

```java
public void clickLikeButton() {
    wait.until(ExpectedConditions.elementToBeClickable(btnLike)).click();
    try { Thread.sleep(500); } catch (Exception ignored) {}
}
```

**d) Bỏ qua test khi Backend chưa hỗ trợ:**

```java
@Test
public void testChucNangChuaCoBackend() {
    try {
        // Code test bình thường...
    } catch (AssertionError e) {
        throw new org.testng.SkipException("Backend chưa hỗ trợ chức năng này.");
    }
}
```

---

### 2.7 Ví dụ hoàn chỉnh: Viết test cho trang Đăng ký

**Bước 1 — Mở `register.jsp`, xem HTML:**

```html
<input name="fullname" type="text">
<input name="username" type="text">
<input name="email" type="email">
<input id="password" type="password">
<input id="confirmPassword" type="password">
<button type="submit">Đăng ký</button>
<div class="alert">Thông báo lỗi/thành công</div>
```

**Bước 2 — Tạo `RegisterPage.java`:**

```java
package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {

    WebDriver driver;
    WebDriverWait wait;

    By txtFullname = By.name("fullname");
    By txtUsername = By.name("username");
    By txtEmail = By.name("email");
    By txtPassword = By.id("password");
    By txtConfirmPassword = By.id("confirmPassword");
    By btnRegister = By.cssSelector("button[type='submit']");
    By alertMessage = By.cssSelector(".alert");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:9090/register");
    }

    public void enterFullname(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtFullname)).sendKeys(v);
    }

    public void enterUsername(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtUsername)).sendKeys(v);
    }

    public void enterEmail(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail)).sendKeys(v);
    }

    public void enterPassword(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword)).sendKeys(v);
    }

    public void enterConfirmPassword(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtConfirmPassword)).sendKeys(v);
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRegister)).click();
    }

    public void register(String f, String u, String e, String p, String cp) {
        enterFullname(f);
        enterUsername(u);
        enterEmail(e);
        enterPassword(p);
        enterConfirmPassword(cp);
        clickRegister();
    }

    public String getAlertMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage)).getText();
    }
}
```

**Bước 3 — Tạo `RegisterTest.java`:**

```java
package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.RegisterPage;

public class RegisterTest extends BaseTest {

    @BeforeMethod
    public void navigateToRegisterPage() {
        driver.get("http://localhost:9090/register");
    }

    @Test
    public void testAllEmptyFields() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register("", "", "", "", "");

        String alertText = registerPage.getAlertMessage();
        Assert.assertTrue(alertText.contains("Vui lòng điền đầy đủ thông tin!"));
    }

    @Test
    public void testRegisterSuccess() {
        RegisterPage registerPage = new RegisterPage(driver);
        String username = "user" + System.currentTimeMillis();
        String email = "user" + System.currentTimeMillis() + "@test.com";

        registerPage.open();
        registerPage.register("Nguyen Van Test", username, email, "123", "123");

        String alertText = registerPage.getAlertMessage();
        Assert.assertTrue(alertText.contains("Đăng ký thành công"));
    }
}
```

---

### 2.8 Tổng kết quy trình viết Test Case

```
1. Mở trang web → F12 → xem HTML
         ↓
2. Tạo file Page Object (page/)
   - Khai báo locator (By.id, By.name, By.cssSelector, By.xpath)
   - Viết method: open(), enterX(), clickX(), getX()
         ↓
3. Tạo file Test Class (tests/)
   - extends BaseTest
   - @BeforeMethod: khởi tạo Page Object (TÊN KHÔNG TRÙNG setUp)
   - @Test: gọi Page method + Assert kiểm tra
         ↓
4. Chạy test → xem kết quả → sửa locator nếu FAIL
```
