function likeVideo(videoId, button) {
  const icon = button.querySelector("#like-icon-" + videoId);
  const isLiked = icon.getAttribute("data-is-liked") === "true";

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
          icon.classList.remove("fa-regular");
          icon.classList.add("fa-solid");
          icon.setAttribute("data-is-liked", "true");
        } else {
          icon.classList.remove("fa-solid");
          icon.classList.add("fa-regular");
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
