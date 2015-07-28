
<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>

<c:import context="/shared" url="/sistemas.jsp?sistemaAtual=sisplan" var="sistemas" scope="request"/> 
${sistemas}
<br>
<c:if test="${ mensagem != '' }">
	<font color="red"><b><center>${ mensagem }</center></b></font><br/>
</c:if>


<% if (request.getParameter("msgSucesso") != null) { %>
<br>
 <table class="formulario" width="100%" align="center">
  <tr valign="middle">
  <td width="15%" align="center"><img src="img/warning.gif"></td>
  <td valign="middle" align="left" style="color: red; text-decoration: bold;">
   	<b>${param['msgSucesso']} </b>
  </td></tr>
 </table>
<br>
<br>
<%}%>


<center>

Esqueceu o login? <a href="/admin/public/recuperar_login.jsf">Clique aqui para recuperá-lo.</a> <br/>
<c:if test="${!ldapMBean.ldapHabilitado }">
	Perdeu o e-mail de confirmação de cadastro? <a href="/admin/public/recuperar_codigo.jsf">Clique aqui para recuperá-lo.</a> <br/>
	Esqueceu a senha? <a href="/admin/public/recuperar_senha.jsf">Clique aqui para recuperá-la.</a>
</c:if>
</center>
<br>

<c:if test="${portal_publico != null}">

	<div class="descricaoOperacao">
		<b>
			ATENÇÃO:</b>
			Para prosseguir com a operação, é necessário se autenticar no SIGRH, utilizando o seu usuário e senha.
		
	</div>

</c:if>

<br>      
<div class="logon" style="width:50%; margin: 0 auto;">
<h3>Entrar no Sistema</h3>

<h:form prependId="false">

	<input type="hidden" name="width" id="width" />
	<input type="hidden" name="height" id="height" />
	<input type="hidden" name="urlRedirect" value="${param.urlRedirect}" />

	<script>
		document.getElementById('width').value = screen.width;
		document.getElementById('height').value = screen.height;
	</script>

<table align="center" width="100%" cellspacing="0" cellpadding="3">
<tbody>
	<tr>
		<th width="35%">  Usuário: </th>
		<td> <h:inputText id="login" value="#{logon.usuario.login}"/>  </td>
	</tr>
	<tr>
		<th> Senha: </th>
		<td>  <h:inputSecret id="senha" value="#{logon.usuario.senha}"/>  </td>
	</tr>
</tbody>
<tfoot>
	<tr>
		<td colspan="2" align="center">
			<h:commandButton id="logar" action="#{logon.logar}" value="Entrar"/> 
		</td>
	</tr>
</tfoot>
</table>
</h:form>

</div>

<table align="center" width="500" border="0" cellspacing="0" cellpadding="0" style="margin: 0 auto">
	<tr>
		<td width="100%" height="3" class="azulMedio"></td>
	</tr>
	<tr>
		<td align="center" width="70%">
			<br><br>
			<b>Servidor, </b><br/>
			caso ainda não possua cadastro no sistemas,<br>
			 clique no link abaixo.  <br>
			 <c:if test="${ldapMBean.ldapHabilitado }">
				<a href="/admin/ldap/form_ldap_auto_cadastro.jsf">
				<img src="/shared/img/novo_usuario.gif" border="0" height="16"/><br>
				Cadastre-se
				</a>
			</c:if>
			 <c:if test="${!ldapMBean.ldapHabilitado }">
			 	<a href="/admin/auto_cadastro/form.jsf">
				<img src="/shared/img/novo_usuario.gif" border="0" height="16"/><br>
				Cadastre-se
				</a>
			 </c:if>

		</td>
	</tr>
</table>

<br><br><br>
<table align="center">
	<tr>
		<td><a href="http://www.sistemas.ufrn.br/download/Firefox.exe" target="_blank"><img src="/shared/img/firefox.jpg" border="0" width="20" height="20"/></a></td>
		<td><b>Este sistema é melhor visualizado utilizando o
			<a href="http://www.sistemas.ufrn.br/download/Firefox.exe" target="_blank">Mozilla Firefox</a>,
			para baixá-lo e instalá-lo,
			<a href="http://www.sistemas.ufrn.br/download/Firefox.exe" target="_blank">clique aqui</a></b>.
		</td>

		<td><a href="http://www.sistemas.ufrn.br/download/Firefox.exe"><img src="/shared/img/firefox.jpg" border="0" width="20" height="20"/></a></td>
	</tr>
</table>
</f:view>

<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
