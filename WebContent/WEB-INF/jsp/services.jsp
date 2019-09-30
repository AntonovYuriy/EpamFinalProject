<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="ua.nure.antonov.st4.db.DBManager" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="title" value="Menu" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%> SERVICES PAGE 
				<c:if test="${userRole.name == 'admin'}">
						<form id="createNewService" action="controller">
							<input type="hidden" name="command" value="CreateNewService" />
							<input type="submit" value="Create New Service"/>
						</form>
				</c:if>


				<form id="Services" action="controller">

					<table id="Services_table">
						<thead>
							<td><a
								href="controller?command=Services&fullServicesSorting=id">Id</a></td>
							<td><a
								href="controller?command=Services&fullServicesSorting=name">Service
									name</a></td>
							<td><a
								href="controller?command=Services&fullServicesSorting=stId">ST
									id</a></td>
							<td><a
								href="controller?command=Services&fullServicesSorting=info">Information</a></td>
							<td><a
								href="controller?command=Services&fullServicesSorting=price">Price</a></td>
							<td>Times ordered</a></td>
						</thead>

						<c:forEach var="fService" items="${fullServices}">
							<tr>
								<td>${fService.id}</td>
								<td>${fService.sName}</td>
								<td>${fService.stId}</td>
								<td>${fService.stInfo}</td>
								<td>${fService.price}</td>
								<td>
								
								</td>
								<td>
									<form id="PurchaseService" action="controller">
										<input type="hidden" name="command" value="PurchaseService" />
										<input type="hidden" name="fullServiceForPurchaseId" value="${fService.id}"/>
										<input type="submit" value="Order">
									</form>
								</td>

								<c:if test="${userRole.name == 'admin'}">
									<td>
									<form id="EditService" action="controller">
										<input type="hidden" name="command" value="EditService" /> 
										<input type="hidden" name="sId" value="${fService.id}" />
										<input type="hidden" name="stId" value="${fService.stId}" />
										<input type="hidden" name="sName" value="${fService.sName}" />
										<input type="hidden" name="sInfo" value="${fService.stInfo}" />
										<input type="hidden" name="sPrice" value="${fService.price}" />
										<input type="submit" value="Edit">
									</form>
									</td>
									<td>
										<form id="DeleteService" action="controller">
											<input type="hidden" name="command" value="DeleteService" /> 
											<input type="hidden" name="sId" value="${fService.id}" />
											<input type="hidden" name="sName" value="${fService.sName}" />
											<input type="hidden" name="sInfo" value="${fService.stInfo}" />
											<input type="submit" value="Delete">
										</form>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>

				</form> <%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>