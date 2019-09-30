<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Menu" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>

				<form id="Orders" action="controller">

					<table id="Orders_table">
						<thead>
							<tr>
								<td>
									<form name="sorting_order_id" action="controller" metod="post">
										<input type="hidden" name="command" value="Orders" />
										<c:if test="${orderSorting !='id'}">
											<input type="hidden" name="orderSorting" value="id" />
										</c:if>
										<c:if test="${orderSorting =='id'}">
											<input type="hidden" name="orderSorting" value="idBack" />
										</c:if>
										<input type="submit" value="Sort by id">
									</form>
								</td>
								<td>
									<form name="sorting_order_userId" action="controller"
										metod="post">
										<input type="hidden" name="command" value="Orders" />
										<c:if test="${orderSorting != 'userId'}">
											<input type="hidden" name="orderSorting" value="userId" />
										</c:if>
										<c:if test="${orderSorting == 'userId'}">
											<input type="hidden" name="orderSorting" value="userIdBack" />
										</c:if>
										<input type="submit" value="Sort by user Id">
									</form>
								</td>
								<td><form name="sorting_order_ServiceTypeId" action="controller"
										metod="post">
										<input type="hidden" name="command" value="Orders" />
										<c:if test="${orderSorting != 'serviceType'}">
											<input type="hidden" name="orderSorting" value="serviceType" />
										</c:if>
										<c:if test="${orderSorting == 'serviceType'}">
											<input type="hidden" name="orderSorting" value="serviceTypeBack" />
										</c:if>
										<input type="submit" value="Sort by ServiceTypeId">
									</form>
								</td>
								<td><form name="sorting_order_Date" action="controller"
										metod="post">
										<input type="hidden" name="command" value="Orders" />
										<c:if test="${orderSorting != 'date'}">
											<input type="hidden" name="orderSorting" value="date" />
										</c:if>
										<c:if test="${orderSorting == 'date'}">
											<input type="hidden" name="orderSorting" value="dateBack" />
										</c:if>
										<input type="submit" value="Sort by Date">
									</form>
								</td>
								<td><form name="sorting_order_PaidStatus" action="controller"
										metod="post">
										<input type="hidden" name="command" value="Orders" />
										<c:if test="${orderSorting != 'paid'}">
											<input type="hidden" name="orderSorting" value="paid" />
										</c:if>
										<c:if test="${orderSorting == 'paid'}">
											<input type="hidden" name="orderSorting" value="paidBack" />
										</c:if>
										<input type="submit" value="Sort by ServiceTypeId">
									</form>
								</td>
							</tr>
						</thead>

						<br />

						<c:forEach var="order" items="${allOrders}">
							<tr>
								<td>${order.id}</td>
								<td>${order.order_user_id}</td>
								<td>${order.order_serviceType_id}</td>
								<td>${order.order_date}</td>
								<td>${order.order_status}</td>
							</tr>
						</c:forEach>
					</table>

				</form> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>