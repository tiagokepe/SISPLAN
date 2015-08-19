<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />

<f:view>
	<h:form>
	   <h:panelGrid columns="2" columnClasses="top, top" width="100%">
	        <rich:tree id="id_tree" var="node" value="#{pdiControllerBean.pdis}" nodeFace="#{node.type}" nodeSelectListener="#{pdiControllerBean.nodeSelected}" 
	            		    reRender="id_detalhes, id_panel_hint" ajaxSubmitSelection="true" switchType="client">
            	<rich:treeNode  type="#{node.type}">
            		<h:outputText value="#{node.name}" /> <h:graphicImage  value="/img/icons/red.png" style="width:20px; height:20px"/>
            	</rich:treeNode>
            </rich:tree>
            <h:panelGroup>
	            <a4j:outputPanel ajaxRendered="true" layout="block" >
		            <rich:panel id="id_panel_hint" header="#{pdiControllerBean.currentNodeSelection.name}" rendered="#{pdiControllerBean.renderedHintPanel}">
		                 <h:outputText value="Descrição: " />
		                 <h:outputText value="#{pdiControllerBean.currentNodeSelection.hint}" />
		            </rich:panel>
	            </a4j:outputPanel>
		        <a4j:outputPanel ajaxRendered="true" layout="block">                    
		            <rich:panel id="id_detalhes" header="#{pdiControllerBean.currentNodeSelection.name}"
		                                    rendered="#{pdiControllerBean.rendered}" style="width: 100%; font-size:medium">
		                <h:outputText value="Descrição: #{pdiControllerBean.currentNodeSelection.descricao}"
		                              rendered="#{pdiControllerBean.currentNodeSelection.renderedDescription}"/>
		                <br/>
		                <br/>
		                <fieldset>
		                    <legend>Detalhes</legend>
		                    <h:panelGrid columns="2" style="width: 100%">
		                            <rich:panel>
		                                <f:facet name="header">
		                                    <h:outputText value="Data Início"/>
		
		                                </f:facet>
		                                
		                                <h:outputText value="Prevista: #{pdiControllerBean.currentNodeSelection.dataInicioPrevista}"/>
		                                <br/>
		                                <h:outputText value="Efetiva: #{pdiControllerBean.currentNodeSelection.dataInicioEfetiva}"/>
		                            </rich:panel>
		                        
		                        <rich:panel>
		                            <f:facet name="header">
		                                <h:outputText value="Data Fim" />
		                            </f:facet>
		                        
		                            <h:outputText value="Prevista: #{pdiControllerBean.currentNodeSelection.dataFimPrevista}" />
		                            <br/>
		                            <h:outputText value="Efetiva: #{pdiControllerBean.currentNodeSelection.dataFimEfetiva}" />
		                        </rich:panel>
		                                                                                                                                            
		                    </h:panelGrid>
		                    <h:form>
		                    
		                    <h:panelGroup>
		                        <h:outputText value="Alterar: "/>
		                        <h:outputLink value="/SISPLAN/portal/alterar_projeto_etapa.jsf">
		                            <f:verbatim><img src="/shared/img/alterar.gif" alt="Alterar" title="Alterar"/></f:verbatim>
		                        </h:outputLink>
		                    </h:panelGroup>
		                    </h:form>                       
		                </fieldset>
		                
		            </rich:panel>
		        </a4j:outputPanel>
	        </h:panelGroup>     
         </h:panelGrid>
       </h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>