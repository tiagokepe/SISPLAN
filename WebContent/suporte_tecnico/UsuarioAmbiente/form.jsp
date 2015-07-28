<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Tipos de Ambiente"/>

	<h:form prependId="false">
		<table class="formulario">
			<caption>Cadastro de Usuários de Ambiente</caption>
			<tr>
				<th>Login: <span class="required">&nbsp;</span></th>
				<td><h:inputText id="login" value="#{ usuarioAmbienteMBean.obj.login }"
					readonly="#{usuarioAmbienteMBean.readOnly}" size="20"
					disabled="#{usuarioAmbienteMBean.readOnly}" /></td>
			</tr>
			<tr>
				<th>Senha: <span class="required">&nbsp;</span></th>
				<td><h:inputSecret id="senha" value="#{ usuarioAmbienteMBean.obj.senha }"
					readonly="#{usuarioAmbienteMBean.readOnly}" size="20"
					disabled="#{usuarioAmbienteMBean.readOnly}" /></td>
			</tr>
			<tr>
				<th>Instituição: <span class="required">&nbsp;</span></th>
				<td><h:inputText id="instituicao" value="#{ usuarioAmbienteMBean.obj.instituicao }"
					readonly="#{usuarioAmbienteMBean.readOnly}" size="70"
					disabled="#{usuarioAmbienteMBean.readOnly}" /></td>
			</tr>
			<tr>
				<th>Pessoa: <span class="required">&nbsp;</span></th>
				<td>
					<h:inputText value="#{usuarioAmbienteMBean.nomePessoa}" id="pessoa" style="width: 430px;" 
					readonly="#{usuarioAmbienteMBean.readOnly}" disabled="#{usuarioAmbienteMBean.readOnly}"/>
	 				<h:inputHidden id="idPessoa" value="#{usuarioAmbienteMBean.obj.idPessoa}"/>
					<rich:suggestionbox for="pessoa" height="100" width="430"  minChars="3" id="suggestion"
					   	suggestionAction="#{pessoaAutoCompleteMBean.autocompleteNomePessoaFisica}" var="_pessoa" 
					   	fetchValue="#{_pessoa.nome}">
					 
					      <h:column>
						<h:outputText value="#{_pessoa.nome}" /> 
					      </h:column> 
					 
					      <a4j:support event="onselect" reRender="idPessoa">
						<f:setPropertyActionListener value="#{_pessoa.id}" target="#{ usuarioAmbienteMBean.obj.idPessoa }" />
					      </a4j:support>  
					</rich:suggestionbox>
				
				</td>
			</tr>
			<tr>
				<th>Ambiente: <span class="required">&nbsp;</span></th>
				<td><h:selectOneMenu id="ambiente" value="#{ usuarioAmbienteMBean.obj.ambiente.id }">
						<f:selectItem itemLabel="--SELECIONE--" itemValue="0"/>
						<f:selectItems value="#{tipoAmbienteMBean.allCombo}"/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<th>Ativo: </th>
				<td><h:selectOneRadio id="ativo" value="#{ usuarioAmbienteMBean.obj.ativo }"
					readonly="#{usuarioAmbienteMBean.readOnly}"
					disabled="#{usuarioAmbienteMBean.readOnly}">
						<f:selectItems value="#{usuarioAmbienteMBean.simNao}"/>
					</h:selectOneRadio></td>
			</tr>
			<tfoot>
				<tr>
					<td colspan="2">
						<h:inputHidden id="id" value="#{usuarioAmbienteMBean.obj.id}" /> 
						<h:commandButton value="#{usuarioAmbienteMBean.confirmButton}" action="#{usuarioAmbienteMBean.cadastrar}" id="cadastrar"/> 
						<c:if test="${usuarioAmbienteMBean.obj.id > 0}">
							<h:commandButton value="<< Voltar" action="#{usuarioAmbienteMBean.listar}" immediate="true" />
						</c:if> 
						<h:commandButton value="Cancelar" action="#{usuarioAmbienteMBean.cancelar}" onclick="#{confirm}" immediate="true" id="cancelar" /></td>
				</tr>
			</tfoot>
		</table>
		<%@include file="/WEB-INF/jsp/include/mensagem_obrigatoriedade.jsp"%>
	</h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
