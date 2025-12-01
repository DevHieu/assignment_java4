<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi" data-bs-theme="dark">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Cài đặt Hồ sơ</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>
    <link href="../styles/NavBar.css" rel="stylesheet" />
    <link rel="stylesheet" href="../styles/Profile.css" />

    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
  </head>

  <body>
    <%@ include file="./components/navbar.jsp" %>

    <c:if test="${not empty message}">
      <div class="container mt-3">
        <div
          class="alert alert-${alertType} alert-dismissible fade show"
          role="alert"
        >
          ${message}
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close"
          ></button>
        </div>
      </div>
    </c:if>

    <div
      class="container-lg d-flex align-items-center justify-content-center mt-4 p-4"
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
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="1.5"
                    d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 
                      0114.998 0A1.5 1.5 0 0118 21.75H6.75a1.5 1.5 0 01-1.45-1.632z"
                  ></path>
                </svg>
                Thông tin Hồ Sơ
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
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="1.5"
                    d="M16.5 10.5V6.75a4.5 4.5 0 00-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 002.25-2.25v-6.75a2.25 2.25 0 00-2.25-2.25H6.75a2.25 2.25 0 00-2.25 2.25v6.75a2.25 2.25 0 
                      002.25 2.25z"
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
              <h2 class="text-white mb-5 fw-bold">Thông Tin Hồ Sơ</h2>

              <div class="d-flex align-items-center gap-4 mb-5">
                <div
                  class="avatar-placeholder"
                  style="
                    background-image: url('${user.avatar != null ? user.avatar : 'https://via.placeholder.com/150'}');
                    background-size: cover;
                    background-position: center;
                  "
                >
                  <c:if test="${user.avatar == null}">
                    <span class="small">Avatar</span>
                  </c:if>
                </div>
                
                <div class="d-flex gap-2 align-items-center">
                  <form action="profile" method="POST" enctype="multipart/form-data" id="avatar-form">
                    <input type="hidden" name="action" value="updateAvatar" />
                    <input
                      type="file"
                      id="avatar-file"
                      name="avatar-file"
                      class="d-none"
                      accept="image/*"
                      required
                    />
                    <button
                      type="button"
                      class="btn btn-primary fw-semibold"
                      onclick="document.getElementById('avatar-file').click();"
                    >
                      Thay đổi Avatar
                    </button>
                  </form>
                  
                  <form action="profile" method="POST">
                    <input type="hidden" name="action" value="deleteAvatar" />
                    <button 
                      type="submit" 
                      class="btn btn-secondary fw-semibold"
                      <c:if test="${user.avatar == null}">disabled</c:if>
                    >
                      Xóa
                    </button>
                  </form>
                </div>
              </div>

              <form action="profile" method="POST">
                <input type="hidden" name="action" value="updateProfile" />
                <div class="mb-4">
                  <label for="username" class="form-label text-white mb-2"
                    >Tên đăng nhập
                  </label>
                  <input
                    type="text"
                    id="username"
                    name="username"
                    class="form-control profile-input"
                    readonly
                    value="${user.id}" 
                    style="color: #ff9800 !important"
                  />
                </div>

                <div class="mb-4">
                  <label for="fullname" class="form-label text-white mb-2"
                    >Họ và tên
                  </label>
                  <input
                    type="text"
                    id="fullname"
                    name="fullname"
                    class="form-control profile-input"
                    value="${user.fullname}"
                    required
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
                    class="form-control profile-input"
                    value="${user.email}"
                    required
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

              <form action="profile" method="POST" onsubmit="return validatePasswordChange(event)">
                <input type="hidden" name="action" value="updatePassword" />
                <div class="mb-4">
                  <label
                    for="current-password"
                    class="form-label text-white mb-2"
                    >Mật khẩu hiện tại</label
                  >
                  <div class="position-relative">
                    <input
                      type="password"
                      id="current-password"
                      name="current-password"
                      class="form-control profile-input"
                      required
                    />
                    <i
                      class="fa-solid fa-eye toggle-password position-absolute top-50 end-0 translate-middle-y me-3"
                      data-target="current-password"
                      style="cursor: pointer"
                    ></i>
                  </div>
                </div>

                <div class="mb-4">
                  <label for="new-password" class="form-label text-white mb-2"
                    >Mật khẩu mới</label
                  >
                  <div class="position-relative">
                    <input
                      type="password"
                      id="new-password"
                      name="new-password"
                      class="form-control profile-input"
                      required
                    />
                    <i
                      class="fa-solid fa-eye toggle-password position-absolute top-50 end-0 translate-middle-y me-3"
                      data-target="new-password"
                      style="cursor: pointer"
                    ></i>
                  </div>
                </div>

                <div class="mb-5">
                  <label
                    for="confirm-password"
                    class="form-label text-white mb-2"
                    >Xác nhận mật khẩu mới</label
                  >
                  <div class="position-relative">
                    <input
                      type="password"
                      id="confirm-password"
                      name="confirm-password"
                      class="form-control profile-input"
                      required
                    />
                    <i
                      class="fa-solid fa-eye toggle-password position-absolute top-50 end-0 translate-middle-y me-3"
                      data-target="confirm-password"
                      style="cursor: pointer"
                    ></i>
                  </div>
                  <div id="password-match-error" class="text-danger mt-2 d-none">
                    Xác nhận mật khẩu mới không khớp.
                  </div>
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
    <%@ include file="./components/footer.jsp" %>
  </body>

  <script>
    function validatePasswordChange(event) {
        const newPassword = document.getElementById("new-password").value;
        const confirmPassword = document.getElementById("confirm-password").value;
        const errorDiv = document.getElementById("password-match-error");

        if (newPassword !== confirmPassword) {
            errorDiv.classList.remove("d-none");
            event.preventDefault(); 
            return false;
        } else {
            errorDiv.classList.add("d-none");
            return true;
        }
    }

    document.addEventListener("DOMContentLoaded", function () {
      const profileTabBtn = document.getElementById("profile-tab-button");
      const passwordTabBtn = document.getElementById("password-tab-button");
      const profileSection = document.getElementById("profile-section");
      const passwordSection = document.getElementById("password-section");
      const avatarInput = document.getElementById('avatar-file');
      const avatarForm = document.getElementById('avatar-form');

      // Logic chuyển tab Hồ sơ
      profileTabBtn.addEventListener("click", function () {
        profileSection.classList.remove("d-none");
        passwordSection.classList.add("d-none");
        profileTabBtn.classList.add("active");
        passwordTabBtn.classList.remove("active");
      });

      // Logic chuyển tab Mật khẩu
      passwordTabBtn.addEventListener("click", function () {
        passwordSection.classList.remove("d-none");
        profileSection.classList.add("d-none");
        passwordTabBtn.classList.add("active");
        profileTabBtn.classList.remove("active");
      });

      document.querySelectorAll(".toggle-password").forEach((eye) => {
        eye.addEventListener("click", function () {
          const input = document.getElementById(this.dataset.target);
          const isPassword = input.type === "password";
          input.type = isPassword ? "text" : "password";
          this.classList.toggle("fa-eye");
          this.classList.toggle("fa-eye-slash");
        });
      });
    });
  </script>
</html>