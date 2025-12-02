<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
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
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
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

        <div class="d-flex gap-2 mb-4">
          <button id="filter-all" class="btn btn-outline-primary active">
            Tất cả
          </button>
          <button id="filter-liked" class="btn btn-outline-primary">
            Đã thích
          </button>
        </div>

        <div class="row g-4" id="video-grid">
          
          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner1.png" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 1</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video ngắn...</p>
                  <p class="text-white-50 small mb-3">1,234 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V1"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner2.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 2</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video ngắn...</p>
                  <p class="text-white-50 small mb-3">5,678 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V2"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner3.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 3</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video...</p>
                  <p class="text-white-50 small mb-3">9,012 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V3"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner1.png" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 4</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">3,456 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V4"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner2.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 5</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">10,200 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V5"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner3.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 6</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">4,900 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V6"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner1.png" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 7</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">2,100 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V7"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner2.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 8</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">8,999 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V8"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner2.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 9</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả...</p>
                  <p class="text-white-50 small mb-3">8,999 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V9"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner3.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 10</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video mới...</p>
                  <p class="text-white-50 small mb-3">500 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V10"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner1.png" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 11</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video mới...</p>
                  <p class="text-white-50 small mb-3">1,500 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V11"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner2.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 12</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video mới...</p>
                  <p class="text-white-50 small mb-3">900 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V12"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner3.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 13</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video mới...</p>
                  <p class="text-white-50 small mb-3">2,800 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V13"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner1.png" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 14</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video mới...</p>
                  <p class="text-white-50 small mb-3">1,100 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V14"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item liked-video">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner2.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 15</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video mới...</p>
                  <p class="text-white-50 small mb-3">4,200 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-liked like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V15"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner3.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 16</h5>
                  <p class="card-text text-white-50 small mb-2">Mô tả video cuối Trang 1...</p>
                  <p class="text-white-50 small mb-3">1,800 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V16"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-3 video-item">
            <div class="card border-0 h-100">
              <div class="card-body p-0">
                <img src="../images/baner3.jpg" class="w-100" style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"/>
                <div class="p-3">
                  <h5 class="card-title text-white mb-2">Video Title 17</h5>
                  <p class="card-text text-white-50 small mb-2">Video đầu tiên của Trang 2...</p>
                  <p class="text-white-50 small mb-3">1,800 views</p>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary like-btn"><i class="fas fa-thumbs-up"></i></button>                  
                    <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="V17"><i class="fas fa-share"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          </div>

        <nav aria-label="Page navigation" class="mt-4" data-bs-theme="dark">
          <ul class="pagination justify-content-center">
            <li class="page-item" id="first-page">
              <a class="page-link page-control" href="#">&laquo;</a>
            </li>

            <li class="page-item" id="prev-page">
              <a class="page-link page-control" href="#">&lt;</a>
            </li>

            <li class="page-item active"><a class="page-link page-number" href="#">1</a></li>
            <li class="page-item"><a class="page-link page-number" href="#">2</a></li>
            <li class="page-item"><a class="page-link page-number" href="#">3</a></li>
            <li class="page-item"><a class="page-link page-number" href="#">4</a></li>
            <li class="page-item"><a class="page-link page-number" href="#">5</a></li>

            <li class="page-item" id="next-page">
              <a class="page-link page-control" href="#">&gt;</a>
            </li>

            <li class="page-item" id="last-page">
              <a class="page-link page-control" href="#">&raquo;</a>
            </li>
          </ul>
        </nav>
      </div>
    </main>
    <%@ include file="./components/footer.jsp" %>
    <%@ include file="./components/shareBox.jsp" %>
  </body>
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      const filterLiked = document.getElementById("filter-liked") ;
      const filterAll = document.getElementById("filter-all") ;
      const videoItems = document.querySelectorAll(".video-item") ;
      const likeButtons = document.querySelectorAll(".like-btn") ;
      const shareModal = document.getElementById('share') ;
      
      // Hằng số và đối tượng Phân trang
      const videosPerPage = 16; 
      const allVideoItems = document.querySelectorAll("#video-grid .video-item");
      const pageNumbers = document.querySelectorAll('.page-number');
      
// --- LOGIC PHÂN TRANG VÀ HIỂN THỊ ---
      
      function applyPagination(pageNumber) {
          const startIndex = (pageNumber - 1) * videosPerPage;
          const endIndex = pageNumber * videosPerPage;
          
          allVideoItems.forEach((item, index) => {
              // Hiển thị nếu Index nằm trong phạm vi của trang hiện tại
              if (index >= startIndex && index < endIndex) {
                  item.style.display = 'block';
              } else {
                  item.style.display = 'none';
              }
          });
          
          // Cập nhật trạng thái active của nút phân trang
          pageNumbers.forEach(pageLink => {
              if (pageLink.textContent === String(pageNumber)) {
                  pageLink.closest('.page-item').classList.add('active');
              } else {
                  pageLink.closest('.page-item').classList.remove('active');
              }
          });
      }

      // 1. KÍCH HOẠT PHÂN TRANG KHI TẢI TRANG (Hiển thị Trang 1)
      applyPagination(1); 

      // 2. GẮN SỰ KIỆN CHO CÁC NÚT SỐ TRANG
      pageNumbers.forEach(link => {
          link.addEventListener('click', function(e) {
              e.preventDefault();
              const page = parseInt(this.textContent);
              applyPagination(page);
          });
      });
      
// --- LOGIC LỌC (FILTER) VÀ LIKE ---

      function filterVideos(filterType) {
        // Tắt phân trang tạm thời khi lọc
        allVideoItems.forEach(video => {
            if (filterType === "all") {
                 // Không làm gì, sẽ áp dụng lại phân trang sau
            } else if (filterType === "liked") {
                const isLiked = video.classList.contains("liked-video");
                video.style.display = isLiked ? "block" : "none"; 
            }
        });

        // Sau khi lọc, nếu đang ở chế độ 'Tất cả', áp dụng lại phân trang Trang 1
        if (filterType === "all") {
             applyPagination(1); 
        }
      }

      filterLiked.addEventListener("click", function () {
        filterLiked.classList.add("active");
        filterAll.classList.remove("active");
        filterVideos("liked");
      });

      filterAll.addEventListener("click", function () {
        filterAll.classList.add("active");
        filterLiked.classList.remove("active");
        filterVideos("all");
      });


      likeButtons.forEach((button) => {
        button.addEventListener("click", function () {
          const videoItem = this.closest(".video-item");

          if (videoItem.classList.contains("liked-video")) {
            videoItem.classList.remove("liked-video");
            this.classList.remove("btn-liked");
            this.classList.add("btn-outline-primary");
            alert("Đã bỏ thích video!");
          } else {
            videoItem.classList.add("liked-video");
            this.classList.remove("btn-outline-primary");
            this.classList.add("btn-liked");
            alert("Đã thích video!");
          }

          if (filterLiked.classList.contains("active")) {
            filterVideos("liked");
          }
        });
      });

      // LOGIC CHIA SẺ (Modal)
      if (shareModal) {
          shareModal.addEventListener('show.bs.modal', function (event) {
              const button = event.relatedTarget; 
              const videoId = button.getAttribute('data-video-id');
              const modalVideoIdInput = shareModal.querySelector('#videoId');
              if (modalVideoIdInput) {
                  modalVideoIdInput.value = videoId; 
              }
          });
      }
    });
  </script>
</html>