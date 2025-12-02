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
