<%@ page contentType="text/html;charset=UTF-8" language="java"%> <%@ taglib
uri="http://java.sun.com/jstl/core_rt" prefix="core"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />
    <script defer src="../bootstrap/js/bootstrap.bundle.min.js"></script>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
      integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link href="../styles/NavBar.css" rel="stylesheet" />
  </head>

  <body>
    <div class="position-relative">
      <%@ include file="./components/navbar.jsp"%>

      <div class="container-fluid p-0">
        <div class="container-fluid py-4">
          <div class="container">
            <div class="row">
              <div class="col-lg-8">
                <div class="ratio ratio-16x9 mb-4 bg-dark rounded">
                  <iframe
                    class="w-100 h-100 rounded-2"
                    src="https://www.youtube.com/embed/${video.id}"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                    allowfullscreen
                  >
                  </iframe>
                </div>

                <div class="video-info mb-4">
                  <h1 class="h3 mb-3">${ video.title }</h1>
                  <div
                    class="d-flex justify-content-between align-items-center mb-3"
                  >
                    <div class="text-muted">
                      <span class="me-3"
                        ><i class="fas fa-eye me-1"></i> ${video.views } lượt
                        xem</span
                      >
                    </div>
                    <div class="d-flex gap-2">
                      <button
                        class="btn btn-outline-light btn-sm border-0 like-btn ${isLiked ? 'liked-class' : ''}"
                        onclick="likeVideo('${video.id}', this)"
                        data-is-liked="${isLiked}"
                        id="like-btn-${video.id}"
                      >
                        <i class="${isLiked ? 'fas' : 'far'} fa-heart me-1"></i>
                        <span>${isLiked ? 'Đã thích' : 'Thích'}</span>
                      </button>

                      <button
                        class="btn btn-sm icon-btn z-3"
                        data-bs-toggle="modal"
                        data-bs-target="#share"
                        data-video-id="${video.id}"
                      >
                        <i class="fas fa-share"></i> Chia sẻ
                      </button>
                    </div>
                  </div>

                  <div
                    class="video-description bg-dark bg-opacity-25 p-3 rounded"
                  >
                    <h6 class="mb-3">Mô tả video</h6>
                    <p class="text-muted mb-2">${video.description }</p>
                    <p class="text-muted mb-0">#hashtag1</p>
                  </div>
                </div>
              </div>

              <div class="col-lg-4">
                <h5 class="mb-3">Video đề xuất</h5>

                <div
                  class="suggested-videos"
                  style="max-height: 800px; overflow-y: auto"
                >
                  <core:forEach var="list" items="${list10Vid}">
                    <a
                      href="/watch?id=${list.id}"
                      class="suggested-video-item d-flex gap-2 mb-3 text-decoration-none text-white"
                    >
                      <div class="flex-shrink-0">
                        <img
                          src="..${list.poster}"
                          alt="Video Thumbnail for ${list.title}"
                          class="rounded"
                          width="120"
                          height="68"
                        />
                      </div>

                      <div class="flex-grow-1">
                        <h6 class="mb-1 small">${list.title}</h6>
                        <p class="text-muted small mb-1">${list.description}</p>
                        <div class="text-muted small">
                          <i class="fas fa-eye me-1"></i>${list.views}
                        </div>
                      </div>
                    </a>
                  </core:forEach>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <%@ include file="./components/footer.jsp"%> <%@ include
      file="./components/shareBox.jsp"%>
    </div>

    <script>
      const contextPath = "${pageContext.request.contextPath}";

      function likeVideo(videoId, button) {
        if (button.classList.contains("disabled")) return;

        button.classList.add("disabled");

        const isLiked = button.getAttribute("data-is-liked") === "true";
        const formData = new FormData();
        formData.append("videoId", videoId);
        formData.append("action", isLiked ? "unlike" : "like");

        fetch(contextPath + "/like-video", {
          method: "POST",
          body: formData,
        })
          .then((response) => {
            if (response.redirected) {
              window.location.href = response.url;
              throw new Error("Redirected");
            }

            if (!response.ok) {
              return response.json().then((errData) => {
                throw new Error(
                  errData.message || `Lỗi HTTP: ${response.status}`
                );
              });
            }

            return response.json();
          })
          .then((data) => {
            if (data.status === "success") {
              const heartIcon = button.querySelector("i");
              const textSpan = button.querySelector("span");

              if (data.action === "like") {
                button.classList.add("liked-class");
                button.setAttribute("data-is-liked", "true");
                heartIcon.className = "fas fa-heart me-1";
                textSpan.textContent = "Đã thích";
              } else {
                button.classList.remove("liked-class");
                button.setAttribute("data-is-liked", "false");
                heartIcon.className = "far fa-heart me-1";
                textSpan.textContent = "Thích";
              }
            } else {
              alert("Có lỗi xảy ra: " + data.message);
            }
          })
          .catch((error) => {
            if (error.message.includes("Vui lòng đăng nhập")) {
              alert(error.message);
            } else if (error.message !== "Redirected") {
              console.error("Lỗi:", error);
              alert(
                "Không thể kết nối đến máy chủ hoặc lỗi không xác định: " +
                  error.message
              );
            }
          })
          .finally(() => {
            button.classList.remove("disabled");
          });
      }
    </script>
  </body>
</html>
