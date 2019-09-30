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
				<%-- CONTENT --%> <c:if
					test="${not empty requestScope.newUpdatedUser}">
					We have succesfully created new user:
					<table>
						<thead>
							<tr>
								<td>Id</td>
								<td>Login</td>
								<td>First name</td>
								<td>Last name</td>
								<td>Password</td>
								<td>Status</td>
								<td>Money</td>
								<td>Locale</td>
								<td>Role</td>
							</tr>
						</thead>

						<br />

						<tr>
							<td>${requestScope.newUpdatedUser.id}</td>
							<td>${requestScope.newUpdatedUser.login}</td>
							<td>${requestScope.newUpdatedUser.firstName}</td>
							<td>${requestScope.newUpdatedUser.lastName}</td>
							<td>${requestScope.newUpdatedUser.password}</td>
							<td>${requestScope.newUpdatedUser.status}</td>
							<td>${requestScope.newUpdatedUser.money}</td>
							<td>${requestScope.newUpdatedUser.locale}</td>
							<td>${requestScope.newUpdatedUser.roleId}</td>
						</tr>
					</table>
					
					
				</c:if> <c:if test="${empty requestScope.newUpdatedUser}">
					Creating new user
				<table>
						<form id="createNewUser" action="controller" method="post">
							<input type="hidden" name="command" value="createNewUser" />
							<tr>
								<td>Login =</td>
								<td><input name="tempUserLogin"></td>
							</tr>
							<tr>
								<td>Password =</td>
								<td><input name="tempUserPassword"></td>
							</tr>
							<tr>
								<td>First name =</td>
								<td><input name="tempUserFirstName"></td>
							</tr>
							<tr>
								<td>Last name =</td>
								<td><input name="tempUserLastName"></td>
							</tr>
							<tr>
								<td>Status (open yes/no)</td>
								<td><select name="tempUserStatus" default="en">
										<option value="true">yes</option>
										<option value="false">no</option>
								</select>
							</tr>
							<tr>
								<td>Ballance =</td>
								<td><input name="tempUserMoney"></td>
							</tr>
							<tr>
								<td>Locale =</td>
								<td><select name="tempUserLocale" default="en">
										<option value="en">English</option>
										<option value="ru">Russian</option>
								</select></td>
							</tr>
							<tr>
								<td>Role =</td>
								<td><select name="tempUserRole" default="en">
										<option value="0">Admin</option>
										<option value="1">User</option>
								</select></td>
							</tr>
							<input type="submit" value="Create User">
						</form>

					</table>

				</c:if> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>