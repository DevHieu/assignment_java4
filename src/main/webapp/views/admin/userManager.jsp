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
                            <label class="form-label mb-0 me-3">Vai trò:</label>
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
                        <th class="text-center" scope="col" style="width:72px">ID</th>
                        <th class="text-center" scope="col">Fullname</th>
                        <th class="text-center" scope="col">Email</th>
                        <th class="text-center" scope="col" style="width:140px">Role</th>
                        <th class="text-center" scope="col" style="width:120px">Avatar</th>
                        <!-- <th scope="col" style="width:120px">Status</th> -->
                        <th class="text-center" scope="col" class="text-end" style="width:180px">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty users}">
                            <tr>
                                <td>${u.id}</td>
                                <td class="fw-semibold">${u.fullname}</td>
                                <td>${u.email}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${u.admin}">
                                            <span class="badge bg-primary">ADMIN</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">USER</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="d-inline-flex align-items-center justify-content-center rounded-circle bg-secondary-subtle"
                                        style="width:36px;height:36px">
                                        <i class="fa-solid fa-user text-secondary"></i>
                                    </span>
                                </td>
                                <!-- <td>
                                    <span class="text-muted">—</span>
                                    <%-- Nếu sau này có u.active (Boolean), dùng:
                                    <c:choose>
                                        <c:when test="${u.active}"><span class="badge bg-success">Active</span></c:when>
                                        <c:otherwise><span class="badge bg-danger">Blocked</span></c:otherwise>
                                    </c:choose>
                                    --%>
                                </td> -->
                                <td class="text-end">
                                    <a href="/admin/users/edit?id=${u.id}" class="btn btn-sm btn-warning me-2">
                                        <i class="fa-solid fa-pen-to-square"></i><span class="ms-1">Edit</span>
                                    </a>
                                    <form action="/admin/users/delete" method="post" class="d-inline">
                                        <input type="hidden" name="id" value="${u.id}" />
                                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Delete this user?')">
                                            <i class="fa-solid fa-trash-can"></i><span class="ms-1">Delete</span>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" class="text-center py-4 text-muted">No users found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <c:if test="${not empty pageCount}">
            <nav class="mt-3" aria-label="Pagination">
                <ul class="pagination justify-content-end mb-0">
                    <c:forEach var="p" begin="1" end="${pageCount}">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link"
                               href="/admin/users?page=${p}&q=${fn:escapeXml(param.q)}&role=${param.role}">${p}</a>
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