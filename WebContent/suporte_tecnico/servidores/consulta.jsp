<%@ include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Consulta de Servidores" />

	<h:form prependId="false">

		<table class="formulario">
			<caption>Dados do Servidor</caption>
			<tr>
				<td><input type="radio" id="buscaSiape" name="tipo" value="siape" onfocus="$('siape').focus()" ${ servidorAdminMBean.tipo == 'siape' ? 'checked="checked"' : '' }/></td>
				<th style="text-align: left;">SIAPE:</th>
				<td><h:inputText value="#{servidorAdminMBean.siape}" id="SIAPE" size="10" onfocus="$('buscaSiape').checked = true"/></td>
			</tr>
			<tr>
				<td><input type="radio" id="buscaCpf" name="tipo" value="cpf" onfocus="$('cpf').focus()" ${ servidorAdminMBean.tipo == 'cpf' ? 'checked="checked"' : '' }/></td>
				<th style="text-align: left;">CPF:</th>
				<td>
					<h:inputText value="#{servidorAdminMBean.cpf}" id="cpf" size="14"maxlength="14"
							onfocus="$('buscaCpf').checked = true" onkeypress="formataCPF(this, event, null);" />
				</td>
			</tr>
			<tr>
				<td><input type="radio" id="buscaNome" name="tipo" value="nome" onfocus="$('nome').focus()" ${ servidorAdminMBean.tipo == 'nome' ? 'checked="checked"' : '' }/></td>
				<th style="text-align: left;">Nome:</th>
				<td><h:inputText value="#{servidorAdminMBean.nome}" id="nome" style="width: 430px;" onfocus="$('buscaNome').checked = true"/></td>
			</tr>
			<tr>
				<td><input type="radio" id="buscaLotacao" name="tipo" value="lotacao" onfocus="$('nomeLotacao').focus()" ${ servidorAdminMBean.tipo == 'lotacao' ? 'checked="checked"' : '' }/></td>
				<th style="text-align: left;">Lotação:</th>
				<td>
					<t:saveState value="#{servidorAdminMBean.lotacao.id}" />
					<h:inputText value="#{servidorAdminMBean.lotacao.nome}" id="nomeLotacao" style="width: 430px;"  onfocus="$('buscaLotacao').checked = true"/> 
					<rich:suggestionbox for="nomeLotacao" height="100" width="430" minChars="2"
						id="suggestionLotacao" suggestionAction="#{unidadeAutoCompleteMBean.autocompleteNomeUnidade}"
						var="_unidade" fetchValue="#{_unidade.codigoNome}">
						
						<h:column>
							<h:outputText value="#{_unidade.codigoNome}" />
						</h:column>

						<a4j:support event="onselect">
							<f:setPropertyActionListener value="#{_unidade.id}" target="#{servidorAdminMBean.lotacao.id}" />
						</a4j:support>
					</rich:suggestionbox>
				</td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{servidorAdminMBean.apenasAtivos}" id="apenasAtivos"/></td>
				<td colspan="2" style="text-align: left;">Apenas Servidores Ativo?</td>
			</tr>
			<%-- INÍCIO CUSTOMIZAÇÃO SIGREF --%>
			<tr>
				<td><h:selectBooleanCheckbox value="#{servidorAdminMBean.apenasServidoresComLogin}" id="apenasServidoresComLogin"/></td>
				<td colspan="2" style="text-align: left;">Apenas Servidores com Login?</td>
			</tr>
			<%-- FIM CUSTOMIZAÇÃO SIGREF --%>
			<tfoot>
				<tr>
					<td colspan="3">
						<h:commandButton value="Consultar" action="#{servidorAdminMBean.consultar}" id="consulta" /> 
						<h:commandButton value="Cancelar" action="#{servidorAdminMBean.cancelar}"
								id="cancela" onclick="#{confirm}" />
					</td>
				</tr>
			</tfoot>
		</table>

		<c:if test="${ not empty servidorAdminMBean.servidores }">
		<br /><br />
		<div class="infoAltRem" style="width: 100%">
		    <img src="/shared/img/lupa.gif" style="overflow: visible;"/>: Ver detalhes
		</div>
		<table class="listagem">
			<caption>Servidores encontrados (${ fn:length(servidorAdminMBean.servidores) })</caption>
			<thead>
				<tr>
					<th>SIAPE</th>
					<th>CPF</th>
					<th>Nome</th>
					<th>Categoria</th>
					<th>Vínculo</th>
					<th>Escolaridade</th>
					<th>Formação</th>
					<th>Situação</th>
					<%-- INÍCIO CUSTOMIZAÇÃO SIGREF --%>
					<th>Login</th>
					<%-- FIM CUSTOMIZAÇÃO SIGREF --%>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="#{servidorAdminMBean.servidores}" var="s" varStatus="loop">
				<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
					<td>${ s.siape }</td>
					<td>${ s.pessoa.cpfCnpjFormatado }</td>
					<td>${ s.pessoa.nome }</td>
					<td>${ s.categoria.descricao }</td>
					<td>${ s.situacao.descricao }</td>
					<td>${ s.escolaridade.descricao }</td>
					<td>${ s.formacao.denominacao }</td>
					<td>${ s.ativo.descricao }</td>
					<%-- INÍCIO CUSTOMIZAÇÃO SIGREF --%>
					<td>${ s.login }</td>
					<%-- FIM CUSTOMIZAÇÃO SIGREF --%>
					<td>
						<h:commandLink action="#{servidorAdminMBean.detalhes}">
							<f:verbatim><img src="/shared/img/lupa.gif" title="Ver detalhes" alt="Ver detalhes"/></f:verbatim>
							<f:param name="id" value="#{ s.id }"/>
						</h:commandLink>
					</td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
		
	</h:form>
</f:view>

<%@ include file="/WEB-INF/jsp/include/rodape.jsp"%>