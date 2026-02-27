package unit_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.asm.dao.FavoriteDAO;
import com.asm.dao.VideoDAO;
import com.asm.dao.impl.FavoriteDAOImpl;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Favorite;
import com.asm.entity.User;
import com.asm.entity.Video;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteDAOTest {

    static FavoriteDAO favoriteDAO;
    static VideoDAO videoDAO;
    static Long createdFavoriteId;

    @BeforeAll
    static void setUp() {
        favoriteDAO = new FavoriteDAOImpl();
        videoDAO = new VideoDAOImpl();
    }

    @Test
    @Order(1)
    void testFindAll() {
        List<Favorite> favorites = favoriteDAO.findAll();
        assertNotNull(favorites, "findAll() không được trả về null");
        System.out.println("testFindAll: Tìm thấy " + favorites.size() + " bản ghi Favorite");
    }

    @Test
    @Order(2)
    void testCountAll() {
        int count = favoriteDAO.countAll();
        assertTrue(count >= 0, "countAll() phải >= 0");
        System.out.println("testCountAll: Tổng số Favorite = " + count);
    }

    @Test
    @Order(3)
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

        assertNotNull(fav.getId(), "ID phải được tạo tự động sau khi persist");
        createdFavoriteId = fav.getId();

        int countAfter = favoriteDAO.countAll();
        assertEquals(countBefore + 1, countAfter,
                "Số lượng Favorite phải tăng 1 sau khi tạo");
        System.out.println("testCreateFavorite: Tạo Favorite ID=" + createdFavoriteId);
    }

    @Test
    @Order(4)
    void testFindByUserAndVideoExist() {
        Favorite fav = favoriteDAO.findByUserAndVideo("admin", "V001");
        assertNotNull(fav, "Phải tìm thấy Favorite của admin + V001 (vừa tạo ở test trước)");
        assertEquals("admin", fav.getUser().getId());
        assertEquals("V001", fav.getVideo().getId());
        System.out.println("testFindByUserAndVideoExist: Tìm thấy Favorite ID=" + fav.getId());
    }

    @Test
    @Order(5)
    void testFindByUserAndVideoNotExist() {
        Favorite fav = favoriteDAO.findByUserAndVideo("USER_KHONG_TON_TAI", "VIDEO_KHONG_TON_TAI");
        assertNull(fav, "Phải trả về null nếu không tìm thấy");
        System.out.println("testFindByUserAndVideoNotExist: null (đúng)");
    }

    @Test
    @Order(6)
    void testDeleteFavorite() {
        if (createdFavoriteId == null) {
            Favorite fav = favoriteDAO.findByUserAndVideo("admin", "V001");
            if (fav != null)
                createdFavoriteId = fav.getId();
        }

        assertNotNull(createdFavoriteId, "Phải có ID để xóa");

        int countBefore = favoriteDAO.countAll();
        favoriteDAO.deleteById(createdFavoriteId);
        int countAfter = favoriteDAO.countAll();

        assertEquals(countBefore - 1, countAfter,
                "Số lượng Favorite phải giảm 1 sau khi xóa");
        System.out.println("testDeleteFavorite: Xóa Favorite ID=" + createdFavoriteId + " thành công");
    }
}
