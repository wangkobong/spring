<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Spring Project</title>
<link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/carousel/">
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>
<!-- Custom styles for this template -->
<link href="resources/css/carousel.css" rel="stylesheet">
</head>
<body>
	<!-- jsp 액션 태그를 이용한 동적 include -->
	<jsp:include page="WEB-INF/views/common/header.jsp" />

	<main role="main">

	<!-- 슬라이드 효과 부트스트랩 -->
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<!-- <li data-target="#myCarousel" data-slide-to="1"></li> 
			<li data-target="#myCarousel" data-slide-to="2"></li> -->
		</ol>
		<div class="carousel-inner">
			<div class="carousel-item active">
				<svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img">
					<rect width="100%" height="100%" fill="#bbb" /></svg>
				<div class="container">
					<div class="carousel-caption text-left">
						<h1>Spring Project 입니다.</h1>
						<p>
							Spring MVC 프로젝트를 이용해서 개발되고 있습니다.
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">View details</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 이전 화살표 -->
		<a class="carousel-control-prev" href="#myCarousel" role="button"
			data-slide="prev"> <span class="carousel-control-prev-icon"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		<!-- 다음 화살표 -->
		</a> <a class="carousel-control-next" href="#myCarousel" role="button"
			data-slide="next"> <span class="carousel-control-next-icon"
			aria-hidden="true"></span> <span class="sr-only">Next</span>
		</a>
	</div>


	<!-- Marketing messaging and featurettes
  ================================================== --> <!-- Wrap the rest of the page in another container to center all the content. -->

	<div class="container marketing">

		<!-- Three columns of text below the carousel -->
		<div class="row">
			<div class="col-lg-4">
				<h3>자유게시판 조회수 Top5</h3>
                <h5>(최근 3일)</h5>
                <table align="center">
                    <thead>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                    </thead>

                    <tbody id="freeBoard-topViews">

                    </tbody>
                </table>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg"
					preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140">
					<title>Placeholder</title><rect width="100%" height="100%" fill="#777" />
					<text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg>
				<h2>Heading</h2>
				<p>Duis mollis, est non commodo luctus, nisi erat porttitor
					ligula, eget lacinia odio sem nec elit. Cras mattis consectetur
					purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo,
					tortor mauris condimentum nibh.</p>
				<p>
					<a class="btn btn-secondary" href="#" role="button">View details &raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<svg class="bd-placeholder-img rounded-circle" width="140" height="140" xmlns="http://www.w3.org/2000/svg"
					preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 140x140">
					<title>Placeholder</title><rect width="100%" height="100%" fill="#777" />
					<text x="50%" y="50%" fill="#777" dy=".3em">140x140</text></svg>
				<h2>Heading</h2>
				<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa justo sit amet risus.</p>
				<p>
					<a class="btn btn-secondary" href="#" role="button">View details &raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
		</div>
		</div>
		<!-- /.row -->
	</main>

	<jsp:include page="WEB-INF/views/common/footer.jsp" />
	
	<script>
		
		$(function(){
			topViews(1);	// 함소 호출
			
			// 일정 시간(1분)마다 리스트 갱신
			setInterval(function(){
				topViews(1);
			}, 60000);
		
		
		});
	
		function topViews(boardType){
			$.ajax({
				url : "board/topViews/" + boardType,
				dataType : "json", 
				success : function(list){
					console.log(list);
					
					$("#freeBoard-topViews").html(""); // 리스트 갱신을 위해 이전 내용 삭제
					
					$.each(list, function(index, item){
						
						var $tr = $("<tr>");
						
						var $td1 = $("<td>").text(item.boardNo);
						var $td2 = $("<td>").text(item.boardTitle);
						var $td3 = $("<td>").text(item.boardWriter);
						var $td4 = $("<td>").text(item.readCount);
						
						$tr.append($td1, $td2, $td3, $td4);
						$("#freeBoard-topViews").append($tr);
						
						
					});
					
				}, error : function(){
					console.log("ajax 통신 실패");
				}
			})
			
		};
	</script>
</body>
</html>