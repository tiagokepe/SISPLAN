<html xmlns="http://www.w3.org/1999/xhtml" lang="pt-br" xml:lang="pt-br">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%-- <%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" /> --%>
<%-- <%@page contentType="text/html; charset=UTF-8" %> --%>
<f:view>
    <h:form id="id_form">
        <rich:panel style="text-align: center" header="Cadastrar Nova Estratégia">
            <rich:panel id="id_pick_list_panel" header="Selecione 1 ou mais Objetivos Específicos">
	            <rich:pickList sourceListWidth="400px" targetListWidth="400px" id="id_pick_list" value="#{novaEstrategiaBean.selectedObjEsp}"
	                           listsHeight="300px">
	                <f:selectItems id="id_items" value="#{novaEstrategiaBean.availableObjEsp}"/>
	            </rich:pickList>
            </rich:panel>
            <br/>
            <br/>
            <h:panelGrid columns="2" >
                <h:outputLabel value=""/>
                <h:outputLabel value=""/>                
                <h:outputText style="color:blue" value="Unidade Responsável: "/>
                <rich:comboBox rendered="#{sisplanUser.planningManager}"
                               width="200px" value="#{novaEstrategiaBean.unidadeName}"
                               valueChangeListener="#{novaEstrategiaBean.unidadeSelectedListener}">
                   <f:selectItems value="#{novaEstrategiaBean.listUnidades}"/>
                   <a4j:support event="onchange" reRender="id_pick_list_panel"/>
                </rich:comboBox>
                <h:outputText style="color:blue" value="#{novaEstrategiaBean.unidadeName}" rendered="#{!sisplanUser.planningManager}"/>
                            
                <h:outputText style="color:blue" value="Descrição da Estratégia: "/>
                <h:inputTextarea id="id_desc" style="width:700px" value="#{novaEstrategiaBean.descricao}"/>

            </h:panelGrid>
            <br/>
            <h:commandButton action="#{novaEstrategiaBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
            <h:commandButton action="#{novaEstrategiaBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
        </rich:panel>
        <br/>
<%--         <div style="text-align: center">
        <h:commandButton action="#{novaEstrategiaBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
        <h:commandButton action="#{novaEstrategiaBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
        </div> --%>
    </h:form>

<%--     </a4j:outputPanel> --%>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
</html>