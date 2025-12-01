<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html data-bs-theme="light">
<head>
    <meta charset="UTF-8" />
    <title>Video Search Test</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        
    </style>
</head>
<body>
    <div class="container text-white  rounded shadow">    
        <form action="${pageContext.request.contextPath}/search" method="get">
            <div class="input-group p-2">
                <input type="text" 
                       class="form-control" 
                       placeholder="Nhập từ khóa tìm kiếm" 
                       name="query" 
                       required>
                <button class="btn btn-primary" type="submit">Tìm Kiếm</button>
            </div>
        </form>
    </div>
</body>
</html>