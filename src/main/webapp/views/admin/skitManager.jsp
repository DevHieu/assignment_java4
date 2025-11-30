<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
<meta charset="UTF-8">
    <title>Skit Management</title>
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

    <div class="modal fade" id="skitModal" tabindex="-1" aria-labelledby="skitModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="skitModalLabel">Add Skit</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Youtube Id</label>
                            <input type="text" name="youtubeId" class="form-control" maxlength="50">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Video Title:</label>
                            <input type="text" name="title" class="form-control" maxlength="200">
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
                                           checked>
                                    <label class="form-check-label" for="statusActive">Active</label>
                                </div>



                                <div class="form-check form-check-inline mb-0">
                                    <input class="form-check-input"
                                           type="radio"
                                           name="status"
                                           id="statusInactive"
                                           value="INACTIVE">
                                    <label class="form-check-label" for="statusInactive">Inactive</label>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="d-flex align-items-center gap-3">
                                <label class="form-label mb-0 me-2">Do you want the video to appear on the banner?
                                    <br>
                                <div class="form-check form-check-inline mb-0">
                                    <input class="form-check-input"
                                        type="radio"
                                        name="isBanner"
                                        id="bannerYes"
                                        value="true">
                                    <label class="form-check-label" for="bannerYes">Yes</label>
                                </div>

                                <div class="form-check form-check-inline mb-0">
                                    <input class="form-check-input"
                                        type="radio"
                                        name="isBanner"
                                        id="bannerNo"
                                        value="false"
                                        checked>
                                    <label class="form-check-label" for="bannerNo">No</label>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Poster:</label>
                            <input type="file" name="posterFile" class="form-control" accept="image/*" id="posterInput">
                            <div class="mt-2">
                                <img id="posterPreview" src="" alt="Poster preview"
                                    style="display:none; max-width:200px; border-radius:8px; object-fit:cover;">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="description" class="form-control" rows="3"
                                    placeholder="Mô tả nội dung tiểu phẩm..."></textarea>
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
                        <th class="text-center" scope="col" style="width:120px">Status</th>
                        <th class="text-center" scope="col" class="text-end" style="width:180px">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="5" class="text-center py-4 text-muted">
                            Chưa có dữ liệu skit.
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <c:if test="${not empty pageCount}">
            <nav class="mt-3" aria-label="Pagination">
                <ul class="pagination justify-content-end mb-0">
                    <c:forEach var="p" begin="1" end="${pageCount}">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link"
                               href="/admin/skit?page=${p}&q=${fn:escapeXml(param.q)}">${p}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const input = document.getElementById('posterInput');
            const img = document.getElementById('posterPreview');

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