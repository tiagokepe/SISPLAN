<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
<%@include file="/portal/menu_administracao.jsp" %>

<admin:caminho titulo="Associar Servidor a Usuário"/>

<h:form id="form">

<table class="formulario" width="60%">
	<caption>Selecione um Servidor</caption>
	<tr>
		<th>Usuário: </th>
		<td>${ associarServidorUsuarioMBean.obj.login }</td>
	</tr>
	<tr>
		<th>Servidor: </th>
		<td>
			<h:inputText value="#{associarServidorUsuarioMBean.servidor.pessoa.nome}" id="nomeServidor" size="59"/>
			<a4j:region>	 
			<rich:suggestionbox for="nomeServidor" width="450" height="100" minChars="3" id="suggestionNomeServidor" 
				suggestionAction="#{servidorAutoCompleteMBean.autocompleteNomeServidor}" var="_servidor" 
				fetchValue="#{_servidor.pessoa.nome}">	
			 
				<h:column>
					<h:outputText value="#{_servidor.nomeSiape}" />
				</h:column>							        
			 
			 	<f:param name="apenasAtivos" value="false" />
			 
				<a4j:support event="onselect">
					<f:setPropertyActionListener value="#{_servidor.id}" target="#{associarServidorUsuarioMBean.servidor.id}" />
				</a4j:support>
			</rich:suggestionbox>
			</a4j:region>
		</td>
	</tr>
	<tfoot>
		<tr>
			<td colspan="2">
				<h:commandButton value="Associar" action="#{associarServidorUsuarioMBean.associar}"/>
				<h:commandButton value="Cancelar" action="#{associarServidorUsuarioMBean.cancelar}" onclick="#{confirm}" id="cancelar"/>
			</td>
		</tr>
	</tfoot>
</table>

</h:form>

</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>