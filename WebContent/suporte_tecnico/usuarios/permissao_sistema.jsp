<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>

	<admin:caminho titulo="Permiss�o de Acesso dos Usu�rios aos Sistemas" />

	<h:form id="form">

		<table class="formulario" width="400">
			<caption>Cadastro de Permiss�es</caption>
			<tr>
				<th>Sistema:</th>
				<td><h:selectOneMenu id="sistema" style="width: 300px" value="#{usuarioPermissaoSistemaMBean.sistemaSelecionado}">
					<f:selectItem itemLabel="TODOS" itemValue="0"/>
					<f:selectItems value="#{sistemaBean.allCombo}" />
				</h:selectOneMenu></td>
			</tr>
			<tr>
				<th>Permiss�o:</th>
				<td><h:selectOneMenu id="permissao" style="width: 300px" value="#{usuarioPermissaoSistemaMBean.permissao}">
					<f:selectItem itemLabel="GRANT" itemValue="G"/>
					<f:selectItem itemLabel="DENY" itemValue="D"/>
				</h:selectOneMenu></td>
			</tr>
			<tfoot>
				<tr><td colspan="2">
					<h:commandButton value="Cadastrar" action="#{usuarioPermissaoSistemaMBean.cadastrar}"/>
					<h:commandButton value="Cancelar" action="#{usuarioPermissaoSistemaMBean.cancelar}" onclick="#{confirm}" id="cancelar"/>
				</td></tr>
			</tfoot>
		</table>

<br />

<c:if test="${not empty usuarioPermissaoSistemaMBean.permissoes}">
		<div class="infoAltRem" style="width: 100%">
		    <img src="/shared/img/delete.gif" style="overflow: visible;"/>: Remover
		</div>
		<table class="listagem">
			<caption>Permiss�es cadastradas</caption>
			<thead>
			<tr>
				<th>Sistema</th>
				<th>Permiss�o</th>
				<th></th>
			</tr>
			</thead>
			<c:forEach var="p" items="#{usuarioPermissaoSistemaMBean.permissoes}" varStatus="loop">
				<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
					<td>${ p.sistema == null ? 'TODOS' : p.sistema.nome }</td>
					<td>${ p.permissao == 'G' ? 'GRANT' : 'DENY'}</td>
					<td width="30">
						<h:commandLink title="Remover" action="#{usuarioPermissaoSistemaMBean.remover}" onclick="#{confirmDelete}">
							<f:param name="id" value="#{p.id}"/>
							<h:graphicImage url="/img/geral/remover.gif"/>
						</h:commandLink>
					</td>
				</tr>
			</c:forEach>
		</table>
</c:if>
<c:if test="${empty usuarioPermissaoSistemaMBean.permissoes}">
	<center><p><font color="red">N�o h� permiss�es cadastradas para o usu�rio selecionado.</font></p></center>
</c:if>
	</h:form>

</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>