<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!--分页 -->
	<div class="col-md-12">
		<!-- 分页文字信息 -->
		<div class="col-md-6">
			当前${pageInfo.pageNum }页,
			总${pageInfo.pages }页,
			共${pageInfo.total }条记录
		</div>
		<!-- 分页条信息 -->
		<div class="col-md-6">
			<nav aria-label="Page navigation">
				<ul class="pagination" >
					<!-- 判断是否是第一页 -->
					<c:if test="${not pageInfo.isFirstPage and pageInfo.pageNum > 0 }">
						<li>
							<a href="${pageContext.request.contextPath }/order/list?page=1&status=${param.status}">
								<span aria-hidden="true">首页</span>
							</a>
						</li>
					</c:if>
					<!-- 判断是否有上一页 -->
					<c:if test="${pageInfo.hasPreviousPage}">
						<li>
							<a href="${pageContext.request.contextPath}/order/list?page=${pageInfo.prePage }&status=${param.status}" aria-label="Previous">
								<span aria-hidden="true">上一页</span>
							</a>
						</li>
					</c:if>
					<!-- 展示连续5条页码 -->
					<c:forEach items="${pageInfo.navigatepageNums }" var="pageNum">
						<c:if test="${pageNum ==  pageInfo.pageNum }">
							<li class="active"><a href="javascript:void(0)">${pageNum }</a></li>
						</c:if>
						<c:if test="${pageNum !=  pageInfo.pageNum }">
							<li>
								<a href="${pageContext.request.contextPath}/order/list?page=${pageNum }&status=${param.status}">${pageNum }</a>
							</li>
						</c:if>
					</c:forEach>
					<!-- 判断是否有下一页 -->
					<c:if test="${pageInfo.hasNextPage  }">
						<li>
							<a href="${pageContext.request.contextPath}/order/list?page=${pageInfo.nextPage }&status=${param.status}" aria-label="Next">
								<span aria-hidden="true">下一页</span>
							</a>
						</li>
					</c:if>
					<!-- 判断是否为最后一页 -->
					<c:if test="${not pageInfo.isLastPage }">
						<li>
							<a href="${pageContext.request.contextPath }/order/list?page=${pageInfo.pages}&status=${param.status}">
								<span aria-hidden="true">末页</span>
							</a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>
<!--分页结束-->
