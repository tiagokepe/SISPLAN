<%@ include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Informações da base de arquivos" />
	
	<h:form>
	
		<%@include file="/suporte_tecnico/base_arquivos/tabela.jsp" %>
	
	</h:form>
	
</f:view>

<%@ include file="/WEB-INF/jsp/include/rodape.jsp"%>