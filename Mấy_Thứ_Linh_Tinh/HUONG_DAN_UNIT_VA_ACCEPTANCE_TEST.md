# Hướng Dẫn Viết Unit Test và Acceptance Testing

Tài liệu này hướng dẫn chi tiết cách viết **Unit Test** (Test DAO/Logic) và **Acceptance Testing** (Auto Test UI) dựa trên cấu trúc hiện tại của dự án `assignment_java4`.

Dự án chia làm 2 thư mục test chính trong `src/test/java/`:
1. `unit_test/`: Chứa các bài kiểm thử Database/DAO sử dụng **JUnit 5**.
2. `auto_test/`: Chứa các bài kiểm thử tự động trên giao diện Web sử dụng **Selenium WebDriver** và **TestNG**, kết hợp pattern **Page Object Model (POM)** (thư mục `page/`).

---

## Phần 1: Viết Unit Test (JUnit 5)

Thư mục: `src/test/java/unit_test`
Công cụ: **JUnit 5** (`org.junit.jupiter.api`) + JDBC/Hibernate thao tác với Database thật.

### 1. Nguyên Tắc Trọng Tâm
- Khởi tạo DAO thực tế (Ví dụ: `new VideoDAOImpl()`).
- Database phải có sẵn kết nối (`hibernate.cfg.xml` hợp lệ) để query thật.
- Kiểm thử các thao tác CRUD và các câu lệnh custom query (HQL/JPQL).
- Dọn dẹp dữ liệu (Clean up) sau khi test INSERT/UPDATE xong nếu cần thiết.

### 2. File Template Mẫu `ExampleDAOTest.java`

```java
package unit_test;

import static org.junit.jupiter.api.Assertions.*; // Dùng Assert của JUnit 5

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.asm.dao.VideoDAO;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Video;

// Ép thứ tự chạy các @Test theo Annotation @Order()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExampleDAOTest {

    static VideoDAO videoDAO;

    // Chạy 1 lần duy nhất trước khi bắt đầu class test
    @BeforeAll
    static void setUp() {
        videoDAO = new VideoDAOImpl();
    }

    // @Order(1) đảm bảo test này chạy đầu tiên
    @Test
    @Order(1)
    void testFindAll() {
        List<Video> videos = videoDAO.findAll();
        assertNotNull(videos, "Danh sách không được null");
        assertFalse(videos.isEmpty(), "DB phải có dữ liệu");
        
        System.out.println("Tổng videos: " + videos.size());
    }

    @Test
    @Order(2)
    void testFindById() {
        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại");
        assertEquals("V001", video.getId(), "ID phải khớp");
    }
}
```

### 3. Assertion Thường Dùng (JUnit 5)
- `assertNotNull(obj)`: Đảm bảo đối tượng tìm được.
- `assertNull(obj)`: Đảm bảo không tìm thấy dữ liệu rác.
- `assertTrue(condition)`: Kiểm tra logic đúng (Ví dụ count > 0).
- `assertFalse(condition)`: Kiểm tra danh sách không rỗng `list.isEmpty()`.
- `assertEquals(expected, actual)`: So sánh 2 giá trị.

---

## Phần 2: Viết Acceptance Testing (Auto Test UI)

Thư mục: `src/test/java/auto_test`
Công cụ: **TestNG** (`org.testng`) + **Selenium WebDriver** (`org.openqa.selenium`).

Dự án áp dụng mô hình **Page Object Model (POM)**:
- Logic thao tác phần tử (tìm input, click nút...) được đưa vào lớp DAO UI trong package `src/test/java/page/` (Vd: `SearchPage.java`, `WatchPage.java`).
- Lớp Test (`SearchTest.java`) chỉ gọn gàng gọi các thao tác đó và thực hiện `Assert`.

### 1. Kiến trúc `BaseTest`
Bạn **PHẢI** kế thừa `BaseTest` vì nó phụ trách việc khởi tạo và đóng trình duyệt:
```java
public class BaseTest {
    protected WebDriver driver;

    @BeforeTest // Chạy 1 lần trước cả Test Suite
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterTest // Tắt trình duyệt khi xong
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
```

### 2. Cách Tạo Kịch Bản Auto Test (TestNG)

**Bước 1:** Nếu chức năng chưa có Page Object, tạo file ở `package page;`.
Ví dụ: `page/LoginPage.java`. Khai báo WebElements (`By`) và các hàm Click, SendKeys kèm theo `WebDriverWait`.

**Bước 2:** Viết Test Class ở `package auto_test;`.

```java
package auto_test;

import org.openqa.selenium.WebElement;
import org.testng.Assert; // Dùng Assert của TestNG
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.SearchPage;
import java.util.List;

public class ExampleTest extends BaseTest {

    private SearchPage searchPage;

    // Khởi tạo lại Page Object và mở URL mới TRƯỚC MỖI @Test
    @BeforeMethod
    public void initPage() {
        searchPage = new SearchPage(driver);
        searchPage.open(); // Ví dụ http://localhost:9090/search
    }

    @Test
    public void TC01_SearchKeyword_Success() {
        // 1. Thực hiện kịch bản (Action) - che giấu toàn bộ `driver.findElement`
        searchPage.search("Hài");

        // 2. Lấy dữ liệu để xác minh
        List<WebElement> titles = searchPage.getVideoTitles();

        // 3. Assertion (Xác nhận)
        Assert.assertFalse(titles.isEmpty(), "Phải có kết quả tìm kiếm 'Hài'");
        
        for(WebElement el : titles) {
            String titleText = el.getText().toLowerCase();
            Assert.assertTrue(titleText.contains("hài"), "Tên video phải chứa từ khóa");
        }
    }
}
```

### 3. Assertion Thường Dùng (TestNG)
Khác với JUnit 5 (tham số message ở cuối), **TestNG đưa tham số câu báo lỗi lên cuối cùng**:
- `Assert.assertEquals(actual, expected, "Message lỗi nếu sai");`
- `Assert.assertTrue(condition, "Message");`
- `Assert.assertFalse(condition, "Message");`
- `Assert.assertNotEquals(actual, expected, "Message");`

(*) **Lưu ý Skip Exception**:
Nếu một tính năng bên Back-end đang bị lỗi (chưa fix được), để Test không báo FAILED "oan uổng" và làm fail pipeline, bạn có thể ném ra `SkipException`:
```java
if (backendLoi) {
    throw new org.testng.SkipException("Backend chưa hoàn thiện luồng này, bỏ qua test.");
}
```

---

## TỔNG KẾT SO SÁNH

| Tiêu Chí | Unit Test (`unit_test`) | Acceptance Testing (`auto_test`) |
| --- | --- | --- |
| **Mục đích** | Test nghiệp vụ logic, câu truy vấn SQL/JPQL. | Đóng giả người dùng thao tác trên trình duyệt, test luồng End-to-End. |
| **Framework** | JUnit 5 Jupiter | TestNG |
| **Công cụ lõi** | Gọi DAO Class thật (JDBC/Hibernate) | Selenium WebDriver |
| **Base Class** | Không (`@BeforeAll` khởi tạo DAO) | Extends `BaseTest` (`@BeforeTest` khởi động Driver) |
| **Pattern** | Transaction / Sequence (`@TestMethodOrder`) | Page Object Model (POM) |

Hãy tham khảo `HistoryDAOTest.java` và `SearchTest.java` để xem mã nguồn thực tế tốt nhất trong project!
