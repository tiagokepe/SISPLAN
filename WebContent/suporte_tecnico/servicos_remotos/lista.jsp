<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<f:view>

<%@include file="/portal/menu_administracao.jsp"%>
<admin:caminho titulo="Listagem de Serviços Remotos" />

<div class="descricaoOperacao">
	<p><a name="topo">
	Esta página contém informações sobre todos os serviços remotos implementados nos sistemas, tanto os que são criados pelos sistemas (exportados) quanto os que são importados pelo sistema.
	</a></p>
	<br/>
	Ir aos serviços do: 
		<c:forEach var="s" items="${ servicosRemotosMBean.lista }" varStatus="loop">
			<a href="#serv_${ s.key }">${ s.key }</a><c:if test="${ !loop.last }">, </c:if>
		</c:forEach> 
	</div>

<c:forEach var="s" items="${ servicosRemotosMBean.lista }">

<table class="listagem" width="100%">
<caption><a name="serv_${ s.key }">Serviços no ${ s.key }</a></caption>
	<tr>
	<td>
		<table class="subFormulario" width="100%">
			<caption>Serviços Exportados</caption>
				<thead>
					<tr>
						<th>Serviço</th>
						<th>Interface</th>
						<th>Implementação</th>
					</tr>
				</thead>
				
			<c:forEach var="item" items="${ s.value[0] }" varStatus="loop">
				<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
					<td>${ item.nomeBean }</td>
					<td>${ item.nomeInterface }</td>
					<td>${ item.nomeImpl }</td>
				</tr>
			</c:forEach>
		</table>
		
	</td>
	</tr>
	<tr>
	<td>
		<table class="subFormulario" width="100%">
		<caption>Serviços Importados</caption>
		<thead>
		<tr><th>Serviço</th><th>Interface</th><th>URL</th></tr>
		</thead>
		<c:forEach var="item" items="${ s.value[1] }" varStatus="loop">
		<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }"><td>${ item.nomeBean }</td><td>${ item.nomeInterface }</td><td>${ item.url}</td></tr>
		</c:forEach>
		</table>
	</td>
	</tr>
</table>
<br/>
<center><a href="#topo">Voltar ao Início</a></center>
<br/> &nbsp;

</c:forEach>

</f:view>

<%@include file="/WEB-INF/jsp/include/rodape.jsp" %>