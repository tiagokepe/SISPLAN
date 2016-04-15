<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />

<f:view>
    <h:form id="id_main_form">
        <h:panelGroup>
            <h2>Diretrizes pendentes por unidade</h2>
        </h:panelGroup>
        <h:panelGrid columns="2" columnClasses="columnTop, columnTop" width="100%">
	        <rich:tree id="id_tree" var="node" ajaxSubmitSelection="true" switchType="ajax"
		               value="#{pendenciesControllerBean.pendenciasTree}">
		               
		        <rich:treeNode>
		            <h:outputText value="#{node.name}" />
		        </rich:treeNode>
		        
		    </rich:tree>
<%--             <h:panelGrid columns="2">
                <h:outputText value="Diretriz 1: "/>
                <h:outputText value="BlaBla..."/>
            </h:panelGrid> --%>		    

	    </h:panelGrid>
    
    </h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>