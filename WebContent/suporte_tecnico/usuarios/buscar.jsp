<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
<%@include file="/portal/menu_administracao.jsp" %>

<admin:caminho titulo="${ buscaUsuarioMBean.operacao.nome }"/>

<h:form id="form">

	<h:messages showDetail="true" showSummary="true" />

	<table class="formulario" width="75%">
		<caption>Busca de Usuários</caption>
			<tr>
				<td><h:selectBooleanCheckbox value="#{buscaUsuarioMBean.buscaLogin}" id="tipoLogin"/> </td>
				<td>
					<h:outputLabel for="tipoLogin" value="Login"/>:
				</td>
				<td><h:inputText value="#{ buscaUsuarioMBean.login }" id="login" onclick="$('form:tipoLogin').checked = true"/>
				</td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{buscaUsuarioMBean.buscaCpf}" id="tipoCpf"/></td>
				<td>
					<h:outputLabel for="tipoCpf" value="CPF"/>:
				</td>
				<td><h:inputText value="#{ buscaUsuarioMBean.cpf }" converter="convertCpf" onkeypress="return(formataCPF(this, event, null));" maxlength="14" id="cpf" onclick="$('form:tipoCpf').checked = true"/></td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{buscaUsuarioMBean.buscaNome}" id="tipoNome"/></td>
				<td>
					<h:outputLabel for="tipoNome" value="Nome"/>:
				</td>
				<td><h:inputText value="#{ buscaUsuarioMBean.nome }" id="nome" size="60" onclick="$('form:tipoNome').checked = true"/></td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{buscaUsuarioMBean.buscaTipo}" id="tipoUsuario"/></td>
				<td nowrap="nowrap"><h:outputLabel for="tipoUsuario" value="Tipo de Usuário:"/> </td>
				<td>
					<h:selectOneMenu value="#{ buscaUsuarioMBean.tipoUsuario }" id="selectTipoUsuario">
						<f:selectItem itemLabel="--SELECIONE--" itemValue="0"/>
						<f:selectItems value="#{ buscaUsuarioMBean.tiposUsuario }"/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<td><h:selectBooleanCheckbox value="#{buscaUsuarioMBean.buscaPapel}" id="tipoPapel"/></td>
				<td>
					<h:outputLabel for="tipoPapel" value="Papel"/>:
				</td>
				<td>
					<h:selectOneMenu value="#{ buscaUsuarioMBean.idPapel }" id="papel" onclick="$('form:tipoPapel').checked = true">
						<f:selectItem itemLabel="--SELECIONE--" itemValue="0"/>
						<f:selectItems value="#{ buscaUsuarioMBean.allPapeis }"/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<td> <h:selectBooleanCheckbox value="#{ buscaUsuarioMBean.buscaUnidade }" id="tipoUnidade"/> </td>
				<td>
					<h:outputLabel for="tipoUnidade" value="Unidade"/>:
				</td>
				<td>
					<h:inputText value="#{buscaUsuarioMBean.unidade.nome}" id="unidade" style="width: 430px;" onclick="$('form:tipoUnidade').checked = true"/>
					
					<rich:suggestionbox for="unidade" height="100" width="430"  minChars="3" id="suggestion"
				    	suggestionAction="#{unidadeAutoCompleteMBean.autocompleteNomeUnidade}" var="_unidade" 
				    	fetchValue="#{_unidade.codigoNome}">
				    	
				       <h:column>
				       		<h:outputText value="#{_unidade.codigoNome}" id="nomeCodUnd"/> 
				       </h:column> 
				       
				       <a4j:support event="onselect">
				       		<f:setPropertyActionListener value="#{_unidade.id}" target="#{buscaUsuarioMBean.unidade.id}"/>
				       </a4j:support>  
					</rich:suggestionbox> 
				</td>
			</tr>
			<tr>
				<td> <h:selectBooleanCheckbox value="#{ buscaUsuarioMBean.somenteAtivos }" id="somenteAtivos"/> </td>
				<td colspan="2">
					<h:outputLabel for="somenteAtivos" value="Buscar somente usuários ativos"/>
				</td>
			</tr>
			<tr>
				<td> <h:selectBooleanCheckbox value="#{ buscaUsuarioMBean.papelUnico }" id="papelUnico"/> </td>
				<td colspan="2">
					<h:outputLabel for="papelUnico" value="Buscar usuários com apenas um ÚNICO papel (informado acima no campo \"Papel\")"/>
				</td>
			</tr>
			<tfoot>
				<tr>
					<td colspan="3" align="center">
						<h:commandButton value="Buscar" action="#{ buscaUsuarioMBean.buscar }" id="btBuscar"/> 
						<h:commandButton value="Cancelar" onclick="#{confirm}" action="#{ buscaUsuarioMBean.cancelar }" id="btCancelar"/>
					</td>
				</tr>
			</tfoot>
		</table>

<br />&nbsp;
<c:if test="${ not empty buscaUsuarioMBean.usuarios }">

	<c:if test="${ !acessoMenu.consultaUsuario }" >
		<div class="infoAltRem">
			<h:graphicImage value="/img/comprovante.png" style="overflow: visible;" id="visualizarDetalhes" />: Visualizar Detalhes
			<h:graphicImage value="/img/seta.gif" style="overflow: visible;" id="SelecionarUsuario" />: Selecionar Usuário
		</div>
	</c:if>

	<table class="listagem">
		<caption>Usuários Encontrados</caption>
			<thead>
				<tr>
					<td>Foto </td>
					<td>Nome</td>
					<c:if test="${ !acessoMenu.consultaUsuario }" >
						<td>Login</td>
					</c:if>
					<c:if test="${ acessoMenu.consultaUsuario }" >
						<td>Ramal</td>
						<td>E-mail</td>
					</c:if>
					<td>Unidade</td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="usr" items="#{ buscaUsuarioMBean.usuarios }" varStatus="status">
					<tr class="${ status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							<c:if test="${usr.idFoto != null}">
								<img src="/admin/verFoto?idFoto=${usr.idFoto}&key=${ sf:generateArquivoKey(usr.idFoto) }" width="45" height="60"/>
							</c:if>
							<c:if test="${usr.idFoto == null}">
								<img src="/shared/img/no_picture.png" width="45" height="60"/>
							</c:if>
						</td>
						<td>${ usr.pessoa.nome }</td>
						<c:if test="${ !acessoMenu.consultaUsuario }" >
							<td>${ usr.login }</td>
						</c:if>
						<c:if test="${ acessoMenu.consultaUsuario }" >
							<td>${ usr.ramal }</td>
							<td>${ usr.email }</td>
						</c:if>
						<td>${ usr.unidade.sigla }</td>
						<c:if test="${ !acessoMenu.consultaUsuario }" >
							<td align="right" width="5px">
								<h:commandButton image="/img/comprovante.png" actionListener="#{usuarioMBean.detalhes}" title="Visualizar Detalhes" id="visuaDet">
									<f:attribute name="idUsuario" value="#{usr.id}"/>
								</h:commandButton>
							</td>
						</c:if>
						<c:if test="${acessoMenu.suporte || acessoMenu.gestao}" >
							<td align="right" width="5px">
								<h:commandButton image="/img/seta.gif" actionListener="#{buscaUsuarioMBean.escolheUsuario}" title="Selecionar Usuário" id="SelUsuario">
									<f:attribute name="idUsuario" value="#{usr.id}"/>
								</h:commandButton>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		
		<c:if test="${ not buscaUsuarioMBean.paginacao.primeiraPagina or not buscaUsuarioMBean.paginacao.ultimaPagina }" >
			<tfoot>
				<tr>
					<td colspan="6" style="text-align: center;">
						<c:if test="${ not buscaUsuarioMBean.paginacao.primeiraPagina }" >
							<h:commandLink action="#{ buscaUsuarioMBean.buscar }" id="pagAnterior">
								<h:graphicImage url="/img/pagina_tras.gif" style="border: none;" title="Página Anterior"/>
								<f:param name="pagina" value="#{ buscaUsuarioMBean.paginacao.paginaAtual - 1}" />
							</h:commandLink>
						</c:if>
						
						<h:selectOneMenu id="comboPaginacao" value="#{ buscaUsuarioMBean.paginacao.paginaAtual }"
								valueChangeListener="#{buscaUsuarioMBean.mudarPagina}" immediate="true"
								onchange="submit()" >
							<f:selectItems id="paramPagina" value="#{paginacao.listaPaginas}" />
						</h:selectOneMenu>
						
						<c:if test="${ not buscaUsuarioMBean.paginacao.ultimaPagina }" >
							<h:commandLink action="#{ buscaUsuarioMBean.buscar }" id="proxPagina">
								<h:graphicImage url="/img/pagina_frente.gif" style="border: none;" title="Próxima Página"/>
								<f:param name="pagina" value="#{ buscaUsuarioMBean.paginacao.paginaAtual + 1}" />
							</h:commandLink>
						</c:if>
					</td>
				</tr>
			</tfoot>
		</c:if>
	</table>
</c:if>

</h:form>
</f:view>

<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
