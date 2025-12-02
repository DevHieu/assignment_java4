<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
<meta charset="UTF-8">
    <title>Thống kê</title>
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
    <link href="../styles/Statistics.css" rel="stylesheet" />
    
</head>
<body>
    <nav class="sticky-top navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid px-5">
            <a class="navbar-brand" href="/admin/home">
                <img src="../../icons/logoAdmin.png" alt="logo" style="height: 60px" />
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#adminNavbar" aria-controls="adminNavbar"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="adminNavbar">
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0 gap-4">
                    <li class="nav-item"><a class="nav-link fw-semibold" href="/home">Trang chủ</a></li>
                    <li class="nav-item"><a class="nav-link fw-semibold" href="/admin/skit">Tiểu phẩm</a></li>
                    <li class="nav-item"><a class="nav-link fw-semibold" href="/admin/users">Người dùng</a></li>
                    <li class="nav-item"><a class="nav-link fw-semibold" href="/admin/statistics">Thống kê</a></li>
                </ul>
            </div>
        </div>
    </nav>

        <main class="container-fluid px-5 py-4">

        <div class="row g-0">
            <div class="col-md-3 col-lg-2 border-end">
                <div class="nav flex-column nav-pills p-3 gap-2" id="statTabs" role="tablist">
                    <button class="nav-link text-start text-warning ${activeTab == 'tab1' ? 'active' : ''}" id="favorites-tab"
                            data-bs-toggle="tab" data-bs-target="#favorites" type="button" role="tab">
                        <i class="fa-solid fa-star me-2"></i>Favorites
                    </button>
                    <button class="nav-link text-start text-warning ${activeTab == 'tab2' ? 'active' : ''}" id="favorite-users-tab"
                            data-bs-toggle="tab" data-bs-target="#favorite-users" type="button" role="tab">
                        <i class="fa-solid fa-user me-2"></i>Favorite Users
                    </button>
                    <button class="nav-link text-start text-warning ${activeTab == 'tab3' ? 'active' : ''}" id="shared-friends-tab"
                            data-bs-toggle="tab" data-bs-target="#shared-friends" type="button" role="tab">
                        <i class="fa-solid fa-share-nodes me-2"></i>Shared Friends
                    </button>
                </div>
            </div>

            <div class="col-md-9 col-lg-10">
                <div class="tab-content p-4" id="statTabsContent">

                    <div class="tab-pane fade ${activeTab == 'tab1' ? 'show active' : ''}" id="favorites" role="tabpanel" aria-labelledby="favorites-tab">
                        <div class="table-responsive rounded-3 overflow-hidden border">
                            <table class="table table-hover table-striped table-bordered align-middle mb-0">
                                <thead class="table-dark">
                                    <tr>
                                        <th class="text-center">Video Title</th>
                                        <th class="text-center" style="width:160px">Favorite Count</th>
                                        <th class="text-center" style="width:160px">Latest Date</th>
                                        <th class="text-center" style="width:160px">Oldest Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty favoriteStatis}">
                                            <c:forEach var="f" items="${favoriteStatis}">
                                                <tr>
                                                    <td class="fw-semibold">${f.title}</td>
                                                    <td>${f.likeCount}</td>
                                                    <td><fmt:formatDate value="${f.latestDate}" pattern="dd/MM/yyyy" /></td>
                                                    <td><fmt:formatDate value="${f.oldestDate}" pattern="dd/MM/yyyy" /></td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="4" class="text-center py-4 text-muted">Không có dữ liệu</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="tab-pane fade ${activeTab == 'tab2' ? 'show active' : ''}" id="favorite-users" role="tabpanel" aria-labelledby="favorite-users-tab">
                        <form class="row g-2 align-items-center mb-3" action="/admin/statistics" method="get">
                            <input type="hidden" name="tab" value="favorite-users" />
                            <div class="col-sm-6 col-md-6">
                                <label class="form-label mb-1 text-warning">Video title</label>
                                <input class="form-control" name="favTitle" value="${param.favTitle}"
                                        placeholder="Nhập tiêu đề video..." />
                            </div>
                            <div class="col-md-6">
                                <label class="form-label mb-1 d-none d-md-block">&nbsp;</label>
                                <div class="d-flex gap-2">
                                    <button class="btn btn-warning" type="submit">
                                        <i class="fa-solid fa-magnifying-glass me-1"></i>
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div class="table-responsive rounded-3 overflow-hidden border">
                            <table class="table table-hover table-striped table-bordered align-middle mb-0">
                                <thead class="table-dark">
                                    <tr>
                                        <th class="text-center" style="width:160px">Username</th>
                                        <th class="text-center">Fullname</th>
                                        <th class="text-center">Email</th>
                                        <th class="text-center" style="width:160px">Favorite Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty favoriteUsers}">
                                            <c:forEach var="item" items="${favoriteUsers}">
                                                <tr>
                                                    <td>${item.user.id}</td>
                                                    <td class="fw-semibold">${item.user.fullname}</td>
                                                    <td>${item.user.email}</td>
                                                    <td><fmt:formatDate value="${item.likeDate}" pattern="dd/MM/yyyy" /></td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="4" class="text-center py-4 text-muted">Không có dữ liệu</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="tab-pane fade ${activeTab == 'tab3' ? 'show active' : ''}" id="shared-friends" role="tabpanel" aria-labelledby="shared-friends-tab">
                        <form class="row g-2 align-items-center mb-3" action="/admin/statistics" method="get">
                            <input type="hidden" name="tab" value="shared-friends" />
                            <div class="col-sm-6 col-md-6">
                                <label class="form-label mb-1 text-warning">Video title</label>
                                <input class="form-control" name="shareTitle" value="${param.shareTitle}"
                                       placeholder="Nhập tiêu đề video..." />
                            </div>
                            <div class="col-md-6">
                                <label class="form-label mb-1 d-none d-md-block">&nbsp;</label>
                                <div class="d-flex gap-2">
                                    <button class="btn btn-warning" type="submit">
                                        <i class="fa-solid fa-magnifying-glass me-1"></i>
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div class="table-responsive rounded-3 overflow-hidden border">
                            <table class="table table-hover table-striped table-bordered align-middle mb-0">
                                <thead class="table-dark">
                                    <tr>
                                        <th class="text-center">Sender Name</th>
                                        <th class="text-center">Sender Email</th>
                                        <th class="text-center">Receiver Email</th>
                                        <th class="text-center" style="width:160px">Sent Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty sharedFriends}">
                                            <c:forEach var="s" items="${sharedFriends}">
                                                <tr>
                                                    <td class="fw-semibold">${s.user.fullname}</td>
                                                    <td>${s.user.email}</td>
                                                    <td>${s.emails}</td>
                                                    <td><fmt:formatDate value="${s.shareDate}" pattern="dd/MM/yyyy" /></td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="4" class="text-center py-4 text-muted">Không có dữ liệu</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div> 
            </div> 
        </div> 
</body>
</html>