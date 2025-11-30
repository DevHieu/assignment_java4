<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="vi" data-bs-theme="dark">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Lịch sử xem - The Haha Factory</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link href="../styles/NavBar.css" rel="stylesheet" />
    <link rel="stylesheet" href="../styles/history.css" />
  </head>

  <body>
    <%@ include file="./components/navbar.jsp" %>

    <main class="container-fluid py-5">
      <div class="container-lg">
        <h1 class="text-white mb-4 fw-bold">Lịch sử xem</h1>

        <!-- Filter Bar -->
        <div class="d-flex gap-2 mb-4">
          <button id="filter-liked" class="btn btn-outline-primary active">
            Đã thích
          </button>
          <button id="filter-all" class="btn btn-outline-primary">
            Tất cả
          </button>
        </div>

        <!-- Video Grid -->
        <div class="row g-4" id="video-grid">
          <!-- VIDEO 1 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner1.png"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 1</h5>
                  <p class="card-text text-white-50 small mb-2">
                    Mô tả video ngắn...
                  </p>
                  <p class="text-white-50 small mb-3">1,234 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- VIDEO 2 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner2.jpg"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 2</h5>
                  <p class="card-text text-white-50 small mb-2">
                    Mô tả video ngắn...
                  </p>
                  <p class="text-white-50 small mb-3">5,678 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- VIDEO 3 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner3.jpg"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 3</h5>
                  <p class="card-text text-white-50 small mb-2">
                    Mô tả video...
                  </p>
                  <p class="text-white-50 small mb-3">9,012 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- VIDEO 4 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner1.png"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 4</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">3,456 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- VIDEO 5 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner2.jpg"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 5</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">10,200 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- VIDEO 6 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner3.jpg"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 6</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">4,900 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- VIDEO 7 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner1.png"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 7</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">2,100 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- VIDEO 8 -->
          <div class="col-md-3">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img
                  src="../images/baner2.jpg"
                  class="w-100"
                  style="
                    aspect-ratio: 16/9;
                    object-fit: cover;
                    border-radius: 0.375rem 0.375rem 0 0;
                  "
                />
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 8</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">8,999 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-thumbs-up"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary">
                      <i class="fas fa-share"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <nav aria-label="Page navigation" class="mt-4" data-bs-theme="dark">
          <ul class="pagination justify-content-center">
            <!-- Nút << -->
            <li class="page-item" id="first-page">
              <a class="page-link page-control" href="#">&laquo;</a>
            </li>

            <!-- Nút < -->
            <li class="page-item" id="prev-page">
              <a class="page-link page-control" href="#">&lt;</a>
            </li>

            <!-- Số trang -->
            <li class="page-item active">
              <a class="page-link page-number" href="#">1</a>
            </li>
            <li class="page-item">
              <a class="page-link page-number" href="#">2</a>
            </li>
            <li class="page-item">
              <a class="page-link page-number" href="#">3</a>
            </li>
            <li class="page-item">
              <a class="page-link page-number" href="#">4</a>
            </li>
            <li class="page-item">
              <a class="page-link page-number" href="#">5</a>
            </li>

            <!-- Nút > -->
            <li class="page-item" id="next-page">
              <a class="page-link page-control" href="#">&gt;</a>
            </li>

            <!-- Nút >> -->
            <li class="page-item" id="last-page">
              <a class="page-link page-control" href="#">&raquo;</a>
            </li>
          </ul>
        </nav>
      </div>
    </main>
    <%@ include file="./components/footer.jsp" %>
  </body>
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      const filterLiked = document.getElementById("filter-liked");
      const filterAll = document.getElementById("filter-all");

      filterLiked.addEventListener("click", function () {
        filterLiked.classList.add("active");
        filterAll.classList.remove("active");
      });

      filterAll.addEventListener("click", function () {
        filterAll.classList.add("active");
        filterLiked.classList.remove("active");
      });
    });
  </script>
</html>
