<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<style>
	tr.header td {padding: 3px ; border-bottom: 1px solid #555; background-color: #eee;}
</style>
<f:view>
		<admin:caminho titulo="Detalhamento do material de treinamento" />
<br />
<h:form prependId="false">

		<c:set var="vazio" value=""/>
		
		<div class="infoAltRem" style="width: 80%">
		    <f:verbatim><img src="/shared/img/alterar.gif" style="overflow: visible;"/></f:verbatim>: Alterar
		    <f:verbatim><img src="/shared/img/delete.gif" style="overflow: visible;"/></f:verbatim>: Remover
		</div>

	<table class="formulario" width="80%">
		<caption>Lista de Materiais de Treinamento</caption>

		<c:forEach items="#{materialTreinamentoMBean.all}" var="linha">
			<c:set var="teste" value="${linha.descricao}"/>
			
			<c:if test="${ not empty _nome }">
				<tr>
					<td style="padding: 25px 0 0;" colspan="4"></td>
				</tr>
			</c:if>

			<c:set var="_nome" value="${linha.nome}"/>

			<tr>
				<td colspan="4" style="text-align: center;" class="subFormulario">Detalhamento do Material de Treinamento</td>
			</tr>
			<tr>
				<td align="left" class="subFormulario"><b>Nome do Material</b></td> 	
				<td align="left" class="subFormulario"><b>Tipo do Material</b></td>
				<td class="subFormulario"></td>
				<td class="subFormulario"></td> 	 		
			</tr>
	
			<tr class="componentes">
				<td align="left"> ${linha.nome} </td>
				<td align="left"> ${linha.descricao} </td>
				<td align="center">
					<h:commandLink id="alterar" title="Alterar" action="#{materialTreinamentoMBean.atualizar}">
					<f:param name="id" value="#{linha.id}"/>
					<h:graphicImage url="/img/geral/alterar.gif" alt="Alterar" title="Alterar"/>
					</h:commandLink>
				</td>
				<td align="center"><h:commandLink id="remover" title="Remover" action="#{materialTreinamentoMBean.remover}" 
				onclick="return confirm('Tem certeza que deseja remover esse Tipo de Material?');">
					<f:param name="id" value="#{linha.id}"/>
					<h:graphicImage url="/img/geral/remover.gif" alt="Remover" title="Remover"/>
					</h:commandLink>
				</td>
			</tr>

			<c:if test="${ not empty teste }">			
				<tr>
					<td class="subFormulario" align="left" colspan="4"><b>Descrição</b></th> 	
				</tr>
	
				<tr>
					<td colspan="4" align="justify">${linha.descricao}</td>
				</tr>
			</c:if>
			
			<c:set var="anexo" value="${linha.endereco}"/>
			<c:if test="${_anexo!= anexo}">			
				<tr>
					<td class="subFormulario" align="left" colspan="4"><b>Itens de ajuda desta funcionalidade</b></th> 	
				</tr>
				<tr>
					<td colspan="4" align="justify" id="link">Esta operação posui um(a) ${linha.tipoMaterialTreinamento.denominacao} de utilização 
						<a href="${linha.endereco}" target="_blank">clique aqui</a> para acessar.
					</td>
				</tr>
				
			</c:if>
	
	    </c:forEach>
	
	</table>
	
  <br />
</h:form>
<br />
<p style="text-align: center"></p>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>