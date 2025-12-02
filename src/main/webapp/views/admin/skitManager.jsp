<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
<meta charset="UTF-8">
    <title>Skit Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/NavBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/UserManager.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/history.css">
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

    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success alert-dismissible fade show mx-5 mt-3">
            <i class="fas fa-check-circle me-2"></i>${sessionScope.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <!-- Thông báo lỗi -->
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show mx-5 mt-3">
            <i class="fas fa-exclamation-circle me-2"></i>${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <div class="modal fade" id="skitModal" tabindex="-1" aria-labelledby="skitModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form action="/admin/skit/save" method="post" enctype="multipart/form-data">
                    <div class="modal-header">
                        <h5 class="modal-title" id="skitModalLabel">
                            ${videoEdit == null ? "Add Skit" : "Edit Skit"}
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Link YouTube:</label>
                            <input type="text" id="youtubeUrl" class="form-control" 
                                placeholder="" value="${videoEdit != null ? videoEdit.id : ''}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Youtube ID:</label>
                            <div class="input-group">
                                <c:choose>
                                    <c:when test="${videoEdit == null}">
                                        <input type="text" name="youtubeId" id="youtubeId" class="form-control" 
                                            placeholder="" required>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="id" value="${videoEdit.id}" />
                                        <input type="text" id="youtubeId" name="youtubeId" class="form-control" 
                                            value="${videoEdit.id}" readonly>
                                    </c:otherwise>
                                </c:choose>
                                <button class="btn btn-outline-warning" type="button" id="extractBtn"
                                    ${videoEdit != null ? 'disabled' : ''}>
                                    Lấy ID
                                </button>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Video Title:</label>
                            <input type="text" name="title" class="form-control" maxlength="200"
                                value="${videoEdit != null ? videoEdit.title : ''}" required>
                        </div>

                        <div class="mb-3">
                            <div class="d-flex align-items-center gap-3">
                                <label class="form-label mb-0 me-2">Status: </label>

                                <div class="form-check form-check-inline mb-0">
                                    <input class="form-check-input"
                                           type="radio"
                                           name="status"
                                           id="statusActive"
                                           value="ACTIVE"
                                           ${videoEdit == null || videoEdit.active ? 'checked' : ''}>
                                    <label class="form-check-label" for="statusActive">Active</label>
                                </div>

                                <div class="form-check form-check-inline mb-0">
                                    <input class="form-check-input"
                                           type="radio"
                                           name="status"
                                           id="statusInactive"
                                           value="INACTIVE"
                                           ${videoEdit != null && !videoEdit.active ? 'checked' : ''}>
                                    <label class="form-check-label" for="statusInactive">Inactive</label>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="d-flex align-items-center gap-3">
                                <label class="form-label mb-0 me-2">Hiển thị trên banner?</label>
                                <div class="form-check form-check-inline mb-0">
                                    <input class="form-check-input"
                                        type="radio"
                                        name="isBanner"
                                        id="bannerYes"
                                        value="true"
                                        ${videoEdit != null && videoEdit.banner ? 'checked' : ''}>
                                    <label class="form-check-label" for="bannerYes">Yes</label>
                                </div>

                                <div class="form-check form-check-inline mb-0">
                                    <input class="form-check-input"
                                        type="radio"
                                        name="isBanner"
                                        id="bannerNo"
                                        value="false"
                                        ${videoEdit == null || !videoEdit.banner ? 'checked' : ''}>
                                    <label class="form-check-label" for="bannerNo">No</label>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Poster:</label>
                            <input type="file" name="posterFile" class="form-control" accept="image/*" id="posterInput">
                            <div class="mt-2">
                                <c:choose>
                                    <c:when test="${videoEdit != null && videoEdit.poster != null}">
                                        <img id="posterPreview" src="${videoEdit.poster}"
                                            style="max-width:200px; display:block; border-radius:8px; object-fit:cover;">
                                    </c:when>
                                    <c:otherwise>
                                        <img id="posterPreview" src="" alt="Poster preview"
                                            style="display:none; max-width:200px; border-radius:8px; object-fit:cover;">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mô tả</label>
                            <textarea name="description" class="form-control" rows="3"
                                    placeholder="Mô tả nội dung tiểu phẩm...">${videoEdit != null ? videoEdit.description : ''}</textarea>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-warning">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <main class="container-fluid px-5 py-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h5 class="m-0">Skit Management</h5>
            <div class="d-flex align-items-center gap-2">
                <form class="d-flex align-items-center gap-2" action="/admin/skit" method="get">
                    <input class="form-control" name="q" type="search"
                           placeholder="Search by video name" value="${param.q}" />
                    <button class="btn btn-warning" type="submit">
                        <i class="fa-solid fa-magnifying-glass me-1"></i>
                    </button>
                </form>
                <button type="button"
                        class="btn btn-success"
                        data-bs-toggle="modal"
                        data-bs-target="#skitModal">
                    <i class="fa-solid fa-plus me-1"></i> Add Skit
                </button>
            </div>
        </div>

        <div class="table-responsive rounded-3 overflow-hidden border">
            <table class="table table-hover table-striped align-middle mb-0 table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th class="text-center" scope="col" style="width:110px">Youtube Id</th>
                        <th class="text-center" scope="col">Video Title</th>
                        <th class="text-center" scope="col">View Count</th>
                        <th class="text-center" scope="col" style="width:100px">Status</th>
                        <th class="text-center" scope="col" style="width:100px">Banner</th>
                        <th class="text-center" scope="col" style="width:100px">Poster</th>
                        <th class="text-center" scope="col" class="text-end" style="width:180px">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty videos}">
                            <c:forEach var="v" items="${videos}">
                                <tr>
                                    <td class="text-center">${v.id}</td>
                                    <td>${v.title}</td>
                                    <td class="text-center">${v.views}</td>
                                    <td class="text-center">
                                        <span class="badge ${v.active ? 'bg-success' : 'bg-secondary'}">
                                            ${v.active ? 'Active' : 'Inactive'}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge ${v.banner ? 'bg-info' : 'bg-secondary'}">
                                            ${v.banner ? 'Yes' : 'No'}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <c:if test="${not empty v.poster}">
                                            <img src="${v.poster}" style="width:60px;height:60px;border-radius:4px;object-fit:cover;" alt="Poster">
                                        </c:if>
                                        <c:if test="${empty v.poster}">
                                            <span class="text-muted">-</span>
                                        </c:if>
                                    </td>
                                    <td class="text-center">
                                        <a href="/admin/skit/edit?id=${v.id}"
                                        class="btn btn-sm btn-warning me-1">
                                            <i class="fa-solid fa-pen-to-square"></i> edit
                                        </a>
                                        <form action="/admin/skit/delete" method="post" class="d-inline">
                                            <input type="hidden" name="id" value="${v.id}" />
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                    onclick="return confirm('Xóa video ${v.id}?')">
                                                <i class="fa-solid fa-trash-can"></i> delete
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="7" class="text-center py-5 text-muted">
                                    Chưa có video nào hết bé ơi
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        
        
        <c:if test="${not empty pageCount}">
            <nav class="mt-4" aria-label="Pagination">
                <ul class="pagination justify-content-center">
                    <!-- Nút Previous -->
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link"
                        href="/admin/skit?page=${currentPage - 1}
                        <c:if test='${not empty paramQ}'>&amp;q=<c:out value='${paramQ}'/></c:if>">
                            <i class="fa-solid fa-angle-left text-primary fs-6"></i>
                        </a>
                    </li>

                    <!-- Các số trang -->
                    <c:forEach var="p" begin="1" end="${pageCount}">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link"
                            href="/admin/skit?page=${p}
                            <c:if test='${not empty paramQ}'>&amp;q=<c:out value='${paramQ}'/></c:if>">
                                ${p}
                            </a>
                        </li>
                    </c:forEach>

                    <!-- Nút Next -->
                    <li class="page-item ${currentPage >= pageCount ? 'disabled' : ''}">
                        <a class="page-link"
                        href="/admin/skit?page=${currentPage + 1}
                        <c:if test='${not empty paramQ}'>&amp;q=<c:out value='${paramQ}'/></c:if>">
                            <i class="fa-solid fa-angle-right text-primary fs-6"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>

    </main>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const urlInput     = document.getElementById('youtubeUrl');
            const idInput      = document.getElementById('youtubeId');
            const extractBtn   = document.getElementById('extractBtn');
            const posterInput  = document.getElementById('posterInput');
            const posterPreview = document.getElementById('posterPreview');

            function extractYouTubeID(url) {
                const regex = /(?:youtube\.com\/watch\?v=|youtu\.be\/|youtube\.com\/embed\/)([^&\n?#]+)/;
                const match = url.match(regex);
                return match ? match[1] : null;
            }

            if (extractBtn) {
                extractBtn.onclick = function () {
                    const url = urlInput.value.trim();
                    if (!url) {
                        alert('Dán link youtube bạn nhé!');
                        return;
                    }
                    const videoId = extractYouTubeID(url);
                    if (videoId) {
                        idInput.value = videoId;
                        alert('Lấy ID thành công: ' + videoId);
                    } else {
                        alert('Link YouTube không hợp lệ');
                    }
                };
            }

            urlInput.addEventListener('blur', function () {
                if (!idInput.value) {
                    const videoId = extractYouTubeID(this.value.trim());
                    if (videoId) idInput.value = videoId;
                }
            });

            if (posterInput) {
                posterInput.addEventListener('change', function () {
                    const file = this.files[0];
                    if (file) {
                        const reader = new FileReader();
                        reader.onload = function (e) {
                            posterPreview.src = e.target.result;
                            posterPreview.style.display = 'block';
                        };
                        reader.readAsDataURL(file);
                    } else {
                        posterPreview.src = '';
                        posterPreview.style.display = 'none';
                    }
                });
            }
        });
    </script>

    <c:if test="${not empty videoEdit}">
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const modal = new bootstrap.Modal(document.getElementById('skitModal'));
                modal.show();
            });
        </script>
    </c:if>
</body>
</html>