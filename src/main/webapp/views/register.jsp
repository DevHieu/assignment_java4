<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../styles/Register.css" rel="stylesheet" />
    <!-- <style>
        body, html {
            height: 100%;
            margin: 0;
            background: linear-gradient(rgba(0, 0, 0, 0.89), rgba(0, 0, 0, 0.803)), /* lớp phủ mờ */
            url('../images/baner1.png') no-repeat center center fixed;
            background-size: cover;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .register-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 90vh;
            padding: 20px;
        }
        .register-card {
            border-radius: 6px;
            box-shadow: 0 0 20px rgba(241, 175, 52, 0.4);
            padding: 40px;
            width: 100%;
            max-width: 400px;
            backdrop-filter: blur(50px);
        }
        .register-title {
            text-align: center;
            margin-bottom: 30px;
            color: #ffc107;
            font-weight: 700;
            font-size: 2rem;
        }
        .form-control {
            border-radius: 5px;
            padding: 12px 20px;
            background-color: #a5aeb608;
            border: 1px solid #837a7a;
            box-shadow: inset 0 1px 3px rgba(0,0,0,0.1);
            color: #ffffff;
        }
        .form-control::placeholder {
            color: #e7d8ab;
            opacity: 1;
        }
        .form-control:focus {
            border-color: #ffc107;
            box-shadow: 0 0 0 0.2rem rgba(106, 17, 203, 0.25);
            background-color: #ffffff1a;
            color: #ffffff;
        }
        .input-group-text {
            border-radius: 0 5px 5px 0;
            background-color: #a5aeb608;
            border: none;
            border-right: none;
            color: #e7d8ab;
        }

        .btn-register {
            border-radius: 5px !important;
            padding: 9px;
            font-weight: 600;
            background: linear-gradient(135deg, #ffc107 0%, #ddc083 100%) !important;
            border: none;
            transition: all 0.3s ease;
            color: black;
        }
        .btn-register:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(29, 208, 124, 0.4);
        }
        .form-check-label {
            font-size: 0.9rem;
        }
        .form-check-input {
            background-color: #a5aeb608;
        }
        .form-check-input:checked {
            background-color: #ffc107;
        }
        .text-link {
            color: #ffc107;
            text-decoration: none;
            font-weight: 500;
        }
        .text-link:hover {
            text-decoration: underline;
        }
        .register-link {
            text-align: center;
            margin-top: 20px;
            font-size: 0.9rem;
        } 
        .register-link a {
            font-size: 0.95rem;
        }
    </style> -->
</head>
<body>
    <div class="logo">
        <img src="../icons/logo2.png" alt="" style="width: 70px; height: auto; margin: 20px 0 0 20px;">
    </div>
    <div class="register-container">
        <div class="register-card">
            <h1 class="register-title">Register</h1>
            <form action="register" method="post">
                <!-- Fullname Field -->
                <div class="mb-3">
                    <div class="input-group">
                        <input type="text" class="form-control" name="fullname" placeholder="Fullname" required>
                        <span class="input-group-text"><i class="fa-solid fa-user"></i></span>
                    </div>
                </div>

                <!-- Username Field -->
                <div class="mb-3">
                    <div class="input-group">
                        <input type="text" class="form-control" name="username" placeholder="Username" required>
                        <span class="input-group-text"><i class="fa-solid fa-user"></i></span>
                    </div>
                </div>

                <!-- Username Field -->
                <div class="mb-3">
                    <div class="input-group">
                        <input type="text" class="form-control" name="email" placeholder="Email" required>
                        <span class="input-group-text"><i class="fa-solid fa-at"></i></span>
                    </div>
                </div>

                <!-- Password Field -->
                <div class="mb-3">
                    <div class="input-group">
                        <input type="password" class="form-control" name="password" placeholder="Password" required>
                        <span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
                    </div>
                </div>

                <!-- Confirm Password Field -->
                <div class="mb-3">
                    <div class="input-group">
                        <input type="password" class="form-control" name="confirmPassword" placeholder="Re-enter password" required>
                        <span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
                    </div>
                </div>

                <!-- Register Button -->
                <button type="submit" class="btn btn-register w-100 mt-3">Register</button>
            </form>

            <!-- Login Link -->
            <div class="register-link" style="color: #ffc107;">
                Already have an account? <a href="/login" class="text-link">Login</a>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS (optional, for interactive components) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>