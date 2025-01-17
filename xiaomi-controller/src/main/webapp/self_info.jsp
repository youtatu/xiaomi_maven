<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 
 
<title>个人中心-收货地址页面</title>
<link rel="stylesheet" type="text/css" href="css/login2.css">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/axios.min.js"></script>
<script src="js/api.js"></script>
<script type="text/javascript">
	async function deleteAddr(id) {
		var res = confirm("是否删除?");
		if (res == true) {
			//window.location.href="userservlet?method=deleteAddress&id="+id;
			const res = await ajaxdeleteaddress(id)
			if (res.status == 200) {
				alert(res.data.msg)
				window.location.href = "self_info.jsp"
			}
		}
	}
	async function defaultAddr(id) {
		var res = confirm("是否设为默认?");
		if (res == true) {
			//window.location.href="userservlet?method=defaultAddress&id="+id;
			const res = await ajaxdefaultaddress(id)
			if (res.status == 200) {
				window.location.href="self_info.jsp"
			}
		}
	}
	async function editAddr(obj) {
		if (obj.innerHTML == '编辑') {
			obj.innerHTML = '更新';
			obj.classList.remove("btn-warning")
			obj.classList.add("btn-info")
			let tr = obj.parentNode.parentNode;
			let id = tr.cells[0].innerHTML;
			tr.cells[0].innerHTML += "<input type='hidden' id='id' value='" + id + "'>"
			let name = tr.cells[1].innerHTML;
			tr.cells[1].innerHTML = "<input type='text' id='name' value='" + name + "'>"
			let phone = tr.cells[2].innerHTML;
			tr.cells[2].innerHTML = "<input type='text' id='phone' value='" + phone + "'>"
			let detail = tr.cells[3].innerHTML;
			tr.cells[3].innerHTML = "<input type='text' id='detail' value='" + detail + "'>"
		} else {
			let id = $("#id").val()
			let name = $("#name").val()
			let phone = $("#phone").val()
			let detail = $("#detail").val()
			if (!name) {
				alert("请输入收件人")
				return;
			}
			if (!phone) {
				alert("请输入电话")
				return;
			}
			if (!detail) {
				alert("请输入详细地址")
				return;
			}
			obj.disabled = true;
			/*$.get("userservlet?method=updateAddress&id="+id+"&name="+name+"&phone="+phone+"&detail="+detail,function (data){
				alert(data.msg)

			})*/

			const res = await ajaxupdateaddress(id, name, phone, detail)
			if(res.status == 200){
				alert('修改成功')
				obj.disabled=false;
				window.location.href="self_info.jsp"
			}
			obj.innerHTML = '编辑';
			obj.classList.remove("btn-info")
			obj.classList.add("btn-warning")
		}
	}
	$(function () {
		//ajax获取地址
		let pr = ajaxgetaddress()
		pr.then(res=>{
			let s = "";
			for (let addr of res.data.data) {
				s += "<tr>"
				s += "<td>" + addr.id + "</td>"
				s += "<td>" + addr.name + "</td>"
				s += "<td>" + addr.phone + "</td>"
				s += "<td>" + addr.detail + "</td>"
				s += "<td>"
				s += '<button onclick="deleteAddr(' + addr.id + ')" class="btn btn-danger btn-sm">删除</button>&nbsp'
				s += '<button onclick="editAddr(this)" class="btn btn-warning btn-sm">编辑</button>&nbsp'
				s += '<button onclick="defaultAddr(' + addr.id + ')" class="btn btn-primary btn-sm">设为默认</button>'
				if (addr.level == 1) {
					s += '<span class="badge" style="background-color:red;">默认</span>'
				} else {
					s += '<span class="badge">普通</span>'
				}
				s += "</td>"
				s += "</tr>"
			}
			$("#tab").append(s)
		})
	})
</script>
</head>
<body>
<%@ include file="header.jsp"%>
<!--网站中间内容开始-->
<div id="dingdanxiangqing_body">
    <div id="dingdanxiangqing_body_big">
        <div id="big_left">
           	   <p style="font-size:18px;margin-top: 15px">订单中心</p>
               <a id="big_left_a" href="dingdanxiangqing.html">我的订单</a><br/>
               <a id="big_left_a" href="">意外保</a><br/>
               <a id="big_left_a" href="">团购订单</a><br/>
               <a id="big_left_a" href="">评价晒单</a><br/>
               <p style="font-size:18px">个人中心</p>
               <a id="big_left_a" href="self_info.html">我的个人中心</a><br/>
               <a id="big_left_a" href="">消息通知</a><br/>
               <a id="big_left_a" href="">优惠券</a><br/>
               <a id="big_left_a" href="">收货地址</a><br/>
        </div>
     <div id="big_right" style="height: 500px;overflow: scroll;">
     
         <div style="margin:0 20px;">
	         <h3>收货地址</h3>
	         <hr>
	         <table id="tab" class="table table-striped table-hover table-bordered" style="width: 100%">
				<tr>
					<th>序号</th>
					<th>收件人</th>
					<th>手机号</th>
					<th>地址</th>
					<th width="25%">操作</th>
				</tr>
				<c:forEach var="address" items="${addList}" varStatus="i" >
					<tr>
						<Td>${i.count}</Td>
						<td>${address.name}</td>
						<td>${address.phone}</td>
						<td>${address.detail}</td>
						<td>
							<button onclick="deleteAddr(${address.id})" class="btn btn-danger btn-sm">删除</button>&nbsp;&nbsp;
							<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal${address.id}">修改</button>&nbsp;&nbsp;
							<!-- 弹出模态框 -->
							
							<div class="modal fade" tabindex="-1" role="dialog" id="myModal${address.id}">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span>&times;</span>
										</button>
										<h4 class="modal-title">修改地址</h4>
									</div>
									<form action="userservlet?method=updateAddress" method="post" class="form-horizontal">
										<div class="motal-body">
											<div class="form-group">
												<label class="col-sm-2 control-label">收件人</label>
												<div class="col-sm-10">
													<input type="hidden" name="id" value="${address.id}">
													<input type="hidden" name="level" value="${address.level}">
													<input type="text" name="name" class="form-control" value="${address.name}">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">电话</label>
												<div class="col-sm-10">
													<input type="text" name="phone" class="form-control" value="${address.phone}">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">收件人</label>
												<div class="col-sm-10">
													<input type="text" name="detail" class="form-control" value="${address.detail}">
												</div>
											</div>
											
										</div>
										<div class="motal-footer">
											<button type="submit" class="btn btn-primary">修改</button>
										</div>
									</form>
								</div>
							</div>
							</div>
							
							<button onclick="defaultAddr(${address.id})" class="btn btn-primary btn-sm">设为默认</button>
							<c:if test="${address.level==1}">
								<span class="badge" style="background-color:red;">默认</span>
							</c:if>
							<c:if test="${address.level==0}">
								<span class="badge">普通</span>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br>
		<div class="container" style="width:960px;">
		
			<form action="userservlet?method=addAddress" method="post" class="form-horizontal">
				<div class="form-group">
				    <label class="col-sm-2 form-label">收件人</label>
				    <div class="col-sm-3">
				      <input type="text" class="form-control" name="name"/>
				    </div>
				</div>
		  		<div class="form-group">
				    <label class="col-sm-2 form-label">手机号</label>
				    <div class="col-sm-3">
				      <input type="text" class="form-control" name="phone"/>
				    </div>
				</div>	
				<div class="form-group">
					<label class="form-label">详细地址</label>
					<textarea rows="3" class="form-control" name="detail" ></textarea>	
				</div>
				<div class="form-group col-md-12">
					<input type="submit" class="btn btn-primary" value="添加地址">
				</div>
				<input type="hidden" value="${loginUser.id}" name="uid">
			</form>
	      </div>
       </div>
    </div>
</div>
	
<!-- 底部 -->
<%@ include file="footer.jsp"%>

</body>
</html>