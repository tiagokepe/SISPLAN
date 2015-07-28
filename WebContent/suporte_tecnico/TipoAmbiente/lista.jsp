<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Lista de Tipos de Ambiente"/>
	<div class="infoAltRem" style="width: 100%"><img
		src="/shared/img/adicionar.gif" style="overflow: visible;" />: <a
		href="${ctx}/suporte_tecnico/TipoAmbiente/form.jsf">Cadastrar Novo
	Tipo de Ambiente</a> <img src="/shared/img/alterar.gif"
		style="overflow: visible;" />: Alterar <img
		src="/shared/img/delete.gif" style="overflow: visible;" />: Remover</div>
	<h:form prependId="false">
		<c:if test="${ not empty tipoAmbienteMBean.all }">
			<table class="listagem">
				<caption>Lista de Tipos de Ambiente</caption>
				<thead>
					<tr>
						<th>Nome</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<c:forEach items="#{tipoAmbienteMBean.all}" var="item"
					varStatus="loop">
					<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>${item.nome}</td>
						<td><h:commandLink
							action="#{ tipoAmbienteMBean.atualizar }" id="atualizar">
							<f:verbatim>
								<img src="/shared/img/alterar.gif" alt="Alterar" title="Alterar" />
							</f:verbatim>
							<f:param name="id" value="#{ item.id }" />
						</h:commandLink></td>
						<td><h:commandLink
							action="#{ tipoAmbienteMBean.inativar }"
							onclick="#{confirmDelete}" id="remover">
							<f:verbatim>
								<img src="/shared/img/delete.gif" alt="Remover" title="Remover" />
							</f:verbatim>
							<f:param name="id" value="#{ item.id }" />
						</h:commandLink></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
