<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <title>User Management</title>

    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../../bootstrap/css/custom-dark.css" />
    <script defer src="../../bootstrap/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"/>

    <link href="../../styles/NavBar.css" rel="stylesheet" />
    <link href="../../styles/UserManager.css" rel="stylesheet" />
    <link rel="stylesheet" href="../styles/history.css" />
</head>

<body>

    <nav class="sticky-top navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid px-5">
            <a class="navbar-brand" href="/admin/home">
                <img src="../../icons/logoAdmin.png" alt="logo" style="height: 60px" />
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#adminNavbar">
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

    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success alert-dismissible fade show m-3" role="alert">
            ${sessionScope.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:set var="dummy" value="${sessionScope.remove('message')}" />
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show m-3" role="alert">
            ${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:set var="dummy" value="${sessionScope.remove('error')}" />
    </c:if>

    <div class="modal fade" id="userModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">

                <form action="/admin/users/save" method="post" enctype="multipart/form-data">

                    <div class="modal-header">
                        <h5 class="modal-title">
                            ${userEdit == null ? "Add User" : "Edit User"}
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>

                    <div class="modal-body">

                        <div class="mb-3">
                            <label class="form-label">ID:</label>
                            <c:choose>
                                <c:when test="${userEdit == null}">
                                    <input type="text" name="id" class="form-control" required
                                           placeholder="Enter user ID (e.g. user01)" />
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="id" value="${userEdit.id}" />
                                    <input type="text" class="form-control" value="${userEdit.id}" readonly />
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Fullname:</label>
                            <input type="text" name="fullname" class="form-control" required
                                   value="${userEdit != null ? userEdit.fullname : ''}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email:</label>
                            <input type="email" name="email" class="form-control" required
                                   value="${userEdit != null ? userEdit.email : ''}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Password:</label>
                            <input type="password" name="password" class="form-control"
                                ${userEdit == null ? 'required' : ''}>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Nhập lại Password:</label>
                            <input type="password" name="confirmPassword" class="form-control"
                                ${userEdit == null ? 'required' : ''}>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Avatar:</label>
                            <input type="file" name="avatarFile" class="form-control"
                                   accept="image/jpeg,image/png,image/gif,image/webp" 
                                   id="avatarInput">

                            <div class="mt-2">
                                <c:choose>
                                    <c:when test="${userEdit != null && userEdit.avatar != null}">
                                        <img id="avatarPreview" src="${userEdit.avatar}"
                                             style="max-width:200px; display:block;" />
                                    </c:when>
                                    <c:otherwise>
                                        <img id="avatarPreview" src="" style="max-width:200px; display:none;" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="mb-2 d-flex align-items-center">
                            <label class="form-label me-3 mb-0">Role:</label>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input"
                                    type="radio" name="role" value="USER" required
                                    ${userEdit == null || !userEdit.admin ? 'checked' : ''}>
                                <label class="form-check-label">User</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input"
                                    type="radio" name="role" value="ADMIN" required
                                    ${userEdit != null && userEdit.admin ? 'checked' : ''}>
                                <label class="form-check-label">Admin</label>
                            </div>
                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary"
                                data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-warning">Save</button>
                    </div>

                </form>
            </div>
        </div>
    </div>

    <main class="container-fluid px-5 py-4">

        <div class="d-flex justify-content-between align-items-center mb-3">
            <h5>User Management</h5>

            <div class="d-flex gap-2">

                <form action="/admin/users" method="get"
                    class="d-flex align-items-center gap-2">

                    <input class="form-control" name="q" type="search"
                        placeholder="Search..." value="${param.q}" />

                    <select class="form-select" name="role" style="min-width: 180px">
                        <option value="">All Roles</option>
                        <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                        <option value="USER" ${param.role == 'USER' ? 'selected' : ''}>User</option>
                    </select>

                    <button class="btn btn-warning" type="submit">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </form>

                <button type="button" class="btn btn-success"
                        data-bs-toggle="modal" data-bs-target="#userModal">
                    <i class="fa-solid fa-plus"></i> Add User
                </button>
            </div>
        </div>

        <div class="table-responsive border rounded-3 overflow-hidden">
            <table class="table table-hover table-striped align-middle mb-0 table-bordered">
                <thead class="table-dark">
                <tr>
                    <th class="text-center" style="width:80px">ID</th>
                    <th>Họ tên</th>
                    <th>Email</th>
                    <th class="text-center">Role</th>
                    <th class="text-center">Avatar</th>
                    <th class="text-center" style="width:180px">Action</th>
                </tr>
                </thead>

                <tbody>
                <c:choose>
                    <c:when test="${not empty users}">
                        <c:forEach var="u" items="${users}">
                            <tr>
                                <td class="text-center fw-bold">${u.id}</td>
                                <td>${u.fullname}</td>
                                <td>${u.email}</td>
                                <td class="text-center">
                                    <span class="badge ${u.admin ? 'bg-primary' : 'bg-secondary'}">
                                        ${u.admin ? 'ADMIN' : 'USER'}
                                    </span>
                                </td>

                                <td class="text-center">
                                    <img src="${u.avatar != null ? u.avatar : ''}"
                                        style="width:40px;height:40px;border-radius:50%;object-fit:cover">
                                </td>

                                <td class="text-center">
                                    <a href="/admin/users/edit?id=${u.id}"
                                    class="btn btn-sm btn-warning me-1">
                                        <i class="fa-solid fa-pen-to-square"></i> edit
                                    </a>

                                    <form action="/admin/users/delete" method="post"
                                        class="d-inline">
                                        <input type="hidden" name="id" value="${u.id}" />
                                        <button type="submit" class="btn btn-sm btn-danger"
                                                onclick="return confirm('Xóa user ${u.id}?')">
                                            <i class="fa-solid fa-trash-can"></i> delete
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
                                Chưa có người dùng nào
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>

        <c:if test="${pageCount > 1}">
            <nav class="mt-4" aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${currentPage - 1}<c:if test="${not empty paramQ}">&q=<c:out value="${paramQ}" /></c:if><c:if test="${not empty paramRole}">&role=<c:out value="${paramRole}" /></c:if>">
                            <i class="fa-solid fa-angle-left text-primary fs-6"></i>
                        </a>
                    </li>

                    <c:forEach begin="1" end="${pageCount}" var="p">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${p}<c:if test="${not empty paramQ}">&q=<c:out value="${paramQ}" /></c:if><c:if test="${not empty paramRole}">&role=<c:out value="${paramRole}" /></c:if>">
                                ${p}
                            </a>
                        </li>
                    </c:forEach>

                    <li class="page-item ${currentPage >= pageCount ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${currentPage + 1}<c:if test="${not empty paramQ}">&q=<c:out value="${paramQ}" /></c:if><c:if test="${not empty paramRole}">&role=<c:out value="${paramRole}" /></c:if>">
                            <i class="fa-solid fa-angle-right text-primary fs-6"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>

    </main>

    <script>
    document.addEventListener("DOMContentLoaded", function () {

        const input = document.getElementById('avatarInput');
        const img = document.getElementById('avatarPreview');

        if (input && img) {
            input.addEventListener('change', function () {
                const file = this.files && this.files[0];
                
                // 1. Nếu người dùng hủy chọn hoặc không có file
                if (!file) {
                    img.style.display = 'none';
                    img.src = '';
                    return;
                }

                // 2. ĐÃ SỬA: Kiểm tra có phải là ảnh hay không
                if (!file.type.startsWith('image/')) {
                    alert('File không hợp lệ! Vui lòng chỉ chọn file hình ảnh (jpg, png, gif...)');
                    this.value = ''; // Xóa file sai khỏi input
                    img.style.display = 'none';
                    img.src = '';
                    return;
                }

                // 3. Nếu đúng là ảnh thì hiển thị
                img.src = URL.createObjectURL(file);
                img.style.display = 'block';
            });
        }
    });
    </script>

    <c:if test="${not empty userEdit}">
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const modal = new bootstrap.Modal(document.getElementById('userModal'));
                modal.show();
            });
        </script>
    </c:if>

</body>
</html>