<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/admin/css/portal_sisplan.css" type="text/css" />
<f:view>
	<a4j:outputPanel ajaxRendered="true" layout="block" >
    	<rich:panel id="id_panel_hint" header="#{pdiControllerBean.currentNodeSelection.name}">
    		<h:form>
	    		<h:panelGrid columns="2">
<%-- 	    		rendered="#{pdiControllerBean.currentNodeSelection.projectNode}" --%>
                                        
                    <h:outputText value="Nome: " rendered="#{pdiControllerBean.currentNodeSelection.projeto}"/>
                    <h:inputText id="id_set_Name" rendered="#{pdiControllerBean.currentNodeSelection.projeto}"
                                 value="#{pdiControllerBean.currentNodeSelection.name}"/>
                    
                    <h:outputText value="Descrição: " />
		        	<h:inputText id="id_set_Descricao" value="#{pdiControllerBean.currentNodeSelection.descricao}"/>
		        	
 					<h:outputText value="Data Início Prevista: "/>
  					<h:panelGroup>
	                    <rich:calendar id="id_set_Data_Inicio_Prevista" disabled="#{pdiControllerBean.currentNodeSelection.disabledDataInicioPrevista}"
	                                   datePattern="dd/MM/yyyy" value="#{pdiControllerBean.currentNodeSelection.dataInicioPrevista}">
	                        <f:validator validatorId="dateValidator"/>
	                    </rich:calendar>
 	                    <h:message for="id_set_Data_Inicio_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
		        	
		        	<h:outputText value="Data Fim Prevista: "/>
                    <h:panelGroup>
                        <rich:calendar id="id_set_Data_Fim_Prevista" disabled="#{pdiControllerBean.currentNodeSelection.disabledDataFimPrevista}"
                                        datePattern="dd/MM/yyyy" value="#{pdiControllerBean.currentNodeSelection.dataFimPrevista}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_set_Data_Fim_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
		        	
		        	<h:outputText value="Data Início Efetiva: "/>
                    <h:panelGroup>
                        <rich:calendar id="id_set_Data_Inicio_Efetiva" disabled="#{pdiControllerBean.currentNodeSelection.disabledDataInicioEfetiva}"
                                        datePattern="dd/MM/yyyy" value="#{pdiControllerBean.currentNodeSelection.dataInicioEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_set_Data_Inicio_Efetiva" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
		        	<h:outputText value="Data Fim Efetiva: "/>
		        	<h:panelGroup>
                        <rich:calendar id="id_set_Data_Fim_Efetiva" disabled="#{pdiControllerBean.currentNodeSelection.disabledDataFimEfetiva}"
                                        datePattern="dd/MM/yyyy" value="#{pdiControllerBean.currentNodeSelection.dataFimEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_set_Data_Fim_Efetiva" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Custo Previsto: " rendered="#{pdiControllerBean.currentNodeSelection.enabledCustoPrevisto}" />
                    <h:panelGroup rendered="#{pdiControllerBean.currentNodeSelection.enabledCustoPrevisto}">
                        <h:inputText    id="id_Custo_Previsto" value="#{pdiControllerBean.currentNodeSelection.custoPrevisto}" 
                                     size="20" required="true" label="Custo Previsto" >
                            <f:converter converterId="bigDecimalConverter"/>
                        </h:inputText>
                        <h:outputText value="Ex: 500.00" style="text-colour:gray;"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Custo Efetivo: " rendered="#{pdiControllerBean.currentNodeSelection.enabledCustoEfetivo}"/>
                    <h:panelGroup rendered="#{pdiControllerBean.currentNodeSelection.enabledCustoEfetivo}">
                        <h:inputText id="id_Custo_Efetivo" value="#{pdiControllerBean.currentNodeSelection.custoEfetivo}" 
                                     size="20" required="true" label="Custo Efetivo" >
                            <f:converter converterId="bigDecimalConverter"/>
                        </h:inputText>
                        <h:outputText value="Ex: 510.00" style="text-colour:gray;"/>
                    </h:panelGroup>
                    
				</h:panelGrid>
	
				<br/>
				<h:commandButton style="horizontal-align:center;" type="submit" value="Salvar" action="#{pdiControllerBean.currentNodeSelection.save}" />
				<h:commandButton style="horizontal-align:center;" type="submit" value="Cancelar" action="#{pdiControllerBean.currentNodeSelection.cancel}"/>
			</h:form>
    	</rich:panel>
	</a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>