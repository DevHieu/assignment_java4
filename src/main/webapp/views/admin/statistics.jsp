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
</head>
<body>
    <nav class="sticky-top navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid px-5">
            <a class="navbar-brand" href="/home">
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

        <ul class="nav nav-tabs" id="statTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="favorites-tab" data-bs-toggle="tab" data-bs-target="#favorites" type="button" role="tab">Favorites</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="favorite-users-tab" data-bs-toggle="tab" data-bs-target="#favorite-users" type="button" role="tab">Favorite Users</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="shared-friends-tab" data-bs-toggle="tab" data-bs-target="#shared-friends" type="button" role="tab">Shared Friends</button>
            </li>
        </ul>

        <div class="tab-content border-start border-end border-bottom rounded-bottom-3 p-3" id="statTabsContent">
            <!-- Favorites summary -->
            <div class="tab-pane fade show active" id="favorites" role="tabpanel" aria-labelledby="favorites-tab">
                <form class="row g-2 align-items-center mb-3" action="/admin/statistics" method="get">
                    <input type="hidden" name="tab" value="favorites" />
                    <div class="col-sm-6 col-md-4">
                        <label class="form-label mb-1">Video title</label>
                        <input class="form-control" name="title" value="${param.title}" placeholder="Nhập tiêu đề video..." />
                    </div>
                    <div class="col-sm-6 col-md-4">
                        <label class="form-label mb-1">Khoảng thời gian</label>
                        <input class="form-control" name="range" value="${param.range}" placeholder="VD: 2020-01-01..2020-12-31" />
                    </div>
                    <div class="col-md-4">
                        <label class="form-label mb-1 d-none d-md-block">&nbsp;</label>
                        <div class="d-flex gap-2">
                            <button class="btn btn-primary" type="submit">
                                <i class="fa-solid fa-magnifying-glass me-1"></i>
                            </button>
                            <a class="btn btn-secondary" href="/admin/statistics?tab=shared-friends">Reset</a>
                        </div>
                    </div>
                </form>

                <div class="table-responsive rounded-3 overflow-hidden border">
                    <table class="table table-hover table-striped align-middle mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th>Video Title</th>
                                <th style="width:160px">Favorite Count</th>
                                <th style="width:160px">Latest Date</th>
                                <th style="width:160px">Oldest Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty favorites}">
                                    <c:forEach var="f" items="${favorites}">
                                        <tr>
                                            <td class="fw-semibold">${f.videoTitle}</td>
                                            <td>${f.favoriteCount}</td>
                                            <td><fmt:formatDate value="${f.latestDate}" pattern="dd/MM/yyyy" /></td>
                                            <td><fmt:formatDate value="${f.oldestDate}" pattern="dd/MM/yyyy" /></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="4" class="text-center py-4 text-muted">Không có dữ liệu</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>    
            <!-- Favorite Users -->
            <div class="tab-pane fade" id="favorite-users" role="tabpanel" aria-labelledby="favorite-users-tab">
                <form class="row g-2 align-items-center mb-3" action="/admin/statistics" method="get">
                    <input type="hidden" name="tab" value="favorite-users" />
                    <div class="col-sm-6 col-md-6">
                        <label class="form-label mb-1">Video title</label>
                        <input class="form-control" name="title" value="${param.title}" placeholder="Nhập tiêu đề video..." />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label mb-1 d-none d-md-block">&nbsp;</label>
                        <div class="d-flex gap-2">
                            <button class="btn btn-primary" type="submit">
                                <i class="fa-solid fa-magnifying-glass me-1"></i>
                            </button>
                            <a class="btn btn-secondary" href="/admin/statistics?tab=shared-friends">Reset</a>
                        </div>
                    </div>
                </form>

                <div class="table-responsive rounded-3 overflow-hidden border">
                    <table class="table table-hover table-striped align-middle mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th style="width:160px">Username</th>
                                <th>Fullname</th>
                                <th>Email</th>
                                <th style="width:160px">Favorite Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty favoriteUsers}">
                                    <c:forEach var="u" items="${favoriteUsers}">
                                        <tr>
                                            <td>${u.username}</td>
                                            <td class="fw-semibold">${u.fullname}</td>
                                            <td>${u.email}</td>
                                            <td><fmt:formatDate value="${u.favoriteDate}" pattern="dd/MM/yyyy" /></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="4" class="text-center py-4 text-muted">Không có dữ liệu</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Shared Friends -->
            <div class="tab-pane fade" id="shared-friends" role="tabpanel" aria-labelledby="shared-friends-tab">
                <form class="row g-2 align-items-center mb-3" action="/admin/statistics" method="get">
                    <input type="hidden" name="tab" value="shared-friends" />
                    <div class="col-sm-6 col-md-6">
                        <label class="form-label mb-1">Video title</label>
                        <input class="form-control" name="title" value="${param.title}" placeholder="Nhập tiêu đề video..." />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label mb-1 d-none d-md-block">&nbsp;</label>
                        <div class="d-flex gap-2">
                            <button class="btn btn-primary" type="submit">
                                <i class="fa-solid fa-magnifying-glass me-1"></i>
                            </button>
                            <a class="btn btn-secondary" href="/admin/statistics?tab=shared-friends">Reset</a>
                        </div>
                    </div>
                </form>

                <div class="table-responsive rounded-3 overflow-hidden border">
                    <table class="table table-hover table-striped align-middle mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th>Sender Name</th>
                                <th>Sender Email</th>
                                <th>Receiver Email</th>
                                <th style="width:160px">Sent Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty sharedFriends}">
                                    <c:forEach var="s" items="${sharedFriends}">
                                        <tr>
                                            <td class="fw-semibold">${s.senderName}</td>
                                            <td>${s.senderEmail}</td>
                                            <td>${s.receiverEmail}</td>
                                            <td><fmt:formatDate value="${s.sentDate}" pattern="dd/MM/yyyy" /></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="4" class="text-center py-4 text-muted">Không có dữ liệu</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
</body>
</html>