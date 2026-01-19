<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
<meta charset="UTF-8">
    <title>Admin Page</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />

    <link href="../styles/NavBar.css" rel="stylesheet" />
    
</head>
<body>
    <nav class="sticky-top navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid px-5">
            <a class="navbar-brand" href="/admin/home">
                <img
                    src="../../icons/logoAdmin.png"
                    alt="logo"
                    class=""
                    style="height: 60px"
                />
            </a>
            <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#adminNavbar"
            aria-controls="adminNavbar"
            aria-expanded="false"
            aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="adminNavbar" >
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0 gap-4">
                    <li class="nav-item">
                        <a class="nav-link fw-semibold" aria-current="page" href="/home"
                            >Trang chủ</a
                        >
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fw-semibold"  href="/admin/skit">Tiểu phẩm</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fw-semibold" href="/admin/users">Người dùng</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fw-semibold" href="/admin/statistics">Thống kê</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    

    <div class="container mt-4">
        <h2 class="mb-4 text-primary">Dashboard Tổng quan</h2>
        
        <div class="row">
            
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card card-metric border-primary shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col me-2">
                                <div class="text-xs fw-bold text-primary text-uppercase mb-1">Tổng số Tiểu phẩm</div>
                                <div class="h5 mb-0 fw-bold text-gray-800">${totalVideo}</div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-video fa-2x text-gray-300 icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card card-metric border-success shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col me-2">
                                <div class="text-xs fw-bold text-success text-uppercase mb-1">Tổng số User</div>
                                <div class="h5 mb-0 fw-bold text-gray-800">${totalUser}</div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-users fa-2x text-gray-300 icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card card-metric border-danger shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col me-2">
                                <div class="text-xs fw-bold text-danger text-uppercase mb-1">Tổng Lượt Like</div>
                                <div class="h5 mb-0 fw-bold text-gray-800">${totalLike}</div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-heart fa-2x text-gray-300 icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    
            <div class="col-xl-3 col-md-6 mb-4">
                <div class="card card-metric border-warning shadow h-100 py-2">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col me-2">
                                <div class="text-xs fw-bold text-warning text-uppercase mb-1">Tổng Lượt Share</div>
                                <div class="h5 mb-0 fw-bold text-gray-800">${totalShare}</div>
                            </div>
                            <div class="col-auto">
                                <i class="fas fa-share-alt fa-2x text-gray-300 icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <hr class="my-4">
    
        <div class="row">
            
            <div class="col-lg-6 mb-4">
                <div class="card shadow">
                    <div class="card-header py-3 bg-primary text-white">
                        <h6 class="m-0 fw-bold">Top 10 Video Được Like Nhiều Nhất</h6>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover table-sm">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Tiêu đề</th>
                                    <th>Tổng lượt like</th>
                                    <th>Tổng lượt xem</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="video" items="${top10Videos}" varStatus="st">
                                    <tr><td>${st.count}</td><td>${video.title}</td><td>${video.totalLike}</td><td>${video.views}</td><td><a href="/watch?id=${video.id}" class="btn btn-sm btn-outline-success fw-bold">Xem</a></td></tr>
                                </c:forEach> 
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
            <div class="col-lg-6 mb-4">
                <div class="card shadow">
                    <div class="card-header py-3 bg-danger text-white">
                        <h6 class="m-0 fw-bold">Quản lý Video trên Banner</h6>
                    </div>
                    <div class="card-body">
                        <table class="table table-bordered table-sm text-center">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Tiêu đề Video</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${bannerVideos}">
                                    <tr>
                                        <td>${item.id}</td>
                                        <td class="text-start">${item.title}</td>
                                        <td>
                                            <form action="/admin/skit/removeBanner" method="post" >
                                                <input type="hidden" name="videoId" value="${item.id}">
                                                <button class="btn btn-sm btn-danger" onclick="return confirm('Xóa hông?')">Xóa</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    
    </div>
</body>
</html>