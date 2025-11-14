<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="vi" data-bs-theme="dark">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Cài đặt Hồ sơ</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>

    <style>
      /* --- ĐỊNH NGHĨA MÀU SẮC CHÍNH (KHỚP VỚI HÌNH ẢNH) --- */
      :root {
        --bs-body-bg: #121212; /* Nền ngoài cùng: Đen */
        --custom-dark-bg: #212121; /* Nền panel chính: Tối hơn, Đen Xám */
        --custom-sidebar-bg: #191919; /* Nền sidebar: Tối hơn panel chính */
        --custom-input-bg: #292929; /* Nền Input fields/Avatar: Tối vừa */
        --custom-primary-color: #ff9800; /* Màu Cam/Vàng chủ đạo (Nổi bật) */
        --custom-primary-hover: #e0851c;
        --custom-text-color: #ffffff;
        --custom-text-secondary: #aaaaaa;
      }

      body {
        background-color: var(--bs-body-bg) !important;
      }

      /* Container chính (Bọc sidebar và main content) */
      .main-container {
          background-color: var(--custom-dark-bg) !important;
          border-radius: 0.375rem;
          /* Loại bỏ đổ bóng mặc định */
          box-shadow: none !important;
      }

      /* --- STYLE CHO SIDEBAR --- */
      .sidebar-nav-container {
        background-color: var(--custom-sidebar-bg) !important;
        /* Bo góc bên trái */
        border-top-left-radius: 0.375rem; 
        border-bottom-left-radius: 0.375rem; 
        padding: 0.75rem !important; /* Giảm padding sidebar */
      }
      
      .nav-link {
        color: var(--custom-text-secondary) !important;
        background-color: transparent !important;
        font-size: 0.95rem; /* Điều chỉnh font size */
        padding: 0.75rem 1rem !important; /* Điều chỉnh padding */
        border-radius: 0.375rem !important;
      }

      /* Tab đang active (Thông tin Hồ sơ) */
      .nav-link.active {
        color: var(--custom-primary-color) !important; /* Màu cam cho chữ */
        background-color: var(--custom-dark-bg) !important; /* Nền tối của panel bên phải */
        font-weight: 600;
      }
      
      /* Icon SVG khi active */
      .nav-link.active svg {
          color: var(--custom-primary-color) !important; /* Màu cam cho icon active */
      }
      
      /* Icon SVG mặc định */
      .nav-link:not(.active) svg {
          color: var(--custom-text-secondary) !important;
      }

      /* --- STYLE CHO MAIN CONTENT --- */
      .main-content-panel {
        background-color: var(--custom-dark-bg) !important;
        border-top-right-radius: 0.375rem; 
        border-bottom-right-radius: 0.375rem; 
        padding: 3rem !important; /* Điều chỉnh padding */
      }

      /* Các nút màu cam (Lưu thay đổi, Thay đổi Avatar) */
      .btn-primary {
        background-color: var(--custom-primary-color) !important;
        border-color: var(--custom-primary-color) !important;
        color: var(--custom-text-color) !important;
        font-size: 0.9rem; /* Điều chỉnh kích thước nút */
        padding: 0.5rem 1rem !important;
      }

      .btn-primary:hover {
        background-color: var(--custom-primary-hover) !important;
        border-color: var(--custom-primary-hover) !important;
      }

      /* Nút Xóa (secondary button) */
      .btn-secondary {
        background-color: transparent !important;
        border-color: #555555 !important;
        color: var(--custom-text-secondary) !important;
        font-size: 0.9rem;
        padding: 0.5rem 1rem !important;
      }

      /* Input fields */
      .form-control {
        background-color: var(--custom-input-bg) !important;
        color: var(--custom-text-color) !important;
        border-color: var(--custom-input-bg) !important; 
        padding: 0.75rem 1rem !important;
      }

      /* Avatar Placeholder */
      .avatar-placeholder {
        border-radius: 50%;
        background-color: var(--custom-input-bg) !important;
        width: 70px; /* Điều chỉnh kích thước */
        height: 70px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 0.8rem;
        color: var(--custom-text-color);
      }
    </style>
  </head>
  <body class="bg-dark">
    <div
      class="container-lg min-vh-100 d-flex align-items-center justify-content-center p-4"
    >
      <div class="row w-100 main-container shadow-lg">
        <aside class="col-md-3 col-lg-3 p-0 sidebar-nav-container">
          <nav class="h-100 p-2"> 
            <div class="d-flex flex-column gap-1">
              <button
                id="profile-tab-button"
                class="nav-link btn text-start p-3 rounded border-0 active"
              >
                <svg
                  class="me-2"
                  width="20"
                  height="20"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="1.5"
                    d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A1.5 1.5 0 0118 21.75H6.75a1.5 1.5 0 01-1.45-1.632z"
                  ></path>
                </svg>
                Thông tin Hồ sơ
              </button>

              <button
                id="password-tab-button"
                class="nav-link btn text-start p-3 rounded border-0"
              >
                <svg
                  class="me-2"
                  width="20"
                  height="20"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="1.5"
                    d="M16.5 10.5V6.75a4.5 4.5 0 00-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 002.25-2.25v-6.75a2.25 2.25 0 00-2.25-2.25H6.75a2.25 2.25 0 00-2.25 2.25v6.75a2.25 2.25 0 002.25 2.25z"
                  ></path>
                </svg>
                Đổi Mật khẩu
              </button>
            </div>
          </nav>
        </aside>

        <main class="col-md-9 col-lg-9 p-0"> 
          <div class="h-100 main-content-panel">
            <section id="profile-section">
              <h2 class="text-white mb-5 fw-bold">Thông tin Hồ sơ</h2>

              <form action="#" method="POST">
                <div class="d-flex align-items-center gap-4 mb-5">
                  <div class="avatar-placeholder">
                    <span class="small">Avatar</span>
                  </div>
                  <div class="d-flex gap-2"> <button type="button" class="btn btn-primary fw-semibold">
                      Thay đổi Avatar
                    </button>
                    <button
                      type="button"
                      class="btn btn-secondary fw-semibold"
                    >
                      Xóa
                    </button>
                  </div>
                </div>

                <div class="mb-4">
                  <label for="username" class="form-label text-white mb-2"
                    >Tên đăng nhập (Username)</label
                  >
                  <input
                    type="text"
                    id="username"
                    name="username"
                    value="hoang_an_dev"
                    class="form-control"
                    readonly
                    style="color: #FF9800 !important;" 
                  />
                </div>

                <div class="mb-4">
                  <label for="fullname" class="form-label text-white mb-2"
                    >Họ và tên (Fullname)</label
                  >
                  <input
                    type="text"
                    id="fullname"
                    name="fullname"
                    value="Hoàng Anh"
                    class="form-control"
                  />
                </div>

                <div class="mb-5">
                  <label for="email" class="form-label text-white mb-2"
                    >Email</label
                  >
                  <input
                    type="email"
                    id="email"
                    name="email"
                    value="hoangan.dev@example.com"
                    class="form-control"
                  />
                </div>

                <div class="text-end">
                  <button
                    type="submit"
                    class="btn btn-primary fw-bold px-4 py-2"
                  >
                    Lưu thay đổi
                  </button>
                </div>
              </form>
            </section>

            <section id="password-section" class="d-none">
              <h2 class="text-white mb-4 fw-bold">Đổi Mật khẩu</h2>

              <form action="#" method="POST">
                <div class="mb-4">
                  <label
                    for="current-password"
                    class="form-label text-white mb-2"
                    >Mật khẩu hiện tại</label
                  >
                  <input
                    type="password"
                    id="current-password"
                    name="current-password"
                    placeholder="Nhập mật khẩu hiện tại"
                    class="form-control"
                  />
                </div>

                <div class="mb-4">
                  <label for="new-password" class="form-label text-white mb-2"
                    >Mật khẩu mới</label
                  >
                  <input
                    type="password"
                    id="new-password"
                    name="new-password"
                    placeholder="Nhập mật khẩu mới"
                    class="form-control"
                  />
                </div>

                <div class="mb-5">
                  <label
                    for="confirm-password"
                    class="form-label text-white mb-2"
                    >Xác nhận mật khẩu mới</label
                  >
                  <input
                    type="password"
                    id="confirm-password"
                    name="confirm-password"
                    placeholder="Nhập lại mật khẩu mới"
                    class="form-control"
                  />
                </div>

                <div class="text-end">
                  <button
                    type="submit"
                    class="btn btn-primary fw-bold px-4 py-2"
                  >
                    Cập nhật Mật khẩu
                  </button>
                </div>
              </form>
            </section>
          </div>
        </main>
      </div>
    </div>

    <script>
      document.addEventListener("DOMContentLoaded", function () {
        const profileTabBtn = document.getElementById("profile-tab-button");
        const passwordTabBtn = document.getElementById("password-tab-button");
        const profileSection = document.getElementById("profile-section");
        const passwordSection = document.getElementById("password-section");

        profileTabBtn.addEventListener("click", function () {
          // Hiển thị Profile, Ẩn Password
          profileSection.classList.remove("d-none");
          passwordSection.classList.add("d-none");

          // Cập nhật trạng thái active của nút
          profileTabBtn.classList.add("active");
          passwordTabBtn.classList.remove("active");
        });

        passwordTabBtn.addEventListener("click", function () {
          // Hiển thị Password, Ẩn Profile
          passwordSection.classList.remove("d-none");
          profileSection.classList.add("d-none");

          // Cập nhật trạng thái active của nút
          passwordTabBtn.classList.add("active");
          profileTabBtn.classList.remove("active");
        });
      });
    </script>
  </body>
</html>