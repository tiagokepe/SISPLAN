<!-- DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd"-->
<!--    charset=ISO-8859-1 -->
<%-- <%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %> --%>
<%-- <%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> --%>

<%-- Tags --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="/tags/ufrn" prefix="ufrn" %>
<%@ taglib uri="/tags/ufrnFunctions" prefix="sf" %>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="/tags/tomahawk" prefix="t"%>
<%@taglib uri="/tags/cewolf" prefix="cewolf" %>
<%@taglib uri="/tags/rich" prefix="rich"%>
<%@taglib uri="/tags/a4j" prefix="a4j"%>
<%@taglib uri="/tags/primefaces-p" prefix="p" %>
<%@taglib uri="/tags/jawr" prefix="jwr"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="admin"%>

<%-- Variáveis globais --%>
<jsp:useBean id="dataAtual" class="java.util.Date" scope="page" />

<c:set var="contexto" value="${pageContext.request.contextPath}" scope="application"/>
<c:set var="ctx" value="<%= request.getContextPath() %>"/>
<c:set var="ajaxScripts" value="false"/>
<%@page import="br.ufrn.comum.dominio.Sistema"%><html class="background">
    <head>
      <fmt:setBundle basename="Mensagens" scope="application" />

	  <title>${ configSistema['siglaSisplan'] } - ${ configSistema['nomeSisplan'] }</title>
		<link rel="shortcut icon" href="/shared/img/ufrn.ico" />

 		<script type="text/javascript" src="/shared/jsBundles/jawr_loader.js" ></script>
        <script type="text/javascript">
                JAWR.loader.style('/bundles/css/sigrh_base.css');
        		JAWR.loader.style('/css/ufrn_print.css', 'print');

        		JAWR.loader.script('/bundles/js/sigrh_base.js');
        </script>
    </head>

<body>

	<c:if test="${sessionScope.avisoBrowser != null}">
		<script type="text/javascript">
			window.open('/sigaa/public/navegador.jsp?ajaxRequest=true','','width=670,height=230, top=100, left=100, scrollbars' );
		</script>
		<% session.removeAttribute("avisoBrowser"); %>
	</c:if>


	<c:if test="${param.ajaxRequest == null and param.dialog == null and sessionScope.ajaxRequest == null}">
	<div id="container">

	<c:if test="${empty param.popup }">
	
	<div id="cabecalho">
		<div id="info-sistema">
			<h1> <span>${ configSistema['siglaInstituicao'] } - ${ configSistema['siglaSisplan'] } </span> - </h1>
			<h3>  ${ configSistema['nomeSisplan'] } </h3>

			<c:if test="${not empty sessionScope.usuario}">
			<div id="tempoSessao"></div>
			<span class="sair-sistema">
				<a href="/SISPLAN/logoff.jsf"> SAIR </a>
			</span>
			</c:if>
		</div>
		<c:set var="confirm" value="if (!confirm('Deseja cancelar a Operação? Todos os dados digitados serão perdidos!')) return false" scope="application"/>
		<c:set var="confirmDelete" value="if (!confirm('Confirma a remoção desta informação?')) return false" scope="application"/>
		
<%-- 		<f:view>
		<h:form>
		  KEPE
		</h:form>
		</f:view>
 --%>
		<div id="painel-usuario" <c:if test="${empty sessionScope.usuario}"> style="height: 20px;" </c:if>>
			<c:if test="${not empty sessionScope.usuario}">
			<div id="menu-usuario">
				<ul>
					<li class="menus">
						<a href="/admin/portal/index.jsf">Portal Admin</a>
					</li>
			
			
				<li class="caixa-postal">
					<a href="<%="/admin/abrirCaixaPostal.jsf?sistema="+String.valueOf(Sistema.SIGADMIN)%>" > 
					<c:if test="${ empty sessionScope.qtdMsgsNaoLidasCxPostal || sessionScope.qtdMsgsNaoLidasCxPostal <= 0 }">
					Caixa Postal					
					</c:if>
					<c:if test="${ not empty sessionScope.qtdMsgsNaoLidasCxPostal && sessionScope.qtdMsgsNaoLidasCxPostal > 0 }">
						<c:if test="${ sessionScope.qtdMsgsNaoLidasCxPostal > 999 }">
							Cx. Postal <font color="red" style="font-size:0.7em;">(99+)</font>
						</c:if>
						<c:if test="${ sessionScope.qtdMsgsNaoLidasCxPostal <= 999 }">
							Cx. Postal <font color="red" style="font-size:0.7em;">(${ sessionScope.qtdMsgsNaoLidasCxPostal })</font>
						</c:if>
					</c:if>
					</a>
				</li>
					
					
					<li class="chamado">
						<c:if test="${configSistema['caminhoAberturaChamado']==null}">
							<c:if test="${sistemaBean.IProjectAtivo}">
								<a href="#" onclick="window.open('/admin/abrirChamado.jsf?tipo=6&sistema=8&idUsuario=${configSistema['idUsuarioChamado']}', 'chamado', 'scrollbars=1,width=830,height=600')">Abrir Chamado</a>
							</c:if>
							<c:if test="${not sistemaBean.IProjectAtivo}">
								<a href="#" onclick="window.open('/admin/novoChamadoAdmin.jsf?sistema=8', 'chamado', 'scrollbars=1,width=830,height=600')">Abrir Chamado</a>
							</c:if>
						</c:if>
						<c:if test="${configSistema['caminhoAberturaChamado']!=null}">
							<a href="#" onclick="window.open('${configSistema['caminhoAberturaChamado']}', 'chamado', 'scrollbars=1,width=830,height=400')">Abrir Chamado</a>
						</c:if>
					</li>

					<li class="dados-pessoais">
						<c:if test="${ldapMBean.ldapHabilitado }">
							<c:if test="${ldapMBean.urlAlteracao != null}">
								<a href="#" onclick="window.open('${ldapMBean.urlAlteracao}','','width=670,height=430, top=100, left=100, scrollbars' )">Alterar senha</a>
							</c:if>
							<c:if test="${ldapMBean.permiteAlterarSenha }">
							<c:if test="${ ldapMBean.urlAlteracao == null}">
								<a href="#" onclick="window.open('/admin/public/usuario/alterar_dados.jsf','','width=670,height=430, top=100, left=100, scrollbars' )">Alterar senha</a>
							</c:if>
							</c:if>
						</c:if>
						<c:if test="${!ldapMBean.ldapHabilitado }">
							<a href="#" onclick="window.open('/admin/public/usuario/alterar_dados.jsf','','width=670,height=430, top=100, left=100, scrollbars' )">Alterar senha</a>
						</c:if>
						
					</li>
					
					<c:if test="${not sistemaBean.IProjectAtivo && acessoMenu.usuario != null && acessoMenu.gestorChamados }">
					<li class="chamados-abertos">
						<a href="<%="/admin/chamado/lista.jsf"%>" > 
						<c:if test="${ chamadoBean.qtdChamadosAbertos <= 0 }">
						Chamados					
						</c:if>
						<c:if test="${ chamadoBean.qtdChamadosAbertos > 0 }">
							<c:if test="${ chamadoBean.qtdChamadosAbertos > 999 }">
								Chamados <font color="red" style="font-size:0.7em;">(99+)</font>
							</c:if>
							<c:if test="${ chamadoBean.qtdChamadosAbertos  <= 999 }">
								Chamados <font color="red" style="font-size:0.7em;">(${ chamadoBean.qtdChamadosAbertos })</font>
							</c:if>
						</c:if>
						</a>
					</li>
					</c:if>
					
				</ul>
			</div>
			<div id="info-usuario">
				<p class="usuario">
					<c:if test="${not empty sessionScope.usuario}">
			 			${sessionScope.usuario.pessoa.nome }
			 			<c:if test="${ acesso.multiplosVinculos }">
			 			&nbsp;<a href="${ctx}/vinculos.jsf"><img src="/shared/img/group_go.png" alt="Alterar vínculo" title="Alterar vínculo"/></a>
			 			</c:if>
			 			<c:if test="${sessionScope.usuarioAnterior != null}">
			 				<a href="/sigrh/retornarUsuario.jsf">(Deslogar)</a>
		 				</c:if>
					</c:if>
				</p>
				<p class="unidade">
					
						<ufrn:format type="texto" length="40" valor="${sessionScope.usuario.unidade.nome}"/>
						(${sessionScope.usuario.unidade.codigoFormatado})
					
				</p>
				
				<p class="periodo-atual">

				</p>
			</div>
			</c:if>

		</div>
		
		<div id="menu-principal">
		</div>
	</div> <%-- Fim do div 'cabecalho' --%>
	
	</c:if>

	<div id="conteudo">
	</c:if>
	<%@include file="/WEB-INF/jsp/include/erros.jsp"%>

	<% if ( request.isSecure() ) { %>
		<c:set var="protocolo" value="https"/>
	<% } else { %>
		<c:set var="protocolo" value="http"/>
	<% }  %>
	
  </body>
</html>  