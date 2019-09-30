<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="userSettings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%> 
				<fmt:message key="setting_jsp.anchor.updateingInfo"/>${userForUpdate.id}
				
				<table>
					<form id="settings_form" action="controller" method="post">
						<c:if test="${userRole.name == 'admin'}">
							<input type="hidden" name="command" value="allSettings" />
						</c:if>
						<c:if test="${userRole.name == 'user'}">
							<input type="hidden" name="command" value="userSettings" />
						</c:if>
						
						<tr>
							<td>Login =</td>
							<td>${userForUpdate.login}</td>
						</tr>
						<tr>
							<td>Locale =</td>
							<td>${userForUpdate.locale}</td>
							<td><select name="locale" value=${user.locale } default="en">
									<option value="en">English</option>
									<option value="ru">Russian</option>
							</select></td>
						</tr>
						<tr>
							<td>First name =</td>
							<td>${userForUpdate.firstName}</td>
							<td><input name="firstName" value=${user.firstName}>
							</td>
						</tr>
						<tr>
							<td>Last name =</td>
							<td>${userForUpdate.lastName}</td>
							<td><input name="lastName" value=${user.lastName}></td>
						</tr>
						<tr>
							<td>Status (open/no) =</td>
							<td>${userForUpdate.status}</td>
						</tr>
						<tr>
							<td>Ballance =</td>
							<td>${user.money}</td>
						</tr>
						<tr>
							<td>Role =</td>
							<td>${userForUpdate.roleId}</td>
						</tr>
						<input type="submit" value="Update">
					</form>

				</table> <%-- CONTENT --%>


			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>