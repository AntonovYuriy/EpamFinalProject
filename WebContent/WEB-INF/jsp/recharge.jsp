<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="userRecharge" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		
		<tr>
			<td class="content">
				<%-- CONTENT --%> 
				RECHARGING USER ACCOUNT PAGE<br>
				Recharging user ${userForRecharge.firstName}
				${userForRecharge.lastName} with ID ${userForRecharge.id} <br>
				From 0 to 1000 <br>
				<table>
					<form id="settings_form" action="controller" method="post">
						<c:if test="${userRole.name == 'admin'}">
							<input type="hidden" name="command" value="allRecharge" />
						</c:if>
						<c:if test="${userRole.name == 'user'}">
							<input type="hidden" name="command" value="userRecharge" />
						</c:if>
					<tr>
						<td>Your ballance =</td>
						<td>${userForRecharge.money}</td>
					</tr>
					<tr>
						<td>Add =</td>
						<td><input name="recharge" value=0></td>
					</tr>
					<input type="submit" value="Add to ballance">
					</form>

				</table> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>