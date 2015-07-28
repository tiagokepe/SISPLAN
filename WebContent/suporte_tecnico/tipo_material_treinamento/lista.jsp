<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
<%@include file="/portal/menu_administracao.jsp" %>
	<admin:caminho titulo="Lista dos Tipos de Materiais de Treinamento" />
	
	<h:form prependId="false">
		<div class="infoAltRem" style="width: 100%"><h:graphicImage
			value="/img/geral/adicionar.gif" style="overflow: visible;" /> <h:commandLink
			action="#{tipoMaterialMBean.iniciar}" value="Cadastrar novo" />
			<h:graphicImage value="/img/geral/remover.gif" style="overflow: visible;" />: Remover
			
		</div>

	<br />
	
		<table class="listagem" style="width: 50%">
			<caption>Tipos de Materiais de Treinamento Cadastrados </caption>
			<thead>
				<tr>
					<td>Tipo de Material</td>
					<td></td>
					<td></td>
				</tr>
			</thead>

			<c:forEach items="#{tipoMaterialMBean.allOrdenado}" var="item" varStatus="status">
				<tr class="${status.index % 2 == 0 ? "linhaPar" : "linhaImpar" }">
					<td>${item.denominacao}</td>
					<td width=25>
						<h:commandLink id="alterar" title="Atualizar" action="#{tipoMaterialMBean.atualizar}">
						<f:param name="id" value="#{item.id}"/>
						<h:graphicImage url="/img/geral/alterar.gif"/>
						</h:commandLink>
					</td>
					<td width=25>
						<h:commandLink id="remover" title="Remover" action="#{tipoMaterialMBean.remover}" onclick="#{confirmDelete}">
							<f:param name="id" value="#{item.id}"/>
							<h:graphicImage url="/img/geral/remover.gif"/>
						</h:commandLink>
					</td>
				</tr>
			</c:forEach>
		</table>
	</h:form>
	<br />
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>