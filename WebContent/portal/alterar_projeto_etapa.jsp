<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/admin/css/portal_sisplan.css" type="text/css" />
<f:view>
	<a4j:outputPanel ajaxRendered="true" layout="block" >
    	<rich:panel id="id_panel_hint" header="#{pdiControllerBean.currentNodeSelection.name}">
    		<h:form>
	    		<h:panelGrid columns="2">
                    <h:outputText value="Descrição: " rendered="#{pdiControllerBean.currentNodeSelection.renderedDescription}"/>
		        	<h:inputText id="id_set_Descricao" value="#{pdiControllerBean.currentNodeSelection.descricao}"
                                 rendered="#{pdiControllerBean.currentNodeSelection.renderedDescription}" />
		        	
 					<h:outputText value="Data Início Prevista: " rendered="#{sisplanUser.planningManager}"/>
 					<h:panelGroup rendered="#{sisplanUser.planningManager}">
			        	<h:inputText id="id_set_Data_Inicio_Prevista" value="#{pdiControllerBean.currentNodeSelection.dataInicioPrevista}"
			        	             valueChangeListener="#{pdiControllerBean.currentNodeSelection.valueChangeListener}">
	                        <f:validator validatorId="dateValidator"/>
	                    </h:inputText>
                        <h:message for="id_set_Data_Inicio_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
		        	
		        	<h:outputText value="Data Fim Prevista: " rendered="#{sisplanUser.planningManager}"/>
		        	<h:panelGroup rendered="#{sisplanUser.planningManager}">
			        	<h:inputText id="id_set_Data_Fim_Prevista" value="#{pdiControllerBean.currentNodeSelection.dataFimPrevista}"
			        	             valueChangeListener="#{pdiControllerBean.currentNodeSelection.valueChangeListener}">
	                        <f:validator validatorId="dateValidator"/>
	                    </h:inputText>
	                    <h:message for="id_set_Data_Fim_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
		        	
		        	<h:outputText value="Data Início Efetiva: " rendered="#{sisplanUser.planningManager}"/>
		        	<h:panelGroup rendered="#{sisplanUser.planningManager}">
			        	<h:inputText id="id_set_Data_Inicio_Efetiva" value="#{pdiControllerBean.currentNodeSelection.dataInicioEfetiva}"
			        	             valueChangeListener="#{pdiControllerBean.currentNodeSelection.valueChangeListener}">
	                        <f:validator validatorId="dateValidator"/>
	                    </h:inputText>
                        <h:message for="id_set_Data_Inicio_Efetiva" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
		        	
		        	<h:outputText value="Data Fim Efetiva: " rendered="#{sisplanUser.planningManager}"/>
		        	<h:panelGroup rendered="#{sisplanUser.planningManager}">
			        	<h:inputText id="id_set_Data_Fim_Efetiva" value="#{pdiControllerBean.currentNodeSelection.dataFimEfetiva}"
			        	             valueChangeListener="#{pdiControllerBean.currentNodeSelection.valueChangeListener}">
	                        <f:validator validatorId="dateValidator"/>
		               </h:inputText>
                        <h:message for="id_set_Data_Fim_Efetiva" style="color: red;font-weight:bold"/>
	               </h:panelGroup>
				</h:panelGrid>
	
				<br/>
				<h:commandButton style="horizontal-align:center;" type="submit" value="Salvar" action="#{pdiControllerBean.currentNodeSelection.save}" />
				<h:commandButton style="horizontal-align:center;" type="submit" value="Cancelar" action="#{pdiControllerBean.currentNodeSelection.cancel}"/>
			</h:form>
    	</rich:panel>
    	<h:form>
            <br/>
            <h:commandLink value="Página Principal"  action="#{pdiControllerBean.currentNodeSelection.returnMainPage}"></h:commandLink>
        </h:form>
	</a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>