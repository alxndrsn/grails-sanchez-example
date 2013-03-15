<%@ page import="sanchez.example.Thing" %>



<div class="fieldcontain ${hasErrors(bean: thingInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="thing.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${thingInstance?.name}"/>
</div>

