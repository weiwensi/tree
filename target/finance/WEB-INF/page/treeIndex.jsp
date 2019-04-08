
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ztree/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/ztree/jquery.ztree.all-3.5.min.js"></script>

<SCRIPT type="text/javascript">
    var setting = {}

    var zNodes ={};

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });



</SCRIPT>


<body>
<h1>最简单的树 -- 简单 JSON 数据</h1>
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>

</body>
</html>
