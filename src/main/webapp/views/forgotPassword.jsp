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
      <img src="../icons/logo2.png" alt="" style="width: 200px; margin-top: 20px; position: absolute"/>
    </a>
    <div class="auth-container">
      <div class="auth-card">
        <h1 class="auth-title">Quên mật khẩu?</h1>
        <form action="forgot_pw" method="post">
          <!-- Username Field -->
          <div class="mb-3">
            <div class="input-group">
              <input type="text" class="form-control" name="username" placeholder="Tên đăng nhập" required/>
              <span class="input-group-text"><i class="fa-solid fa-user"></i></span>
            </div>
          </div>

          <!-- Email Field -->
          <div class="mb-3">
            <div class="input-group">
              <input type="text" class="form-control" name="email" placeholder="Email" required/>
              <span class="input-group-text"><i class="fa-solid fa-at"></i></span>
            </div>
          </div>

          <c:if test="${not empty message}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
          </c:if>

          <!-- Login Button -->
          <button type="submit" class="btn btn-auth w-100 mt-3">
            Khôi Phục
          </button>
        </form>
      </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
