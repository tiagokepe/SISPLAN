<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />

<f:view>
    <h:form id="id_main_form">
        <h:panelGroup>
            <h2>Objetivos Estratégicos pendentes por Unidade</h2>
        </h:panelGroup>
        <h:panelGrid columns="2" columnClasses="columnTop, columnTop" width="100%">
            <rich:tree id="id_tree" var="node" ajaxSubmitSelection="true" switchType="ajax"
                       value="#{objetivoEstrategicoPendenciesControllerBean.pendenciasTree}">
                       
                <rich:treeNode>
                    <h:outputText value="#{node.name}" />
                </rich:treeNode>
                
            </rich:tree>
        </h:panelGrid>
    
    </h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>