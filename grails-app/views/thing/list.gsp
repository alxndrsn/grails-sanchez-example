
<%@ page import="sanchez.example.Thing" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'thing.label', default: 'Thing')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-thing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-thing" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'thing.name.label', default: 'Name')}" />
					</tr>
				</thead>
				<tbody>
					<g:each in="${thingInstanceList}" status="i" var="thingInstance">
i=${i}:${thingInstance}
						<g:render template="thing" model="[name:thingInstance.name]"/>
					</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${thingInstanceTotal}" />
			</div>
		</div>
		<sanchez:render id="thing" runtimeVars="name"/>
		<a onclick="dynamicThingAdd()">Dynamic Thing Adder</a>

		<g:javascript>
			function dynamicThingAdd() {
				sanchez.append("tbody", "thing", {name:prompt("Name the new thing:")});
			}
		</g:javascript>
	</body>
</html>
