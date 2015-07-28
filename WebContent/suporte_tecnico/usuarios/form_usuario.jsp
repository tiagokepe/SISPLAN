<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<f:view>
<%@include file="/portal/menu_administracao.jsp" %>

<c:if test="${ usuarioMBean.obj.id == 0 }">
<admin:caminho titulo="Cadastro de Usuário"/>
</c:if>
<c:if test="${ usuarioMBean.obj.id != 0 }">
<admin:caminho titulo="Alterar Dados de Usuário"/>
</c:if>
<h:form id="form">

<table class="formulario" width="75%">
	<caption>Dados do Usuário</caption>
	<tr>
		<th nowrap="nowrap" style="width: 150px" class="obrigatorio">Tipo de Usuário: </th>
		<td>
			<h:selectOneMenu value="#{usuarioMBean.obj.tipo.id}" id="selectTipoUsuario">
				<f:selectItem itemLabel="-- SELECIONE --" itemValue="0"/>
				<f:selectItems value="#{usuarioMBean.tiposUsuario}"/>
				<a4j:support event="onclick" reRender="vinculos,tituloVinculos" 
						actionListener="#{usuarioMBean.alterouTipo}">
					<f:attribute name="tipo" value="#{usuarioMBean.obj.tipo.id}" />
				</a4j:support>
			</h:selectOneMenu>
		</td>
		<th class="obrigatorio">Tipo de Pessoa:</th>
		<td>
			<h:selectOneMenu value="#{usuarioMBean.obj.pessoa.tipo}" id="selectTipoPessoa" onchange="submit()">
				<f:selectItem itemLabel="Física" itemValue="F" />
				<f:selectItem itemLabel="Jurídica" itemValue="J"/>
			</h:selectOneMenu>
		</td>
	</tr>
	<tr>
		<th class="obrigatorio">Nome: </th>
		<td colspan="3"><h:inputText id="inputNome" value="#{usuarioMBean.obj.pessoa.nome}" maxlength="100" style="width: 430px"/> </td>
	</tr>
	<tr>
		<th class="obrigatorio">CPF/CNPJ: </th>
		<td>
			<h:inputText id="inputCPF" value="#{usuarioMBean.obj.pessoa.cpf_cnpj}" rendered="#{usuarioMBean.pessoaFisica}"
					onkeypress="return(formataCPF(this, event, null));"  size="14" maxlength="14" converter="convertCpf" >
				<a4j:support event="onchange" reRender="vinculos,tituloVinculos" actionListener="#{usuarioMBean.alterouCPF}" >
					<f:attribute name="cpf" value="#{usuarioMBean.obj.pessoa.cpf_cnpj}" />
				</a4j:support>
			</h:inputText>
			
			<h:inputText id="inputCNPJ" value="#{usuarioMBean.obj.pessoa.cpf_cnpj}" rendered="#{usuarioMBean.pessoaJuridica}"
					onkeypress="return(formataCNPJ(this, event, null));"  size="18" maxlength="18" converter="convertCpf" >
				<a4j:support event="onchange" reRender="vinculos,tituloVinculos" actionListener="#{usuarioMBean.alterouCPF}" >
					<f:attribute name="cpf" value="#{usuarioMBean.obj.pessoa.cpf_cnpj}" />
				</a4j:support>
			</h:inputText>
		</td>
		<th class="obrigatorio">Sexo:</th>
		<td>
			<h:selectOneMenu id="selectPessoaSexo" value="#{usuarioMBean.obj.pessoa.sexo}">
				<f:selectItems value="#{usuarioMBean.mascFem}"/>
			</h:selectOneMenu>
		</td>
	</tr>
	<tr>
		<th>
			<h:panelGroup id="tituloVinculos">
				<h:outputLabel rendered="#{ not empty usuarioMBean.vinculos }">
					Vínculo: <span class="required">&nbsp;</span>
				</h:outputLabel>
			</h:panelGroup>
		</th>
		<td colspan="3">
			<h:panelGroup id="vinculos">
				<h:selectOneRadio value="#{ usuarioMBean.obj.idServidor }"
						rendered="#{ not empty usuarioMBean.vinculos }"
						layout="pageDirection" >
					<f:selectItems value="#{ usuarioMBean.vinculos }" />
				</h:selectOneRadio>
			</h:panelGroup>
		</td>
	</tr>
	<tr>
		<th class="obrigatorio">Data de Nascimento: </th>
		<td>
			<t:inputCalendar id="calDataNascimento" value="#{usuarioMBean.obj.pessoa.dataNascimento}" renderAsPopup="true"
				renderPopupButtonAsImage="true" onkeypress="return(formatarMascara(this,event,'##/##/####'))" size="15" maxlength="10" title="Data de Nascimento">
				<f:convertDateTime pattern="dd/MM/yyyy"/>
			</t:inputCalendar>
		</td>
		<th>Inativo: </th>
		<td>
			<h:selectOneRadio value="#{usuarioMBean.obj.inativo}">
				<f:selectItems value="#{usuarioMBean.simNao}"/>
			</h:selectOneRadio>
		</td>
	</tr>
	<tr>
		<th class="obrigatorio">Login: </th>
		<td><h:inputText id="inputLogin" value="#{usuarioMBean.obj.login}" size="15" maxlength="20"/> </td>
		<th ${ usuarioMBean.obj.id == 0 ? 'class=\'obrigatorio\'' : '' } >Senha: </th>
		<td><h:inputSecret id="inputSenha" value="#{usuarioMBean.obj.senha}" size="15" maxlength="20"/> </td>
	</tr>
	<tr>
		<th class="obrigatorio">E-Mail: </th>
		<td><h:inputText id="inputEmail" value="#{usuarioMBean.obj.email}" size="30" maxlength="100"/> </td>
		<th>Ramal: </th>
		<td><h:inputText id="inputRamal" value="#{usuarioMBean.obj.ramal}" size="15" maxlength="20"/> </td>
	</tr>
	<tr>
		<th class="obrigatorio">Unidade: </th>
		<td colspan="3">
			<h:inputText value="#{usuarioMBean.obj.unidade.nome}" id="unidade" style="width: 430px;" />
			<h:inputHidden value="#{usuarioMBean.obj.unidade.id}" id="idUnidade" />
	 
			<rich:suggestionbox for="unidade" height="100" width="430"  minChars="3" id="suggestion"
			   	suggestionAction="#{unidadeAutoCompleteMBean.autocompleteNomeUnidade}" var="_unidade" 
			   	fetchValue="#{_unidade.codigoNome}">
			 
			      <h:column>
					<h:outputText value="#{_unidade.codigoNome}" /> 
			      </h:column> 
			 
			      <a4j:support event="onselect" reRender="idUnidade">
					<f:setPropertyActionListener value="#{_unidade.id}" target="#{usuarioMBean.obj.unidade.id}" />
			      </a4j:support>  
			</rich:suggestionbox>
		</td>
	</tr>
	<tfoot>
		<tr>
			<td colspan="4">
				<h:commandButton value="Cadastrar" action="#{usuarioMBean.cadastrar}" rendered="#{usuarioMBean.obj.id == 0}"/>
				<h:commandButton value="Atualizar" action="#{usuarioMBean.cadastrar}" rendered="#{usuarioMBean.obj.id != 0}"/>
				<h:commandButton value="Cancelar" action="#{usuarioMBean.cancelar}" immediate="true" onclick="#{confirm}"/>
			</td>
		</tr>
	</tfoot>
</table>

<%@include file="/WEB-INF/jsp/include/mensagem_obrigatoriedade.jsp" %>

</h:form>

</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>