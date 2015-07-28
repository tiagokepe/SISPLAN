<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>

	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Configurações de Timers" />
	
	<c:if test="${not empty tarefaTimerMBean.logs}">
	<table class="listagem">
	<caption>Logs encontrados</caption>
	<thead>
	<tr>
		<th>Data/Hora</th>
		<th>Texto</th>
		<th>Nível</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="log" items="#{tarefaTimerMBean.logs}" varStatus="loop">
	<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
		<td><fmt:formatDate value="${ log['data_log'] }" pattern="dd/MM/yyyy HH:mm:ss"/></td>
		<td>${ log['texto_log'] }</td>
		<td>
		<c:choose>
		<c:when test="${ log['nivel_log'] == 'D' }">DEBUG</c:when>
		<c:when test="${ log['nivel_log'] == 'I' }">INFO</c:when>
		<c:when test="${ log['nivel_log'] == 'W' }">WARN</c:when>
		<c:when test="${ log['nivel_log'] == 'E' }">ERROR</c:when>
		</c:choose>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	</c:if>
	<c:if test="${empty tarefaTimerMBean.logs}">
	<center><font color="red">Nenhum log encontrado para a execução selecionada</font></center>
	</c:if>		

	<br/>

	<h:form>	
		<center>	
		<h:commandLink value="Voltar" action="#{ tarefaTimerMBean.execucoes }">
			<f:param name="id" value="#{ tarefaTimerMBean.id  }" />
		</h:commandLink>
		</center>
	</h:form>

</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
