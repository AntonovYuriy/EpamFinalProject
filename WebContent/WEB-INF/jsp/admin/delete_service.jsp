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
				
				<c:if test="${deleteHasBeenDone =='true'}" >
					Service has been successfully Deleted
					<a name="deleteHasBeenDone" value="false"></a>
					<form id="BackToAllServices" action="controller">
						<input type="hidden" name="command" value="Services" /> <input
							type="submit" value="All services">
					</form>
				</c:if> <c:if test="${deleteHasBeenDone != 'true'}">
					<table>
						<thead>
							<tr>
								<td>ID</td>
								<td> ${sId}</td>
							</tr>
							<tr>
								<td>Name</td>
								<td> ${sName}</td>
							</tr>
							<tr>
								<td>Info</td>
								<td> ${sInfo}</td>
							</tr>
						</thead>
						<tr>
							<td>
								<form id="deleteService" action="controller" method="post">
									<input type="hidden" name="command" value="DeleteService" /> 
									<input type="hidden" name="sId" value="${sId}" />
									<input type="hidden" name="sName" value="${sName}" />
									<input type="hidden" name="sInfo" value="${sInfo}" />
									<input type="hidden" name="deleteServiceConfirm" value='true' />
									<input type="submit" value="Delete" />
								</form>
							</td>
						</tr>

					</table>
				</c:if> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>