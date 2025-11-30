<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Register</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
    />
    <!-- Bootstrap 5 CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <!-- Custom CSS -->
    <link href="../styles/Auth.css" rel="stylesheet" />
  </head>
  <body class="container">
    <a href="<c:url value='/home' />" class="logo">
      <img
        src="../icons/logo2.png"
        alt=""
        style="width: 200px; margin-top: 20px; position: absolute"
      />
    </a>
    <div class="auth-container">
      <div class="auth-card">
        <h1 class="auth-title">Đăng Ký</h1>
        <form action="register" method="post">
          <!-- Fullname Field -->
          <div class="mb-3">
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                name="fullname"
                placeholder="Họ và Tên"
                required
              />
              <span class="input-group-text"
                ><i class="fa-solid fa-user"></i
              ></span>
            </div>
          </div>

          <!-- Username Field -->
          <div class="mb-3">
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                name="username"
                placeholder="Tên đăng nhập"
                required
              />
              <span class="input-group-text"
                ><i class="fa-solid fa-user"></i
              ></span>
            </div>
          </div>

          <!-- Username Field -->
          <div class="mb-3">
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                name="email"
                placeholder="Email"
                required
              />
              <span class="input-group-text"
                ><i class="fa-solid fa-at"></i
              ></span>
            </div>
          </div>

          <!-- Password Field -->
          <div class="mb-3">
            <div class="input-group">
              <input
                type="password"
                class="form-control"
                name="password"
                id="password"
                placeholder="Mật khẩu"
                required
              />
              <span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
              <span class="position-absolute top-50 end-0 translate-middle-y pe-5" style="cursor: pointer;"
                    id="togglePassword">
                <i class="fa-solid fa-eye" style="color: #e7d8ab;"></i>
              </span>
            </div>
          </div>

          <!-- Confirm Password Field -->
          <div class="mb-3">
            <div class="input-group">
              <input
                type="password"
                class="form-control"
                name="confirmPassword"
                id="confirmPassword"
                placeholder="Xác nhận mật khẩu"
                required
              />
              <span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
              <span class="position-absolute top-50 end-0 translate-middle-y pe-5" style="cursor: pointer;"
                    id="togglePassword2">
                <i class="fa-solid fa-eye" style="color: #e7d8ab;"></i>
              </span>
            </div>
          </div>

          <c:if test="${not empty message}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
              ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
          </c:if>

          <!-- Register Button -->
          <button type="submit" class="btn btn-auth w-100 mt-3">
            Đăng Ký
          </button>
        </form>

        <!-- Login Link -->
        <div class="auth-link" style="color: #ffc107">
          Bạn đã có tài khoản? <a href="/login" class="text-link">Đăng Nhập</a>
        </div>
      </div>
    </div>
    <script>
      const togglePassword = document.getElementById('togglePassword');
      const togglePassword2 = document.getElementById('togglePassword2');
      const password = document.getElementById('password');
      const confirmPassword = document.getElementById('confirmPassword');

      togglePassword.addEventListener('click', function() {
          const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
          password.setAttribute('type', type);
      });

      togglePassword2.addEventListener('click', function() {
          const type = confirmPassword.getAttribute('type') === 'password' ? 'text' : 'password';
          confirmPassword.setAttribute('type', type);
      });
    </script> 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
