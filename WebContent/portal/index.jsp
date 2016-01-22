<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<%@page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />

<f:view>
	<h:form id="id_main_form" acceptcharset="UTF-8">
        <rich:toolBar id="id_tool_bar" height="26px">
            <h:outputText value="Unidade:"/>
            <rich:comboBox defaultLabel="#{pdiControllerBean.unidadeSelectedName}"
                           valueChangeListener="#{pdiControllerBean.unidadeSelectedListener}"
                           enableManualInput="false" >
               <f:selectItems value="#{pdiControllerBean.listUnidades}"/>
               <a4j:support event="onchange" reRender="id_tree"/>
            </rich:comboBox>
        
            <rich:dropDownMenu submitMode="ajax">
                <f:facet name="label">
                    <h:outputText value="Periodo Planejamento"/>
                </f:facet>
                <rich:menuItem disabled="#{not sisplanUser.planningManager}" value="Novo" action="#{toolBarBean.goToNovoPeriodoPlan}"/>
				<rich:menuItem value="#{toolBarBean.valueMenuShowHide}" action="#{toolBarBean.setShowPeriodoPlan}" reRender="id_tool_bar" />
            </rich:dropDownMenu>
            
            <h:outputText rendered="#{toolBarBean.showPeriodoPlan}"
                          value="De #{toolBarBean.periodoPlan.strDataInicio} até #{toolBarBean.periodoPlan.strDataFim}"/>
            <rich:progressBar interval="3600000" rendered="#{toolBarBean.showPeriodoPlan}"
                              label="#{toolBarBean.barCurrentValue}%" value="#{toolBarBean.barCurrentValue}" minValue="0" maxValue="100"/>
            
        </rich:toolBar>
        <br/>
                
        <rich:panel header="Visão Geral do Planejamento Estratégico" style="text-align: center;">
<%--         <rich:panel style="width:220px;">
           <f:facet name="header">
               <h:outputText value="Unidade"></h:outputText>
           </f:facet>
	        <rich:comboBox width="200px" defaultLabel="#{pdiControllerBean.unidadeSelectedName}"
	                       valueChangeListener="#{pdiControllerBean.unidadeSelectedListener}">
	           <f:selectItems value="#{pdiControllerBean.listUnidades}"/>
	           <a4j:support event="onchange" reRender="id_tree"/>
	        </rich:comboBox>
        </rich:panel> --%>
	    <h:panelGrid columns="2" columnClasses="columnTop, columnTop" width="100%">
	        <rich:tree id="id_tree" var="node" ajaxSubmitSelection="true" switchType="ajax"
	                   value="#{pdiControllerBean.pdisTree}"
	                   nodeFace="#{node.type}"
	                   nodeSelectListener="#{pdiControllerBean.nodeSelected}" 
	                   adviseNodeOpened="#{pdiControllerBean.adviseNodeOpened}" 
	            	   reRender="id_detalhes" >
	            	   
<%--             	<rich:treeNode id="id_tree_node" changeExpandListener="#{node.processExpansion}" type="#{node.type}"> --%>
                <rich:treeNode changeExpandListener="#{node.processExpansion}" type="#{node.type}">
            		<h:outputText value="#{node.name}" /> <%-- <h:graphicImage  value="/img/icons/red.png" style="width:20px; height:20px"/> --%>
            	</rich:treeNode>
            	
            </rich:tree>
            
            <h:panelGroup>
		        <a4j:outputPanel ajaxRendered="true">                    
		            <rich:panel id="id_detalhes" style="width: 100%; font-size:medium"
		                        header="#{pdiControllerBean.currentNodeSelection.name}"
                                rendered="#{pdiControllerBean.renderPanel}">
                        <h:outputText rendered="#{pdiControllerBean.currentNodeSelection.renderedDescricao}"
                                      value="Descrição: #{pdiControllerBean.currentNodeSelection.descricao}">
                            <br/>
                        </h:outputText>
                        <h:outputText rendered="#{pdiControllerBean.currentNodeSelection.renderedUnidade}"
                                      value="Unidade: #{pdiControllerBean.currentNodeSelection.unidadeName}">
                            <br/>
                        </h:outputText>
                                                
                        <h:panelGroup rendered="#{pdiControllerBean.currentNodeSelection.renderedProjetoOrEtapa}">
                            <br/>
                            <h:outputText value="Responsável: #{pdiControllerBean.currentNodeSelection.responsavelName}"/>
                            <br/>
                            <br/>
                        
			                <fieldset>
			                    <legend>Detalhes</legend>
			                    <h:panelGrid columns="2" style="width: 100%">
			                            <rich:panel>
			                                <f:facet name="header">
			                                    <h:outputText value="Data Início"/>
			
			                                </f:facet>
			                                
			                                <h:outputText value="Prevista: #{pdiControllerBean.currentNodeSelection.strDataInicioPrevista}"/>
			                                <br/>
			                                <h:outputText value="Efetiva: #{pdiControllerBean.currentNodeSelection.strDataInicioEfetiva}"/>
			                            </rich:panel>
			                        
			                        <rich:panel>
			                            <f:facet name="header">
			                                <h:outputText value="Data Fim" />
			                            </f:facet>
			                        
			                            <h:outputText value="Prevista: #{pdiControllerBean.currentNodeSelection.strDataFimPrevista}" />
			                            <br/>
			                            <h:outputText value="Efetiva: #{pdiControllerBean.currentNodeSelection.strDataFimEfetiva}" />
			                        </rich:panel>
			                                                                                                                                            
			                    </h:panelGrid>
			                </fieldset>
		                </h:panelGroup>
		                
                        <h:panelGroup style="text-align: center;">
                            <br/>
                            <br/>
                            <h:outputLink rendered="#{pdiControllerBean.currentNodeSelection.renderedCadastrar}"
                                          title="#{pdiControllerBean.currentNodeSelection.cadastroTitle}"
                                          value="#{pdiControllerBean.currentNodeSelection.cadastroURL}" >
                                <f:verbatim><img src="/shared/img/adicionar.gif"/></f:verbatim>
                            </h:outputLink>
                            <h:outputText rendered="#{pdiControllerBean.currentNodeSelection.renderedCadastrar}"
                                          value="          " />
                            
                            <h:outputLink rendered="#{pdiControllerBean.currentNodeSelection.renderedAlterar}"
                                          value="#{pdiControllerBean.currentNodeSelection.alterarURL}">
                                <f:verbatim><img src="/shared/img/alterar.gif" alt="Alterar" title="Alterar"/></f:verbatim>
                            </h:outputLink>
                            <h:outputText rendered="#{pdiControllerBean.currentNodeSelection.renderedAlterar}"
                                          value="          " />
                        
                            <h:commandButton rendered="#{pdiControllerBean.currentNodeSelection.renderedExcluir}"
                                             title="Excluir" alt="Excluir" image="/img/delete.gif"
                                             action="#{pdiControllerBean.currentNodeSelection.delete}">
                                <a4j:support reRender="id_tree, id_detalhes" />
                            </h:commandButton>
                        </h:panelGroup>
	                    		                
		            </rich:panel>

		        </a4j:outputPanel>
	        </h:panelGroup>     
        </h:panelGrid>
        </rich:panel>
    </h:form>
</f:view>