<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <a4j:outputPanel ajaxRendered="true" layout="block" >
        <h:form>
	        <rich:panel id="id_panel_hint" header="#{pdiControllerBean.currentNodeSelection.name}">
		        <h:panelGrid columns="2">
		            <h:outputText style="color:blue" value="Descrição:"/>
		            <h:inputTextarea styleClass="descricao" value="#{pdiControllerBean.currentNodeSelection.descricao}"/>
                </h:panelGrid>
	        </rich:panel>
            <br/>
            <h:commandButton style="horizontal-align:center;" type="submit" value="Salvar" action="#{pdiControllerBean.currentNodeSelection.save}" />
            <h:commandButton style="horizontal-align:center;" type="submit" value="Cancelar" action="#{pdiControllerBean.currentNodeSelection.cancel}"/>
        </h:form>
    </a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>        