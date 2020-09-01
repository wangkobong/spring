<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board.boardName}</title>
<style>
	#board-area{ margin-bottom:100px;}
	#board-content{ padding-bottom:150px;}
	#date-area{font-size: 12px; line-height: 12px}
	#date-area>p{margin: 0}
 

	.boardImgArea{
		height: 300px;
	}

	.boardImg{
		width : 100%;
		height: 100%;
		
		max-width : 300px;
		max-height: 300px;
		
		margin : auto;
	}
	
	#content-main{ margin: 100px auto;}
	
	/* 이미지 화살표 색 조정
	-> fill='%23000' 부분의 000을
	   RGB 16진수 값을 작성하여 변경 가능 
	 */
	.carousel-control-prev-icon {
 		background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E") !important;
	}
	
	.carousel-control-next-icon {
  		background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E") !important;
	}
	
	
</style>
</head>
<body>
	<jsp:include page="../common/header.jsp"/>
	<div class="container" id="content-main">

		<div>
			<h1>${board.boardName}</h1>
			<hr>
			<div id="board-area">

				<!-- Category -->
				<h6 class="mt-4">카테고리 : [${board.boardCategory}]</h6>
				
				<!-- Title -->
				<h3 class="mt-4">${board.boardTitle}</h3>
	
				<hr>

				<!-- Writer -->
				<div class="lead">
					작성자 : ${board.boardWriter} <br>
				 	<span class="float-right">조회수 : ${board.readCount} </span>
					<div id="date-area">
						<p>작성일 : ${board.boardCreateDate}</p>
						<p>마지막 수정일 : ${board.boardModifyDate}</p>  
					</div>
					
				</div>

				<hr>

				
				<!-- 이미지 부분 -->
				<c:if test="${!empty files}">

                    <div class="carousel slide m-3" id="carousel-325626">

                        <div class="carousel-inner boardImgArea">


                            <c:forEach var="at" items="${files}" varStatus="vs">
                                <c:set var="src" value="${contextPath}${at.filePath}/${at.fileChangeName}"/>

                                <div class="carousel-item <c:if test="${vs.index == 0}"> active</c:if>">
                                    <img class="d-block w-100 boardImg" src="${src}" />
                                    <input type="hidden" value="${at.fileNo}">
                                </div>
                            </c:forEach>

                        </div> 


                        <a class="carousel-control-prev" href="#carousel-325626" data-slide="prev"><span class="carousel-control-prev-icon"></span> <span class="sr-only">Previous</span></a> <a class="carousel-control-next" href="#carousel-325626" data-slide="next">
                        <span class="carousel-control-next-icon"></span> 
                        <span class="sr-only">Next</span></a>
                    </div>
                </c:if>


				<!-- Content -->
				<div id="board-content">
					<%pageContext.setAttribute("newLine", "\n");%>
					<!-- JSTL/JSP 개행문자 처리 -->
					${fn:replace(board.boardContent, newLine, "<br>") }			
				</div>

				<hr>
				
				<div>
					<div class="float-right">
						<a class="btn btn-primary" href="${listUrl}">목록으로</a>
<%-- 						<a class="btn btn-primary" 
							href="<c:url value="../list/${board.boardType}">
									<c:param name="cp" value="${param.cp}"/>						
								  </c:url>">목록으로</a> --%>
							
							<!-- 상세조회 주소 예시 : /1/500?cp=1  -->
							<!-- 목록으로 주소 예시 : board/list/1?cp=1  -->

	                	<!-- 글 작성자와 로그인한 회원이 같을 경우 -->
	                	
	                	<c:if test="${board.boardWriter == loginMember.memberId}">
	                		<c:url var="updateUrl" value="${board.boardNo}/update">
	                			<c:param name="cp" value="${param.cp}"/>
	                		</c:url>
	                		
	                		
	                		
							<a href="${updateUrl}" class="btn btn-primary ml-1 mr-1">수정</a>
							<button id="deleteBtn" class="btn btn-primary">삭제</button> 
						</c:if>
					</div>
				</div>
			</div>

			<hr>

	
		</div>
	</div>
	<jsp:include page="../common/footer.jsp"/>
	
	
	<script>	
		$("#deleteBtn").on("click",function(){
			if(confirm("정말 삭제하시겠습니까?")){
				// spring/board/1/515/delete
				location.href="${board.boardNo}/delete";
			}
				
		});
		
		
	</script>
</body>
</html>
