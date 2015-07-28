<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/admin/css/portal_sisplan.css" type="text/css" />
<f:view>
		<h:panelGrid columns="1"  width="100%" >
					<h:form>
				        <rich:tree id="id_tree" var="node" value="#{pdiControllerBean.pdis}" nodeFace="#{node.type}" nodeSelectListener="#{pdiControllerBean.nodeSelected}" 
				            		    style="width:300px" reRender="id_detalhes, id_panel_hint" ajaxSubmitSelection="true" switchType="client" >
			            	<rich:treeNode  type="#{node.type}">
			            		<h:outputText value="#{node.name}" />
<%-- 	 		            		<rich:toolTip value="#{node.hint}" /> --%>
			            	</rich:treeNode>
			            </rich:tree>
			        </h:form>
		            
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
		                			
									<h:outputText value="Prevista: #{pdiControllerBean.currentNodeSelection.dataInicioPrevista}" />
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
							<h:commandLink action="#{pdiControllerBean.currentNodeSelection.changeDate}">
								<f:verbatim><img src="/shared/img/alterar.gif" alt="Alterar" title="Alterar"/></f:verbatim>
							</h:commandLink>
<%-- 							<h:commandLink action="#{pdiControllerBean.currentNodeSelection.changeDate}">
								<f:verbatim><img src="/SISPLAN/img/icons/salvar.png" alt="Salvar" title="Salvar"/></f:verbatim>
							</h:commandLink> --%>
						</h:panelGroup>
						</h:form>		                
		            </fieldset>
			        
		    	</rich:panel>
		    </a4j:outputPanel>
	</h:panelGrid>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>