<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="dark">

<head>
  <meta charset="UTF-8" />
  <title>Search Videos</title>
  <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
  <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
  <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
    integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />

  <link href="../styles/NavBar.css" rel="stylesheet" />
  <style>
    .video-card {
      transition: transform 0.3s ease;
    }
    
    .video-card:hover {
      transform: translateY(-5px);
    }
    
    .card-img-top {
      height: 200px;
      object-fit: cover;
    }
    
    .sort-dropdown .dropdown-menu {
      min-width: 200px;
    }
  </style>
</head>

<body>
  <div class="position-relative">
    <%@ include file="./components/navbar.jsp" %>

      <div class="container-fluid p-0">
        <div class="container-fluid py-4 mb-4 border-bottom border-secondar bg-">
          <div class="container">
            <div class="row align-items-center">
              <div class="col-md-6">
                <h1 class="h2 mb-0">Kết quả tìm kiếm</h1>
                <p class="text-muted mb-0">Tìm được 4 video liên quan</p>
              </div>
              <div class="col-md-6">
                <div class="d-flex justify-content-md-end gap-3 mt-3 mt-md-0">
                  <div class="dropdown sort-dropdown">
                    <button class="btn btn-outline-light dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                      <i class="fas fa-sort me-2"></i>Lọc
                    </button>
                    <ul class="dropdown-menu">
                      <li><a class="dropdown-item" href="#"><i class="fas fa-calendar me-2"></i>Mới nhất</a></li>
                      <li><a class="dropdown-item" href="#"><i class="fas fa-calendar me-2"></i>Cổ nhất</a></li>
                      <li><a class="dropdown-item" href="#"><i class="fas fa-eye me-2"></i>Nhiều views</a></li>
                      <li><a class="dropdown-item" href="#"><i class="fas fa-eye me-2"></i>Ít views</a></li>
                      <li><a class="dropdown-item" href="#"><i class="fas fa-heart me-2"></i>Nhiều likes</a></li>
                      <li><a class="dropdown-item" href="#"><i class="fas fa-heart me-2"></i>Ít likes</a></li>
                      <li><hr class="dropdown-divider"></li>
                      <li><a class="dropdown-item" href="#"><i class="fas fa-font me-2"></i>(A-Z)</a></li>
                      <li><a class="dropdown-item" href="#"><i class="fas fa-font me-2"></i>(Z-A)</a></li>
                    </ul>
                  </div>
                  
                  <div class="btn-group" role="group">
                    <input type="radio" class="btn-check" name="view-mode" id="grid-view"  checked>
                    <label class="btn btn-outline-light" for="grid-view">
                      <i class="fas fa-th"></i>
                    </label>
                    
                    <input type="radio" class="btn-check" name="view-mode" id="list-view" >
                    <label class="btn btn-outline-light" for="list-view">
                      <i class="fas fa-list"></i>
                    </label>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Video -->
        <div class="container">
          <div class="row g-4" id="video-grid">
            <div class="col-md-6 col-lg-4 col-xl-3">
              <div class="card video-card h-100 bg-dark">
                <img src="../images/baner1.png" class="card-img-top" alt="Video Thumbnail">
                <div class="card-body d-flex flex-column">
                  <div class="d-flex justify-content-between align-items-start mb-2">
                    <span class="text-muted small">12:45</span>
                  </div>
                  <h5 class="card-title">Đây là 1 tiêu đề hay</h5>
                  <p class="card-text text-muted flex-grow-1">Đây là 1 phần giới thiệu có vẻ hay.</p>
                  <div class="d-flex justify-content-between align-items-center mt-auto">
                    <div class="d-flex gap-3 text-muted small">
                      <span><i class="fas fa-eye me-1"></i> 2.4K</span>
                      <span><i class="fas fa-heart me-1"></i> 156</span>
                    </div>
                    <small class="text-muted">3 ngày trước </small>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-md-6 col-lg-4 col-xl-3">
              <div class="card video-card h-100 bg-dark ">
                <img src="../images/baner1.png" class="card-img-top" alt="Video Thumbnail">
                <div class="card-body d-flex flex-column">
                  <div class="d-flex justify-content-between align-items-start mb-2">
                    <span class="text-muted small">18:22</span>
                  </div>
                  <h5 class="card-title">Đây là 1 tiêu đề hay</h5>
                  <p class="card-text text-muted flex-grow-1">Đây là 1 phần giới thiệu có vẻ hay.</p>
                  <div class="d-flex justify-content-between align-items-center mt-auto">
                    <div class="d-flex gap-3 text-muted small">
                      <span><i class="fas fa-eye me-1"></i> 1.8K</span>
                      <span><i class="fas fa-heart me-1"></i> 89</span>
                    </div>
                    <small class="text-muted">1 tuần trước</small>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-md-6 col-lg-4 col-xl-3">
              <div class="card video-card h-100 bg-dark ">
               <img src="../images/baner1.png" class="card-img-top" alt="Video Thumbnail">
                <div class="card-body d-flex flex-column">
                  <div class="d-flex justify-content-between align-items-start mb-2">
                    <span class="text-muted small">25:10</span>
                  </div>
                  <h5 class="card-title">Đây là 1 tiêu đề hay</h5>
                  <p class="card-text text-muted flex-grow-1">Đây là 1 phần giới thiệu có vẻ hay.</p>
                  <div class="d-flex justify-content-between align-items-center mt-auto">
                    <div class="d-flex gap-3 text-muted small">
                      <span><i class="fas fa-eye me-1"></i> 5.2K</span>
                      <span><i class="fas fa-heart me-1"></i> 324</span>
                    </div>
                    <small class="text-muted">2 tuần trước</small>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-md-6 col-lg-4 col-xl-3">
              <div class="card video-card h-100 bg-dark ">
                <img src="../images/baner1.png" class="card-img-top" alt="Video Thumbnail">
                <div class="card-body d-flex flex-column">
                  <div class="d-flex justify-content-between align-items-start mb-2">
                    <span class="text-muted small">15:33</span>
                  </div>
                  <h5 class="card-title">Đây là 1 tiêu đề hay</h5>
                  <p class="card-text text-muted flex-grow-1">Đây là 1 phần giới thiệu có vẻ hay.</p>
                  <div class="d-flex justify-content-between align-items-center mt-auto">
                    <div class="d-flex gap-3 text-muted small">
                      <span><i class="fas fa-eye me-1"></i> 3.7K</span>
                      <span><i class="fas fa-heart me-1"></i> 201</span>
                    </div>
                    <small class="text-muted">3 tuần trước</small>
                  </div>
                </div>
              </div>
            </div>

          </div>

          <!-- Page -->
          <nav aria-label="Search results pagination" class="mt-5">
            <ul class="pagination justify-content-center">
              <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
              </li>
              <li class="page-item active" aria-current="page">
                <a class="page-link" href="#">1</a>
              </li>
              <li class="page-item"><a class="page-link" href="#">2</a></li>
              <li class="page-item"><a class="page-link" href="#">3</a></li>
              <li class="page-item">
                <a class="page-link" href="#">Next</a>
              </li>
            </ul>
          </nav>
          
        </div>
      </div>
  </div>
</body>

</html>