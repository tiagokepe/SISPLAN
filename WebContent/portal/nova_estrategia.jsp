<%@include file="/WEB-INF/jsp/include/cabecalho.jsp" %>

<%@page import="br.ufrn.comum.dominio.LocalizacaoNoticiaPortal"%>
<link rel="stylesheet" media="all" href="/SISPLAN/css/portal_sisplan.css" type="text/css" />
<f:view>
    <h:form id="id_form">
    
        <rich:panel style="text-align: center" header="Cadastrar Novo Objetivo Espec�fico">
            <h:panelGrid columns="2" >

                <h:outputText style="color:blue" value="#{novaEstrategiaBean.objetivoName}: "/>
                <h:outputText value="#{novaEstrategiaBean.objetivoDesc}"/>
                
                <h:outputText style="color:blue" value="Unidade Respons�vel: "/>
                <h:outputText value="#{novaEstrategiaBean.unidadeName}"/>
                            
                <h:outputText style="color:blue" value="Descri��o da Estrat�gia: "/>
                <h:inputText size="70" value="#{novaEstrategiaBean.name}"/>

            </h:panelGrid>
            <br/>
            <h:commandButton action="#{novaEstrategiaBean.save}" style="horizontal-align:center;" type="submit" value="Salvar"  />
            <h:commandButton action="#{novaEstrategiaBean.cancel}" style="horizontal-align:center;" type="submit" value="Cancelar" />
        </rich:panel>
        <br/>
        <h:commandLink value="P�gina Principal"  action="#{novaEstrategiaBean.returnMainPage}"></h:commandLink>
    </h:form>

<%--     </a4j:outputPanel> --%>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>