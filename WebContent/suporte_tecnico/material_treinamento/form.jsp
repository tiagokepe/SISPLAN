<%@ include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<%@include file="/WEB-INF/jsp/include/ajax_tags.jsp"%>

<f:view>
	<%@include file="/portal/menu_administracao.jsp" %>
		<admin:caminho titulo="Materiais de Treinamento" />

	<ufrn:manual codigo="1234"/>

	 <div class="infoAltRem" style="width: 100%">
	    <h:graphicImage value="/img/geral/buscar.gif"style="overflow: visible;"/>
	  		<a href="${ctx}/suporte_tecnico/material_treinamento/lista.jsf"> Buscar Materiais de Treinamento</a>
	 </div>

	<h:form id="form">
		<table class="formulario" width="80%">
			<caption>Cadastrar Materias de Treinamento</caption>
				<tr>
					<th class="obrigatorio">Código da Operação: </th>
					<td><h:inputText value="#{materialTreinamentoMBean.obj.codigoUc}" maxlength="200" style="width: 300px;" id="codigo"/></td>
				</tr>
			    <tr>
				  <th class="obrigatorio">Nome:</th>
					<td>
						<h:inputText value="#{materialTreinamentoMBean.obj.nome}" maxlength="60" style="width: 300px;" id="nome"/>
						<h:inputHidden value="#{materialTreinamentoMBean.confirmButton}" id="confirmButton" />
						<h:inputHidden value="#{materialTreinamentoMBean.obj.id}" id="objid" />
					</td>
				</tr>
				<tr>
   				  <th class="obrigatorio">URL:</th>
					<td>
						<h:inputText value="#{materialTreinamentoMBean.obj.endereco}" maxlength="200" style="width: 300px;" id="url"/>
					</td>
			    </tr>
				<tr>
   				  <th class="obrigatorio">Tipo de Material:</th>
					<td>
						<h:selectOneMenu value="#{materialTreinamentoMBean.obj.tipoMaterialTreinamento.id}" id="tipo">
						<f:selectItem itemValue="0" itemLabel="-- SELECIONE --" />
						<f:selectItems value="#{tipoMaterialMBean.allCombo}" />
						</h:selectOneMenu>
					</td>
			    </tr>
				<tr>
				  <th>Descrição:</th>
					<td>
						<h:inputTextarea value="#{materialTreinamentoMBean.obj.descricao}" style="width: 300px;" id="descricao"/>
					</td>
				</tr>
				<tr>
				  <th>Ativo:</th>
					<td>
						<h:selectOneRadio value="#{materialTreinamentoMBean.obj.ativo}" id="ativo" >
							<f:selectItem itemLabel="Sim" itemValue="true" />
							<f:selectItem itemLabel="Não" itemValue="false" />
						</h:selectOneRadio>
					</td>
				</tr>
				<tr>
					<th class="obrigatorio">Sistema:</th>
					<td>
						<h:selectOneMenu value="#{materialTreinamentoMBean.obj.sistema.id}" id="sistema">
							<a4j:support event="onchange" reRender="subsistemas"/>
							<f:selectItem itemLabel="--SELECIONE--" itemValue="0"/>
							<f:selectItems value="#{sistemaBean.allCombo}"/>
						</h:selectOneMenu>
					</td>
				</tr>
				<tr>
					<th>Sub-sistema:</th>
					<td>
						<h:selectOneMenu id="subsistemas" value="#{materialTreinamentoMBean.obj.subSistema.id}">
							<f:selectItem itemLabel="--SELECIONE--" itemValue="0"/>
							<f:selectItems value="#{materialTreinamentoMBean.subSistemasCombo}"/>
						</h:selectOneMenu>
					</td>
				</tr>
			<tfoot>
				<tr>
					<td colspan="3">
						<h:commandButton value="#{materialTreinamentoMBean.confirmButton}" id="cadastrar"
							action="#{materialTreinamentoMBean.cadastrar}" /> 
						<h:commandButton value="Cancelar" onclick="#{confirm}" id="cancelar"
							action="#{materialTreinamentoMBean.cancelar}" immediate="true" />
					</td>
				</tr>
			</tfoot>
		</table>
	</h:form>
   <br />
 		<center>
			<h:graphicImage value="/img/geral/required.gif"style="overflow: visible;"/> 
			<span class="fontePequena"> Campos de preenchimento obrigatório. </span> 
			<br><br>
		</center>
 
</f:view>
<%@ include file="/WEB-INF/jsp/include/rodape.jsp"%>