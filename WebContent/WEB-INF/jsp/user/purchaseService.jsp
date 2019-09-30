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
				
				<c:if test="${orderConfirmation == 'true'}">
					THE ORDER HAS BEEN SUCCESFULLY PURCHASED <br>
					PLEASE GO TO THE NEXT PAGE <br>
					
				</c:if>
				
				
				<c:if test="${orderConfirmation != 'true'}">					
				PLEASE CONFIRM THAT YOU WANT TO PURCHASE FOLLOWING SERVICE:

				<table id="Services_table">
					<thead>
						<td>Id</a></td>
						<td>Service name</a></td>
						<td>ST id</a></td>
						<td>Information</a></td>
						<td>Price</a></td>
						<td>Your Ballance</a></td>
					</thead>

					<br>
					
					<tr>
						<td>${fullServiceForPurchaseId}</td>
						<td>${fullServiceForPurchaseName}</td>
						<td>${fullServiceForPurchaseStId}</td>
						<td>${fullServiceForPurchaseStInfo}</td>
						<td>${fullServiceForPurchasePrice}</td>
						<td>${userMoney}</td>
					<tr/>
				
				<tr>
					<form id="ConfirmPurchase" action="controller">
						<input type="hidden" name="command" value="PurchaseService" />
						<input type="hidden" name="fullServiceForPurchaseIdConfirmed" value="${fullServiceForPurchaseId}" />
						<input type="submit" value="ORDER"></form>
				</tr>
				<tr>
					<form id="RejectPurchase" action="controller">
						<input type="hidden" name="command" value="Services" />
						<input type="submit" value="NO">
					</form>
				</tr>
				</table>
				
				</c:if>
				<%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>