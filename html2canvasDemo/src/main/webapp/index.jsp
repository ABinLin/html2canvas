<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="html2canvas.js"></script>
<!-- <script src="https://cdn.bootcss.com/html2canvas/0.4.1/html2canvas.js"></script> -->
<!-- <script src="http://html2canvas.hertzen.com/dist/html2canvas.js"></script> -->
<script src="html2canvas.js"></script>

<script>
	$(function(){
		var ctx="<%=request.getContextPath()%>";
		$("#btn").click(function(){
			var shareContent = document.getElementById('contbox');//需要截图的包裹的（原生的）DOM 对象
            var width = shareContent.offsetWidth; //获取dom 宽度
            var height = shareContent.offsetHeight; //获取dom 高度
            var canvas = document.createElement("canvas"); //创建一个canvas节点
            var scale = 2; //定义任意放大倍数 支持小数
            canvas.width = width * scale; //定义canvas 宽度 * 缩放
            canvas.height = height * scale; //定义canvas高度 *缩放
            canvas.getContext("2d").scale(scale,scale); //获取context,设置scale

            var opts = {
                scale:scale, // 添加的scale 参数
                canvas:canvas, //自定义 canvas
                logging: true, //日志开关
                width:width, //dom 原始宽度
                height:height, //dom 原始高度
                backgroundColor: '#ffffff',
            };
            html2canvas(shareContent, opts).then(function (canvas) {
                var myImage = canvas.toDataURL("image/jpeg");
                //并将图片上传到服务器
                $.ajax({
					type : "POST",
					url : ctx+'/qsupload',
					data :{data : myImage},
					timeout : 60000,
					success : function(data){
						$("#down1").attr('href', ctx+"/download?fileName=" + data+".jpg" );
						$("#down1").attr('download', data+".pdf");
						document.getElementById("down1").click();
					}
				});
            });
		});
	});
</script>
</head>
<body>
	<div class="btn" id="btn">截取屏幕并下载</div>
	<div id="contbox" style="width: 1000px">
		<h2 style="color: red">html2canvasDemo</h2>
		<div style="color: red">你的网页内容………………</div>
		<div>
		<img alt="" src="img/2.jpg">
		</div>
		<div>
		<img alt="" src="img/1.jpg">
		</div>
		
	</div>
	<div >
	
	<div hidden="true">
		<a id="down1" class="down" href="" download="downImg">截图下载</a>
	</div>
	</div>
</body>
</html>
