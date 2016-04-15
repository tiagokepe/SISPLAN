<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <a4j:outputPanel ajaxRendered="true" layout="block" >
        <rich:panel style="text-align: center" header="Cadastrar Novo Projeto">
            <h:form>
	            <rich:panel id="id_pick_list_panel" header="Selecione 1 ou mais Objetivos Estratégicos Institucionais">
	                <rich:pickList sourceListWidth="400px" targetListWidth="400px" id="id_pick_list" value="#{novoProjetoBean.selectedEst}"
	                               listsHeight="300px">
	                    <f:selectItems id="id_items" value="#{novoProjetoBean.availableEst}"/>
	                </rich:pickList>
	            </rich:panel>
	            <br/>
	            <br/>
                <h:panelGrid columns="2">
	                <h:outputText style="color:blue" value="Unidade Responsável: "/>
	                <rich:comboBox rendered="#{sisplanUser.planningManager}"
	                               width="200px" value="#{novoProjetoBean.unidadeName}"
	                               valueChangeListener="#{novoProjetoBean.unidadeSelectedListener}">
	                   <f:selectItems value="#{novoProjetoBean.listUnidades}"/>
	                   <a4j:support event="onchange" reRender="id_pick_list_panel, id_reponsaveis"/>
	                </rich:comboBox>
	                <h:outputText style="color:blue" value="#{novoProjetoBean.unidadeName}" rendered="#{!sisplanUser.planningManager}"/>
	                                 
                    <h:outputText value="Responsável"></h:outputText>
                    <rich:comboBox id="id_reponsaveis" width="200px" defaultLabel="Responsavel pelo projeto"
                                   value="#{novoProjetoBean.responsavelNameSelected}"
                                   valueChangeListener="#{novoProjetoBean.responsavelSelectedListener}">
                        <f:selectItems value="#{novoProjetoBean.responsaveis}"/>
                        <a4j:support event="onchange" reRender="id_reponsaveis"/>
                    </rich:comboBox>

                    <h:outputText value="Nome: "/>
                    <h:inputText value="#{novoProjetoBean.name}"/>
                    
                    <h:outputText value="Descrição: "/>
                    <h:inputTextarea style="width:250px" value="#{novoProjetoBean.descricao}"/>
                    
                    <h:outputText value="Data Início Prevista: "/>
                    <h:panelGroup>
                        <rich:calendar enableManualInput="true" id="id_Data_Inicio_Prevista" datePattern="dd/MM/yyyy" value="#{novoProjetoBean.dataInicioPrevista}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_Data_Inicio_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Fim Prevista: " />
                    <h:panelGroup>
                        <rich:calendar id="id_Data_Fim_Prevista" enableManualInput="true" datePattern="dd/MM/yyyy" value="#{novoProjetoBean.dataFimPrevista}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_Data_Fim_Prevista" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Início Efetiva: " />
                    <h:panelGroup>
                        <rich:calendar id="id_Data_Inicio_Efetiva" enableManualInput="true" datePattern="dd/MM/yyyy" value="#{novoProjetoBean.dataInicioEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_Data_Inicio_Efetiva" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Fim Efetiva: " />
                    <h:panelGroup>
                        <rich:calendar id="id_Data_Fim_Efetiva" enableManualInput="true" datePattern="dd/MM/yyyy" value="#{novoProjetoBean.dataFimEfetiva}">
                            <f:validator validatorId="dateValidator"/>
                        </rich:calendar>
                        <h:message for="id_Data_Fim_Efetiva" style="color: red;font-weight:bold"/>
                    </h:panelGroup>
                </h:panelGrid>
    
                <br/>
                <h:commandButton action="#{novoProjetoBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
                <h:commandButton action="#{novoProjetoBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
            </h:form>
        </rich:panel>
    </a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>