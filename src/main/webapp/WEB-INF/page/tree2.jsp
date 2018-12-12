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

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 许可维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <jsp:include page="/WEB-INF/jsp/common/userinfo.jsp"></jsp:include>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
            </div>
        </div>
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

<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH }/script/docs.min.js"></script>
<script src="${APP_PATH }/jquery/layer/layer.js"></script>
<script src="${APP_PATH }/ztree/jquery.ztree.all-3.5.min.js"></script>


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

    var setting = {
        async: {
            enable: true,
            url:"${APP_PATH}/permission/loadData.do"
        },
        view : { //视图设置
            addDiyDom: function(treeId, treeNode){  //自定义设置  treeId表示容器id;   treeNode表示节点，相当于Permission对象。
                var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                if ( treeNode.icon ) {
                    icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background","");
                }
            },
            addHoverDom: function(treeId, treeNode){   //鼠标移动到节点上时触发的事件
                var aObj = $("#" + treeNode.tId + "_a"); //
                aObj.attr("href", "javascript:;");//禁用href属性
                if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
                var s = '<span id="btnGroup'+treeNode.tId+'">';
                if ( treeNode.level == 0 ) { //根节点
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="window.location.href=\'${APP_PATH}/permission/toAdd.htm?id='+treeNode.id+'\'" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if ( treeNode.level == 1 ) { //分支节点
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="window.location.href=\'${APP_PATH}/permission/toUpdate.htm?id='+treeNode.id+'\'" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    if (treeNode.children.length == 0) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deletePermission('+treeNode.id+',\''+treeNode.name+'\')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="window.location.href=\'${APP_PATH}/permission/toAdd.htm?id='+treeNode.id+'\'" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if ( treeNode.level == 2 ) { //叶子节点
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="window.location.href=\'${APP_PATH}/permission/toUpdate.htm?id='+treeNode.id+'\'" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deletePermission('+treeNode.id+',\''+treeNode.name+'\')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                }

                s += '</span>';
                aObj.after(s);
            },
            removeHoverDom: function(treeId, treeNode){ //鼠标离开节点时触发的事件
                $("#btnGroup"+treeNode.tId).remove();
            }
        }

    };




    /* var index = -1 ;
    $.ajax({
        type:"POST",
        url:"${APP_PATH}/permission/loadData.do",
        		data:{},
        		beforeSend:function(){
        			index = layer.load(2, {time: 10*1000});
        			return true ;
        		},
        		success:function(result){
        			layer.close(index);
        			if(result.success){
        				var zNodes = result.data;
        				$.fn.zTree.init($("#treeDemo"), setting, zNodes); //同步加载许可树的数据。
        			}else{
        				layer.msg("加载许可树失败！", {time:1000, icon:5, shift:6});
        			}
        		}            		
        	}); */
    $.fn.zTree.init($("#treeDemo"), setting); //异步加载许可树的数据


    function deletePermission(id,name){

        layer.confirm("您确认删除【"+name+"】许可吗？",  {icon: 3, title:'提示'}, function(cindex){
            layer.close(cindex);


            $.ajax({
                type:"POST",
                url:"${APP_PATH}/permission/doDelete.do",
                data:{
                    id:id
                },
                beforeSend:function(){
                    loadingIndex = layer.msg('正在删除许可数据,请稍后！', {icon: 16});
                    return true ;
                },
                success:function(result){
                    layer.close(loadingIndex);
                    if(result.success){
                        layer.msg("删除成功！", {time:1000, icon:6});
                        //window.location.href="${APP_PATH}/permission/index.htm";  // TODO  临时代码，后期改进

                        //读取当前树对象
                        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");

                        //刷新当前树对象的数据（异步）：树形结构数据必须异步加载
                        treeObj.reAsyncChildNodes(null, "refresh");


                    }else{
                        layer.msg("删除失败！", {time:1000, icon:5, shift:6});
                    }
                }
            });


        }, function(cindex){
            layer.close(cindex);
        });

    }
</script>
</body>
</html>
    