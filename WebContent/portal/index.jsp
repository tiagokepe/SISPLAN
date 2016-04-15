<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<%-- <%@page contentType="text/html; charset=UTF-8" %> --%>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />

<f:view>
<%-- 	<h:form id="id_main_form" acceptcharset="UTF-8"> --%>
    <h:form id="id_main_form">
        <rich:toolBar id="id_tool_bar" height="26px">
            <h:outputText value="Unidade:"/>
            <rich:comboBox defaultLabel="#{pdiControllerBean.unidadeSelectedName}"
                           valueChangeListener="#{pdiControllerBean.unidadeSelectedListener}"
                           enableManualInput="false" >
               <f:selectItems value="#{pdiControllerBean.listUnidades}"/>
               <a4j:support event="onchange" reRender="id_main_form"/>
            </rich:comboBox>
        
<%--         rendered="#{periodoPlanControllerBean.periodoPlanAtivo or sisplanUser.planningManager}" --%>
            <rich:dropDownMenu  
                               submitMode="ajax">
                <f:facet name="label">
                    <h:outputText value="Periodo Planejamento"/>
                </f:facet>
                <rich:menuItem  rendered="#{periodoPlanControllerBean.periodoPlanAtivo or sisplanUser.planningManager}"
                                value="Novo"
                                disabled="#{not sisplanUser.planningManager}"
                                action="#{periodoPlanControllerBean.goToNovoPeriodoPlan}"/>
                                
				<rich:menuItem  
				                value="#{periodoPlanControllerBean.valueMenuShowHide}"
				                action="#{periodoPlanControllerBean.setShowPeriodoPlan}"
				                reRender="id_tool_bar" />
			    
			    <rich:menuItem rendered="#{periodoPlanControllerBean.periodoPlanAtivo or sisplanUser.planningManager}"
			                   value="Pendências"
			                   disabled="#{not sisplanUser.planningManager}"
			                   action="#{pendenciesControllerBean.gotToPendencias}"/>
				
            </rich:dropDownMenu>
            
            <h:outputText  rendered="#{not periodoPlanControllerBean.periodoPlanAtivo}" 
                          value="Planejamento em Execução..." style="font-size:110%; color: green;"/>
            
            <h:outputText rendered="#{periodoPlanControllerBean.showPeriodoPlan}"
                          value="De #{periodoPlanControllerBean.periodoPlan.strDataInicio} até #{periodoPlanControllerBean.periodoPlan.strDataFim}"/>
            <rich:progressBar interval="3600000" rendered="#{periodoPlanControllerBean.showPeriodoPlan}"
                              label="#{periodoPlanControllerBean.barCurrentValue}%" value="#{periodoPlanControllerBean.barCurrentValue}" minValue="0" maxValue="100"/>
            
            <h:outputLink rendered="#{sisplanUser.planningManager}"
                          value="#{historicoControllerBean.historyURL}" title="Histórico">
                Histórico
            </h:outputLink>

        </rich:toolBar>
        <br/>
                
        <rich:panel header="Visão Geral do Planejamento Estratégico" style="text-align: center;">

		    <h:panelGrid columns="2" columnClasses="columnTop, columnTop" width="100%">
		        <rich:tree id="id_tree" var="node" ajaxSubmitSelection="true" switchType="ajax"
		                   value="#{pdiControllerBean.pdisTree}"
		                   nodeFace="#{node.type}"
		                   nodeSelectListener="#{pdiControllerBean.nodeSelected}" 
		                   adviseNodeOpened="#{pdiControllerBean.adviseNodeOpened}" 
		            	   reRender="id_detalhes" >
		            	   
	                <rich:treeNode changeExpandListener="#{node.processExpansion}" type="#{node.type}">
	            		<h:outputText value="#{node.name}" />
	            		<h:graphicImage rendered="#{node.showProgressStatus}"
	            		                value="#{node.imgStatus}"
	            		                style="width:25px; height:25px"
	            		                title="#{node.legenda}"
	            		                >
                            <rich:toolTip value="#{node.legenda}" />
                        </h:graphicImage>
	            	</rich:treeNode>
	            	
                </rich:tree>
	            
	            <h:panelGroup>
			        <a4j:outputPanel ajaxRendered="true">                    
			            <rich:panel id="id_detalhes" 
			                        style="width: 475px;"
	                                rendered="#{pdiControllerBean.renderPanel}"
	                                headerClass="#{pdiControllerBean.currentNodeSelection.statusStyleClass}"
	                                header="#{pdiControllerBean.currentNodeSelection.name}">

<!--                                           style="width: 460px; overflow-x: scroll; display: block;"  -->
	                       <pre style="text-align:left !important; white-space: pre-line; font-family: Arial,Verdana,sans-serif !important;" >
	                        <h:outputText rendered="#{pdiControllerBean.currentNodeSelection.renderedDescricao}"
	                                      value="Descrição: #{pdiControllerBean.currentNodeSelection.descricao}"/>
	                       </pre>
	                        
	                        <h:outputText rendered="#{pdiControllerBean.currentNodeSelection.renderedUnidade}"
	                                      value="Unidade: #{pdiControllerBean.currentNodeSelection.unidadeName}">
	                        </h:outputText>
	                        
	                        <rich:panel rendered="#{not pdiControllerBean.currentNodeSelection.renderedProjetoOrEtapa}">
	                            <f:facet name="header">
	                                <h:outputText value="Custos" />
	                            </f:facet>
	                            <h:outputText value="Previsto: #{pdiControllerBean.currentNodeSelection.custoPrevisto}" />
	                            <br/>
	                            <h:outputText value="Efetivo: #{pdiControllerBean.currentNodeSelection.custoEfetivo}" />
	                            <br/>
	                        </rich:panel>
	                                                
	                        <h:panelGroup rendered="#{pdiControllerBean.currentNodeSelection.renderedProjetoOrEtapa}">
	                            <h:outputText value="Responsável: #{pdiControllerBean.currentNodeSelection.responsavelName}"/>
	                            <br/>
	                            
	                            <rich:panel>
		                            <f:facet name="header">
		                                <h:outputText value="Custos" />
		                            </f:facet>
		                            
		                            <h:outputText value="Previsto: #{pdiControllerBean.currentNodeSelection.custoPrevisto}" />
		                            <br/>
		                            <h:outputText value="Efetivo: #{pdiControllerBean.currentNodeSelection.custoEfetivo}" />
		                            <br/>
	                            </rich:panel>
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
			                
	                        <h:panelGroup style="text-align: center;" >
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
	                                             action="#{pdiControllerBean.currentNodeSelection.delete}"
	                                             onclick="return confirm('Realmente deseja excluir?\n\nEsta ação excluirá todos os componentes logo abaixo na hierarquia.')">
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