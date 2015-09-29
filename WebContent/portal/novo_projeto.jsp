<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <a4j:outputPanel ajaxRendered="true" layout="block" >
        <rich:panel style="text-align: center" header="Cadastrar Novo Projeto">
            <h:form>
                <h:panelGrid columns="2">
                
                    <h:outputText style="color:blue" value="#{novoProjetoBean.estrategiaName}: "/>
                    <h:outputText value="#{novoProjetoBean.estrategiaDesc}"/>

                    <h:outputText value="Nome: "/>
                    <h:inputText value="#{novoProjetoBean.name}"/>
                    
                    <h:outputText value="Descrição: "/>
                    <h:inputText value="#{novoProjetoBean.desc}"/>
                    
                    <h:outputText value="Data Início Prevista: "/>
                    <h:panelGroup>
	                    <h:inputText id="id_data_ini_prevista" label="Data Início Prevista"
	                                 value="#{novoProjetoBean.dataInicioPrevista}">
	                        <f:validator validatorId="dateValidator"/>
	                    </h:inputText>
	                    <h:message for="id_data_ini_prevista" style="color: red; font-weight:bold"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Fim Prevista: " />
                    <h:panelGroup>
	                    <h:inputText id="id_data_fim_prevista" label="Data Fim Prevista"
	                                 value="#{novoProjetoBean.dataFimPrevista}">
	                        <f:validator validatorId="dateValidator"/>
	                    </h:inputText>
	                    <h:message for="id_data_fim_prevista" style="color: red"/>
                    </h:panelGroup>
                    
                    <h:outputText value="Data Início Efetiva: " />
                    <h:panelGroup>
	                    <h:inputText id="id_data_ini_efetiva" label="Data Início Efetiva"
	                                 value="#{novoProjetoBean.dataInicioEfetiva}">
	                        <f:validator validatorId="dateValidator"/>
	                    </h:inputText>
                        <h:message for="id_data_ini_efetiva" style="color: red"/>
                    </h:panelGroup>
                                        
                    <h:outputText value="Data Fim Efetiva: " />
                    <h:panelGroup>
	                    <h:inputText id="id_data_fim_efetiva" label="Data Fim Efetiva"
	                                 value="#{novoProjetoBean.dataFimEfetiva}">
	                        <f:validator validatorId="dateValidator"/>
	                    </h:inputText>
                        <h:message for="id_data_fim_efetiva" style="color: red"/>
                    </h:panelGroup>
                </h:panelGrid>
    
                <br/>
                <h:commandButton action="#{novoProjetoBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
                <h:commandButton action="#{novoProjetoBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
            </h:form>
        </rich:panel>
        <h:form>
            <br/>
            <h:commandLink value="Página Principal"  action="#{novoProjetoBean.returnMainPage}"></h:commandLink>
        </h:form>
    </a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>