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
				<c:if test="${changePasswordConfirm == 'done'}">
				Password changing has been successfully Changed
				</c:if> 
				
				<c:if test="${changePasswordConfirm != 'done'}">
					<table>
						<form id="changePassword" action="controller" method="post">
							<tr>
								<td>User ID</td>
								<td>${userForPasswordChangeIdConfirmed}</td>
							</tr>

							<input type="hidden" name="command" value="allPasswordChange" />
							<tr>
								<td>New Password</td>
								<td><input type="text" name="newPassword"></td>
							</tr>
							<input type="submit" value="Change Password">
						</form>
						</c:if>
					</table>

					<%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>