<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
<meta charset="UTF-8">
    <title>User Management</title>
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
    <link href="../styles/UserManager.css" rel="stylesheet" />

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

    <div class="modal fade" id="userModal" tabindex="-1" aria-labelledby="userModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="userModalLabel">Add User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">ID:</label>
                            <input type="text" name="id" class="form-control" maxlength="20">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Fullname:</label>
                            <input type="text" name="fullname" class="form-control" maxlength="50">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email:</label>
                            <input type="email" name="email" class="form-control" maxlength="50">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Password:</label>
                            <input type="password" name="password" class="form-control" maxlength="50">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Nhập lại Password:</label>
                            <input type="password" name="confirmPassword" class="form-control" maxlength="50">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Avatar:</label>
                            <input type="file" name="avatarFile" class="form-control" accept="image/*" id="avatarInput">
                            <div class="mt-2">
                                <img id="avatarPreview" src="" alt="Avatar preview"
                                    style="display:none; max-width:200px; border-radius:8px; object-fit:cover;">
                            </div>
                        </div>

                        <div class="mb-2 d-flex align-items-center">
                            <label class="form-label mb-0 me-3">Role:</label>
                            <div class="form-check form-check-inline mb-0">
                                <input class="form-check-input" type="radio" name="role" id="roleUser" value="USER" checked>
                                <label class="form-check-label" for="roleUser">User</label>
                            </div>
                            <div class="form-check form-check-inline mb-0">
                                <input class="form-check-input" type="radio" name="role" id="roleAdmin" value="ADMIN">
                                <label class="form-check-label" for="roleAdmin">Admin</label>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-warning">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <main class="container-fluid px-5 py-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h5 class="m-0">User Management</h5>
            <div class="d-flex align-items-center gap-2">
                <form class="d-flex align-items-center gap-2" action="/admin/users" method="get">
                    <input class="form-control" name="q" type="search"
                           placeholder="Search by name or email..." value="${param.q}" />
                    <select class="form-select" name="role" style="min-width: 180px">
                        <option value="">All Roles</option>
                        <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                        <option value="USER" ${param.role == 'USER' ? 'selected' : ''}>User</option>
                    </select>
                    <button class="btn btn-warning" type="submit">
                        <i class="fa-solid fa-magnifying-glass me-1"></i>
                    </button>
                </form>
                <button type="button"
                        class="btn btn-success"
                        data-bs-toggle="modal"
                        data-bs-target="#userModal">
                    <i class="fa-solid fa-plus me-1"></i> Add User
                </button>
            </div>
        </div>

        <div class="table-responsive rounded-3 overflow-hidden border">
            <table class="table table-hover table-striped align-middle mb-0 table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th class="text-center" style="width:80px">ID</th>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th class="text-center" style="width:120px">Vai trò</th>
                        <th class="text-center" style="width:100px">Avatar</th>
                        <th class="text-center" style="width:180px">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty users}">
                            <!-- ĐÚNG RỒI ĐÂY: DÙNG forEach ĐỂ LẶP DANH SÁCH -->
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td class="text-center fw-bold">${u.id}</td>
                                    <td>${u.fullname}</td>
                                    <td>${u.email}</td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${u.admin}">
                                                <span class="badge bg-primary">ADMIN</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">USER</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <div class="bg-secondary-subtle rounded-circle d-inline-flex align-items-center justify-content-center"
                                             style="width:40px;height:40px;overflow:hidden">
                                            <i class="fa-solid fa-user text-secondary"></i>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <a href="/admin/users/edit?id=${u.id}" class="btn btn-sm btn-warning me-1">
                                            <i class="fa-solid fa-pen-to-square"></i> Sửa
                                        </a>
                                        <form action="/admin/users/delete" method="post" class="d-inline">
                                            <input type="hidden" name="id" value="${u.id}" />
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                    onclick="return confirm('Xóa user ${u.id} thật hả bé?')">
                                                <i class="fa-solid fa-trash-can"></i> Xóa
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" class="text-center py-5 text-muted">
                                    <i class="fa-solid fa-users-slash fa-3x mb-3"></i><br>
                                    Chưa có người dùng nào hết bé ơi
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <!-- Phân trang -->
        <c:if test="${pageCount > 1}">
            <nav class="mt-4" aria-label="Pagination">
                <ul class="pagination justify-content-center mb-0">
                    <c:forEach var="p" begin="1" end="${pageCount}">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link"
                               href="/admin/users?page=${p}&q=${fn:escapeXml(paramQ)}&role=${paramRole}">${p}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const input = document.getElementById('avatarInput');
            const img = document.getElementById('avatarPreview');

            if (!input || !img) return;

            input.addEventListener('change', function () {
                const file = this.files && this.files[0];
                if (!file) {
                    img.src = '';
                    img.style.display = 'none';
                    return;
                }
                const url = URL.createObjectURL(file);
                img.src = url;
                img.style.display = 'block';
            });
        });
    </script>
</body>
</html>