<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/ztree/zTreeStyle.css">


    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>


<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 许可树</h3>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/script/docs.min.js"></script>
<script src="${pageContext.request.contextPath}/static/jquery/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/ztree/jquery.ztree.all-3.5.min.js"></script>


<script type="text/javascript">
    $(function () { //页面加载完成时执行的事件处理
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });

    });

    var setting = {};
    var zNodes={};

                $.ajax({
                    type:"POST",
                    url:"${pageContext.request.contextPath}/permission/loadData.do",
        	     	success:function(result){
        				var zNodes = result;
        				$.fn.zTree.init($("#treeDemo"), setting, zNodes); //同步加载许可树的数据。

        				//layer.msg("加载许可树失败！", {time:1000, icon:5, shift:6});

        		}
        	});




  //  $.fn.zTree.init($("#treeDemo"), setting); //异步加载许可树的数据


</script>
</body>
</html>
    