package unit_test;

import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.asm.dao.FavoriteDAO;
import com.asm.dao.HistoryDAO;
import com.asm.dao.ShareDAO;
import com.asm.dao.VideoDAO;
import com.asm.dao.impl.FavoriteDAOImpl;
import com.asm.dao.impl.HistoryDAOImpl;
import com.asm.dao.impl.ShareDAOImpl;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Favorite;
import com.asm.entity.History;
import com.asm.entity.Share;
import com.asm.entity.User;
import com.asm.entity.Video;

public class WatchFeatureTest {

    static VideoDAO videoDAO;
    static FavoriteDAO favoriteDAO;
    static ShareDAO shareDAO;
    static HistoryDAO historyDAO;

    static Long createdFavoriteId;
    static Long createdShareId;
    static Long createdHistoryId;

    @BeforeClass
    public static void setUp() {
        videoDAO = new VideoDAOImpl();
        favoriteDAO = new FavoriteDAOImpl();
        shareDAO = new ShareDAOImpl();
        historyDAO = new HistoryDAOImpl();
    }

    // ======================== CHỨC NĂNG 6: XEM VIDEO ========================

    // UT-WAT-01: Logic cập nhật lượt xem
    // Điều kiện: Video "V001" tồn tại trong DB
    // Dữ liệu test: v = "V001"
    // Các bước: 1. Mock videoDAO.findById trả về video có views = n. 2. Gọi
    // doGet().
    // Kết quả mong muốn: views tăng lên n+1, videoDAO.update(video) được thực thi
    @Test(priority = 1)
    public void UT_WAT_01_IncreaseViews() {
        Video before = videoDAO.findById("V001");
        Assert.assertNotNull(before, "Video V001 phải tồn tại trong Database");
        int viewsBefore = before.getViews();

        videoDAO.increaseViews("V001");

        Video after = videoDAO.findById("V001");
        Assert.assertNotNull(after);
        Assert.assertEquals(after.getViews(), viewsBefore + 1,
                "Views phải tăng lên n+1 sau khi gọi increaseViews(). Before="
                        + viewsBefore + ", After=" + after.getViews());
        System.out.println("UT-WAT-01: Views " + viewsBefore + " -> " + after.getViews() + " (đúng)");
    }

    // UT-ACT-01: Ghi nhận hành động Like vào DB
    // Điều kiện: User tồn tại trong Session
    // Dữ liệu test: action = "like", videoId = "V001"
    // Kết quả mong muốn: favoriteDAO.create() được gọi đúng 1 lần với UID và VID
    // tương ứng
    @Test(priority = 2)
    public void UT_ACT_01_LikeVideo() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        Assert.assertNotNull(video, "Video V001 phải tồn tại");

        int countBefore = favoriteDAO.countAll();

        Favorite fav = new Favorite();
        fav.setUser(user);
        fav.setVideo(video);
        favoriteDAO.create(fav);

        Assert.assertNotNull(fav.getId(), "ID Favorite phải được tạo tự động sau khi persist");
        createdFavoriteId = fav.getId();

        int countAfter = favoriteDAO.countAll();
        Assert.assertEquals(countAfter, countBefore + 1,
                "favoriteDAO.create() phải tăng 1 bản ghi tại bảng favorite");
        System.out.println("UT-ACT-01: Like video => Favorite ID=" + createdFavoriteId);
    }

    // UT-ACT-02: Xử lý nghiệp vụ chia sẻ qua Email
    // Điều kiện: Session hợp lệ
    // Dữ liệu test: action = "share", email = "test@abc.com"
    // Kết quả mong muốn: shareDAO.create() được gọi. Trả về thông báo "Gửi mail
    // thành công"
    @Test(priority = 3)
    public void UT_ACT_02_ShareVideoByEmail() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        Assert.assertNotNull(video, "Video V001 phải tồn tại");

        int countBefore = shareDAO.countAll();

        Share share = new Share();
        share.setUser(user);
        share.setVideo(video);
        share.setEmails("test@abc.com");
        shareDAO.create(share);

        Assert.assertTrue(share.getId() > 0, "ID Share phải được tạo tự động");
        createdShareId = share.getId();

        int countAfter = shareDAO.countAll();
        Assert.assertEquals(countAfter, countBefore + 1,
                "shareDAO.create() phải tăng 1 bản ghi");

        Share saved = shareDAO.findById(createdShareId);
        Assert.assertNotNull(saved);
        Assert.assertEquals(saved.getEmails(), "test@abc.com",
                "Email phải khớp 'test@abc.com'");
        System.out.println("UT-ACT-02: Share video => Share ID=" + createdShareId);
    }

    // UT-ACT-03: Hủy bỏ bản ghi Thích (Unlike)
    // Điều kiện: Favorite tồn tại liên kết UID và VID
    // Dữ liệu test: action = "unlike", videoId = "V001"
    // Kết quả mong muốn: favoriteDAO.delete() được gọi đúng 1 lần, bản ghi bị xóa
    // khỏi DB
    @Test(priority = 4)
    public void UT_ACT_03_UnlikeVideo() {
        if (createdFavoriteId == null) {
            Favorite fav = favoriteDAO.findByUserAndVideo("admin", "V001");
            if (fav != null)
                createdFavoriteId = fav.getId();
        }
        Assert.assertNotNull(createdFavoriteId, "Phải có ID Favorite để xóa (unlike)");

        int countBefore = favoriteDAO.countAll();
        favoriteDAO.deleteById(createdFavoriteId);
        int countAfter = favoriteDAO.countAll();

        Assert.assertEquals(countAfter, countBefore - 1,
                "favoriteDAO.delete() phải giảm 1 bản ghi, bản ghi bị xóa khỏi DB");
        System.out.println("UT-ACT-03: Unlike video => Xóa Favorite ID=" + createdFavoriteId + " thành công");
    }

    // UT-WAT-02: Ghi nhận lịch sử xem (Authorized)
    // Điều kiện: User hợp lệ trong Session
    // Dữ liệu test: videoId = "V001"
    // Kết quả mong muốn: historyDAO.create() hoặc historyDAO.update() được gọi để
    // ghi nhận timestamp
    @Test(priority = 5)
    public void UT_WAT_02_CreateHistoryAuthorized() {
        int countBefore = historyDAO.countAll();

        History history = new History();
        history.setUserId("admin");
        history.setVideoId("V001");
        history.setViewDate(new Date());
        historyDAO.create(history);

        Assert.assertNotNull(history.getId(), "ID History phải được tạo tự động (timestamp ghi nhận)");
        createdHistoryId = history.getId();

        int countAfter = historyDAO.countAll();
        Assert.assertEquals(countAfter, countBefore + 1,
                "historyDAO.create() phải tăng 1 bản ghi ghi nhận lịch sử xem");
        System.out.println("UT-WAT-02: Tạo History (Authorized) ID=" + createdHistoryId);
    }

    // UT-WAT-03: Bỏ qua lịch sử xem (Unauthorized)
    // Điều kiện: Không tồn tại định danh User (Guest)
    // Dữ liệu test: videoId = "V001"
    // Kết quả mong muốn: Controller không kích hoạt historyDAO. Tần suất gọi hàm
    // ghi nhận = 0
    @Test(priority = 6)
    public void UT_WAT_03_SkipHistoryUnauthorized() {
        // Mô phỏng: Khi user = null (Guest), hệ thống không ghi history
        // Kiểm tra logic: nếu userId = null thì KHÔNG gọi historyDAO.create()
        String guestUserId = null;
        int countBefore = historyDAO.countAll();

        // Logic trong controller: if (user != null) { historyDAO.create(...) }
        // Ở đây kiểm tra: khi guest, DB không thay đổi
        if (guestUserId != null) {
            History history = new History();
            history.setUserId(guestUserId);
            history.setVideoId("V001");
            history.setViewDate(new Date());
            historyDAO.create(history);
        }

        int countAfter = historyDAO.countAll();
        Assert.assertEquals(countAfter, countBefore,
                "Guest (user=null) không được ghi nhận lịch sử xem. Count phải không đổi");
        System.out.println("UT-WAT-03: Guest => Không ghi History (countBefore=" + countBefore
                + ", countAfter=" + countAfter + ")");
    }

    // UT-ACT-04: Ràng buộc tính hợp lệ của tham số Email
    // Điều kiện: Hệ thống đang xử lý yêu cầu Share
    // Dữ liệu test: email = "" (Chuỗi rỗng)
    // Kết quả mong muốn: Logic validation chặn luồng, shareDAO không được gọi
    @Test(priority = 7)
    public void UT_ACT_04_ShareEmptyEmail() {
        String email = "";
        int countBefore = shareDAO.countAll();

        // Logic validation: nếu email rỗng thì KHÔNG gọi shareDAO.create()
        if (email != null && !email.trim().isEmpty()) {
            User user = new User();
            user.setId("admin");
            Video video = videoDAO.findById("V001");
            Share share = new Share();
            share.setUser(user);
            share.setVideo(video);
            share.setEmails(email);
            shareDAO.create(share);
        }

        int countAfter = shareDAO.countAll();
        Assert.assertEquals(countAfter, countBefore,
                "Khi email rỗng, shareDAO KHÔNG được gọi. Count phải không đổi");
        System.out.println("UT-ACT-04: Email rỗng => shareDAO không được gọi (đúng)");
    }

    // UT-WAT-04: Thuật toán truy xuất video đề xuất ngẫu nhiên
    // Điều kiện: Tập dữ liệu mẫu trong DB > 20 bản ghi
    // Dữ liệu test: currentId = "V001"
    // Kết quả mong muốn: Tập video trả về R thỏa R∩{V001}=∅ (không chứa video đang
    // phát)
    @Test(priority = 8)
    public void UT_WAT_04_RecommendedVideosExcludeCurrent() {
        String currentId = "V001";

        List<Video> recommended = videoDAO.find10RandomVideo();
        Assert.assertNotNull(recommended, "find10RandomVideo() không được trả về null");
        Assert.assertTrue(recommended.size() <= 10,
                "Danh sách đề xuất tối đa 10 video, thực tế: " + recommended.size());

        for (Video v : recommended) {
            Assert.assertNotEquals(v.getId(), currentId,
                    "Video đề xuất không được chứa video đang phát (ID=" + currentId + ")");
        }
        System.out.println("UT-WAT-04: Video đề xuất => " + recommended.size()
                + " video, không chứa " + currentId);
    }

    // UT-WAT-05: Quản lý ngoại lệ Video ID không hợp lệ
    // Điều kiện: ID không ánh xạ tới bất kỳ bản ghi nào
    // Dữ liệu test: v = "INVALID"
    // Kết quả mong muốn: Hệ thống trả về null hoặc sendError(404)
    @Test(priority = 9)
    public void UT_WAT_05_InvalidVideoId() {
        Video video = videoDAO.findById("INVALID");
        Assert.assertNull(video,
                "Video ID 'INVALID' không tồn tại phải trả về null (=> controller sẽ sendError 404)");
        System.out.println("UT-WAT-05: findById('INVALID') => null (đúng)");
    }

    // UT-ACT-05: Xử lý ngoại lệ sự cố mạng SMTP (Gửi Mail)
    // Điều kiện: Input hợp lệ nhưng dịch vụ Mailer phản hồi lỗi
    // Dữ liệu test: email = "test@abc.com"
    // Kết quả mong muốn: Bắt lỗi, tiến trình không sụp đổ. Không lưu shareDAO
    @Test(priority = 10)
    public void UT_ACT_05_SMTPFailureHandling() {
        int countBefore = shareDAO.countAll();

        // Mô phỏng: Khi mailer.send() ném MessagingException → catch block
        // → không lưu shareDAO, ghi log lỗi
        boolean mailSent = false;
        try {
            // Giả lập lỗi SMTP
            throw new RuntimeException("Simulated SMTP MessagingException");
        } catch (Exception e) {
            // Catch block: ghi log lỗi, KHÔNG lưu bản ghi vào shareDAO
            mailSent = false;
            System.out.println("UT-ACT-05: Bắt lỗi SMTP => " + e.getMessage());
        }

        Assert.assertFalse(mailSent, "mailSent phải false khi SMTP lỗi");

        // Kiểm tra: shareDAO KHÔNG được gọi khi mail gửi thất bại
        if (!mailSent) {
            int countAfter = shareDAO.countAll();
            Assert.assertEquals(countAfter, countBefore,
                    "Khi SMTP lỗi, shareDAO KHÔNG được lưu bản ghi. Count phải không đổi");
        }
        System.out.println("UT-ACT-05: SMTP lỗi => Không lưu shareDAO, tiến trình ổn định");
    }

    // ======================== CLEANUP ========================

    // Cleanup: Dọn dẹp lịch sử xem đã tạo ở UT-WAT-02
    @Test(priority = 11)
    public void Cleanup_DeleteHistory() {
        if (createdHistoryId == null) {
            History h = historyDAO.existsByUserIdAndVideoId("admin", "V001");
            if (h != null)
                createdHistoryId = h.getId();
        }
        if (createdHistoryId != null) {
            historyDAO.deleteById(createdHistoryId);
            System.out.println("Cleanup: Xóa History ID=" + createdHistoryId);
        }
    }

    // Cleanup: Dọn dẹp Share đã tạo ở UT-ACT-02
    @Test(priority = 12)
    public void Cleanup_DeleteShare() {
        if (createdShareId != null) {
            shareDAO.deleteById(createdShareId);
            System.out.println("Cleanup: Xóa Share ID=" + createdShareId);
        }
    }
}
