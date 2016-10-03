<html xmlns="http://www.w3.org/1999/xhtml" lang="pt-br" xml:lang="pt-br">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />

<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>
    <f:view>
    <a4j:form id="id_form">
        <rich:panel style="text-align: center" header="Histórico de alterações">
		    <rich:dataTable rowClasses="whiteRow, grayRow" id="mytable" value="#{historicoControllerBean.listHistorico}"
		                    var="historico" rows="15" style="align:center; width:100%;">
	
	            <rich:column>
	               <f:facet name="header">
	                   <h:outputText value="Timestamp" />
	               </f:facet>
	               <h:outputText value="#{historico.timeStamp}" />
	            </rich:column>
	            	                    
		        <rich:column>
			       <f:facet name="header">
			           <h:outputText value="Responsável" />
			       </f:facet>
			       <h:outputText value="#{historico.responsavel}" />
			    </rich:column>
		      
		        <rich:column>
		             <f:facet name="header">
		                 <h:outputText value="Tipo do Componente" />
		             </f:facet>
		             <h:outputText value="#{historico.tipoComponente}" />
		        </rich:column>
		        
	            <rich:column>
	                 <f:facet name="header">
	                     <h:outputText value="Detalhe" />
	                 </f:facet>
	                 <h:outputText value="#{historico.detalhe}" />
	            </rich:column>
		         
		        <rich:column>
		             <f:facet name="header">
		                 <h:outputText value="Unidade" />
		             </f:facet>
		             <h:outputText value="#{historico.unidade}" />
		        </rich:column>
		         
		        <rich:column>
		             <f:facet name="header">
		                 <h:outputText value="Campo Alterado" />
		             </f:facet>
		             <h:outputText value="#{historico.campoAlterado}" />
		        </rich:column>
		         
		        <rich:column>
		             <f:facet name="header">
		                 <h:outputText value="De" />
		             </f:facet>
		             <h:outputText value="#{historico.de}" />
		        </rich:column>
		         
		        <rich:column>
		             <f:facet name="header">
		                 <h:outputText value="Para" />
		             </f:facet>
		             <h:outputText value="#{historico.para}" />
		        </rich:column>
		        
		         <f:facet name="footer">
		            <h:panelGroup style="text-align: center; horizontal-align:center;align: center">
			            <rich:datascroller reRender="id_form"/>
			            <div align="center">
                            <h:commandLink value="Gerar Relatório" action="#{historicoControllerBean.generatePDF}" />
                        </div>		                                     
                    </h:panelGroup>
		        </f:facet>
		    
		    </rich:dataTable>
        </rich:panel>
    </a4j:form>
    </f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
</html>