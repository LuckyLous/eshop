<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!-- 订单详情的模态框 -->
<div class="modal fade" id="orderItemsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document" style="width: 60%; height: 60%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">订单编号</h4>
				<div id="showId"></div>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<table class="table table-hover" id="showTable">
						<tbody>
							<tr>
							  <th class="active">图片</th>
							  <th class="success">商品</th>
							  <th class="warning">价格</th>
							  <th class="danger">数量</th>
							  <th class="info">小计</th>
							</tr>
						</tbody>
						<tbody>
							
						</tbody>
					</table>
				</div>	
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
