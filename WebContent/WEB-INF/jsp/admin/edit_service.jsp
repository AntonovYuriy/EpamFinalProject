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
				
				<c:if test="${editHasBeenDone =='true'}" >
					Service has been successfully Edited
					<a name="editHasBeenDone" value="false"></a>
					<form id="BackToAllServices" action="controller">
						<input type="hidden" name="command" value="Services" /> 
						<input type="submit" value="All services">
					</form>
				</c:if> <c:if test="${editHasBeenDone != 'true'}">
					<table>
						<thead>
								<td>ID</td>
								<td>Name</td>
								<td>ST ID</td>
								<td>Info</td>
								<td>Price</td>
							</tr>
						</thead>
						<tr>
								<form id="editervice" action="controller" method="post">
									<input type="hidden" name="command" value="EditService" /> 
									<td> <input type="hidden" name="sId" value="${sId}" />${sId}</td>
									<td> <input type="hidden" name="sName" value="${sName}" />${sName}</td>
									<td> <input type="hidden" name="stId" value="${stId}" />${stId}</td>
									<td> <input type="text" name="sInfo" value="${sInfo}" />${sInfo} </td>
									<td> <input type="text" name="sPrice" value="${sPrice}" /> ${sPrice}</td>
									<input type="hidden" name="editServiceConfirm" value='true' />
									<input type="submit" value="Edit" />
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