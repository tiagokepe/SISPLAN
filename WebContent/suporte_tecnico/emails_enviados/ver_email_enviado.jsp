<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/shared/css/mailbox_novo.css" />

<f:view>

	<h:form id="formVerEmail">

		<admin:caminho titulo="Ver E-mail Enviado" />

		<table class="visualizacao" width="100%">

			<caption>Dados do E-mail</caption>
			<tr>
				<th>Data de Envio:</th>
				<td><ufrn:format valor="${emailEnvidadoMBean.obj.enviadoEm}"
					type="data" /></td>
			</tr>
			<tr>
				<th nowrap="nowrap">Nome do Destinat�rio:</th>
				<td>${emailEnvidadoMBean.obj.paraNome}</td>
			</tr>
			<tr>
				<th nowrap="nowrap">Email do Destinat�rio:</th>
				<td>${emailEnvidadoMBean.obj.paraEmail}</td>
			</tr>
			<tr>
				<th>Assunto:</th>
				<td>${emailEnvidadoMBean.obj.assunto}</td>
			</tr>
			<tr>
				<th style="vertical-align: top;">Conte�do:</th>
				<td width="80%">
					<div style="width:100%; height: 200px; overflow: auto; text-align: left;">
							${emailEnvidadoMBean.obj.conteudo}
					</div>
					</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>

		</table>



	</h:form>


	<br />
	<center><a href="javascript:history.go(-1)"> <h:outputText
		value="<< Voltar" /></a></center>

</f:view>

<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>