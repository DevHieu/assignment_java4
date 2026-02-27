package unit_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WatchFeatureTest {

    static VideoDAO videoDAO;
    static FavoriteDAO favoriteDAO;
    static ShareDAO shareDAO;
    static HistoryDAO historyDAO;

    static Long createdFavoriteId;
    static Long createdShareId;
    static Long createdHistoryId;

    @BeforeAll
    static void setUp() {
        videoDAO = new VideoDAOImpl();
        favoriteDAO = new FavoriteDAOImpl();
        shareDAO = new ShareDAOImpl();
        historyDAO = new HistoryDAOImpl();
    }

    // ======================== XEM VIDEO ========================

    // UT-WAT-01: Logic tăng lượt xem
    @Test
    @Order(1)
    void UT_WAT_01_IncreaseViews() {
        Video before = videoDAO.findById("V001");
        assertNotNull(before, "Video V001 phải tồn tại trong Database");
        int viewsBefore = before.getViews();

        videoDAO.increaseViews("V001");

        Video after = videoDAO.findById("V001");
        assertNotNull(after);
        assertEquals(viewsBefore + 1, after.getViews(),
                "Views phải tăng 1 sau khi gọi increaseViews(). Before="
                        + viewsBefore + ", After=" + after.getViews());
        System.out.println("UT-WAT-01: Views " + viewsBefore + " -> " + after.getViews() + " (đúng)");
    }

    // UT-WAT-01b: Tìm video theo ID tồn tại
    @Test
    @Order(2)
    void UT_WAT_01b_FindVideoByIdExist() {
        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại");
        assertEquals("V001", video.getId());
        assertNotNull(video.getTitle(), "Title không được null");
        System.out.println("UT-WAT-01b: V001 => " + video.getTitle());
    }

    // UT-WAT-01c: Tìm video theo ID không tồn tại
    @Test
    @Order(3)
    void UT_WAT_01c_FindVideoByIdNotExist() {
        Video video = videoDAO.findById("KHONG_TON_TAI_999");
        assertNull(video, "Video không tồn tại phải trả về null");
        System.out.println("UT-WAT-01c: KHONG_TON_TAI_999 => null (đúng)");
    }

    // UT-WAT-01d: Lấy 10 video ngẫu nhiên để đề xuất
    @Test
    @Order(4)
    void UT_WAT_01d_Find10RandomVideo() {
        List<Video> randomVideos = videoDAO.find10RandomVideo();
        assertNotNull(randomVideos, "find10RandomVideo() không được trả về null");
        assertTrue(randomVideos.size() <= 10,
                "Danh sách đề xuất tối đa 10 video, thực tế: " + randomVideos.size());
        System.out.println("UT-WAT-01d: Lấy được " + randomVideos.size() + " video ngẫu nhiên");
    }

    // ======================== LỊCH SỬ XEM ========================

    // UT-WAT-01e: Tạo lịch sử xem khi user đã login
    @Test
    @Order(5)
    void UT_WAT_01e_CreateHistory() {
        int countBefore = historyDAO.countAll();

        History history = new History();
        history.setUserId("admin");
        history.setVideoId("V001");
        history.setViewDate(new Date());
        historyDAO.create(history);

        assertNotNull(history.getId(), "ID History phải được tạo tự động");
        createdHistoryId = history.getId();

        int countAfter = historyDAO.countAll();
        assertEquals(countBefore + 1, countAfter,
                "Số lượng History phải tăng 1 sau khi tạo");
        System.out.println("UT-WAT-01e: Tạo History ID=" + createdHistoryId);
    }

    // UT-WAT-01f: Kiểm tra lịch sử xem đã tồn tại
    @Test
    @Order(6)
    void UT_WAT_01f_ExistHistory() {
        History history = historyDAO.existsByUserIdAndVideoId("admin", "V001");
        assertNotNull(history, "Phải tìm thấy lịch sử xem của admin + V001");
        assertEquals("admin", history.getUserId());
        assertEquals("V001", history.getVideoId());
        System.out.println("UT-WAT-01f: Tìm thấy History ID=" + history.getId());
    }

    // UT-WAT-01g: Cập nhật ngày xem khi xem lại
    @Test
    @Order(7)
    void UT_WAT_01g_UpdateHistoryViewDate() {
        if (createdHistoryId == null) {
            History h = historyDAO.existsByUserIdAndVideoId("admin", "V001");
            if (h != null)
                createdHistoryId = h.getId();
        }
        assertNotNull(createdHistoryId);

        History history = historyDAO.findById(createdHistoryId);
        assertNotNull(history);

        Date newDate = new Date();
        history.setViewDate(newDate);
        historyDAO.update(history);

        History updated = historyDAO.findById(createdHistoryId);
        assertNotNull(updated);
        System.out.println("UT-WAT-01g: Cập nhật viewDate thành công cho History ID=" + createdHistoryId);
    }

    // Cleanup history
    @Test
    @Order(8)
    void UT_WAT_01h_DeleteHistory() {
        if (createdHistoryId == null) {
            History h = historyDAO.existsByUserIdAndVideoId("admin", "V001");
            if (h != null)
                createdHistoryId = h.getId();
        }
        assertNotNull(createdHistoryId, "Phải có ID để xóa");

        int countBefore = historyDAO.countAll();
        historyDAO.deleteById(createdHistoryId);
        int countAfter = historyDAO.countAll();

        assertEquals(countBefore - 1, countAfter,
                "Số lượng History phải giảm 1 sau khi xóa");
        System.out.println("UT-WAT-01h: Xóa History ID=" + createdHistoryId + " thành công");
    }

    // ======================== LIKE VIDEO ========================

    // UT-ACT-01: Like video (Lưu DB)
    @Test
    @Order(9)
    void UT_ACT_01_LikeVideo() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại");

        int countBefore = favoriteDAO.countAll();

        Favorite fav = new Favorite();
        fav.setUser(user);
        fav.setVideo(video);
        favoriteDAO.create(fav);

        assertNotNull(fav.getId(), "ID Favorite phải được tạo tự động sau khi persist");
        createdFavoriteId = fav.getId();

        int countAfter = favoriteDAO.countAll();
        assertEquals(countBefore + 1, countAfter,
                "Số lượng Favorite phải tăng 1 sau khi like");
        System.out.println("UT-ACT-01: Like video => Favorite ID=" + createdFavoriteId);
    }

    // UT-ACT-01b: Kiểm tra isLiked sau khi like
    @Test
    @Order(10)
    void UT_ACT_01b_IsLikedAfterLike() {
        Favorite fav = favoriteDAO.findByUserAndVideo("admin", "V001");
        assertNotNull(fav, "Phải tìm thấy Favorite của admin + V001");
        assertEquals("admin", fav.getUser().getId());
        assertEquals("V001", fav.getVideo().getId());
        System.out.println("UT-ACT-01b: findByUserAndVideo('admin', 'V001') => ID=" + fav.getId());
    }

    // UT-ACT-01c: Unlike video (Xóa bản ghi)
    @Test
    @Order(11)
    void UT_ACT_01c_UnlikeVideo() {
        if (createdFavoriteId == null) {
            Favorite fav = favoriteDAO.findByUserAndVideo("admin", "V001");
            if (fav != null)
                createdFavoriteId = fav.getId();
        }
        assertNotNull(createdFavoriteId, "Phải có ID Favorite để xóa (unlike)");

        int countBefore = favoriteDAO.countAll();
        favoriteDAO.deleteById(createdFavoriteId);
        int countAfter = favoriteDAO.countAll();

        assertEquals(countBefore - 1, countAfter,
                "Số lượng Favorite phải giảm 1 sau khi unlike");
        System.out.println("UT-ACT-01c: Unlike video => Xóa Favorite ID=" + createdFavoriteId + " thành công");
    }

    // UT-ACT-01d: isLiked = false khi user không tồn tại
    @Test
    @Order(12)
    void UT_ACT_01d_IsLikedNotExist() {
        boolean liked = videoDAO.isLiked("V001", "USER_KHONG_TON_TAI");
        assertFalse(liked, "User không tồn tại thì isLiked phải false");
        System.out.println("UT-ACT-01d: isLiked('V001', 'USER_KHONG_TON_TAI') = false (đúng)");
    }

    // ======================== SHARE VIDEO ========================

    // UT-ACT-02: Share video qua Mail (Lưu DB)
    @Test
    @Order(13)
    void UT_ACT_02_ShareVideo() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        assertNotNull(video, "Video V001 phải tồn tại");

        int countBefore = shareDAO.countAll();

        Share share = new Share();
        share.setUser(user);
        share.setVideo(video);
        share.setEmails("test@gmail.com");
        shareDAO.create(share);

        assertTrue(share.getId() > 0, "ID Share phải được tạo tự động");
        createdShareId = share.getId();

        int countAfter = shareDAO.countAll();
        assertEquals(countBefore + 1, countAfter,
                "Số lượng Share phải tăng 1 sau khi chia sẻ");
        System.out.println("UT-ACT-02: Share video => Share ID=" + createdShareId);
    }

    // UT-ACT-02b: Kiểm tra thông tin Share vừa tạo
    @Test
    @Order(14)
    void UT_ACT_02b_FindShareById() {
        if (createdShareId == null) {
            List<Share> all = shareDAO.findAll();
            if (!all.isEmpty())
                createdShareId = all.get(0).getId();
        }
        assertNotNull(createdShareId);

        Share share = shareDAO.findById(createdShareId);
        assertNotNull(share, "Phải tìm thấy Share vừa tạo");
        assertEquals("test@gmail.com", share.getEmails(),
                "Email phải khớp với dữ liệu đã nhập");
        System.out.println("UT-ACT-02b: Share ID=" + createdShareId
                + ", emails=" + share.getEmails());
    }

    // Cleanup share
    @Test
    @Order(15)
    void UT_ACT_02c_DeleteShare() {
        assertNotNull(createdShareId, "Phải có ID Share để xóa");

        int countBefore = shareDAO.countAll();
        shareDAO.deleteById(createdShareId);
        int countAfter = shareDAO.countAll();

        assertEquals(countBefore - 1, countAfter,
                "Số lượng Share phải giảm 1 sau khi xóa");
        System.out.println("UT-ACT-02c: Xóa Share ID=" + createdShareId + " thành công");
    }
}
