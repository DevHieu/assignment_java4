<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>


<nav class="sticky-top navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="/home">
      <img
        src="../../icons/logo2.png"
        alt="logo"
        class="mx-5"
        style="height: 60px"
      />
    </a>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0 gap-4">
        <li class="nav-item">
          <a class="nav-link fw-semibold" aria-current="page" href="/home#banner"
            >Trang chủ</a
          >
        </li>
        <li class="nav-item">
          <a class="nav-link fw-semibold"  href="/home#skit">Tiểu phẩm</a>
        </li>
        <li class="nav-item">
          <a class="nav-link fw-semibold" href="/home#about">Giới thiệu</a>
        </li>
        <li class="nav-item">
          <a class="nav-link fw-semibold" href="#footer">Liên hệ</a>
        </li>
      </ul>

          <div class="d-flex align-items-center me-5">
            <form class="d-flex position-relative me-2" role="search" action="/search" method="get">
              <input class="form-control rounded-pill" name="query" type="search" placeholder="Tìm kiếm"
                aria-label="Search" style="padding-right: 3rem" />

              <button class="btn btn-link position-absolute end-0 top-50 translate-middle-y" type="submit"
                style="color: #ffc107; padding: 0.375rem 0.75rem" aria-label="Search">
                <i class="fa-solid fa-magnifying-glass"></i>
              </button>
            </form>

        <c:choose>
          <c:when test="${not empty sessionScope.user}">
            <div class="dropdown">
              <a
                  class="nav-link d-flex align-items-center p-0"
                  href="#"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
              >
                  <i class="fa-solid fa-circle-user fa-lg mx-2 text-primary" style="font-size: 35px;"></i>
              </a>
      
              <ul class="dropdown-menu dropdown-menu-end">
                  <li>
                      <a class="dropdown-item " href="#footer">
                          <i class="fa-solid fa-address-card me-2 text-primary "></i>
                          Cập nhật Tài khoản
                      </a>
                    </li>

                    <li>
                      <hr class="dropdown-divider">
                    </li>

                    <li>
                      <a class="dropdown-item " href="/history">
                        <i class="fa-solid fa-film me-2 text-primary "></i>
                        Lịch sử xem
                      </a>
                    </li>

                    <li>
                      <hr class="dropdown-divider">
                    </li>

                    <li>
                      <a class="dropdown-item " href="/logout">
                        <i class="fa-solid fa-right-from-bracket me-2 text-primary"></i>
                        Đăng xuất
                      </a>
                    </li>
                  </ul>
                </div>
              </c:when>
              <c:otherwise>
                <a href="/login"
                  class="text-decoration-none d-flex align-items-center ms-2 border rounded-pill px-3 py-1 bg-dark-subtle">
                  <i class="fa-solid fa-user me-2" style="color: #ffc107"></i>
                  <span class="text-white fw-semibold">Login</span>
                </a>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </nav>