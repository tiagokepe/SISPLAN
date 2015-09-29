<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <a4j:outputPanel ajaxRendered="true" layout="block" >
        <rich:panel style="text-align: center" header="Cadastrar Nova Etapa">
            <h:form>
                <h:panelGrid columns="2">
                
                    <h:outputText style="color:blue" value="#{novaEtapaBean.projetoName}: "/>
                    <h:outputText value="#{novaEtapaBean.projetoDesc}"/>

                    <h:outputText value="Descri��o: "/>
                    <h:inputText value="#{novaEtapaBean.desc}"/>
                    
                    <h:outputText value="Data In�cio Prevista: "/>
                    <h:panelGroup>
                        <h:inputText id="id_data_ini_prevista" label="Data In�cio Prevista"
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
                    
                    <h:outputText value="Data In�cio Efetiva: " />
                    <h:panelGroup>
                        <h:inputText id="id_data_ini_efetiva" label="Data In�cio Efetiva"
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
                    </h:panelGroup>
                </h:panelGrid>
    
                <br/>
                <h:commandButton action="#{novaEtapaBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
                <h:commandButton action="#{novaEtapaBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
            </h:form>
        </rich:panel>
        <h:form>
            <br/>
            <h:commandLink value="P�gina Principal"  action="#{novaEtapaBean.returnMainPage}"></h:commandLink>
        </h:form>
    </a4j:outputPanel>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>