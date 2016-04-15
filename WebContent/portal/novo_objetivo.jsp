<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/tld/rich.tld" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="rich" uri="/WEB-INF/tld/rich.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <f:view>
    <h:form>
        <p>This example shows a pickList in the simpler, single column display, with ordering of the result list enabled:</p>
        <rich:panel style="width:560px;">
            <f:facet name="header">
                <h:outputText value="Simple pick list" />
            </f:facet>
            <rich:pickList value="#{listSelectBean.selectedCapitals}">
                <f:selectItems value="#{listSelectBean.capitals}" />
                <f:converter converterId="capitalsConverter" />
            </rich:pickList>
        </rich:panel>
        
        <h:commandButton action="#{listSelectBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
    </h:form>
    </f:view>
</body>
</html> --%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="pt-br" xml:lang="pt-br">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->

<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <f:view>
    <h:form>
        <rich:panel style="text-align: center" header="Cadastrar Novo Objetivo Espec�fico">
            <rich:panel header="Selecione 1 ou mais Objetivos Estrat�gicos Institucionais" >
	            <rich:pickList lang="pt" validator="#{novoObjetivoBean.validate}"
	                           sourceListWidth="400px" targetListWidth="400px" id="id_pick_list" value="#{novoObjetivoBean.selectedObjEst}"
	                           listsHeight="300px">
	                <f:selectItems id="id_items" value="#{novoObjetivoBean.availableObjEst}"/>
<%--   	                <f:converter converterId="listShuttleconverter"/> --%>
	            </rich:pickList>
            </rich:panel>
            <br/>
            <br/>
            <h:panelGrid columns="2" >
                <h:outputText style="color:blue" value="Unidade Respons�vel: "/>
                <rich:comboBox rendered="#{sisplanUser.planningManager}" width="200px" value="#{novoObjetivoBean.unidadeName}" valueChangeListener="#{novoObjetivoBean.unidadeSelectedListener}">
                   <f:selectItems value="#{novoObjetivoBean.listUnidades}"/>
                </rich:comboBox>
                <h:outputText rendered="#{!sisplanUser.planningManager}" style="color:blue" value="#{novoObjetivoBean.unidadeName}" />
                
                <h:outputText style="color:blue" value="Descri��o do Objetivo: "/>
                <h:inputTextarea style="width:700px" value="#{novoObjetivoBean.descricao}"/>

            </h:panelGrid>
            <br/>
            <h:commandButton action="#{novoObjetivoBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
            <h:commandButton action="#{novoObjetivoBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
        </rich:panel>
        <br/>
<%--         <h:commandLink value="P�gina Principal"  action="#{novoObjetivoBean.returnMainPage}"/> --%>      
    </h:form>
    </f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
</html>

<%-- <%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <h:form id="id_form">
    
        <rich:panel style="text-align: center" header="Cadastrar Novo Objetivo Espec�fico">
            <h:outputText style="text-align: right" value="Objetivo(s) Estrat�gico(s): "/>
            <rich:pickList id="id_pick_list2" value="#{novoObjetivoBean.selectedObjEst}">
                <f:selectItems id="id_pick_list" value="#{novoObjetivoBean.availableObjEst}"/>                        
            </rich:pickList>
            <h:panelGrid columns="2" >
	            <h:outputText style="color:blue" value="Unidade Respons�vel: "/>
	            <rich:comboBox width="200px" value="#{novoObjetivoBean.unidadeName}" valueChangeListener="#{novoObjetivoBean.unidadeSelectedListener}">
	               <f:selectItems value="#{novoObjetivoBean.listUnidades}"/>
	            </rich:comboBox>
                
                <h:outputText style="color:blue" value="Descri��o do Objetivo: "/>
                <h:inputText size="70" value="#{novoObjetivoBean.name}"/>

            </h:panelGrid>
            <br/>
            <h:commandButton action="#{novoObjetivoBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
            <h:commandButton action="#{novoObjetivoBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
        </rich:panel>
        <br/>
        <h:commandButton action="#{novoObjetivoBean.save}" style="horizontal-align:center;" type="submit" value="Salvar2222"  />
        <br/>
        <h:commandLink value="P�gina Principal"  action="#{novoObjetivoBean.returnMainPage}"></h:commandLink>
    </h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%> --%>