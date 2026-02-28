package unit_test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.asm.dao.ShareDAO;
import com.asm.dao.VideoDAO;
import com.asm.dao.impl.ShareDAOImpl;
import com.asm.dao.impl.VideoDAOImpl;
import com.asm.entity.Share;
import com.asm.entity.User;
import com.asm.entity.Video;

public class ShareDAOTest {

    static ShareDAO shareDAO;
    static VideoDAO videoDAO;
    static Long createdShareId;

    @BeforeClass
    public static void setUp() {
        shareDAO = new ShareDAOImpl();
        videoDAO = new VideoDAOImpl();
    }

    @Test(priority = 1)
    public void testFindAll() {
        List<Share> shares = shareDAO.findAll();
        Assert.assertNotNull(shares, "findAll() không được trả về null");
        System.out.println("testFindAll: Tìm thấy " + shares.size() + " bản ghi Share");
    }

    @Test(priority = 2)
    public void testCountAll() {
        int count = shareDAO.countAll();
        Assert.assertTrue(count >= 0, "countAll() phải >= 0");
        System.out.println("testCountAll: Tổng số Share = " + count);
    }

    @Test(priority = 3)
    public void testCreateShare() {
        User user = new User();
        user.setId("admin");

        Video video = videoDAO.findById("V001");
        Assert.assertNotNull(video, "Video V001 phải tồn tại");

        int countBefore = shareDAO.countAll();

        Share share = new Share();
        share.setUser(user);
        share.setVideo(video);
        share.setEmails("test@example.com");
        shareDAO.create(share);

        Assert.assertTrue(share.getId() > 0, "ID phải được tạo tự động sau khi persist");
        createdShareId = share.getId();

        int countAfter = shareDAO.countAll();
        Assert.assertEquals(countAfter, countBefore + 1,
                "Số lượng Share phải tăng 1 sau khi tạo");
        System.out.println("testCreateShare: Tạo Share ID=" + createdShareId);
    }

    @Test(priority = 4)
    public void testFindById() {
        if (createdShareId == null) {
            List<Share> all = shareDAO.findAll();
            if (!all.isEmpty())
                createdShareId = all.get(0).getId();
        }
        Assert.assertNotNull(createdShareId);

        Share share = shareDAO.findById(createdShareId);
        Assert.assertNotNull(share, "Phải tìm thấy Share vừa tạo");
        Assert.assertEquals(share.getEmails(), "test@example.com");
        System.out.println("testFindById: Share ID=" + createdShareId + ", emails=" + share.getEmails());
    }

    @Test(priority = 5)
    public void testDeleteShare() {
        Assert.assertNotNull(createdShareId, "Phải có ID để xóa");

        int countBefore = shareDAO.countAll();
        shareDAO.deleteById(createdShareId);
        int countAfter = shareDAO.countAll();

        Assert.assertEquals(countAfter, countBefore - 1,
                "Số lượng Share phải giảm 1 sau khi xóa");
        System.out.println("testDeleteShare: Xóa Share ID=" + createdShareId + " thành công");
    }
}
