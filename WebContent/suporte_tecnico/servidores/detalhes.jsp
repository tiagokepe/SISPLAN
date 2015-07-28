<%@ include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Detalhes do Servidor" />
	<c:set var="s" value="${ servidorAdminMBean.servidor }" />

	<table class="formulario" width="100%">
		<caption>Dados Gerais do Servidor</caption>
		<tr>
			<td>Nome:</td>
			<td>${ s.pessoa.nome }</td>
			<td>C.P.F. No:</td>
			<td>${ s.pessoa.cpfCnpjFormatado }</td>
		</tr>
		<tr>
			<td>Data de Nascimento:</td>
			<td><fmt:formatDate value="${ s.pessoa.dataNascimento }"
				pattern="dd/MM/yyyy" /></td>
			<td>Sexo:</td>
			<td><ufrn:format type="sexo" valor="${ s.pessoa.sexo }" /></td>
		</tr>
		<tr>
			<td>Matrícula:</td>
			<td>${ s.siape }</td>
			<td>Cargo:</td>
			<td>${ s.cargo.denominacao }</td>
		</tr>
		<tr>
			<td>Ativo descrição:</td>
			<td>${ s.ativo.descricao }</td>
			<td>Descrição da Situação:</td>
			<td>${ s.situacao.descricao }</td>
		</tr>
		<tr>
			<td>Categoria:</td>
			<td>${ s.categoria.descricao }</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>Lotação:</td>
			<td>${ s.unidade.codigoNome }</td>
			<td>Atividade do Servidor:</td>
			<td>${ s.atividade.descricao }</td>
		</tr>
		<tr>
			<td>Dedicação Exclusiva:</td>
			<td><ufrn:format type="simNao" valor="${ s.dedicacaoExclusiva }"/></td>
			<td>Regime de Trabalho:</td>
			<td>${ s.regimeTrabalho }</td>
		</tr>
		<%--
		<tr>
			<td>Endereço:</td>
			<td>${ s.pessoa.endereco }</td>
			<td>Bairro:</td>
			<td>${ s.pessoa.bairro }</td>
		</tr>
		<tr>
			<td>CEP:</td>
			<td>${ s.pessoa.CEP }</td>
			<td>E-mail:</td>
			<td>${ s.pessoa.email }</td>
		</tr>
		<tr>
			<td>Cidade:</td>
			<td>${ s.pessoa.cidade }</td>
			<td>UF:</td>
			<td>${ s.pessoa.UF }</td>
		</tr>
		<tr>
			<td>Telefone:</td>
			<td>${ s.pessoa.telefone }</td>
			<td>Fax:</td>
			<td>${ s.pessoa.fax }</td>
		</tr>
		 --%>
		<c:if test="${ not empty s.designacoes }">
		<tr>
			<td colspan="4">
			<table class="subFormulario" width="100%">
				<caption>Designações</caption>
				<thead>
					<tr>
						<th>Início</th>
						<th>Fim</th>
						<th>Cód. Atividade</th>
						<th>Descrição da Atividade</th>
						<th>Unidade</th>
					</tr>
				</thead>
				<c:forEach var="d" items="${ s.designacoes }">
					<tr>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${ d.inicio }"/></td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${ d.fim }"/></td>
						<td>${ d.atividade.codigoRH }</td>
						<td>${ d.atividade.descricao }</td>
						<td>${ d.unidade }</td>
					</tr>
				</c:forEach>
			</table>
			</td>
		</tr>
		</c:if>
		<c:if test="${ not empty servidorAdminMBean.ferias }">
		<tr>
			<td colspan="4">
			<table class="subFormulario" width="100%">
				<caption>Férias</caption>
				<thead>
					<tr>
						<th>Início</th>
						<th>Fim</th>
					</tr>
				</thead>
				<c:forEach var="f" items="${ servidorAdminMBean.ferias }">
					<tr>
						<td><fmt:formatDate value="${ d.dataInicio }" pattern="dd/MM/yyyy"/> </td>
						<td><fmt:formatDate value="${ d.dataFim }" pattern="dd/MM/yyyy"/></td>
					</tr>
				</c:forEach>
			</table>
			</td>
		</tr>
		</c:if>
		<c:if test="${ not empty servidorAdminMBean.usuarios }">
		<tr>
			<td colspan="4">
			<table class="subFormulario" width="100%">
				<caption>Logins</caption>
				<thead>
					<tr>
						<th>Login</th>
						<th>Ativo</th>
					</tr>
				</thead>
				<c:forEach var="u" items="${ servidorAdminMBean.usuarios }">
					<tr>
						<td>${ u.login } </td>
						<td>${ u.inativo ? 'Não': 'Sim' }
					</tr>
				</c:forEach>
			</table>
			</td>
		</tr>
		</c:if>
	</table>

</f:view>
<%@ include file="/WEB-INF/jsp/include/rodape.jsp"%>