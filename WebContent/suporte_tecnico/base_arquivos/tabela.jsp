<table class="formulario" width="90%">
	<caption>Informações da Base de Arquivos</caption>
	<tr>
		<td width="22%"><b>Número de Arquivos:</b></td>
		<td><h:outputText value="#{baseArquivosMBean.qtdArquivos}" /></td>
	</tr>
	<tr>
		<td><b>Tamanho total:</b></td>
		<td><h:outputText value="#{baseArquivosMBean.totalSize }" /> MB</td>
	</tr>
	<tr>
		<td nowrap><b>Tamanho médio do arquivo:</b></td>
		<td><h:outputText value="#{baseArquivosMBean.tamanhoMedio}" /> KB</td>
	</tr>
	<tr>
		<td colspan="2">
		<table class="subFormulario" width="100%">
			<caption>Informações por Content Type</caption>
			<thead>
				<tr>
					<th>Content Type</th>
					<th style="text-align: right;">Quantidade</th>
					<th style="text-align: right;">Tamanho (MB)</th>
				</tr>
			</thead>
			<c:forEach var="i" varStatus="loop" items="${baseArquivosMBean.contentTypes}">
				<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
					<td>${i[0]}</td>
					<td style="text-align: right;">${i[1]}</td>
					<td style="text-align: right;"><fmt:formatNumber value="${i[2]}" pattern="#,##0.00000"/></td>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<table class="subFormulario" width="100%">
			<caption>Informações dos Clusters</caption>
			<thead>
				<tr>
					<th style="text-align: center">Cluster</th>
					<th style="text-align: right;">Capacidade (MB)</th>
					<th style="text-align: right;">Quantidade</th>
					<th style="text-align: right;">Tamanho (MB)</th>
				</tr>
			</thead>
			<c:forEach var="i" varStatus="loop" items="${baseArquivosMBean.clusters}">
				<tr class="${ loop.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
					<td style="text-align: center;">${i.key}</td>
					<c:forEach var="j" items="${i.value}" varStatus="loop">
						<c:if test="${ loop.first or loop.last }">
							<td style="text-align: right;">
								<fmt:formatNumber value="${j}" pattern="#,##0.00"/>
							</td>
						</c:if>
						<c:if test="${ !loop.first and !loop.last }">
							<td style="text-align: right;">
								${j}
							</td>
						</c:if>
						
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>