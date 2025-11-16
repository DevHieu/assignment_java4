<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
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
        <h1 class="login-title">Login</h1>
        <form action="login" method="post">
          <!-- Username Field -->
          <div class="mb-3">
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                name="username"
                placeholder="Username"
                required
              />
              <span class="input-group-text"
                ><i class="fa-solid fa-user"></i
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
                placeholder="Password"
                required
              />
              <span class="input-group-text"
                ><i class="fa-solid fa-lock"></i
              ></span>
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
                >Remember me</label
              >
            </div>
            <a
              href="/login?action=forgot_pw"
              class="text-decoration-none"
              style="color: #ffc107"
              >Forgot Password?</a
            >
          </div>

          <!-- Login Button -->
          <button type="submit" class="btn btn-login w-100">Login</button>
        </form>

        <!-- Register Link -->
        <div class="register-link" style="color: #ffc107">
          Don't have an account?
          <a href="/register" class="text-link">Register</a>
        </div>
      </div>
    </div>
  </body>
</html>
