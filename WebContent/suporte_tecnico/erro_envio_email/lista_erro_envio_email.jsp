<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<style type="text/css">
.esquerda {
	text-align: left;
}

.direita {
	text-align: right;
}

.centro {
	text-align: center;
}
</style>

<f:view>

	<f:subview id="menu">
		<%@include file="/portal/menu_administracao.jsp" %>
	</f:subview>
	<h:form id="form">

		<admin:caminho titulo="Consultar E-mails com erro de envio" />

		<table class="formulario" align="center" width="400">
			<caption class="listagem">Consulta de E-mails com erro de envio</caption>
			<tr>
				<td width="4%"><h:selectBooleanCheckbox value="#{erroEnvioEmailMBean.buscaDestinatario}" id="selectDestinatario" styleClass="noborder"/></td>
				<th style="text-align: left;" width="15%">Destinatário(s)</th>
				<td><h:inputText
					value="#{erroEnvioEmailMBean.objFiltro.paraNome}" id="inputDest" onclick="$('form:selectDestinatario').checked = true" /></td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{erroEnvioEmailMBean.buscaEmail}" id="selectEmail" styleClass="noborder"/></td>
				<th nowrap style="text-align: left;">Email do Destinatário:</th>
				<td><h:inputText
					value="#{erroEnvioEmailMBean.objFiltro.paraEmail}" id="inputEmail" onclick="$('form:selectEmail').checked = true" /></td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{erroEnvioEmailMBean.buscaAssunto}" id="selectAssunto" styleClass="noborder"/></td>
				<th style="text-align: left;">Assunto:</th>
				<td><h:inputText
					value="#{erroEnvioEmailMBean.objFiltro.assunto}" id="inputAssunto" onclick="$('form:selectAssunto').checked = true" /></td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{erroEnvioEmailMBean.buscaDataInicio}" id="selectDataInicio" styleClass="noborder"/></td>
				<th style="text-align: left;">Início:</th>
				<td><t:inputCalendar
					value="#{erroEnvioEmailMBean.objFiltro.dataInicio}"
					popupDateFormat="dd/MM/yyyy" size="10" maxlength="10"
					renderAsPopup="true" renderPopupButtonAsImage="true"
					id="dataInicio" title="Data de Início"
					onkeypress="return(formatarMascara(this,event,'##/##/####'))" onchange="$('form:selectDataInicio').checked = true" /></td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{erroEnvioEmailMBean.buscaDataFim}"  id="selectDataFim" styleClass="noborder"/></td>
				<th style="text-align: left;">Fim:</th>
				<td><t:inputCalendar
					value="#{erroEnvioEmailMBean.objFiltro.dataFim}"
					popupDateFormat="dd/MM/yyyy" size="10" maxlength="10"
					renderAsPopup="true" renderPopupButtonAsImage="true" id="dataFim" title="Data de Fim"
					onkeypress="return(formatarMascara(this,event,'##/##/####'))" onchange="$('form:selectDataFim').checked = true" /></td>
			</tr>
			<tfoot>
				<tr>
					<td colspan="3"><h:commandButton value="Listar" id="listar"
						action="#{ erroEnvioEmailMBean.listar }" /><h:commandButton id="cancelar"
						value="Cancelar" action="#{ erroEnvioEmailMBean.cancelar }"
						onclick="" /></td>
				</tr>
			</tfoot>
		</table>
		<br />
		<br />
		<t:htmlTag value="div" styleClass="infoAltRem" rendered="#{erroEnvioEmailMBean.colecao.rowCount > 0}">
			<h:graphicImage value="/img/geral/lupa.gif" />: Visualizar E-mail
		</t:htmlTag>
		<h:dataTable width="100%" value="#{erroEnvioEmailMBean.colecao}"
			var="item" id="TableEmails" styleClass="listagem"
			rowClasses="linhaImpar, linhaPar" rendered="#{erroEnvioEmailMBean.colecao.rowCount > 0}">
			<f:facet name="caption">
				<h:outputText value="Lista de E-mails Enviados" />
			</f:facet>
			<t:column>
				<f:facet name="header">
					<f:verbatim>
						<div style="text-align: center;">Data de Envio</div>
					</f:verbatim>
				</f:facet>
				<h:outputText
					style="text-align: center; display:block; text-transform: uppercase;"
					value="#{item.enviadoEm}">
					<!-- Customização do SIGREF -->
				    <f:convertDateTime pattern="dd/MM/yyyy hh:mm:ss" />
				    <!-- FIM Customização do SIGREF -->
				</h:outputText>	
			</t:column>
			<t:column style="text-align: right;">
				<f:facet name="header">
					<f:verbatim>
						<div style="text-align: left;">Destinatário</div>
					</f:verbatim>
				</f:facet>
				<h:outputText style="text-align: left; display:block;"
					value="#{item.paraNome}" />
			</t:column>
			<t:column style="text-align: right;">
				<f:facet name="header">
					<f:verbatim>
						<div style="text-align: left;">E-mail</div>
					</f:verbatim>
				</f:facet>
				<h:outputText style="text-align: left; display:block;"
					value="#{item.paraEmail}" />
			</t:column>
			<t:column style="text-align: right;">
				<f:facet name="header">
					<f:verbatim>
						<div style="text-align: left;">Assunto</div>
					</f:verbatim>
				</f:facet>
				<h:outputText style="text-align: left; display:block;"
					value="#{item.assunto}" />
			</t:column>
			<t:column>
				<h:commandLink title="Visualizar E-mail" action="#{erroEnvioEmailMBean.verErroEnvioEmail}" id="cmdlinkVerEmail">
					<h:graphicImage url="/img/geral/lupa.gif"/>
					<f:param name="idEmail" value="#{item.id}" />
				</h:commandLink>
			</t:column>
	</h:dataTable>
	
		<c:if test="${erroEnvioEmailMBean.colecao.rowCount > 0}">
			<br/>
			<div style="text-align: center;"> 
				
				<h:commandLink actionListener="#{paginacao.previousPage}" rendered="#{paginacao.paginaAtual > 0 }" id="btnPreviousPage">
					<f:verbatim><img src="/shared/img/voltar.gif" alt="Página Anterior" title="Página Anterior" style="vertical-align: middle;"/></f:verbatim>
				</h:commandLink>
			
				<h:selectOneMenu value="#{paginacao.paginaAtual}" valueChangeListener="#{paginacao.changePage}" onchange="submit()" immediate="true" id="selectChangePage">
					<f:selectItems id="paramPagina" value="#{paginacao.listaPaginas}"/>
				</h:selectOneMenu>
			
				<h:commandLink actionListener="#{paginacao.nextPage}" rendered="#{paginacao.paginaAtual < (paginacao.totalPaginas - 1)}" id="btnNextPage">
					<f:verbatim><img src="/shared/img/avancar.gif" alt="Próxima Página" title="Próxima Página" style="vertical-align: middle;"/></f:verbatim>
				</h:commandLink>
				<br/><br/>
			
				<em><h:outputText value="#{paginacao.totalRegistros }"/> Registro(s) Encontrado(s)</em>
			</div>
		</c:if>
		
	</h:form>

</f:view>

<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>