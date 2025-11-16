<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html data-bs-theme="dark">

<head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
        integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="../styles/NavBar.css" rel="stylesheet" />
    
</head>

<body>
    <div class="position-relative">
        <%@ include file="./components/navbar.jsp" %>

        <div class="container-fluid p-0">
            <div class="container-fluid py-4">
                <div class="container">
                    <div class="row">
                        <!-- Video -->
                        <div class="col-lg-8">
                            <!-- Video player -->
                            <div class="ratio ratio-16x9 mb-4 bg-dark rounded">
                                <video controls class="w-100 h-100 rounded-2">
                                    <source src="..." type="video/mp4">
                                    Trình duyệt của cùi đến mức không hỗ trợ định dạng video mp4.
                                </video>
                            </div>

                            <!-- Thông tin  -->
                            <div class="video-info mb-4">
                                <h1 class="h3 mb-3">Đây là tiêu đề có vẻ hay</h1>
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <div class="text-muted">
                                        <span class="me-3"><i class="fas fa-eye me-1"></i> 1K lượt xem</span>
                                        <span><i class="fas fa-calendar me-1"></i> 1 ngày trước</span>
                                    </div>
                                    <div class="d-flex gap-2">
                                        <button class="btn btn-outline-light btn-sm  border-0">
                                            <i class="far fa-heart me-1"></i>
                                            <span>324</span>
                                        </button>
                                        <button class="btn btn-outline-light btn-sm  border-0">
                                            <i class="fa-solid fa-share me-1"></i>
                                            <span>Chia sẻ</span>
                                        </button>
                                    </div>
                                </div>

                                <!-- người đăng -->
                                <div class="d-flex align-items-center gap-3 p-3 bg-dark bg-opacity-50 rounded mb-3">
                                    <img src="../images/baner1.png" class="rounded-circle" width="50" height="50" alt="Avatar">
                                    <h6 class="mb-1">Đây là 1 cái tên có vẻ ngầu</h6>
                                </div>

                                <!-- mô tả -->
                                <div class="video-description bg-dark bg-opacity-25 p-3 rounded">
                                    <h6 class="mb-3">Mô tả video</h6>
                                    <p class="text-muted mb-2">Đây là phần mô tả chi tiết về video có vẻ sẽ hay hihi</p>
                                    <p class="text-muted mb-0">#hashtag1</p>
                                </div>
                            </div>
                        </div>

                        <!--  đề xuất -->
                        <div class="col-lg-4">
                            <h5 class="mb-3">Video đề xuất</h5>
                            <div class="suggested-videos">
                                <div class="suggested-video-item d-flex gap-2 mb-3">
                                    <div class="flex-shrink-0">
                                        <img src="../images/baner1.png" alt="Video Thumbnail" class="rounded" width="120" height="68">
                                    </div>
                                    <div class="flex-grow-1">
                                        <h6 class="mb-1 small">Đây là tiêu đề video ở đề xuất có vẻ hay</h6>
                                        <p class="text-muted small mb-1">Tên này có vẻ chất lượng</p>
                                        <div class="text-muted small">
                                            <span>12K lượt xem</span> • <span>2 ngày trước</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>