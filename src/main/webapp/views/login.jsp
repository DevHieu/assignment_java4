<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Login</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
    />
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>
    <link href="../styles/Login.css" rel="stylesheet" />
  </head>
  <body class="container">
    <a href="<c:url value='/home' />" class="logo">
      <img
        src="../icons/logo2.png"
        alt=""
        style="width: 200px; margin-top: 20px; position: absolute"
      />
    </a>
    <div class="login-container">
      <div class="login-card">
        <h1 class="login-title">Đăng Nhập</h1>
        <form action="login" method="post">
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

          <!-- Password Field -->
          <div class="mb-3 position-relative">
            <div class="input-group">
              <input
                type="password"
                class="form-control"
                name="password"
                id="password"
                placeholder="Mật khẩu"
                required
              />
              <span class="input-group-text" style="border-radius: 0 50px 50px 0;">
                <i class="fa-solid fa-lock"></i>
              </span>
              <span class="position-absolute top-50 end-0 translate-middle-y pe-5" style="cursor: pointer;"
                    id="togglePassword">
                <i class="fa-solid fa-eye" style="color: #e7d8ab;"></i>
              </span>
              
              
            </div>
          </div>
          

          <!-- Remember me & Forgot password -->
          <div
            class="d-flex justify-content-between align-items-center mb-3 mt-2 w-100"
          >
            <div class="form-check">
              <input class="form-check-input" type="checkbox" id="rememberMe" />
              <label
                class="form-check-label"
                for="rememberMe"
                style="color: #ffc107"
                >Nhớ mật khẩu</label
              >
            </div>
            <a
              href="/login?action=forgot_pw"
              class="text-decoration-none"
              style="color: #ffc107"
              >Quên mật khẩu?</a
            >
          </div>

          <!-- Login Button -->
          <button type="submit" class="btn btn-login w-100">Đăng Nhập</button>
        </form>

        <!-- Register Link -->
        <div class="register-link" style="color: #ffc107">
          Bạn chưa có tài khoản?
          <a href="/register" class="text-link">Đăng ký</a>
        </div>
        <c:if test="${not empty error}">
          <div class="text-white">
            ${error}
          </div>
        </c:if>
        
        
      </div>
    </div>
    <script>
    const togglePassword = document.getElementById('togglePassword');
    const password = document.getElementById('password');

    togglePassword.addEventListener('click', function() {
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
    });
  </script> 
  </body>
  
</html>
