<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
<%@include file="/portal/menu_administracao.jsp" %>

<admin:caminho titulo="Detalhes do Usuário"/>

<h:form id="form">

<table class="visualizacao" width="70%">
<caption>Detalhes do Usuário</caption>

<tr>
	<th>Nome: </th>
	<td>${ usuarioMBean.obj.pessoa.nome }</td>
	<td rowspan="8" valign="top">
		<c:if test="${usuarioMBean.obj.idFoto != null}">
			<img src="${ctx}/verFoto?idFoto=<h:outputText value="#{usuarioMBean.obj.idFoto}"/>&key=${ sf:generateArquivoKey(usuarioMBean.obj.idFoto) }"/> 
		</c:if>
		<c:if test="${usuarioMBean.obj.idFoto == null}">
			<img src="/shared/img/no_picture.png" height="125"/>
		</c:if>
	</td>
</tr>

<tr>
	<th>CPF: </th>
	<td>${ usuarioMBean.obj.pessoa.cpfCnpjFormatado }</td>
</tr>

<tr>
	<th>Data de Nascimento: </th>
	<td><fmt:formatDate value="${ usuarioMBean.obj.pessoa.dataNascimento }" pattern="dd/MM/yyyy"/></td>
</tr>

<tr>
	<th>Sexo: </th>
	<td>${ usuarioMBean.obj.pessoa.sexo }</td>
</tr>

<tr>
	<th>Login: </th>
	<td>${ usuarioMBean.obj.login }</td>
</tr>

<tr>
	<th>Ramal: </th>
	<td>${ usuarioMBean.obj.ramal }</td>
</tr>

<tr>
	<th>E-Mail: </th>
	<td>${ usuarioMBean.obj.email }</td>
</tr>

<tr>
	<th>Unidade: </th>
	<td>${ usuarioMBean.obj.unidade.codigoNome }</td>
</tr>

<c:if test="${ empty usuarioMBean.obj.papeisOrdenado }">
	<tr>
		<th>Permissões:</th>
		<td>O usuário não possui permissões.</td>
		<td></td>
	</tr>
</c:if>

<c:if test="${ not empty usuarioMBean.obj.papeisOrdenado }">
	<c:forEach var="papel" items="#{ usuarioMBean.obj.papeisOrdenado }" varStatus="i">
		<tr>
			<th>
				<c:if test="${i.index == 0}">
					Permissões:
				</c:if>
			</th>
			<td>
				<span title="${ papel.descricao }">${ papel.nome }</span><br/>
			</td>
			<td></td>
		</tr>
		
	</c:forEach>
</c:if>



</table>

<center>
	<br/>
	<h:commandLink action="#{usuarioMBean.voltar}" value="<< Voltar" id="voltar"/>
</center>

</h:form>
</f:view>

<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
