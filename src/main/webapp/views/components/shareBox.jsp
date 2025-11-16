<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="share" tabindex="-1" aria-labelledby="shareLabel">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5>Gửi video cho mọi người</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
        ></button>
      </div>
      <div class="modal-body">
        <form action="/share-video" method="post">
          <input type="hidden" name="redirectUrl" value="${currentUrl}" />
          <input type="hidden" name="currentPage" value="${page}" />
          <input type="hidden" id="videoId" name="videoId" />
          <input type="hidden" id="section" name="currentSection" />

          <div class="mb-3">
            <input
              type="email"
              class="form-control form-control-lg"
              id="emailInput"
              name="emailTo"
              placeholder="Nhập email người nhận"
              required
            />
          </div>

          <div class="d-grid mt-4">
            <button type="submit" class="btn btn-warning btn-lg fw-bold">
              Gửi Video
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
