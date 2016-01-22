<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <a4j:outputPanel ajaxRendered="true" layout="block" >
        <rich:panel style="text-align: center" header="Cadastrar Período Planejamento">
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText style="color:blue" value="Unidade Responsável: "/>
	                <rich:comboBox width="200px"
	                               value="#{periodoPlanejamentoBean.unidadeName}" valueChangeListener="#{periodoPlanejamentoBean.unidadeSelectedListener}">
	                   <f:selectItems value="#{periodoPlanejamentoBean.listUnidades}"/>
	                </rich:comboBox>
	                
                    <h:outputText value="Data Início: "/>
                    <h:panelGroup>
                        <rich:calendar id="id_set_Data_Inicio_Prevista"
                                       datePattern="dd/MM/yyyy"
                                       value="#{periodoPlanejamentoBean.dataIni}">
                        </rich:calendar>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Fim: "/>
                    <h:panelGroup>
                        <rich:calendar id="id_set_Data_Fim_Prevista"
                                        datePattern="dd/MM/yyyy"
                                        value="#{periodoPlanejamentoBean.dataFim}">
                        </rich:calendar>
                    </h:panelGroup>
                </h:panelGrid> 
    
                <br/>
                <h:commandButton action="#{periodoPlanejamentoBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
                <h:commandButton action="#{periodoPlanejamentoBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
            </h:form>
        </rich:panel>
    </a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>