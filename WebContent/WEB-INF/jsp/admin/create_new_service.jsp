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
				<%-- CONTENT --%> <c:if test="${createHasBeenDone =='true'}">
					Service has been successfully Created
					<a name="createHasBeenDone" value="false"></a>
					<form id="BackToAllServices" action="controller">
						<input type="hidden" name="command" value="Services" /> <input
							type="submit" value="All services">
					</form>
				</c:if> <c:if test="${createHasBeenDone != 'true'}">
					<table>
						<thead>
						<tr>
							<td>ID</td>
							<td>Name</td>
							<td>Info</td>
							<td>Price</td>
						</tr>
						</thead>
						<tr>
							<form id="editervice" action="controller" method="post">
								<input type="hidden" name="command" value="CreateNewService" />

								<td>NEW</td>
								<td><select name="stId">
										<c:forEach var="service" items="${allServicesList}">
											<option value="${service.id}">${service.s_name}
										</c:forEach>
								</select></td>
								<td><input type="text" name="sInfo" /></td>
								<td><input type="text" name="sPrice" />

								<input type="submit" value="Edit" />
							</form>
						</tr>

					</table>
				</c:if> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>