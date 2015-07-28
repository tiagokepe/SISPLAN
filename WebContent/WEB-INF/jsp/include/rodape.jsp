<%@page import="br.ufrn.comum.dominio.Sistema"%>
<%@page import="br.ufrn.comum.dominio.UsuarioGeral"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@page import="br.ufrn.arq.util.AmbienteUtils"%>

<div class="clear"> </div>
<c:if test="${param.ajaxRequest == null}">
<%
	UsuarioGeral usuario = (UsuarioGeral) request.getSession().getAttribute("usuario");
%>
	<br />

	<c:if test="${not empty sessionScope.usuario}">
	<c:if test="${hideSubsistema == null}">
	<% if( usuario.isUserInSistema(Sistema.SIGADMIN) ) { %>	
		<div style="width: 80%; text-align: center; margin: 0 auto;">
			 <a href="/admin/portal/index.jsf">Portal da Administração</a>
		</div>
	<% } %>
	</c:if>
	</c:if>


	<div id="rodape">
		<p>	
		${ configSistema['siglaSigadmin']} | ${ configSistema['nomeResponsavelInformatica']} - ${ configSistema['siglaInstituicao'] } - ${ configSistema['telefoneHelpDesk'] } | Copyright &copy; <%= br.ufrn.arq.util.UFRNUtils.getCopyright(2009) %> - UFRN - <%= br.ufrn.arq.util.AmbienteUtils.getNomeServidorComInstancia() %>
		<%  if (AmbienteUtils.dadosVersao("versao_admin") != null) { %> 
			<a onclick="javascript:versao();">v<%=AmbienteUtils.dadosVersao("versao_admin").get("sistema") %></a>
		<%} %>
		<small><ufrn:format type="dataHora" name="dataAtual" /></small>	
		</p>
	</div>


	<!-- Fim dos containers -->
	</div>
	</div>

<div id="painel-mensagem-envio"> </div>

</c:if>

</body>
</html>
<%  if (AmbienteUtils.dadosVersao("versao_admin") != null) { %> 
	<script type="text/javascript" charset="UTF-8">
		function versao(){
			var msg='';
			msg+='${ configSistema['siglaSigadmin'] } <%=AmbienteUtils.dadosVersao("versao_admin").get("sistema") %>,  publicado em: <%=AmbienteUtils.dadosVersao("versao_admin").get("dataPublicacao") %>\n\n';
			msg+='Depend\u00eancias:\n';
			msg+='Arquitetura <%=AmbienteUtils.dadosVersao("versao_admin").get("arquitetura") %>\n';
			msg+='Entidades Comuns <%=AmbienteUtils.dadosVersao("versao_admin").get("entidadesComum") %>\n';
			msg+='Servicos Integrados <%=AmbienteUtils.dadosVersao("versao_admin").get("servicosIntegrados") %>\n\n';
			msg+='Copyrigth SINFO/UFRN';
			alert(msg);
		}	
	</script>
<%} %>
<c:if test="${ sessionScope.alertErro != null }">
<script type="text/javascript">
	alert('${ sessionScope.alertErro }');
</script>
<c:remove var="alertErro" scope="session"/>
</c:if>
<script language="javascript">
Relogio.init(<%= session.getMaxInactiveInterval() / 60 %>);
</script>