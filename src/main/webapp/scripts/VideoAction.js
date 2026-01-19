document.addEventListener("DOMContentLoaded", function () {
  const shareModal = document.getElementById("share");

  if (!shareModal) return;

  shareModal.addEventListener("show.bs.modal", function (event) {
    const button = event.relatedTarget;
    if (!button) return;

    const isLogin = button.getAttribute("data-is-login") === "true";

    if (!isLogin) {
      event.preventDefault();

      alert("Vui lòng đăng nhập để chia sẻ video");

      return;
    }

    const videoId = button.getAttribute("data-video-id");

    const idInput = shareModal.querySelector("#videoId");

    if (idInput) {
      idInput.value = videoId;
    }
  });
});
