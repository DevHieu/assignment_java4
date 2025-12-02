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
          <c:choose>
            <c:when test="${not empty historyVideos}">
              <c:forEach var="video" items="${historyVideos}" varStatus="loop">
                <div class="col-md-3 video-item ${video.liked ? 'liked-video' : ''}" data-video-id="${video.id}">
                  <div class="card border-0 h-100">
                    <div class="card-body p-0">
                      <a href="watch?id=${video.id}"> 
                        <img 
                          src="${pageContext.request.contextPath}/${video.poster}" 
                          class="w-100" 
                          style="aspect-ratio: 16/9; object-fit: cover; border-radius: 0.375rem 0.375rem 0 0;"
                          alt="${video.title}"
                        />
                      </a>
                      <div class="p-3">
                        <h5 class="card-title text-white mb-2">
                           <a href="watch?id=${video.id}" class="text-white text-decoration-none">
                              ${video.title}
                           </a>
                        </h5>
                        
                        <p class="card-text text-white-50 small mb-2">${video.description}</p>
                        
                        <p class="text-white-50 small mb-1"><i class="fas fa-eye me-1"></i> ${video.views} lượt xem</p>
                        
                        <p class="text-white-50 small mb-3">
                            <i class="fas fa-clock me-1"></i> Xem lần cuối: ${video.viewDate}
                        </p>
                        
                        <div class="d-flex gap-2">
                          <button class="btn btn-sm ${video.liked ? 'btn-liked' : 'btn-outline-primary'} like-btn" data-video-id="${video.id}">
                             <i class="fas fa-thumbs-up"></i>
                          </button>                  
                          <button class="btn btn-sm btn-outline-primary share-btn" data-bs-toggle="modal" data-bs-target="#share" data-video-id="${video.id}">
                             <i class="fas fa-share"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <div class="col-12">
                  <p class="text-white">Bạn chưa xem video nào gần đây.</p>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
        <nav aria-label="Page navigation" class="mt-4" data-bs-theme="dark">
          <ul class="pagination justify-content-center">
            <li class="page-item" id="first-page">
              <a class="page-link page-control" href="#">&laquo;</a>
            </li>

            <li class="page-item" id="prev-page">
              <a class="page-link page-control" href="#">&lt;</a>
            </li>
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
      const likeButtons = document.querySelectorAll(".like-btn") ;
      const shareModal = document.getElementById('share') ;
      
      const videosPerPage = 16; 
      const allVideoItems = document.querySelectorAll("#video-grid .video-item");
      const paginationContainer = document.querySelector('.pagination.justify-content-center');
      const prevPageItem = document.getElementById("prev-page");
      const nextPageItem = document.getElementById("next-page");
      const firstPageItem = document.getElementById("first-page");
      const lastPageItem = document.getElementById("last-page");
      
      let currentPage = 1;

      function calculateTotalPages(totalVideos) {
          return Math.ceil(totalVideos / videosPerPage);
      }
      
      function setupPageNumbers(totalVideos) {
          const totalPages = calculateTotalPages(totalVideos);
          
          document.querySelectorAll('.page-number-item').forEach(item => item.remove());
          
          for (let i = 1; i <= totalPages; i++) {
              const li = document.createElement('li');
              li.classList.add('page-item', 'page-number-item');
              
              const a = document.createElement('a');
              a.classList.add('page-link', 'page-number');
              a.href = '#';
              a.textContent = i;
              a.setAttribute('data-page-num', i); 
                            
              a.addEventListener('click', function(e) {
                  e.preventDefault();
                  currentPage = parseInt(this.getAttribute('data-page-num')); 
                  applyPagination(currentPage);
              });

              li.appendChild(a);
              
              paginationContainer.insertBefore(li, nextPageItem);
          }
          
          updatePageControls(totalPages);
      }
      
      function updatePageControls(totalPages) {
          document.querySelectorAll('.page-number-item').forEach(item => item.classList.remove('active'));
          const currentNumberItem = document.querySelector(`.page-number[data-page-num="${currentPage}"]`);
          if (currentNumberItem) {
              currentNumberItem.closest('.page-item').classList.add('active');
          } else {
              document.querySelector('.pagination').style.display = totalPages > 0 ? 'flex' : 'none';
          }
          
          firstPageItem.classList.toggle('disabled', currentPage === 1 || totalPages === 0);
          prevPageItem.classList.toggle('disabled', currentPage === 1 || totalPages === 0);
          nextPageItem.classList.toggle('disabled', currentPage === totalPages || totalPages === 0);
          lastPageItem.classList.toggle('disabled', currentPage === totalPages || totalPages === 0);

          // Cập nhật trạng thái active cho số trang
          const activePageNum = document.querySelector(`.page-number-item a[data-page-num="${currentPage}"]`);
          if (activePageNum) {
              document.querySelectorAll('.page-number-item').forEach(item => item.classList.remove('active'));
              activePageNum.closest('.page-item').classList.add('active');
          }
      }
      function applyPagination(pageNumber) {
          const videosToDisplay = Array.from(allVideoItems).filter(item => {
              if (filterLiked.classList.contains("active")) {
                  return item.classList.contains("liked-video");
              }
              return true;
          });

          const startIndex = (pageNumber - 1) * videosPerPage;
          const endIndex = startIndex + videosPerPage;
          
          allVideoItems.forEach(item => item.style.display = 'none');
          
          videosToDisplay.forEach((item, index) => {
              if (index >= startIndex && index < endIndex) {
                  item.style.display = 'block';
              }
          });
          
          setupPageNumbers(videosToDisplay.length);
          updatePageControls(calculateTotalPages(videosToDisplay.length));
          
          document.querySelectorAll('.page-number-item').forEach(li => {
              li.classList.remove('active');
              if (parseInt(li.querySelector('.page-number').textContent) === pageNumber) {
                  li.classList.add('active');
              }
          });
      }
      
      function filterVideos(filterType) {
        filterAll.classList.remove('active');
        filterLiked.classList.remove('active');
        
        if (filterType === 'all') {
            filterAll.classList.add('active');
        } else if (filterType === 'liked') {
            filterLiked.classList.add('active');
        }
        
        currentPage = 1; 
        applyPagination(currentPage);
      }
      
      filterAll.addEventListener('click', () => filterVideos('all'));
      filterLiked.addEventListener('click', () => filterVideos('liked'));
   
      prevPageItem.addEventListener('click', function(e) {
          e.preventDefault();
          if (currentPage > 1) {
              currentPage--;
              applyPagination(currentPage);
          }
      });
      
      nextPageItem.addEventListener('click', function(e) {
          e.preventDefault();
          const videosToDisplay = Array.from(allVideoItems).filter(item => filterAll.classList.contains("active") || item.classList.contains("liked-video"));
          const totalPages = calculateTotalPages(videosToDisplay.length);
          if (currentPage < totalPages) {
              currentPage++;
              applyPagination(currentPage);
          }
      });

      firstPageItem.addEventListener('click', function(e) {
          e.preventDefault();
          currentPage = 1;
          applyPagination(currentPage);
      });

      lastPageItem.addEventListener('click', function(e) {
          e.preventDefault();
          const videosToDisplay = Array.from(allVideoItems).filter(item => filterAll.classList.contains("active") || item.classList.contains("liked-video"));
          const totalPages = calculateTotalPages(videosToDisplay.length);
          currentPage = totalPages;
          applyPagination(currentPage);
      });
      
      likeButtons.forEach((button) => {
          button.addEventListener("click", function () {
              const videoItem = this.closest(".video-item");
              const isLiked = videoItem.classList.contains("liked-video");

              if (isLiked) {
                  videoItem.classList.remove("liked-video");
                  this.classList.remove("btn-liked");
                  this.classList.add("btn-outline-primary");
              } else {
                  videoItem.classList.add("liked-video");
                  this.classList.remove("btn-outline-primary");
                  this.classList.add("btn-liked");
              }

              const filterType = filterLiked.classList.contains("active") ? 'liked' : 'all';
              filterVideos(filterType);
          });
      });
      
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

      const initialVideos = Array.from(allVideoItems).filter(item => {
          if (filterLiked.classList.contains("active")) {
              return item.classList.contains("liked-video");
          }
          return true;
      });
      setupPageNumbers(initialVideos.length); 
      applyPagination(currentPage);
    });
  </script>
</html>