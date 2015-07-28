<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Tipos de Ambiente"/>

	<h:form prependId="false">
		<table class="formulario">
			<caption>Cadastro de Tipos de Ambiente</caption>
			<tr>
				<th>Nome: <span class="required">&nbsp;</span></th>
				<td><h:inputText id="nomeAmbiente" value="#{ tipoAmbienteMBean.obj.nome }"
					readonly="#{tipoAmbienteMBean.readOnly}" size="70"
					disabled="#{tipoAmbienteMBean.readOnly}" /></td>
			</tr>
			<tfoot>
				<tr>
					<td colspan="2">
						<h:inputHidden id="ativo" value="#{tipoAmbienteMBean.obj.ativo}"/>
						<h:inputHidden id="idAmbiente" value="#{tipoAmbienteMBean.obj.id}" /> 
						<h:commandButton value="#{tipoAmbienteMBean.confirmButton}" action="#{tipoAmbienteMBean.cadastrar}" id="cadastrar" /> 
						<c:if test="${tipoAmbienteMBean.obj.id > 0}">
							<h:commandButton value="<< Voltar" action="#{tipoAmbienteMBean.listar}" immediate="true" />
						</c:if> 
						<h:commandButton value="Cancelar" action="#{tipoAmbienteMBean.cancelar}" onclick="#{confirm}" immediate="true" id="cancelar" /></td>
				</tr>
			</tfoot>
		</table>
		<%@include file="/WEB-INF/jsp/include/mensagem_obrigatoriedade.jsp"%>
	</h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
