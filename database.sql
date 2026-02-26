CREATE DATABASE IF NOT EXISTS `java4_asm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `java4_asm`;

DROP TABLE IF EXISTS `favorite`;
DROP TABLE IF EXISTS `share`;
DROP TABLE IF EXISTS `history`;
DROP TABLE IF EXISTS `video`;
DROP TABLE IF EXISTS `user`;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE `user` (
  `id` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NULL,
  `fullname` VARCHAR(255) NULL,
  `email` VARCHAR(255) NULL,
  `avatar` VARCHAR(255) NULL,
  `admin` BIT(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `video`
-- -----------------------------------------------------
CREATE TABLE `video` (
  `id` VARCHAR(50) NOT NULL,
  `title` VARCHAR(255) NULL,
  `poster` VARCHAR(255) NULL,
  `video` VARCHAR(255) NULL,
  `views` INT DEFAULT 0,
  `description` TEXT NULL,
  `isBanner` BIT(1) DEFAULT b'0',
  `active` BIT(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `favorite`
-- -----------------------------------------------------
CREATE TABLE `favorite` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(50) NULL,
  `videoId` VARCHAR(50) NULL,
  `likeDate` DATE NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_favorite_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_favorite_video` FOREIGN KEY (`videoId`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `share`
-- -----------------------------------------------------
CREATE TABLE `share` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(50) NULL,
  `videoId` VARCHAR(50) NULL,
  `emails` VARCHAR(255) NULL,
  `shareDate` DATE NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_share_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_share_video` FOREIGN KEY (`videoId`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `history`
-- -----------------------------------------------------
CREATE TABLE `history` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `UserId` VARCHAR(50) NULL,
  `VideoId` VARCHAR(50) NULL,
  `ViewDate` DATE NULL,
  PRIMARY KEY (`Id`)
);

-- =====================================================
-- BƯỚC THÊM DỮ LIỆU MẪU (Mock Data)
-- =====================================================

-- 1. Insert User
-- Account "testuser" / "123" for testing WAT & ACT
INSERT INTO `user` (`id`, `password`, `fullname`, `email`, `avatar`, `admin`) VALUES 
('admin', '123', 'System Admin', 'admin@example.com', 'admin.png', b'1'),
('testuser', '123', 'Test User', 'testuser@example.com', 'user.png', b'0'),
('user1', '123', 'Nguyen Van A', 'user1@example.com', '1.png', b'0');

-- 2. Insert Videos
-- Test cases requires:
-- WAT-001/002: Video V001 with YouTube iframe "dQw4w9WgXcQ"
-- SEA-001: Video containing "Hài"
-- SEA-005: Videos containing "Phim" (enough for 2 pages pagination, need ~10 videos for "Phim" and >20 videos overall to test recommended panel WAT-004)
INSERT INTO `video` (`id`, `title`, `poster`, `video`, `views`, `description`, `isBanner`, `active`) VALUES
('V001', 'Phim Hài Tết 2026', 'poster1.jpg', 'dQw4w9WgXcQ', 1500, 'Mô tả phim hài tết', b'1', b'1'),
('V002', 'Phim Hành Động Kịch Tính', 'poster2.jpg', 'v_v2.mp4', 800, 'Video phim hành động', b'0', b'1'),
('V003', 'Phim Tình Cảm Lãng Mạn', 'poster3.jpg', 'v_v3.mp4', 2000, 'Phim tình cảm', b'0', b'1'),
('V004', 'Phim Kinh Dị Hồi Hộp', 'poster4.jpg', 'v_v4.mp4', 120, 'Kinh dị rùng rợn', b'0', b'1'),
('V005', 'Phim Hoạt Hình Dễ Thương', 'poster5.jpg', 'v_v5.mp4', 3500, 'Hoạt hình cho bé', b'0', b'1'),
('V006', 'Hài Kịch Chấn Động', 'poster6.jpg', 'v_v6.mp4', 500, 'Hài kịch cuối tuần', b'0', b'1'),
('V007', 'Phim Khoa Học Viễn Tưởng', 'poster7.jpg', 'v_v7.mp4', 600, 'Sci-Fi viễn tưởng', b'0', b'1'),
('V008', 'Phim Cổ Trang Xưa', 'poster8.jpg', 'v_v8.mp4', 400, 'Cổ trang', b'0', b'1'),
('V009', 'Phim Truyền Thuyết', 'poster9.jpg', 'v_v9.mp4', 900, 'Truyền thuyết xưa', b'0', b'1'),
('V010', 'Phim Trinh Thám Lọc Lõi', 'poster10.jpg', 'v_v10.mp4', 110, 'Trinh thám hay', b'0', b'1'),
('V011', 'Phim Tâm Lý Gia Đình', 'poster11.jpg', 'v_v11.mp4', 201, 'Phim gia đình', b'0', b'1'),
('V012', 'Phim Ca Nhạc Tạp Kỹ', 'poster12.jpg', 'v_v12.mp4', 330, 'Nhạc kịch', b'0', b'1'),
('V013', 'Hài Độc Thoại Hay', 'poster13.jpg', 'v_v13.mp4', 700, 'Độc thoại mặn mòi', b'0', b'1'),
('V014', 'Video Giải Trí Cuối Tuần', 'poster14.jpg', 'v_v14.mp4', 150, 'Giải trí', b'0', b'1'),
('V015', 'Phim Ngắn Đạt Giải', 'poster15.jpg', 'v_v15.mp4', 4200, 'Phim ngắn hay', b'0', b'1'),
('V016', 'Bí Mật Màn Ảnh', 'poster16.jpg', 'v_v16.mp4', 85, 'Behind the screen', b'0', b'1'),
('V017', 'Phim Hình Sự Chuyên Nghiệp', 'poster17.jpg', 'v_v17.mp4', 1050, 'Hình sự kịch tính', b'0', b'1'),
('V018', 'Đột Kích (Phim Hài)', 'poster18.jpg', 'v_v18.mp4', 920, 'Action comedy', b'0', b'1'),
('V019', 'Phim Tài Liệu Tự Nhiên', 'poster19.jpg', 'v_v19.mp4', 300, 'Documentary', b'0', b'1'),
('V020', 'Siêu Phim Bom Tấn', 'poster20.jpg', 'v_v20.mp4', 10, 'Blockbuster', b'0', b'1'),
('V021', 'Phim Đặc Sắc Nhất', 'poster21.jpg', 'v_v21.mp4', 55, 'Special release', b'0', b'1'),
('V022', 'Phim Kém Nổi Tiếng', 'poster22.jpg', 'v_v22.mp4', 0, 'No views', b'0', b'1');

-- 3. Insert Favorites
-- Test ACT-002: Requirements mention V002 is already liked by the user.
-- Also for SRT-003, we need sorting by Most Likes -> V001 has 2 likes, V002 has 1 like, V003 has 1 like.
INSERT INTO `favorite` (`userId`, `videoId`, `likeDate`) VALUES
('admin', 'V001', '2026-01-01'),
('user1', 'V001', '2026-01-02'),
('testuser', 'V002', '2026-02-01'), -- testuser already liked V002
('admin', 'V003', '2026-02-05');

-- 4. Insert Shares
INSERT INTO `share` (`userId`, `videoId`, `emails`, `shareDate`) VALUES
('testuser', 'V001', 'friend@example.com', '2026-02-10');

-- 5. Insert History
INSERT INTO `history` (`UserId`, `VideoId`, `ViewDate`) VALUES
('testuser', 'V002', '2026-02-05');
