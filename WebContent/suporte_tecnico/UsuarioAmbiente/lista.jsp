<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Usuários dos Ambientes"/>
	<div class="infoAltRem" style="width: 100%"><img
		src="/shared/img/adicionar.gif" style="overflow: visible;" />: <a
		href="${ctx}/suporte_tecnico/UsuarioAmbiente/form.jsf">Cadastrar Novo
	Usuário de Ambiente</a> <img src="/shared/img/alterar.gif"
		style="overflow: visible;" />: Alterar <img
		src="/shared/img/delete.gif" style="overflow: visible;" />: Remover</div>
	<h:form prependId="false">
		<c:if test="${ not empty usuarioAmbienteMBean.all }">
			<table class="listagem">
				<caption>Lista de Índices Acadêmicos</caption>
				<thead>
					<tr>
						<th>Login</th>
						<th>Instituição</th>
						<th>Ambiente</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<c:forEach items="#{usuarioAmbienteMBean.all}" var="item"
					varStatus="loop">
					<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>${item.login}</td>
						<td>${item.instituicao}</td>
						<td>${item.ambiente.nome}</td>
						<td><h:commandLink
							action="#{ usuarioAmbienteMBean.atualizar }" id="alterar">
							<f:verbatim>
								<img src="/shared/img/alterar.gif" alt="Alterar" title="Alterar" />
							</f:verbatim>
							<f:param name="id" value="#{ item.id }" />
						</h:commandLink></td>
						<td><h:commandLink
							action="#{ usuarioAmbienteMBean.inativar }"
							onclick="#{confirmDelete}" id="deletar">
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
