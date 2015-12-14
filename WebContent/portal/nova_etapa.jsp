<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <a4j:outputPanel ajaxRendered="true" layout="block" >
        <rich:panel style="text-align: center" header="Cadastrar Nova Etapa">
            <h:form>
                <h:panelGrid columns="2">
                
                    <h:outputText style="color:blue" value="Projeto: "/>
                    <h:outputText value="#{novaEtapaBean.projetoName}"/>
                    
                    <h:outputText value="Responsável"></h:outputText>
                    <rich:comboBox id="id_reponsaveis" width="200px" defaultLabel="Responsável pelo projeto"
                                  value="#{novaEtapaBean.responsavelNameSelected}">
                        <f:selectItems value="#{novaEtapaBean.responsaveis}"/>
                        <a4j:support event="onchange" reRender="id_reponsaveis"/>                        
                    </rich:comboBox>

                    <h:outputText value="Descrição: "/>
                    <h:inputText value="#{novaEtapaBean.descricao}"/>
                    
                    <h:outputText value="Data Início Prevista: "/>
                    <h:panelGroup>
	                    <rich:calendar id="id_Data_Inicio_Prevista" datePattern="dd/MM/yyyy" value="#{novaEtapaBean.dataInicioPrevista}">
	                       <f:validator validatorId="dateValidator"/>
	                    </rich:calendar>
                        <h:message for="id_Data_Inicio_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Fim Prevista: " />
                    <h:panelGroup>
	                    <rich:calendar id="id_Data_Fim_Prevista" datePattern="dd/MM/yyyy" value="#{novaEtapaBean.dataFimPrevista}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
	                    <h:message for="id_Data_Fim_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Início Efetiva: " />
                    <h:panelGroup>
                        <rich:calendar id="id_Data_Inicio_Efetiva" datePattern="dd/MM/yyyy" value="#{novaEtapaBean.dataInicioEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_Data_Inicio_Efetiva" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Fim Efetiva: " />
                    <h:panelGroup>
                        <rich:calendar id="id_Data_Fim_Efetiva" datePattern="dd/MM/yyyy" value="#{novaEtapaBean.dataFimEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_Data_Fim_Efetiva" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
<%--                     <h:outputText value="Data Início Prevista: "/>
                    <h:panelGroup>
                        <h:inputText id="id_data_ini_prevista" label="Data Início Prevista"
                                     value="#{novaEtapaBean.dataInicioPrevista}">
                            <f:validator validatorId="dateValidator"/>
                        </h:inputText>
                        <h:message for="id_data_ini_prevista" style="color: red; font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Fim Prevista: " />
                    <h:panelGroup>
                        <h:inputText id="id_data_fim_prevista" label="Data Fim Prevista"
                                     value="#{novaEtapaBean.dataFimPrevista}">
                            <f:validator validatorId="dateValidator"/>
                        </h:inputText>
                        <h:message for="id_data_fim_prevista" style="color: red"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Início Efetiva: " />
                    <h:panelGroup>
                        <h:inputText id="id_data_ini_efetiva" label="Data Início Efetiva"
                                     value="#{novaEtapaBean.dataInicioEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </h:inputText>
                        <h:message for="id_data_ini_efetiva" style="color: red"/>
                    </h:panelGroup>
                                        
                    <h:outputText value="Data Fim Efetiva: " />
                    <h:panelGroup>
                        <h:inputText id="id_data_fim_efetiva" label="Data Fim Efetiva"
                                     value="#{novaEtapaBean.dataFimEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </h:inputText>
                        <h:message for="id_data_fim_efetiva" style="color: red"/>
                    </h:panelGroup> --%>
                </h:panelGrid> 
    
                <br/>
                <h:commandButton action="#{novaEtapaBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
                <h:commandButton action="#{novaEtapaBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
            </h:form>
        </rich:panel>
    </a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>