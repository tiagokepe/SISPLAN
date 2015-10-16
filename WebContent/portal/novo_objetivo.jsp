<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <h:form id="id_form">
    
        <rich:panel style="text-align: center" header="Cadastrar Novo Objetivo Específico">
            <h:panelGrid columns="2" >

	            <h:outputText style="color:blue" value="Unidade Responsável: "/>
	            <rich:comboBox width="200px" value="#{novoObjetivoBean.unidadeName}" valueChangeListener="#{novoObjetivoBean.unidadeSelectedListener}">
	               <f:selectItems value="#{novoObjetivoBean.listUnidades}"/>
	            </rich:comboBox>
                
                <h:outputText style="color:blue" value="#{novoObjetivoBean.objetivoEstrategicoName}: "/>
                <h:outputText value="#{novoObjetivoBean.objetivoEstrategicoDesc}"/>
                            
                <h:outputText style="color:blue" value="Descrição do Objetivo: "/>
                <h:inputText size="70" value="#{novoObjetivoBean.name}"/>

            </h:panelGrid>
            <br/>
            <h:commandButton action="#{novoObjetivoBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
            <h:commandButton action="#{novoObjetivoBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
        </rich:panel>
        <br/>
        <h:commandLink value="Página Principal"  action="#{novoObjetivoBean.returnMainPage}"></h:commandLink>
    </h:form>

<%--     </a4j:outputPanel> --%>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>