<%@ page contentType="text/html;charset=UTF-8" language="java"%> <%@ taglib
uri="http://java.sun.com/jstl/core_rt" prefix="core"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
  <head>
    <meta charset="UTF-8" />
    <title>Search Videos</title>
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
    <style>
      .video-card {
        transition: transform 0.3s ease;
      }

      .video-card:hover {
        transform: translateY(-5px);
        color: #19d65d !important;
        text-shadow: 0 0 5px rgba(25, 214, 93, 0.7),
          0 0 10px rgba(25, 214, 93, 0.5);
        /* transition: color 0.3s ease, text-shadow 0.3s ease; */
      }

      .card-img-top {
        height: 200px;
        object-fit: cover;
      }

      .sort-dropdown .dropdown-menu {
        min-width: 200px;
      }
    </style>
  </head>

  <body>
    <div class="position-relative">
      <core:set
        var="currentQuery"
        value="${currentQuery != null ? currentQuery : ''}"
      ></core:set>
      <core:set
        var="path"
        value="${pageContext.request.contextPath}/search"
      ></core:set>
      <core:set
        var="pathMain"
        value="${pageContext.request.contextPath}"
      ></core:set>
      <%@ include file="./components/navbar.jsp"%>

      <div class="container-fluid p-0">
        <div
          class="container-fluid py-4 mb-4 border-bottom border-secondar bg-"
        >
          <div class="container">
            <div class="row align-items-center">
              <div class="col-md-6">
                <h1 class="h2 mb-0">Kết quả tìm kiếm</h1>
                <p class="text-muted mb-0">
                  Tìm được ${listVideoSearchList.size()} video liên quan
                </p>
              </div>
              <div class="col-md-6">
                <div class="d-flex justify-content-md-end gap-3 mt-3 mt-md-0">
                  <div class="dropdown sort-dropdown">
                    <button
                      class="btn btn-outline-light dropdown-toggle"
                      type="button"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      <i class="fas fa-sort me-2"></i>Lọc
                    </button>
                    <ul class="dropdown-menu">
                      <li>
                        <a
                          class="dropdown-item"
                          href="${path}/viewHtoL?query=${currentQuery}"
                          ><i class="fas fa-eye me-2"></i>Nhiều views</a
                        >
                      </li>
                      <li>
                        <a
                          class="dropdown-item"
                          href="${path}/viewLtoH?query=${currentQuery}"
                          ><i class="fas fa-eye me-2"></i>Ít views</a
                        >
                      </li>
                      <li>
                        <a
                          class="dropdown-item"
                          href="${path}/likeHtoL?query=${currentQuery}"
                          ><i class="fas fa-heart me-2"></i>Nhiều likes</a
                        >
                      </li>
                      <li>
                        <a
                          class="dropdown-item"
                          href="${path}/likeLtoH?query=${currentQuery}"
                          ><i class="fas fa-heart me-2"></i>Ít likes</a
                        >
                      </li>
                      <li>
                        <hr class="dropdown-divider" />
                      </li>
                      <li>
                        <a
                          class="dropdown-item"
                          href="${path}/AZ?query=${currentQuery}"
                          ><i class="fas fa-font me-2"></i>(A-Z)</a
                        >
                      </li>
                      <li>
                        <a
                          class="dropdown-item"
                          href="${path}/ZA?query=${currentQuery}"
                          ><i class="fas fa-font me-2"></i>(Z-A)</a
                        >
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Video -->
          <div class="container">
            <div class="row g-4" id="video-grid">
              <core:forEach var="list" items="${listVideoSearchList}">
                <div class="col-md-6 col-lg-4 col-xl-3">
                  <div class="card video-card h-100 bg-dark">
                    <img
                      src="..${list[0].poster}"
                      class="card-img-top"
                      alt="Video Thumbnail"
                    />
                    <div class="card-body d-flex flex-column">
                      <span class="text-muted small">25:10</span>

                      <h5 class="card-title">
                        <a
                          class="text-decoration-none text-reset"
                          href="${pathMain}/watch?id=${list[0].id}"
                          >${list[0].title}</a
                        >
                      </h5>
                      <p class="card-text text-muted flex-grow-1">
                        ${list[0].description}
                      </p>

                      <div
                        class="d-flex justify-content-between align-items-center mt-auto"
                      >
                        <div class="d-flex gap-3 text-muted small">
                          <span
                            ><i class="fas fa-eye me-1"></i
                            >${list[0].views}</span
                          >

                          <span class="like-btn" style="cursor: pointer">
                            <i class="far fa-heart me-1"></i>
                            <span class="like-count"
                              ><a
                                href="http://localhost:8080/ASM-J4/views/sreachPage.jsp"
                                class="text-decoration-none text-reset"
                                >${list[1]}</a
                              ></span
                            >
                          </span>
                          <span class="like-btn" style="cursor: pointer">
                            <i class="fa-solid fa-share"></i>
                            <span class="like-count"
                              ><a
                                href="#"
                                class="text-decoration-none text-reset"
                                >${list[2]}</a
                              ></span
                            >
                          </span>
                        </div>
                        <small class="text-muted">2 tuần trước</small>
                      </div>
                    </div>
                  </div>
                </div>
              </core:forEach>
            </div>

            <!-- Page -->
          </div>
        </div>
        <%@ include file="./components/footer.jsp"%> <%@ include
        file="./components/shareBox.jsp"%>
      </div>
    </div>
  </body>
</html>
