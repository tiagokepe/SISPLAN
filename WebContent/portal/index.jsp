<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<%@page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />

<f:view>
	<h:form acceptcharset="UTF-8">
        <rich:panel header="Visão Geral do Planejamento Estratégico" style="text-align: center;">
        <rich:panel style="width:220px;">
           <f:facet name="header">
               <h:outputText value="Unidade"></h:outputText>
           </f:facet>
	        <rich:comboBox width="200px" defaultLabel="Unidade" valueChangeListener="#{pdiControllerBean.unidadeSelectedListener}">
	           <f:selectItems value="#{pdiControllerBean.listUnidades}"/>
	           <a4j:support event="onchange" reRender="id_tree"/>
	        </rich:comboBox>
<%-- 	        <h:selectOneMenu>
                <f:selectItems value="#{pdiControllerBean.listUnidades}"/>
	        </h:selectOneMenu> --%>
        </rich:panel>
	    <h:panelGrid columns="2" columnClasses="columnTop, columnTop" width="100%">
	        <rich:tree id="id_tree" var="node" value="#{pdiControllerBean.pdisTree}" nodeFace="#{node.type}" nodeSelectListener="#{pdiControllerBean.nodeSelected}" 
	            		    reRender="id_detalhes, id_panel_desc, id_panel_unidade, id_panel_obj_especifico" ajaxSubmitSelection="true" switchType="client">
            	<rich:treeNode  type="#{node.type}">
            		<h:outputText value="#{node.name}" /> <%-- <h:graphicImage  value="/img/icons/red.png" style="width:20px; height:20px"/> --%>
            	</rich:treeNode>
            </rich:tree>
            <h:panelGroup>
	            <a4j:outputPanel ajaxRendered="true" layout="block" >
		            <rich:panel id="id_panel_desc" header="#{pdiControllerBean.currentNodeSelection.name}" rendered="#{pdiControllerBean.renderedDescPanel}">
		                 <h:outputText value="Descrição: " />
		                 <h:outputText value="#{pdiControllerBean.currentNodeSelection.desc}" />
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
		                    
		                    <h:panelGroup>
		                        <h:outputText value="Alterar: "/>
		                        <h:outputLink value="/SISPLAN/portal/alterar_projeto_etapa.jsf">
		                            <f:verbatim><img src="/shared/img/alterar.gif" alt="Alterar" title="Alterar"/></f:verbatim>
		                        </h:outputLink>
		                    </h:panelGroup>
		                </fieldset>
		                
	                    <h:panelGroup rendered="#{pdiControllerBean.currentNodeSelection.renderedCadastrar}">
	                        <br/>
	                        <h:outputText value="#{pdiControllerBean.currentNodeSelection.cadastroTitle}: " />
	                        <h:outputLink  value="#{pdiControllerBean.currentNodeSelection.cadastroURL}" >
	                            <f:verbatim><img src="/shared/img/adicionar.gif" alt="Cadastrar" title="Cadastrar"/></f:verbatim>
	                        </h:outputLink>
	                    </h:panelGroup>		                
		            </rich:panel>

		        </a4j:outputPanel>
		        
                <a4j:outputPanel ajaxRendered="true" layout="block" >
                    <rich:panel id="id_panel_unidade" header="#{pdiControllerBean.currentNodeSelection.name}" rendered="#{pdiControllerBean.renderedCadastroPanel}">
                        <h:panelGroup rendered="#{not empty pdiControllerBean.currentNodeSelection.desc}">
                            <h:outputText value="Descrição: #{pdiControllerBean.currentNodeSelection.desc}" />
                            <br/>
                            <br/>
                        </h:panelGroup>
                        <h:outputText value="#{pdiControllerBean.currentNodeSelection.cadastroTitle}: " />
                        <h:outputLink  value="#{pdiControllerBean.currentNodeSelection.cadastroURL}" >
                            <f:verbatim><img src="/shared/img/adicionar.gif" alt="Cadastrar" title="Cadastrar"/></f:verbatim>
                        </h:outputLink>
                    </rich:panel>
                </a4j:outputPanel>
                
                <a4j:outputPanel ajaxRendered="true" layout="block" >
                    <rich:panel id="id_panel_obj_especifico" header="#{pdiControllerBean.currentNodeSelection.name}" rendered="#{pdiControllerBean.objEspecificoPanel}">
                        <h:outputText value="Descrição: #{pdiControllerBean.currentNodeSelection.desc}" />
                        <br/>
                        <h:outputText value="Unidade: #{pdiControllerBean.currentNodeSelection.unidadeName}"/>
                        <br/>
                        <h:outputText value="#{pdiControllerBean.currentNodeSelection.cadastroTitle}: " />
                        <h:outputLink value="#{pdiControllerBean.currentNodeSelection.cadastroURL}" >
                            <f:verbatim><img src="/shared/img/adicionar.gif" alt="Cadastrar" title="Cadastrar"/></f:verbatim>
                        </h:outputLink>
                    </rich:panel>
                </a4j:outputPanel>
                
	        </h:panelGroup>     
        </h:panelGrid>
        </rich:panel>
    </h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>