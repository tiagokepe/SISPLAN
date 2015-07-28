<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>

	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Configura��es de Timers" />

	<h:form>
		<c:set var="tarefa" value="#{ tarefaTimerMBean.tarefa }" scope="page"/>
		
		<table class="listagem" style="width: 90%">
			<tr>
				<td width="20%"> <strong>Tarefa:</strong> </td>
				<td colspan="5"> ${tarefa['classe']} (${ tarefa['ativa'] ? 'ATIVA' : 'INATIVA' }) </td>
			</tr>
			<tr>
				<td width="20%"> <strong>Tipo de Repeti��o:</strong> </td>
				<td width="20%"> 
					<h:outputText value="Di�ria" rendered="#{ tarefa['tipo_repeticao'] == 'D' }"/>
					<h:outputText value="Mensal" rendered="#{ tarefa['tipo_repeticao'] == 'M' }"/>
					<h:outputText value="Semanal" rendered="#{ tarefa['tipo_repeticao'] == 'S' }"/>
					<h:outputText value="Tempo" rendered="#{ tarefa['tipo_repeticao'] == 'T' }"/>
				</td>
				<td> 
					<strong>
						<c:choose>
						<c:when test="${ tarefa['tipo_repeticao'] == 'D' }">Hora de Execu��o:</c:when>
						<c:when test="${ tarefa['tipo_repeticao'] == 'M' }">Dias de Execu��o:</c:when>
						<c:when test="${ tarefa['tipo_repeticao'] == 'S' }">Dias de Execu��o:</c:when>
						<c:when test="${ tarefa['tipo_repeticao'] == 'T' }">Tempo:</c:when>
						</c:choose>
					</strong>  
				</td>
				<td>
					<c:choose>
					<c:when test="${ tarefa['tipo_repeticao'] == 'D' }">${ tarefa['hora_execucao'] }h</c:when>
					<c:when test="${ tarefa['tipo_repeticao'] == 'M' }">dia ${ tarefa['dia_mes_execucao'] } �s ${ tarefa['hora_execucao'] }h</c:when>
					<c:when test="${ tarefa['tipo_repeticao'] == 'S' }">
					<c:choose>
					<c:when test="${ tarefa['dia_execucao'] == 1 }">Domingos</c:when>
					<c:when test="${ tarefa['dia_execucao'] == 2 }">Segundas-feiras</c:when>
					<c:when test="${ tarefa['dia_execucao'] == 3 }">Ter�as-feiras</c:when>
					<c:when test="${ tarefa['dia_execucao'] == 4 }">Quartas-feiras</c:when>
					<c:when test="${ tarefa['dia_execucao'] == 5 }">Quintas-feiras</c:when>
					<c:when test="${ tarefa['dia_execucao'] == 6 }">Sextas-feiras</c:when>
					<c:when test="${ tarefa['dia_execucao'] == 7 }">S�bados</c:when>
					</c:choose>
					�s ${ tarefa['hora_execucao'] }h</c:when>
					<c:when test="${ tarefa['tipo_repeticao'] == 'T' }">a cada ${ tarefa['tempo'] } min</c:when>
					</c:choose>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td> <strong>�ltima Execu��o:</strong>  </td>
				<td><fmt:formatDate value="${ tarefa['ultima_execucao'] }" pattern="dd/MM/yyyy HH:mm:ss"/></td>
				<td> <strong>Servidor Restri��o:</strong>  </td>
				<td> ${ tarefa['servidor_restricao'] } </td>
				<td> <strong>Servidor Execu��o:</strong>  </td>
				<td>${ tarefa['servidor_execucao'] }</td>
			</tr>
		</table>
		
		<br/>

		<c:if test="${ not empty tarefaTimerMBean.execucoes }">
		<div class="infoAltRem" style="width: 100%">
		<img src="/shared/img/lupa.gif" style="overflow: visible;"/> Ver Logs 
		</div>
		<table class="listagem">
			<caption>Execu��es encontradas</caption>
			<thead>
				<tr>
					<th>In�cio da Execu��o</th>
					<th>Fim da Execu��o</th>
					<th>Servidor</th>
					<th style="text-align: center;">Status</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="ex" items="#{ tarefaTimerMBean.execucoes }" varStatus="loop">
			<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
				<td><fmt:formatDate value="${ ex['inicio_execucao'] }" pattern="dd/MM/yyyy HH:mm:ss"/></td>
				<td><fmt:formatDate value="${ ex['fim_execucao'] }" pattern="dd/MM/yyyy HH:mm:ss"/></td>
				<td>${ ex['servidor_execucao'] }</td>
				<td style="text-align: center; color: ${ ex['sucesso'] ? 'green' : 'red' }">
				${ ex['sucesso'] ? 'Sucesso' : 'Falha' }
				</td>
				<td>
					<h:commandLink action="#{ tarefaTimerMBean.logs }">
						<f:verbatim><img src="/shared/img/lupa.gif" alt="Ver Execu��es" title="Ver Logs"/></f:verbatim>
						<f:param name="id" value="#{ ex['id'] }" />
					</h:commandLink>
				</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		</c:if>
		<c:if test="${ empty tarefaTimerMBean.execucoes }">
		<center><font color="red">Nenhuma execu��o encontrada para a tarefa selecionada</font></center>
		</c:if>

		<br/>
		<center><h:commandLink value="Voltar" action="#{tarefaTimerMBean.cancelar }"/></center>

	</h:form>

</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
