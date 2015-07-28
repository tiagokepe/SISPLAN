<%@ include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Informações de desempenho de classes" />
	
	<h:form prependId="false">
	
		<table class="formulario">
		<caption>Opções de Busca</caption>
		
		<tr><th>Classe: </th><td> <h:inputText value="#{ profilingMBean.classe }" size="60" id="classe"/> </td></tr>
		<tr><th>Método: </th><td> <h:inputText value="#{ profilingMBean.metodo }" size="60" id="metodo"/> </td></tr>
		<tr><th>Número mínimo de chamadas: </th><td> <h:inputText value="#{ profilingMBean.chamadas }" id="chamadas" size="15"/> </td></tr>
		<tr><th>Tempo de execução mínimo (ms): </th><td> <h:inputText value="#{ profilingMBean.tempo }" id="tempo" size="15"/> </td></tr>
		<tr><th valign="top">Ordenar por: </th><td>
			<h:selectOneRadio value="#{profilingMBean.ordenacao}" layout="pageDirection" id="ordenacao">
				<f:selectItem itemLabel="Nome da classe" itemValue="classe"/>
				<f:selectItem itemLabel="Nome do método" itemValue="metodo"/>
				<f:selectItem itemLabel="Número de Chamadas" itemValue="count_call"/>
				<f:selectItem itemLabel="Tempo Máximo" itemValue="max_time"/>
				<f:selectItem itemLabel="Tempo Médio" itemValue="mean_time"/>
			</h:selectOneRadio>
		</td></tr>
		
		<tfoot>
		<tr><td colspan="2">
			<h:commandButton value="Buscar" action="#{profilingMBean.buscar}" id="busca"/>
			<h:commandButton value="Cancelar" action="#{profilingMBean.cancelar}" onclick="#{confirm}" immediate="true" id="cancelar"/>
		</td></tr>
		</tfoot>
		</table>
	
		<br/>
	
		<table class="listagem">
		<caption>Informações Encontradas</caption>
		<thead>
		<tr><th>Classe</th><th>Método</th><th>Número de chamadas</th><th>Tempo Máximo (ms)</th><th>Tempo Médio (ms)</th><th>Última atualização</th></tr>
		</thead>
		
		<c:forEach var="item" items="${ profilingMBean.result }" varStatus="loop">
		<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }"><td>${ item['classe'] }</td><td>${ item['metodo'] }</td><td>${ item['count_call'] }</td>
		<td>${ item['max_time'] }</td><td>${ item['mean_time'] }</td><td>
		<fmt:formatDate value="${ item['ultima_atualizacao'] }" pattern="dd/MM/yyyy HH:mm:ss"/></td></tr>		
		</c:forEach>
		
		</table>
	
	</h:form>
	
</f:view>

<%@ include file="/WEB-INF/jsp/include/rodape.jsp"%>