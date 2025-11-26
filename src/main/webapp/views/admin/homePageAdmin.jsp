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
            <a class="navbar-brand" href="/home">
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


</body>
</html>