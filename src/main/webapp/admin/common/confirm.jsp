<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!-- 确认模态框 -->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document" style="width: 300px; height: 200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					提示
				</h4>
			</div>
			<div class="modal-body" id="myModalBody">
				确认该操作吗？
			</div>
			<div class="modal-footer">
				<input type="hidden" id="url"/>
				<button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
				<button onclick="urlSubmit()" type="button" class="btn btn-success" data-dismiss="modal">确认</button>
			</div>
		</div>
	</div>
</div>
