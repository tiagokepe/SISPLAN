<%@ include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<%@include file="/WEB-INF/jsp/include/ajax_tags.jsp" %>

<f:view>
	<%@include file="/portal/menu_administracao.jsp" %>
	<admin:caminho titulo="Tipo de Materiais de Treinamento" />

   <h:form>
	 <div class="infoAltRem" style="width: 100%">
	  <h:graphicImage value="/img/geral/listar.gif"style="overflow: visible;"/>
	  		<a href="${ctx}/suporte_tecnico/tipo_material_treinamento/lista.jsf"> Listar Tipo Material de Treinamento</a>
	 </div>
    </h:form>

	<h:form prependId="false">
	
		<table class="formulario" width="70%" border="1">
		<caption> Dados para o Cadastro de Tipos de Materiais</caption>
	
		<tr>
			<td align="center"> Nome: <img src="/shared/img/required.gif" /> 
					<h:inputText id="nome" value="#{tipoMaterialMBean.obj.denominacao}" size="60" maxlength="90" />
					<h:inputHidden value="#{tipoMaterialMBean.confirmButton}" id="confirmButton" />
					<h:inputHidden value="#{tipoMaterialMBean.obj.id}" id="objid" />
			</td>
	   	 </tr>
		  <tfoot>
		<tr>
			<td colspan="3">
				<h:commandButton value="#{tipoMaterialMBean.confirmButton}" 
					action="#{ tipoMaterialMBean.cadastrar}" id="cadastrar" />
				<h:commandButton value="Cancelar" 
					onclick="#{confirm}" action="#{tipoMaterialMBean.cancelar}" immediate="true"/>
			</td>
		</tr>	
		</tfoot>		
		</table>
		<div>
			<tr>
				<center><img src="/shared/img/required.gif" /> <span
					class="fontePequena"> Campo de preenchimento obrigatório. </span></center>
			</tr>
		</div>
	</h:form>
</f:view>

<%@ include file="/WEB-INF/jsp/include/rodape.jsp"%>