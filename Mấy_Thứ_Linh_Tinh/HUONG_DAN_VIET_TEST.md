# Hướng Dẫn Chi Tiết Cách Viết Test

Tài liệu này hướng dẫn cách viết test trong dự án dựa trên các file test có sẵn. Dự án sử dụng **2 loại test chính**:

| Loại Test | Framework | Mục đích |
|-----------|-----------|----------|
| **Selenium UI Test** | TestNG + Selenium WebDriver | Test giao diện web qua trình duyệt |
| **DAO Unit Test** | JUnit 5 | Test trực tiếp các lớp truy xuất dữ liệu (DAO) |

---

## Mục Lục

1. [Cấu trúc thư mục test](#1-cấu-trúc-thư-mục-test)
2. [Selenium UI Test (TestNG)](#2-selenium-ui-test-testng)
   - [BaseTest - Lớp cha chung](#21-basetest---lớp-cha-chung)
   - [Page Object Model (POM)](#22-page-object-model-pom)
   - [Viết Test Class kế thừa BaseTest](#23-viết-test-class-kế-thừa-basetest)
   - [Ví dụ thực tế: RegisterTest](#24-ví-dụ-thực-tế-registertest)
   - [Ví dụ thực tế: SearchTest](#25-ví-dụ-thực-tế-searchtest)
   - [Ví dụ thực tế: WatchTest](#26-ví-dụ-thực-tế-watchtest)
3. [DAO Unit Test (JUnit 5)](#3-dao-unit-test-junit-5)
   - [Cấu trúc chung của DAO Test](#31-cấu-trúc-chung-của-dao-test)
   - [Ví dụ thực tế: FavoriteDAOTest](#32-ví-dụ-thực-tế-favoritedaotest)
   - [Ví dụ thực tế: VideoDAOTest](#33-ví-dụ-thực-tế-videodaotest)
   - [Ví dụ thực tế: HistoryDAOTest](#34-ví-dụ-thực-tế-historydaotest)
   - [Ví dụ thực tế: ShareDAOTest](#35-ví-dụ-thực-tế-sharedaotest)
4. [Các mẫu kiểm tra (Assertion Patterns)](#4-các-mẫu-kiểm-tra-assertion-patterns)
5. [Checklist khi viết test mới](#5-checklist-khi-viết-test-mới)

---

## 1. Cấu trúc thư mục test

```
src/test/java/
├── page/                          ← Page Object (Selenium)
│   ├── RegisterPage.java          ← Trang Đăng ký
│   ├── SearchPage.java            ← Trang Tìm kiếm
│   └── WatchPage.java             ← Trang Xem video
└── tests/                         ← Test Class
    ├── BaseTest.java              ← Lớp cha Selenium (mở/đóng trình duyệt)
    ├── RegisterTest.java          ← Test chức năng Đăng ký (Selenium)
    ├── SearchTest.java            ← Test chức năng Tìm kiếm (Selenium)
    ├── WatchTest.java             ← Test chức năng Xem video (Selenium)
    ├── FavoriteDAOTest.java       ← Test DAO Yêu thích (JUnit 5)
    ├── HistoryDAOTest.java        ← Test DAO Lịch sử xem (JUnit 5)
    ├── ShareDAOTest.java          ← Test DAO Chia sẻ (JUnit 5)
    └── VideoDAOTest.java          ← Test DAO Video (JUnit 5)
```

---

## 2. Selenium UI Test (TestNG)

### 2.1 BaseTest - Lớp cha chung

Mọi Selenium test đều **kế thừa** `BaseTest`. Lớp này quản lý vòng đời của trình duyệt:

```java
package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected WebDriver driver;  // ← Biến driver dùng chung cho mọi test con

    @BeforeTest    // Chạy 1 lần TRƯỚC tất cả @Test trong class
    public void setUp() {
        driver = new ChromeDriver();          // Mở trình duyệt Chrome
        driver.manage().window().maximize();  // Phóng to toàn màn hình
    }

    @AfterTest     // Chạy 1 lần SAU tất cả @Test trong class
    public void tearDown() {
        if (driver != null) {
            driver.quit();   // Đóng trình duyệt
        }
    }
}
```

**Các annotation quan trọng:**

| Annotation | Ý nghĩa |
|------------|----------|
| `@BeforeTest` | Chạy **1 lần** trước tất cả test trong class |
| `@AfterTest` | Chạy **1 lần** sau tất cả test trong class |
| `@BeforeMethod` | Chạy **trước mỗi** phương thức `@Test` |
| `@Test` | Đánh dấu phương thức là test case |

---

### 2.2 Page Object Model (POM)

Mỗi trang web có **1 lớp Page Object** riêng. Page Object đóng gói:
- **Locator** (vị trí phần tử trên trang)
- **Action** (hành động: nhập liệu, click, lấy text...)

#### Bước 1: Khai báo locator

Sử dụng `By` để định vị phần tử HTML:

```java
// Tìm theo thuộc tính name
By txtFullname = By.name("fullname");

// Tìm theo id
By txtPassword = By.id("password");

// Tìm theo CSS Selector
By btnRegister = By.cssSelector("button[type='submit']");
By videoItems  = By.cssSelector(".video-card");

// Tìm theo XPath
By noResultMessage = By.xpath("//p[contains(text(),'Tìm được 0 video liên quan')]");
```

**Quy tắc đặt tên biến locator:**

| Prefix | Loại phần tử | Ví dụ |
|--------|--------------|-------|
| `txt` | Text input | `txtFullname`, `txtEmail` |
| `btn` | Button | `btnRegister`, `btnSearch` |
| `alert` | Thông báo | `alertMessage` |
| `link` | Liên kết | `linkHistory` |

#### Bước 2: Constructor với WebDriverWait

```java
public class RegisterPage {
    WebDriver driver;
    WebDriverWait wait;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Chờ tối đa 10 giây
    }
}
```

#### Bước 3: Viết phương thức hành động

Mỗi hành động trên trang tạo thành **1 phương thức**:

```java
// ① Mở trang
public void open() {
    driver.get("http://localhost:9090/register");
}

// ② Nhập liệu (chờ phần tử xuất hiện rồi mới nhập)
public void enterFullname(String v) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(txtFullname))
            .sendKeys(v);
}

// ③ Click nút (chờ phần tử có thể click được)
public void clickRegister() {
    wait.until(ExpectedConditions.elementToBeClickable(btnRegister))
            .click();
}

// ④ Lấy text
public String getAlertMessage() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage))
            .getText();
}

// ⑤ Kiểm tra hiển thị (có try-catch trả false nếu không tìm thấy)
public boolean isNoResultMessageDisplayed() {
    try {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noResultMessage))
                .isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

// ⑥ Phương thức tổng hợp (gọi nhiều action liên tiếp)
public void register(String f, String u, String e, String p, String cp) {
    enterFullname(f);
    enterUsername(u);
    enterEmail(e);
    enterPassword(p);
    enterConfirmPassword(cp);
    clickRegister();
}
```

**ExpectedConditions thường dùng:**

| Condition | Khi nào dùng |
|-----------|--------------|
| `visibilityOfElementLocated(by)` | Chờ phần tử hiển thị (để đọc text, nhập liệu) |
| `elementToBeClickable(by)` | Chờ phần tử có thể click (cho button, link) |
| `presenceOfAllElementsLocatedBy(by)` | Chờ và lấy danh sách phần tử |
| `presenceOfElementLocated(by)` | Chờ phần tử tồn tại trong DOM (không cần hiển thị) |

---

### 2.3 Viết Test Class kế thừa BaseTest

**Cấu trúc chuẩn:**

```java
package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.TenPage;

public class TenTest extends BaseTest {  // ← Kế thừa BaseTest

    private TenPage page;

    @BeforeMethod   // Chạy trước mỗi @Test
    public void setUp() {
        page = new TenPage(driver);  // Tạo Page Object với driver từ BaseTest
        page.open();                 // Mở trang
    }

    @Test
    public void testTenTestCase() {
        // 1. Thao tác (Act)
        page.doSomething();

        // 2. Kiểm tra (Assert)
        Assert.assertTrue(condition, "Mô tả lỗi nếu fail");
    }
}
```

---

### 2.4 Ví dụ thực tế: RegisterTest

**Mục tiêu:** Test form đăng ký với nhiều kịch bản khác nhau.

```java
public class RegisterTest extends BaseTest {

    @BeforeMethod
    public void navigateToRegisterPage() {
        driver.get("http://localhost:9090/register");
    }

    // TC1: Bỏ trống tất cả các trường
    @Test
    public void testAllEmptyFields() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register("", "", "", "", "");

        String alertText = registerPage.getAlertMessage();
        Assert.assertTrue(alertText.contains("Vui lòng điền đầy đủ thông tin!"));
    }

    // TC2: Thiếu fullname
    @Test
    public void testMissingFullnameFields() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register("", "test1", "test@gmail.com", "123", "123");

        String alertText = registerPage.getAlertMessage();
        Assert.assertTrue(alertText.contains("Vui lòng điền đầy đủ thông tin!"));
    }

    // TC3: Password không khớp
    @Test
    public void testWrongPasswordRepeat() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register("Nguyen Van Test", "test1", "test@gmail.com", "123", "456");

        String alertText = registerPage.getAlertMessage();
        Assert.assertTrue(alertText.contains("Mật khẩu xác nhận không khớp!"));
    }

    // TC4: Username đã tồn tại
    @Test
    public void testUsernameExisted() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register("Nguyen Van Test", "admin", "test@gmail.com", "123", "123");

        String alertText = registerPage.getAlertMessage();
        Assert.assertTrue(alertText.contains("Tên đăng nhập đã tồn tại!"));
    }

    // TC5: Đăng ký thành công (dùng timestamp tránh trùng lặp)
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

**Bài học rút ra:**
- Mỗi kịch bản validation → 1 test method riêng
- Dùng `System.currentTimeMillis()` để tạo dữ liệu unique tránh trùng
- Kiểm tra alert message bằng `contains()` thay vì `equals()` cho linh hoạt

---

### 2.5 Ví dụ thực tế: SearchTest

**Mục tiêu:** Test tìm kiếm, sắp xếp, phân trang.

```java
public class SearchTest extends BaseTest {

    private SearchPage searchPage;

    @BeforeMethod
    public void navigateToSearchPage() {
        searchPage = new SearchPage(driver);
        searchPage.open();
    }

    // Test tìm kiếm với từ khóa hợp lệ
    @Test
    public void testSearchWithValidKeyword() {
        searchPage.search("Hài");

        List<WebElement> titles = searchPage.getVideoTitles();
        Assert.assertFalse(titles.isEmpty(), "Danh sách video không được rỗng");

        // Kiểm tra MỌI kết quả đều chứa từ khóa
        for (WebElement title : titles) {
            Assert.assertTrue(
                title.getText().toLowerCase().contains("hài"),
                "Tiêu đề phải chứa 'Hài': " + title.getText());
        }
    }

    // Test tìm kiếm không có kết quả
    @Test
    public void testSearchWithNonExistentKeyword() {
        searchPage.search("XYZ123");
        boolean isDisplayed = searchPage.isNoResultMessageDisplayed();
        Assert.assertTrue(isDisplayed, "Phải hiển thị thông báo không có kết quả");
    }

    // Test sắp xếp theo lượt xem giảm dần
    @Test
    public void testSortByMostViews() {
        searchPage.clickManyViews();

        // Kiểm tra URL đúng
        Assert.assertTrue(driver.getCurrentUrl().contains("/search/viewHtoL"));

        // Kiểm tra thứ tự: phần tử đầu >= phần tử sau
        List<WebElement> items = searchPage.getVideoItems();
        Assert.assertTrue(items.size() >= 2, "Cần ít nhất 2 video để kiểm tra");

        long firstViews  = searchPage.getViewsAt(0);
        long secondViews = searchPage.getViewsAt(1);
        Assert.assertTrue(firstViews >= secondViews,
            "Video đầu phải có views >= video sau");
    }

    // Test sắp xếp A-Z
    @Test
    public void testSortByAZ() {
        searchPage.clickAZ();
        Assert.assertTrue(driver.getCurrentUrl().contains("/search/AZ"));

        String firstTitle  = searchPage.getTitleAt(0);
        String secondTitle = searchPage.getTitleAt(1);
        Assert.assertTrue(firstTitle.compareToIgnoreCase(secondTitle) <= 0,
            "'" + firstTitle + "' phải đứng trước '" + secondTitle + "'");
    }

    // Test phân trang giữ từ khóa tìm kiếm
    @Test
    public void testFilterMaintainedOnPageTwo() {
        searchPage.search("Phim");
        Assert.assertFalse(searchPage.getVideoItems().isEmpty());

        searchPage.clickNextPage();

        // Kiểm tra trang 2 vẫn giữ kết quả theo từ khóa
        for (WebElement title : searchPage.getVideoTitles()) {
            Assert.assertTrue(title.getText().toLowerCase().contains("phim"));
        }
    }
}
```

**Bài học rút ra:**
- Test sắp xếp: so sánh **2 phần tử liền kề** (đầu tiên vs thứ hai)
- Test phân trang: kiểm tra kết quả trang 2 vẫn phù hợp với bộ lọc
- Kiểm tra URL để xác nhận đúng route

---

### 2.6 Ví dụ thực tế: WatchTest

**Mục tiêu:** Test trang xem video - hiển thị, like, share, lịch sử.

**Kỹ thuật đặc biệt: Login trước khi test**

Một số test yêu cầu đăng nhập trước. Sử dụng cách login inline:

```java
// Login inline trong test method
driver.get("http://localhost:9090/login");
driver.findElement(By.name("username")).sendKeys("admin");
driver.findElement(By.name("password")).sendKeys("123");
driver.findElement(By.cssSelector("button[type='submit']")).click();
```

**Các test case tiêu biểu:**

```java
public class WatchTest extends BaseTest {

    private WatchPage watchPage;

    @BeforeMethod
    public void initWatchPage() {
        watchPage = new WatchPage(driver);
    }

    // Test hiển thị chi tiết video
    @Test
    public void testVideoDetailDisplay() {
        watchPage.openByVideoId("V001");

        Assert.assertTrue(watchPage.getCurrentUrl().contains("/watch?id=V001"));

        String title = watchPage.getVideoTitle();
        Assert.assertFalse(title.isEmpty(), "Tiêu đề không được rỗng");

        String description = watchPage.getVideoDescription();
        Assert.assertFalse(description.isEmpty(), "Mô tả không được rỗng");
    }

    // Test iframe YouTube
    @Test
    public void testYoutubeIframeLoaded() {
        watchPage.openByVideoId("V001");

        Assert.assertTrue(watchPage.isIframeDisplayed());
        String iframeSrc = watchPage.getIframeSrc();
        Assert.assertTrue(
            iframeSrc.contains("youtube") || iframeSrc.contains("youtu.be"));
    }

    // Test lượt xem tự động tăng
    @Test
    public void testViewCountAutoIncrement() {
        watchPage.openByVideoId("V001");
        long viewsBefore = watchPage.getViews();

        watchPage.refresh();
        long viewsAfter = watchPage.getViews();

        Assert.assertEquals(viewsAfter, viewsBefore + 1,
            "Views phải tăng 1 sau khi refresh");
    }

    // Test Like video (cần đăng nhập)
    @Test
    public void testLikeVideo() {
        // Login
        driver.get("http://localhost:9090/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        watchPage.openByVideoId("V001");
        watchPage.clickLikeButton();

        String afterText = watchPage.getLikeButtonText();
        Assert.assertTrue(
            afterText.contains("Đã thích") || afterText.contains("Thích"),
            "Nút Like phải thay đổi trạng thái");
    }

    // Test Share video
    @Test
    public void testShareVideoByEmail() {
        // Login trước
        driver.get("http://localhost:9090/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        watchPage.openByVideoId("V001");
        watchPage.shareVideo("test@gmail.com");

        Assert.assertTrue(watchPage.getCurrentUrl().contains("/watch"));
    }

    // Test video đề xuất (dùng SkipException khi backend chưa hoàn thiện)
    @Test
    public void testRecommendedVideosDisplayed() {
        try {
            watchPage.openByVideoId("V001");

            List<WebElement> recommended = watchPage.getRecommendedItems();
            Assert.assertFalse(recommended.isEmpty());

            for (WebElement item : recommended) {
                String href = item.getAttribute("href");
                String itemId = "";
                if (href != null && href.contains("?id=")) {
                    itemId = href.substring(href.indexOf("?id=") + 4);
                }
                Assert.assertNotEquals(itemId, "V001",
                    "Video đề xuất không được trùng video đang xem");
            }
        } catch (AssertionError e) {
            // Skip khi backend chưa loại trừ video đang xem
            throw new org.testng.SkipException(
                "Backend chưa chặn video đang xem khỏi list đề xuất.");
        }
    }
}
```

**Bài học rút ra:**
- Dùng `SkipException` khi biết backend chưa hoàn thiện → test sẽ được đánh dấu **SKIP** thay vì FAIL
- Test toggle (Like/Unlike): lấy trạng thái **trước** → click → kiểm tra trạng thái **sau** phải khác
- `Thread.sleep(500)` trong Page Object cho các action cần chờ server xử lý (AJAX)

---

## 3. DAO Unit Test (JUnit 5)

### 3.1 Cấu trúc chung của DAO Test

```java
package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // ← Chạy test theo thứ tự
public class XxxDAOTest {

    static XxxDAO xxxDAO;         // DAO cần test
    static Long createdId;        // Lưu ID vừa tạo để dùng cho test sau

    @BeforeAll                    // Chạy 1 lần trước tất cả test
    static void setUp() {
        xxxDAO = new XxxDAOImpl();
    }

    @Test @Order(1) void testFindAll()   { /* ... */ }  // Đọc
    @Test @Order(2) void testCountAll()  { /* ... */ }  // Đếm
    @Test @Order(3) void testCreate()    { /* ... */ }  // Tạo mới
    @Test @Order(4) void testFindById()  { /* ... */ }  // Tìm theo ID
    @Test @Order(5) void testUpdate()    { /* ... */ }  // Cập nhật
    @Test @Order(6) void testDelete()    { /* ... */ }  // Xóa
}
```

**So sánh annotation JUnit 5 vs TestNG:**

| JUnit 5 | TestNG | Ý nghĩa |
|---------|--------|----------|
| `@BeforeAll` | `@BeforeTest` | Chạy 1 lần trước tất cả |
| `@Test` | `@Test` | Đánh dấu test |
| `@Order(n)` | `priority = n` | Thứ tự chạy |
| `@TestMethodOrder` | — | Khai báo cách sắp xếp |

**So sánh Assertion:**

| JUnit 5 | TestNG | Ý nghĩa |
|---------|--------|----------|
| `assertNotNull(obj, msg)` | `Assert.assertNotNull(obj, msg)` | Kiểm tra khác null |
| `assertEquals(expected, actual, msg)` | `Assert.assertEquals(actual, expected, msg)` | So sánh bằng |
| `assertTrue(condition, msg)` | `Assert.assertTrue(condition, msg)` | Kiểm tra đúng |
| `assertNull(obj, msg)` | — | Kiểm tra null |
| `assertFalse(condition, msg)` | `Assert.assertFalse(condition, msg)` | Kiểm tra sai |

> ⚠️ **Lưu ý:** Thứ tự tham số `assertEquals` **khác nhau** giữa JUnit 5 và TestNG!  
> - JUnit 5: `assertEquals(expected, actual, message)`  
> - TestNG: `Assert.assertEquals(actual, expected, message)`

---

### 3.2 Ví dụ thực tế: FavoriteDAOTest

**Mục tiêu:** Test CRUD cho bảng Favorite (Yêu thích).

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteDAOTest {

    static FavoriteDAO favoriteDAO;
    static VideoDAO videoDAO;
    static Long createdFavoriteId;  // ← Lưu ID để dùng ở test sau

    @BeforeAll
    static void setUp() {
        favoriteDAO = new FavoriteDAOImpl();
        videoDAO = new VideoDAOImpl();
    }

    // Test 1: findAll() không trả về null
    @Test @Order(1)
    void testFindAll() {
        List<Favorite> favorites = favoriteDAO.findAll();
        assertNotNull(favorites, "findAll() không được trả về null");
    }

    // Test 2: countAll() >= 0
    @Test @Order(2)
    void testCountAll() {
        int count = favoriteDAO.countAll();
        assertTrue(count >= 0, "countAll() phải >= 0");
    }

    // Test 3: Tạo mới → kiểm tra count tăng 1
    @Test @Order(3)
    void testCreateFavorite() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại");

        int countBefore = favoriteDAO.countAll();

        Favorite fav = new Favorite();
        fav.setUser(user);
        fav.setVideo(video);
        favoriteDAO.create(fav);

        assertNotNull(fav.getId(), "ID phải được tạo tự động");
        createdFavoriteId = fav.getId(); // ← Lưu lại

        int countAfter = favoriteDAO.countAll();
        assertEquals(countBefore + 1, countAfter, "Count phải tăng 1");
    }

    // Test 4: Tìm theo User + Video (tồn tại)
    @Test @Order(4)
    void testFindByUserAndVideoExist() {
        Favorite fav = favoriteDAO.findByUserAndVideo("admin", "V001");
        assertNotNull(fav);
        assertEquals("admin", fav.getUser().getId());
        assertEquals("V001", fav.getVideo().getId());
    }

    // Test 5: Tìm theo User + Video (KHÔNG tồn tại)
    @Test @Order(5)
    void testFindByUserAndVideoNotExist() {
        Favorite fav = favoriteDAO.findByUserAndVideo("USER_KHONG_TON_TAI", "VIDEO_KHONG_TON_TAI");
        assertNull(fav, "Phải trả về null nếu không tìm thấy");
    }

    // Test 6: Xóa → kiểm tra count giảm 1
    @Test @Order(6)
    void testDeleteFavorite() {
        // Fallback: nếu createdFavoriteId bị null (test 3 chưa chạy)
        if (createdFavoriteId == null) {
            Favorite fav = favoriteDAO.findByUserAndVideo("admin", "V001");
            if (fav != null) createdFavoriteId = fav.getId();
        }
        assertNotNull(createdFavoriteId, "Phải có ID để xóa");

        int countBefore = favoriteDAO.countAll();
        favoriteDAO.deleteById(createdFavoriteId);
        int countAfter = favoriteDAO.countAll();

        assertEquals(countBefore - 1, countAfter, "Count phải giảm 1");
    }
}
```

**Kỹ thuật quan trọng:**
1. **Biến `static` chia sẻ giữa các test:** `createdFavoriteId` được tạo ở test 3, dùng ở test 4, 6
2. **Fallback khi biến null:** Test 6 có logic tìm lại ID nếu `createdFavoriteId == null`
3. **Kiểm tra count trước/sau:** `countBefore` → thao tác → `countAfter` → so sánh

---

### 3.3 Ví dụ thực tế: VideoDAOTest

**Mục tiêu:** Test DAO Video gồm: tìm kiếm, lượt xem, like, JPQL.

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VideoDAOTest {

    static VideoDAO videoDAO;

    @BeforeAll
    static void setUp() {
        videoDAO = new VideoDAOImpl();
    }

    // Tìm theo ID tồn tại
    @Test @Order(2)
    void testFindByIdExist() {
        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại");
        assertEquals("V001", video.getId());
        assertNotNull(video.getTitle(), "Title không được null");
    }

    // Tìm theo ID không tồn tại
    @Test @Order(3)
    void testFindByIdNotExist() {
        Video video = videoDAO.findById("KHONG_TON_TAI_999");
        assertNull(video, "Phải trả về null");
    }

    // Tìm kiếm theo tiêu đề
    @Test @Order(4)
    void testSearchByTitleWithKeyword() {
        List<Video> results = videoDAO.searchByTitle("Hài");
        assertNotNull(results);
        for (Video v : results) {
            assertTrue(v.getTitle().toLowerCase().contains("hài"));
        }
    }

    // Tìm chuỗi rỗng → trả về tất cả
    @Test @Order(5)
    void testSearchByTitleEmpty() {
        List<Video> results = videoDAO.searchByTitle("");
        List<Video> allVideos = videoDAO.findAll();
        assertEquals(allVideos.size(), results.size(),
            "Tìm chuỗi rỗng phải trả về tất cả video");
    }

    // Test tăng lượt xem
    @Test @Order(9)
    void testIncreaseViews() {
        Video before = videoDAO.findById("V001");
        int viewsBefore = before.getViews();

        videoDAO.increaseViews("V001");

        Video after = videoDAO.findById("V001");
        assertEquals(viewsBefore + 1, after.getViews());
    }

    // Test isLiked (trường hợp không tồn tại)
    @Test @Order(11)
    void testIsLikedNotExist() {
        boolean liked = videoDAO.isLiked("V001", "USER_KHONG_TON_TAI");
        assertFalse(liked, "User không tồn tại thì isLiked phải false");
    }

    // Test JPQL tùy chỉnh
    @Test @Order(13)
    void testSearchVideoWithJPQL() {
        String JPQL = "SELECT v, COUNT(f.video.id), COUNT(s.video.id)"
            + "FROM Video v LEFT JOIN Favorite f on v.id = f.video.id "
            + "left join Share s on v.id = s.video.id "
            + "WHERE v.title LIKE :text "
            + "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active "
            + "ORDER BY v.views DESC";
        List<Object[]> results = videoDAO.searchVideo("%%", JPQL);
        assertNotNull(results);
        assertFalse(results.isEmpty(), "searchVideo với %% phải trả về kết quả");
    }
}
```

---

### 3.4 Ví dụ thực tế: HistoryDAOTest

**Mục tiêu:** Test CRUD + tìm kiếm cho DAO Lịch sử xem.

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HistoryDAOTest {

    static HistoryDAO historyDAO;
    static Long createdHistoryId;

    @BeforeAll
    static void setUp() {
        historyDAO = new HistoryDAOImpl();
    }

    // Tạo mới History
    @Test @Order(3)
    void testCreateHistory() {
        int countBefore = historyDAO.countAll();

        History history = new History();
        history.setUserId("admin");
        history.setVideoId("V001");
        history.setViewDate(new Date());  // ← Sử dụng java.util.Date
        historyDAO.create(history);

        assertNotNull(history.getId());
        createdHistoryId = history.getId();

        assertEquals(countBefore + 1, historyDAO.countAll());
    }

    // Tìm theo UserId
    @Test @Order(6)
    void testFindByUserId() {
        List<History> list = historyDAO.findByUserId("admin");
        assertNotNull(list);
        assertFalse(list.isEmpty());

        // Kiểm tra TẤT CẢ kết quả đúng userId
        for (History h : list) {
            assertEquals("admin", h.getUserId());
        }
    }

    // Cập nhật
    @Test @Order(7)
    void testUpdateHistory() {
        History history = historyDAO.findById(createdHistoryId);
        assertNotNull(history);

        Date newDate = new Date();
        history.setViewDate(newDate);
        historyDAO.update(history);

        History updated = historyDAO.findById(createdHistoryId);
        assertNotNull(updated);
    }

    // Xóa
    @Test @Order(8)
    void testDeleteHistory() {
        int countBefore = historyDAO.countAll();
        historyDAO.deleteById(createdHistoryId);
        int countAfter = historyDAO.countAll();

        assertEquals(countBefore - 1, countAfter);
    }
}
```

---

### 3.5 Ví dụ thực tế: ShareDAOTest

**Mục tiêu:** Test CRUD cho bảng Share (Chia sẻ video).

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShareDAOTest {

    static ShareDAO shareDAO;
    static VideoDAO videoDAO;
    static Long createdShareId;

    @BeforeAll
    static void setUp() {
        shareDAO = new ShareDAOImpl();
        videoDAO = new VideoDAOImpl();
    }

    // Tạo Share mới
    @Test @Order(3)
    void testCreateShare() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        assertNotNull(video);

        int countBefore = shareDAO.countAll();

        Share share = new Share();
        share.setUser(user);
        share.setVideo(video);
        share.setEmails("test@example.com");  // ← Trường đặc thù của Share
        shareDAO.create(share);

        assertTrue(share.getId() > 0);        // ← Kiểm tra ID > 0 thay vì not null
        createdShareId = share.getId();

        assertEquals(countBefore + 1, shareDAO.countAll());
    }

    // Tìm theo ID
    @Test @Order(4)
    void testFindById() {
        // Fallback nếu createdShareId null
        if (createdShareId == null) {
            List<Share> all = shareDAO.findAll();
            if (!all.isEmpty()) createdShareId = all.get(0).getId();
        }
        assertNotNull(createdShareId);

        Share share = shareDAO.findById(createdShareId);
        assertNotNull(share);
        assertEquals("test@example.com", share.getEmails()); // ← Kiểm tra dữ liệu
    }

    // Xóa
    @Test @Order(5)
    void testDeleteShare() {
        int countBefore = shareDAO.countAll();
        shareDAO.deleteById(createdShareId);
        int countAfter = shareDAO.countAll();

        assertEquals(countBefore - 1, countAfter);
    }
}
```

---

## 4. Các mẫu kiểm tra (Assertion Patterns)

### Mẫu 1: Kiểm tra danh sách phải có kết quả

```java
List<Video> list = videoDAO.findAll();
assertNotNull(list, "Không được trả về null");
assertFalse(list.isEmpty(), "Phải có ít nhất 1 phần tử");
```

### Mẫu 2: Kiểm tra text chứa chuỗi

```java
String alertText = registerPage.getAlertMessage();
Assert.assertTrue(alertText.contains("Vui lòng điền đầy đủ thông tin!"));
```

### Mẫu 3: Kiểm tra count tăng/giảm sau thao tác

```java
int countBefore = dao.countAll();
dao.create(entity);
int countAfter = dao.countAll();
assertEquals(countBefore + 1, countAfter, "Count phải tăng 1 sau khi tạo");
```

### Mẫu 4: Kiểm tra thứ tự sắp xếp

```java
long first  = searchPage.getViewsAt(0);
long second = searchPage.getViewsAt(1);
Assert.assertTrue(first >= second, "Phải giảm dần");  // Descending
Assert.assertTrue(first <= second, "Phải tăng dần");   // Ascending
```

### Mẫu 5: Kiểm tra toggle (Like/Unlike)

```java
String before = watchPage.getLikeButtonText();
watchPage.clickLikeButton();
String after = watchPage.getLikeButtonText();
Assert.assertNotEquals(after, before, "Phải thay đổi trạng thái");
```

### Mẫu 6: Kiểm tra URL chứa path đúng

```java
Assert.assertTrue(driver.getCurrentUrl().contains("/watch?id=V001"));
```

### Mẫu 7: Kiểm tra tìm thấy/không tìm thấy

```java
// Trường hợp tồn tại
Favorite fav = dao.findByUserAndVideo("admin", "V001");
assertNotNull(fav, "Phải tìm thấy");

// Trường hợp không tồn tại
Favorite fav2 = dao.findByUserAndVideo("xxx", "yyy");
assertNull(fav2, "Phải trả về null");
```

---

## 5. Checklist khi viết test mới

### Selenium UI Test

- [ ] Tạo **Page Object** mới trong `page/` (nếu chưa có)
  - [ ] Khai báo locator cho mỗi phần tử cần tương tác
  - [ ] Viết phương thức hành động cho từng thao tác
  - [ ] Dùng `WebDriverWait` + `ExpectedConditions` (không dùng `Thread.sleep`)
- [ ] Tạo **Test Class** trong `tests/`
  - [ ] Kế thừa `BaseTest`
  - [ ] Dùng `@BeforeMethod` để mở trang
  - [ ] Mỗi kịch bản → 1 method `@Test` riêng
  - [ ] Đặt tên test bắt đầu bằng `test` + mô tả kịch bản
  - [ ] Test cả trường hợp **thành công** và **thất bại**
  - [ ] Thêm message mô tả lỗi trong assertion

### DAO Unit Test

- [ ] Tạo Test class trong `tests/`
  - [ ] Dùng `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)`
  - [ ] Dùng `@BeforeAll` + `static` để khởi tạo DAO
  - [ ] Dùng `@Order(n)` để sắp xếp thứ tự: Read → Create → Find → Update → Delete
  - [ ] Dùng biến `static` để chia sẻ ID giữa các test
  - [ ] Thêm fallback khi biến ID có thể null
  - [ ] Kiểm tra count trước/sau khi Create và Delete
  - [ ] Test cả trường hợp **tồn tại** và **không tồn tại**
  - [ ] Thêm `System.out.println` để log kết quả test
