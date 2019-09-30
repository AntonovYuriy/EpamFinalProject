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
				<%-- CONTENT --%> USERS PAGE
				<form id="createNewUser" action="controller">
					<input type="hidden" name="command" value="createNewUser" /> <input
						type="submit" value="Create New User">
				</form>

				<form id="feeLTheDBWithRandomData" action="controller">
					<input type="hidden" name="command" value="randomFeelDataBase" />
					<input type="submit" value="Add random Information">
				</form>



				<form id="Users" action="controller">

					<table id="Users_table">
						<thead>
							<tr>
								<td><a href="controller?command=allUsers&userSorting=id">Id</a></td>
								<td><a href="controller?command=allUsers&userSorting=login">Login</a></td>
								<td><a
									href="controller?command=allUsers&userSorting=firstName">First
										Name</a></td>
								<td><a
									href="controller?command=allUsers&userSorting=lastName">Last
										name</a></td>
								<td><a
									href="controller?command=allUsers&userSorting=status">Status</a></td>
								<td><a
									href="controller?command=allUsers&userSorting=ballance">Ballance</a></td>
								<td><a
									href="controller?command=allUsers&userSorting=locale">Locale</a></td>
								<td><a href="controller?command=allUsers&userSorting=role">Role</a></td>
							</tr>
						</thead>
						<br />

						<c:forEach var="tempUser" items="${allUsers}">
							<tr>
								<td>${tempUser.id}</td>
								<td>${tempUser.login}</td>
								<td>${tempUser.firstName}</td>
								<td>${tempUser.lastName}</td>
								<td>${tempUser.status}</td>
								<td>${tempUser.money}</td>
								<td>${tempUser.locale}</td>
								<td>${tempUser.roleId}</td>
								<td>
									<form action="controller" method="post">
										<input type="hidden" name="command" value="allSettings">
										<input type="hidden" name="userForUpdateId"
											value=${tempUser.id}> <input type="submit"
											value="Update" />
									</form>
								</td>
								<td>
									<form action="controller" method="post">
										<input type="hidden" name="command" value="allRecharge">
										<input type="hidden" name="userForRechargeId" value=${tempUser.id}> 
										<input type="submit" value="Recharge" />
									</form>
								</td>
								<td>
									<form action="controller" method="post">
										<input type="hidden" name="command" value="allPasswordChange"/>
										<input type="hidden" name="userForPasswordChangeId" value="${tempUser.id}"/>
										<input type="submit" value="Change Password" />
									</form>
								</td>

							</tr>
						</c:forEach>
					</table>

				</form> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>