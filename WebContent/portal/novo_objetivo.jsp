<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <h:form id="id_form">
    
        <rich:panel style="text-align: center" header="Cadastrar Novo Objetivo Espec�fico">
            <h:panelGrid columns="2" >

	            <h:outputText style="color:blue" value="Unidade Respons�vel: "/>
                <h:outputText value="#{novoObjetivoBean2.unidadeName}"/>
                
                <h:outputText style="color:blue" value="#{novoObjetivoBean2.objetivoEstrategicoName}: "/>
                <h:outputText value="#{novoObjetivoBean2.objetivoEstrategicoDesc}"/>
                            
                <h:outputText style="color:blue" value="Descri��o do Objetivo: "/>
                <h:inputText size="70" value="#{novoObjetivoBean2.name}"/>

            </h:panelGrid>
            <br/>
            <h:commandButton action="#{novoObjetivoBean2.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
            <h:commandButton action="#{novoObjetivoBean2.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
        </rich:panel>
        <br/>
        <h:commandLink value="P�gina Principal"  action="#{novoObjetivoBean2.returnMainPage}"></h:commandLink>
    </h:form>

<%--     </a4j:outputPanel> --%>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>