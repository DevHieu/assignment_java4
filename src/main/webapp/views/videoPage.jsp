<%@ page contentType="text/html;charset=UTF-8" language="java"%> 
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="core" %>
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
      <%@ include file="./components/navbar.jsp" %>

      <div class="container-fluid p-0">
        <div class="container-fluid py-4">
          <div class="container">
            <div class="row">
              <div class="col-lg-8">
                <div class="ratio ratio-16x9 mb-4 bg-dark rounded">
                  <video controls class="w-100 h-100 rounded-2">
                    <source src="..." type="video/mp4" />
                    Trình duyệt của cùi đến mức không hỗ trợ định dạng video
                    mp4.
                  </video>
                </div>

                <div class="video-info mb-4">
                  <h1 class="h3 mb-3">${video.title }</h1>
                  <div
                    class="d-flex justify-content-between align-items-center mb-3"
                  >
                    <div class="text-muted">
                      <span class="me-3"
                        ><i class="fas fa-eye me-1"></i> ${video.views } lượt xem</span
                      >
                      
                    </div>
                    <div class="d-flex gap-2">
                      <button class="btn btn-outline-light btn-sm border-0">
                        <i class="far fa-heart me-1"></i>
                        <span>Thích</span>
                      </button>
                      <button class="btn btn-outline-light btn-sm border-0">
                        <i class="fa-solid fa-share me-1"></i>
                        <span>Chia sẻ</span>
                      </button>
                    </div>
                  </div>

                  <div
                    class="video-description bg-dark bg-opacity-25 p-3 rounded"
                  >
                    <h6 class="mb-3">Mô tả video</h6>
                    <p class="text-muted mb-2">
                     ${video.description }
                    </p>
                    <p class="text-muted mb-0">#hashtag1</p>
                  </div>
                </div>
              </div>

              <div class="col-lg-4">
                <h5 class="mb-3">Video đề xuất</h5>
                
                <div class="suggested-videos" style="max-height: 800px; overflow-y: auto;">
                  
                  <core:forEach var="list" items="${ListVideoAll}">
                  
                    <div class="suggested-video-item d-flex gap-2 mb-3">
                      <div class="flex-shrink-0">
                        <img
                          src="../images/baner1.png"
                          alt="Video Thumbnail"
                          class="rounded"
                          width="120"
                          height="68"
                        />
                      </div>
                      <div class="flex-grow-1">
                        <h6 class="mb-1 small">
                          ${list.title}
                        </h6>
                        <p class="text-muted small mb-1">
                          ${list.description}
                        </p>
                        <div class="text-muted small">
                          <span><i class="fas fa-eye me-1"></i>${list.views }</span>
                        </div>
                      </div>
                    </div>
                  

                  </core:forEach>
                  
                </div> 
                </div>
            </div>
          </div>
        </div>
      </div>
      <%@ include file="./components/footer.jsp" %> 
      <%@ include file="./components/shareBox.jsp" %>
    </div>
  </body>
</html>