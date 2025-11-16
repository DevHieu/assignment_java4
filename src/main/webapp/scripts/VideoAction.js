document.addEventListener("DOMContentLoaded", function () {
  const shareModal = document.getElementById("share");

  if (shareModal) {
    shareModal.addEventListener("show.bs.modal", function (event) {
      const button = event.relatedTarget;

      const videoId = button.getAttribute("data-video-id");

      const idInput = shareModal.querySelector("#videoId");
      const sectionInput = shareModal.querySelector("#section");

      if (idInput) {
        idInput.value = videoId;
      }

      if (sectionInput) {
        sectionInput.value = "#skit";
      }
    });
  }
});
-liked", "true");
        } else {
          icon.classList.remove("liked-class");
          icon.setAttribute("data-is-liked", "false");
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

document.addEventListener("DOMContentLoaded", function () {
  const shareModal = document.getElementById("share");

  if (shareModal) {
    shareModal.addEventListener("show.bs.modal", function (event) {
      const button = event.relatedTarget;

      const videoId = button.getAttribute("data-video-id");

      const idInput = shareModal.querySelector("#videoId");
      const sectionInput = shareModal.querySelector("#section");

      if (idInput) {
        idInput.value = videoId;
      }

      if (sectionInput) {
        sectionInput.value = "#skit";
      }
    });
  }
});
f (sectionInput) {
        sectionInput.value = "#skit";
      }
    });
  }
});
