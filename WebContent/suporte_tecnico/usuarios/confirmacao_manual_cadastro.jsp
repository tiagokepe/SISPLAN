<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
<%@include file="/portal/menu_administracao.jsp" %>

<admin:caminho titulo="Confirma��o de cadastro de usu�rios"/>

<h:form prependId="false">

<c:if test="${ not empty autoCadastroUsuarioMBean.usuariosNaoConfirmados.wrappedData }">

<div class="descricaoOperacao">
<p>A listagem abaixo mostra todos os usu�rios cuja confirma��o de cadastro ainda n�o foi realizada. Selecione os usu�rios
que deseja confirmar o cadastro e clique no bot�o <strong>Confirmar</strong>.</p>
</div>

<h:dataTable styleClass="listagem" var="usr" value="#{ autoCadastroUsuarioMBean.usuariosNaoConfirmados }" footerClass="rodapeTabela" rowClasses="linhaPar,linhaImpar">
	<f:facet name="caption">
	<h:outputText value="Usu�rios com confirma��o de cadastro n�o realizada"/>
	</f:facet>
	
	<h:column>
		<f:facet name="header">
			<f:verbatim>
				<input type="checkbox" id="chkMarcarTodos" onclick="$$('.chkAutorizado').each(function(item) { item.checked = $('chkMarcarTodos').checked; })"/>
			</f:verbatim>
		</f:facet>
		<h:selectBooleanCheckbox styleClass="chkAutorizado" value="#{ usr.autorizado }"/>
	</h:column>
	
	<h:column>
		<f:facet name="header">
			<h:outputText value="Nome"/>
		</f:facet>
		<h:outputText value="#{ usr.pessoa.nome }"/>
	</h:column>
	
	<h:column>
		<f:facet name="header">
			<h:outputText value="Login"/>
		</f:facet>
		<h:outputText value="#{ usr.login }"/>
	</h:column>
	
	<h:column>
		<f:facet name="header">
			<h:outputText value="E-mail"/>
		</f:facet>
		<h:outputText value="#{ usr.email }"/>
	</h:column>
	
	<h:column>
		<f:facet name="header">
			<h:outputText value="Unidade"/>
		</f:facet>
		<h:outputText value="#{ usr.unidade.codigoNome }"/>
	</h:column>
	
	<h:column>
		<f:facet name="header">
			<h:outputText value="Data"/>
		</f:facet>
		<h:outputText value="#{ usr.dataCadastro }">
			<f:convertDateTime pattern="dd/MM/yyyy"/>
		</h:outputText>
	</h:column>
	
	<f:facet name="footer">
		<h:column>
			<div style="text-align: center;">
				<h:commandButton value="Confirmar" action="#{ autoCadastroUsuarioMBean.confirmarCadastroManual }"/>
				<h:commandButton value="Cancelar" action="#{ autoCadastroUsuarioMBean.cancelar }" onclick="#{confirm}" immediate="true"/>
			</div>
		</h:column>
	</f:facet>
	
</h:dataTable>
<br/>
</c:if>
<c:if test="${ empty autoCadastroUsuarioMBean.usuariosNaoConfirmados.wrappedData }">
	<p style="text-align: center; color: red">N�o foram encontrados usu�rios com confirma��o de cadastro pendente.</p>
</c:if>




</h:form>

</f:view>

<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>