<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="dark">
  <head>
    <meta charset="UTF-8" />
    <title>JSP Page</title>
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

    <link href="../styles/HomePage.css" rel="stylesheet" />
    <link href="../styles/NavBar.css" rel="stylesheet" />
    <script src="../scripts/VideoAction.js"></script>
  </head>
  <body>
    <div class="position-relative">
      <%@ include file="./components/navbar.jsp" %>
      <div class="container-fluid p-0" id="banner">
        <header>
          <!-- data-bs-ride="carousel" -->
          <div id="carousel" class="carousel slide">
            <div class="carousel-inner" style="height: 90vh">
              <c:forEach var="item" items="${bannerVideos}" varStatus="st">
                <div class="carousel-item h-100 w-100 ${st.first ? 'active' : ''}">
                <a href="/watch?id=${item.id}" class="stretched-link">
                    <img
                      src="..${item.poster}"
                      class="d-block w-100 h-100 object-fit-cover"
                      alt="..."
                    />
                </a>
                  <div
                    class="carousel-caption d-md-block w-100 text-white p-0"
                    style="height: 100%"
                  >
                    <h1 style="font-size: 80px; margin-bottom: 0.5em">
                      ${item.title}
                    </h1>
                    <h5>
                      ${item.description}
                    </h5>
                  </div>
                </div>
              </c:forEach>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              data-bs-target="#carousel"
              data-bs-slide="prev"
            >
              <span
                class="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              data-bs-target="#carousel"
              data-bs-slide="next"
            >
              <span
                class="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </header>
        <section id="skit">
          <h1 class="mt-5 ms-5 skit-title">Tiểu phẩm hài nổi bật</h1>
          <div class="row mx-5">
            <c:forEach var="video" items="${videos}">
              <div class="col-sm-4 my-3">
                <div class="card p-3 border-0 skit-item position-relative">
                  <div
                    class="card-img-top bg-light object-fit-cover overflow-hidden"
                    style="height: 300px; border-radius: 8px"
                  >
                    <a href="/watch?id=${video.id}" class="stretched-link">
                      <img
                        src="..${video.poster}"
                        alt="thumbnail"
                        class="h-100"
                      />
                    </a>
                  </div>
  
                  <div class="card-body p-0 pt-2">
                    <div class="row g-0 align-items-end">
                      <div class="col-10">
                        <h5 class="card-title fw-bold mb-0 fs-4">${video.title}</h5>
                        <p class="card-text text-muted small my-2">${video.description}</p>
                        <p class="card-text small mb-0 mt-1"><i class="fa-solid fa-eye me-2 text-primary" style="font-size: 15px;"></i>${video.views}</p>
                      </div>
  
                      <div
                        class="col-2 text-end d-flex justify-content-end align-items-center"
                      >
                        <button class="btn btn-sm icon-btn z-3" 
                        data-bs-toggle="modal" 
                        data-bs-target="#share" 
                        data-video-id="${video.id}"
                        data-is-login="${sessionScope.user != null}" >
                          <i class="fas fa-share"></i>
                        </button>
  
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
          <form action="/home" method="get" class="d-flex justify-content-center mt-5">
            <ul class="pagination custom-pagination border-0">
          
              <!-- Trang đầu -->
              <li class="page-item">
                <button type="submit" name="page" value="1" class="page-link bg-transparent border-0" aria-label="First">
                  <i class="fa-solid fa-angles-left text-success fs-3"></i>
                </button>
              </li>
          
              <!-- Trang trước -->
              <li class="page-item">
                <button type="submit" name="page" value="${currentPage - 1}" 
                        class="page-link bg-transparent border-0" aria-label="Previous"
                        ${(currentPage == 1) ? 'disabled' : ''}>
                  <i class="fa-solid fa-angle-left text-primary fs-3"></i>
                </button>
              </li>
          
              <!-- Trang sau -->
              <li class="page-item">
                <button type="submit" name="page" value="${currentPage + 1}" 
                        class="page-link bg-transparent border-0" aria-label="Next"
                        ${currentPage == maxPage ? 'disabled' : ''}>
                  <i class="fa-solid fa-angle-right text-primary fs-3"></i>
                </button>
              </li>
          
              <!-- Trang cuối -->
              <li class="page-item">
                <button type="submit" name="page" value="${maxPage}" class="page-link bg-transparent border-0" aria-label="Last">
                  <i class="fa-solid fa-angles-right text-success fs-3"></i>
                </button>
              </li>
          
            </ul>
          </form>
          </div>
        </section>
        <section id="about">
          <div class="container">
            <div class="about p-4 p-md-5">
              <div class="row g-4 align-items-center">
                <div class="col-lg-7">
                  <div class="mb-3">
                    <span
                      class="about-title badge badge-accent rounded-pill px-3 py-lg-2 fs-5"
                      >Về The Haha Factory</span
                    >
                  </div>

                  <h1 class="display-6 fw-bold mb-3">
                    Mang tiếng cười, kết nối niềm vui cho mọi sự kiện
                  </h1>

                  <p class="lead mb-3">
                    <strong>The Haha Factory</strong> được thành lập với sứ mệnh
                    mang đến tiếng cười, niềm vui và năng lượng tích cực cho mọi
                    sự kiện. Chúng tôi tin rằng tiếng cười không chỉ kết nối con
                    người mà còn tạo ra những kỷ niệm đáng nhớ cho tập thể.
                  </p>

                  <p class="mb-3">
                    Với đội ngũ diễn viên, biên kịch và đạo diễn giàu kinh
                    nghiệm, chúng tôi cung cấp
                    <strong
                      >tiểu phẩm hài, kịch ngắn và chương trình giải trí</strong
                    >
                    dành cho các sự kiện doanh nghiệp như lễ kỷ niệm, hội nghị,
                    tiệc tri ân và Year End Party. Mỗi tiết mục được đầu tư kỹ
                    lưỡng từ kịch bản đến dàn dựng, phù hợp với mục tiêu và văn
                    hóa của khách hàng.
                  </p>

                  <div class="row gy-2 gx-3">
                    <div class="col-sm-6">
                      <ul class="list-unstyled mb-0">
                        <li>• Tiểu phẩm theo chủ đề &amp; theo yêu cầu</li>
                        <li>• Chương trình kịch ngắn, sân khấu tương tác</li>
                      </ul>
                    </div>
                    <div class="col-sm-6">
                      <ul class="list-unstyled mb-0">
                        <li>• Dàn dựng bài bản, chuyên nghiệp</li>
                        <li>• Chia sẻ trích đoạn miễn phí trên website</li>
                      </ul>
                    </div>
                  </div>

                  <p class="mt-3 mb-0">
                    Nếu bạn đang tìm một giải pháp giải trí khác biệt, chuyên
                    nghiệp và truyền cảm hứng cho sự kiện,
                    <strong>The Haha Factory</strong> sẵn sàng đồng hành cùng
                    bạn.
                  </p>

                  <div class="mt-4">
                    <a href="#footer" class="btn btn-lg me-2 btn-contact"
                      >Liên hệ đặt show</a
                    >
                    <a
                      href="#skit"
                      class="btn btn-outline-secondary btn-lg btn-skits"
                      >Xem các tiểu phẩm hài</a
                    >
                  </div>
                </div>

                <div class="col-lg-5 text-center">
                  <div
                    class="ratio ratio-4x3 rounded overflow-hidden shadow-sm"
                  >
                    <img
                      src="../images/haikich.jpg"
                      alt="Tiểu phẩm hài - The Haha Factory"
                      class="img-fluid object-fit-cover"
                    />
                  </div>
                  <p class="text-muted small mt-3">
                    Ảnh minh họa các tiết mục của The Haha Factory
                  </p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
      <%@ include file="./components/footer.jsp" %>
      <%@ include file="./components/shareBox.jsp" %>
    </div>
  </body>
  <script>

    document.addEventListener('DOMContentLoaded', function() {
            const targetScrollId = "${targetScrollId}";

            if (targetScrollId && targetScrollId.startsWith('#')) {
                const targetElement = document.querySelector(targetScrollId);
                if (targetElement) {
                    targetElement.scrollIntoView({
                        behavior: 'instant', // Cuộn mượt
                        block: 'start' // Đưa phần tử lên đầu màn hình
                    });
                }
            }
        });

    function likeVideo(videoId, button) {
      const isLiked = button.getAttribute("data-is-liked") === "true";

      const formData = new FormData();
      formData.append("videoId", videoId);
      formData.append("action", isLiked ? "unlike" : "like");

      fetch("/like-video", {
        method: "POST",
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.status === "success") {
            if (data.action === "like") {
              button.classList.add("liked-class");
              button.setAttribute("data-is-liked", "true");
            } else {
              button.classList.remove("liked-class");
              button.setAttribute("data-is-liked", "false");
            }
          } else {
            alert("Có lỗi xảy ra: " + data.message);
          }
        })
        .catch((error) => {
          console.error("Lỗi khi gửi yêu cầu:", error);
          alert("Không thể kết nối đến máy chủ.");
        });
    }

    
  </script>
</html>
