<%@taglib uri="/tags/tomahawk" prefix="t"%>

<%-- MENU DE OP��ES PARA O DOCENTE --%>
<div id="menu-dropdown">
<div class="wrapper">
<h:form id="menuform">
	<input type="hidden" name="jscook_action"/>
	
<t:jscookMenu id="menu" layout="hbr" theme="ThemeOffice" styleLocation="/css/jscookmenu">

	<t:navigationMenuItem id="comunicacao" itemLabel="Comunica��o" icon="/img/menu/comunicacao.png" rendered="#{ acessoMenu.comunicacao || acessoMenu.questionarios || acessoMenu.gestorChamados}">
		
		<t:navigationMenuItem id="noticia" itemLabel="Not�cias" rendered="#{ acessoMenu.comunicacao}">
			<t:navigationMenuItem itemLabel="Cadastrar" id="cadastrar" action="#{noticiaPortalBean.iniciar}"/>
			<t:navigationMenuItem itemLabel="Alterar/Remover" action="#{noticiaPortalBean.redirectNoticias}"/>
			<t:navigationMenuItem itemLabel="Gerenciar Permiss�o Cadastro Not�cia/Evento"  split="true" >
			<t:navigationMenuItem itemLabel="Inserir" itemValue="/configuracoes/gerenciaMensagemPortal/form.jsf" action="#{usuarioPortal.iniciar}"/>
			<t:navigationMenuItem itemLabel="Listar/Alterar" itemValue="/configuracoes/gerenciaMensagemPortal/form.jsf" action="#{usuarioPortal.listar}"  rendered="#{ acessoMenu.administracao }" />
		</t:navigationMenuItem>
		</t:navigationMenuItem>

		<t:navigationMenuItem itemLabel="Comunica��o em Grupo" id="comunicacaoGrupo" rendered="#{ acessoMenu.comunicacao}">
			<t:navigationMenuItem itemLabel="Notifica��es" id="notificacoes">
				<t:navigationMenuItem itemLabel="Notificar" action="#{envioNotificacaoBean.preCadastro}"/>
				<t:navigationMenuItem itemLabel="Consultar Enviadas" action="#{envioNotificacaoBean.listarEnviadas}"/>
				<t:navigationMenuItem itemLabel="Reativar Notifica��o" action="#{ignoracaoNotificacaoMBean.direcionaReativarNotificacao}"/>
			</t:navigationMenuItem>
			<t:navigationMenuItem itemLabel="Ger�ncia de Grupos" id="gerenciaGrupo" rendered="#{ acessoMenu.suporte }">
				<t:navigationMenuItem itemLabel="Gerenciar Grupos" split="true"action="#{grupoDestinatariosBean.listar}"/>
				<%--MUDANCA ESPECIFICA IFPR  --%>
				<t:navigationMenuItem itemLabel="Gerenciar Grupos por Unidades" action="#{grupoDestinatariosPersonalizadoBean.iniciar}" />
				<!-- FIM DA MUDANCA -->
				<t:navigationMenuItem itemLabel="Gerenciar Permiss�es de acesso aos grupos" action="#{permissaoEnvioBean.listar}"/>
			</t:navigationMenuItem>
		</t:navigationMenuItem>
		
		<t:navigationMenuItem split="true" id="notificacaoUsuarioLogado" itemLabel="Notifica��o On-Line para usu�rios Logados" rendered="#{ acessoMenu.suporte }">
			<t:navigationMenuItem itemLabel="Inserir" itemValue="/comunicacao/notificacao_usuarios/adicionar.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Remover" itemValue="/comunicacao/notificacao_usuarios/remover.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Tela de Aviso Ap�s Logon" id="telaAvisoLogon" rendered="#{ acessoMenu.suporte }">
			<t:navigationMenuItem itemLabel="Cadastrar" itemValue="/comunicacao/tela_aviso/form.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Listar" itemValue="/comunicacao/tela_aviso/lista.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		
		<t:navigationMenuItem split="true" rendered="#{acessoMenu.comunicacao}"/>	
		
		<t:navigationMenuItem itemLabel="Gest�o de Question�rio" id="questinario"  
			rendered="#{acessoMenu.questionarios}">
				
				<t:navigationMenuItem itemLabel="Cadastrar Question�rio" id="cadastrarQuestinario" 
					action="#{questionarioBean.preCadastrar}"  
					rendered="#{ acessoMenu.gestorQuestionarioAvaliacao}"/>
				
				<t:navigationMenuItem itemLabel="Consultar Respostas Individuais" 
						action="#{relatorioQuestionarioBean.consultarRespostasIndividuais}"/>
				
				<t:navigationMenuItem itemLabel="Alterar Question�rio" id="listarAlterarQuestinario" 
					action="#{questionarioBean.listar}" 
					rendered="#{ acessoMenu.gestorQuestionarioAvaliacao}"/>
				
				<t:navigationMenuItem itemLabel="Aplica��o de Question�rio" split="true"  
					rendered="#{ acessoMenu.gestorQuestionarioAvaliacao}">
					<t:navigationMenuItem itemLabel="Cadastrar" action="#{questionarioAplicadoBean.preCadastrar}"/>
					<t:navigationMenuItem itemLabel="Gerenciar"  action="#{questionarioAplicadoBean.listar}"/>
				</t:navigationMenuItem>
				
				<t:navigationMenuItem itemLabel="Reativar Question�rio para Usu�rio" id="reativarUsuarioQuestinario" 
					action="#{questionarioAplicadoBean.iniciarReativarQuestionarioUsuarios}"  
					rendered="#{ acessoMenu.gestorQuestionarioAvaliacao}"/>
					
				<t:navigationMenuItem split="true" rendered="#{acessoMenu.gestorQuestionarioAvaliacao}"/>	
				
				<t:navigationMenuItem itemLabel="Relat�rios">
					<t:navigationMenuItem itemLabel="Relat�rio Sint�tico" 
						action="#{relatorioQuestionarioBean.consultarSintetico}"/>
					<t:navigationMenuItem itemLabel="Relat�rio Quantitativo" 
						action="#{relatorioQuestionarioBean.consultarQuantitativo}"/>
				</t:navigationMenuItem>
		</t:navigationMenuItem>
		
		
		<t:navigationMenuItem itemLabel="Gest�o de Chamados" action="#{chamadoBean.listar}" rendered="#{ acessoMenu.gestorChamados && not sistemaBean.IProjectAtivo }">
			<t:navigationMenuItem itemLabel="Listar Chamados" action="#{chamadoBean.listar}"/>
			<t:navigationMenuItem itemLabel="Cadastrar Chamado" action="#{chamadoBean.iniciarCadastroAtendimento}"/>
			<t:navigationMenuItem itemLabel="Listar/Alterar Situa��o de Chamado" action="#{statusChamadoBean.listar}"/>
			<t:navigationMenuItem itemLabel="Listar/Alterar Tipo de Chamado" action="#{tipoChamadoBean.listar}"/>
			<t:navigationMenuItem itemLabel="Listar/Alterar Tipo de Log de Chamado" action="#{tipoLogChamadoBean.listar}"/>
		</t:navigationMenuItem>
			
	</t:navigationMenuItem>



	<t:navigationMenuItem itemLabel="Gest�o de Unidades" id="gestao" icon="/img/menu/gestao.png" rendered="#{ acessoMenu.gestao || acessoMenu.suporte || acessoMenu.gestorUnidades || acessoMenu.gestorOrganizacional }">
		<t:navigationMenuItem itemLabel="Cadastro de Unidades" id="unidadesAdministrativasAcademicas" rendered="#{ acessoMenu.gestao || acessoMenu.gestorUnidades  }">
			<t:navigationMenuItem itemLabel="Altera��o do Nome Oficial da Unidade" id="alteracaoNomeOficialUnidade" actionListener="#{unidadeAdminMBean.popularListagem}" rendered="#{acessoMenu.gestao || acessoMenu.gestorUnidades  }"/>
			<t:navigationMenuItem itemLabel="Cadastrar" id="cadastrarUnidade" actionListener="#{unidadeAdminMBean.popularCadastro}" rendered="#{acessoMenu.gestao || acessoMenu.gestorUnidades  }"/>
			<t:navigationMenuItem itemLabel="Listar/Alterar" id="listarAlterarUnidade" actionListener="#{unidadeAdminMBean.popularListagem}" rendered="#{acessoMenu.gestao || acessoMenu.gestorUnidades  }"/>
			<t:navigationMenuItem itemLabel="Sincronizar Unidades" id="sincronizarUnidade" action="#{sincronizarUnidadesMBean.iniciar}" rendered="#{acessoMenu.gestao || acessoMenu.gestorUnidades }"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Hierarquia Organizacional de Unidades" id="hierarquiaOrganizacional" action="#{unidadeAdminMBean.hierarquiaUnidades}" rendered="#{ acessoMenu.gestao ||  acessoMenu.gestorUnidades  }" />
		<t:navigationMenuItem itemLabel="Respons�veis por Unidade" id="responsaveisUnidades">
			<t:navigationMenuItem itemLabel="Listar/Alterar" id="responsabilidadeUnidade" rendered="#{ acessoMenu.suporte }" actionListener="#{unidadeAdminMBean.popularListagem}"/>
			<t:navigationMenuItem itemLabel="Buscar Responsabilidades por Servidor" id="unidadesResponsabilizadas" action="#{unidadeAdminMBean.consultarUnidadesResponsabilizadas}" rendered="#{acessoMenu.gestorUnidades  }"/>
			<t:navigationMenuItem itemLabel="Adicionar Unidades Extras aos Usu�rios" id="usuarioUnidade" action="#{ usuarioUnidadeMBean.init }"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Sincronizar Unidades SIGEO" id="sincronizarUnidadesSIGEO" action="#{ unidadeAdminMBean.sincronizaUnidadesSigeo }" rendered="false"/>
	</t:navigationMenuItem>
	
	<t:navigationMenuItem itemLabel="Usu�rios" id="menuUsuarios" icon="/img/menu/application_key.png" rendered="#{ acessoMenu.gestao || acessoMenu.consultaUsuario || acessoMenu.suporte  }">
		<t:navigationMenuItem itemLabel="Consultar Usu�rios" id="consultarUsuarios" action="#{ implantarPermissoesMBean.init }" />
		<t:navigationMenuItem itemLabel="Permiss�es" id="menupermissoes" rendered="#{acessoMenu.gestao || acessoMenu.suporte}">
			
			<t:navigationMenuItem itemLabel="Autorizar Permiss�es" id="autorizarPermissaoGestao" action="#{ autorizarPermissoesMBean.listar }" rendered="#{ acessoMenu.gestao }"/>
			
			<t:navigationMenuItem itemLabel="Implantar Permiss�es" id="implantarPermissaoGestao" action="#{ implantarPermissoesMBean.init }" rendered="#{ acessoMenu.gestao || acessoMenu.suporte }"/>
			<t:navigationMenuItem itemLabel="Transferir Permiss�es" id="transferirPermissaoGestao" action="#{ transferirPermissoesMBean.init }" rendered="#{ acessoMenu.suporte }"/>
			<t:navigationMenuItem itemLabel="Relat�rio de Usu�rios por Papel" id="relatorioUsuarioPapel" itemValue="/configuracoes/papeis/lista_usuario_papel.jsf" rendered="#{ acessoMenu.gestao }" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Gestores de Permiss�es" id="gestoresPermissao2" itemValue="/configuracoes/gestores/lista.jsf" rendered="#{acessoMenu.gestorPermissoesGlobal}" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Gest�o de Usu�rios" id="gestaoUsuarios" rendered="#{acessoMenu.suporte}">
			<t:navigationMenuItem itemLabel="Cadastrar Usu�rio" id="gestaoUsuariosCadastrarUsuario" action="#{usuarioMBean.form}"/>
			<t:navigationMenuItem itemLabel="Alterar Dados de Usu�rio" id="gestaoUsuariosAlterarDadosUsuario" action="#{usuarioMBean.init}"/>
			<t:navigationMenuItem itemLabel="Confirma��o de Cadastro de Usu�rios" id="gestaoUsuariosConfirmacaoCadastroUsuario" action="#{autoCadastroUsuarioMBean.listaConfirmacaoManual}"/>
			<t:navigationMenuItem itemLabel="Associar Servidor a Usu�rio" id="gestaoUsuariosAssociarServidorUsuario" action="#{associarServidorUsuarioMBean.init}"/>
			<t:navigationMenuItem itemLabel="Permiss�es de acesso aos sistemas" id="gestaoUsuariosPermissoesAcessoSistema" action="#{usuarioPermissaoSistemaMBean.init}"/>
		</t:navigationMenuItem>
	</t:navigationMenuItem>
	
	<t:navigationMenuItem itemLabel="Auditoria" id="auditoria" icon="/img/menu/auditoria.png" rendered="#{ acessoMenu.suporte || acessoMenu.auditoria }">
		<t:navigationMenuItem itemLabel="Consultar Erros" id="consultarErros" itemValue="/auditoria/registro_entrada/erros.jsf" actionListener="#{menu.redirecionar}"/>		
		<t:navigationMenuItem itemLabel="Consultar Logs" id="consultarLogs">
			<t:navigationMenuItem itemLabel="Registro de Entrada" id="registroEntrada" itemValue="/auditoria/registro_entrada/consulta.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Registro de Acesso P�blico" id="registroAcessoPublico" itemValue="/auditoria/registro_acesso_publico/consulta.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Logar como" id="logarComo" itemValue="/auditoria/logar_como/consulta.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Log Opera��o" id="logOperacao" itemValue="/auditoria/log_operacao/consulta_log.jsf" actionListener="#{menu.redirecionar}"/>
			<%-- 
			<t:navigationMenuItem itemLabel="Log do Servidor" id="logServidor" itemValue="/auditoria/consulta_log/consulta.jsf" actionListener="#{menu.redirecionar}"/>
			--%>
			<t:navigationMenuItem itemLabel="LogDB" id="logDB" itemValue="/auditoria/log_db.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Log Updates JDBC" id="logJDBC" itemValue="/auditoria/log_jdbc.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Extrato Di�rio de Movimenta��o" id="extratoDiarioMovimentacao">
			<t:navigationMenuItem itemLabel="Cadastro de Processadores e Movimentos" id="cadastroProcessadores" action="#{extratoMovimentacoesMBean.listagem}"/>
			<t:navigationMenuItem itemLabel="Visualizar relat�rio" id="extratoMovimenta��es" action="#{extratoMovimentacoesMBean.relatorio}"/>
			<t:navigationMenuItem itemLabel="Estat�stica de Acessos do Dia" id="estatisticaDia" rendered="#{sistemaBean.administrativoAtivo || sistemaBean.academicoAtivo}">
				<t:navigationMenuItem itemLabel="Acad�mico" id="estatisticaAcademico" itemValue="/auditoria/movimentos/logins_academico.jsf" actionListener="#{menu.redirecionar}" rendered="#{sistemaBean.academicoAtivo}"/>
				<t:navigationMenuItem itemLabel="Administrativo" id="estatisticaAdministrativo" itemValue="/auditoria/movimentos/logins_adm.jsf" actionListener="#{menu.redirecionar}" rendered="#{sistemaBean.administrativoAtivo}"/>
			</t:navigationMenuItem>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Relat�rio de Acessos" id="relatorioAcesso" itemValue="/auditoria/relatorio_acessos.jsf" actionListener="#{menu.redirecionar}"/>
	</t:navigationMenuItem>
	
	<t:navigationMenuItem itemLabel="Suporte T�cnico" id="suporteTecnico" icon="/img/menu/help.png" rendered="#{ acessoMenu.suporte }">
		<t:navigationMenuItem itemLabel="Consultar Recursos dos Sistemas" id="consultarRecursosSistemas">
			<t:navigationMenuItem itemLabel="Consultar Base de Arquivos" id="consultarBaseArquivos" itemValue="/suporte_tecnico/base_arquivos/info.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Consultar Timers" id="consultarTimers" actionListener="#{menu.redirecionar}" itemValue="/suporte_tecnico/timers/lista.jsf"/>		
			<%--<t:navigationMenuItem itemLabel="Consultar Servi�os Remotos" id="consultarServicos" actionListener="#{menu.redirecionar}" itemValue="/suporte_tecnico/servicos_remotos/lista.jsf"/> --%>
		</t:navigationMenuItem>

		<%-- 
		<t:navigationMenuItem itemLabel="Profiling">
			<t:navigationMenuItem itemLabel="Ativar Profiling" id="ativarProfiling" rendered="#{!profilingAspect.profilingAtivo}" action="#{profilingMBean.ativarProfiling}"/>
			<t:navigationMenuItem itemLabel="Inativar Profiling" id="inativarProfiling" rendered="#{profilingAspect.profilingAtivo}" action="#{profilingMBean.inativarProfiling}"/>
			<t:navigationMenuItem itemLabel="Consultar Informa��es de Profiling" id="consultarInformacoesProfiling" itemValue="/suporte_tecnico/profiling/busca.jsf" actionListener="#{menu.redirecionar}"/>		
		</t:navigationMenuItem>
		--%>
		<t:navigationMenuItem itemLabel="Consultar E-mails">		
			<t:navigationMenuItem itemLabel="E-mails Enviados" id="consultarEmailEnviado" action="#{emailEnvidadoMBean.iniciar}"/>
			<t:navigationMenuItem itemLabel="E-mails N�o Enviados" id="consultarErroEnvioEmail" action="#{erroEnvioEmailMBean.iniciar}"/>
		</t:navigationMenuItem>
		
		<t:navigationMenuItem itemLabel="Consultar Unidades" id="consultarUnidades" actionListener="#{unidadeAdminMBean.popularListagem}"/>
		<t:navigationMenuItem itemLabel="Consultar Servidores" id="consultarServidores" itemValue="/suporte_tecnico/servidores/consulta.jsf" actionListener="#{menu.redirecionar}"/>
		<t:navigationMenuItem itemLabel="Materiais de Ajuda ao Usu�rio" id="materiaisAjudaUsuario">
			<t:navigationMenuItem itemLabel="Material de Treinamento" id="materialTreinamento" action="#{ materialTreinamentoMBean.iniciar}" />
			<t:navigationMenuItem itemLabel="Tipos de Materiais de Treinamento"  id="tipoMaterialTreinamento" action="#{ tipoMaterialMBean.iniciar}"/>
		</t:navigationMenuItem>
				
		<t:navigationMenuItem itemLabel="Permiss�es" id="permissoes">
			<t:navigationMenuItem itemLabel="Implantar Permiss�es" id="implantarPermissoes" action="#{ implantarPermissoesMBean.init }"/>
			<t:navigationMenuItem itemLabel="Transferir Permiss�es" id="tranferirPermissoes" action="#{ transferirPermissoesMBean.init }"/>
			<t:navigationMenuItem split="true"/>
			<t:navigationMenuItem itemLabel="Consultar Pap�is" id="consultarPapeis" action="#{papelMBean.init}"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Usu�rios" id="usuarios">
			<t:navigationMenuItem itemLabel="Cadastrar Usu�rio" id="cadastrarUsuario" action="#{usuarioMBean.form}"/>
			<t:navigationMenuItem itemLabel="Alterar Dados de Usu�rio" id="alterarDadosUsuario" action="#{usuarioMBean.init}"/>
			<t:navigationMenuItem itemLabel="Confirma��o de Cadastro de Usu�rios" id="confirmacaoCadastroUsuario" action="#{autoCadastroUsuarioMBean.listaConfirmacaoManual}"/>
			<t:navigationMenuItem itemLabel="Associar Servidor a Usu�rio" id="AssociarServidorUsuario" action="#{associarServidorUsuarioMBean.init}"/>
			<t:navigationMenuItem itemLabel="Permiss�es de acesso aos sistemas" id="PermissoesAcessoSistema" action="#{usuarioPermissaoSistemaMBean.init}"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Ambientes" id="ambientes" rendered="#{ acessoMenu.ambientesDsPresente }">
			<t:navigationMenuItem itemLabel="Tipos de Ambientes" id="tipoAmbientes">
				<t:navigationMenuItem itemLabel="Cadastrar" id="cadastrarAmbiente" actionListener="#{menu.redirecionar}" itemValue="/suporte_tecnico/TipoAmbiente/form.jsf"/>
				<t:navigationMenuItem itemLabel="Listar" id="listarAmbiente" actionListener="#{menu.redirecionar}" itemValue="/suporte_tecnico/TipoAmbiente/lista.jsf"/>
			</t:navigationMenuItem>
			<t:navigationMenuItem itemLabel="Usu�rios para Acesso aos Ambientes" id="usuariosAcessoAmbientes">
				<t:navigationMenuItem itemLabel="Cadastrar" id="cadastrarUsuarioAcessoAmbioentes" actionListener="#{menu.redirecionar}" itemValue="/suporte_tecnico/UsuarioAmbiente/form.jsf"/>
				<t:navigationMenuItem itemLabel="Listar" id="listarUsuarioAcessoAmbioentes" actionListener="#{menu.redirecionar}" itemValue="/suporte_tecnico/UsuarioAmbiente/lista.jsf"/>
			</t:navigationMenuItem>
		</t:navigationMenuItem>
	</t:navigationMenuItem>
	
	<t:navigationMenuItem itemLabel="Config. do Sistema" id="configSistema" icon="/img/menu/config.png" rendered="#{ acessoMenu.administracao }">
		<t:navigationMenuItem itemLabel="Dados Institucionais" id="dadosInstitucionais" action="#{ dadosInstitucionaisMBean.init }"/>
		<t:navigationMenuItem itemLabel="Manuten��o dos Sistemas" id="manutencaoSistemas" itemValue="/configuracoes/manutencao/sistemas.jsf" actionListener="#{menu.redirecionar}"/>
		<t:navigationMenuItem itemLabel="Sincronizar todos os usu�rios" id="sincronizarTodosUsuarios" itemValue="/configuracoes/sincronizacao/form.jsf" actionListener="#{menu.redirecionar}"/>
		<%--
		<t:navigationMenuItem itemLabel="Hibernate" id="operacoesHibernate">
			<t:navigationMenuItem itemLabel="Limpar Cache do Hibernate" id="limparCacheHibernate" itemValue="/configuracoes/limpar_cache_hibernate.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Consultar Entidades Mapeadas" id="consultarEntidadesMapeadas" itemValue="/configuracoes/consultar_entidades_mapeadas.jsf" actionListener="#{menu.redirecionar}"/>			
		</t:navigationMenuItem>
		--%>
		<t:navigationMenuItem split="true"/>
		
		<t:navigationMenuItem itemLabel="Feriados" id="Feriados">
			<t:navigationMenuItem itemLabel="Inserir" action="#{feriadoMBean.init}"/>
			<t:navigationMenuItem itemLabel="Listar" action="#{feriadoMBean.direcionar}"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Mensagens de Aviso" id="manutencaoAviso">
			<t:navigationMenuItem itemLabel="Inserir" id="cadastrarUsuarioMensagemAviso" action="#{mensagemAvisoMBean.preCadastrar}" />
			<t:navigationMenuItem itemLabel="Listar/Alterar" itemValue="/configuracoes/mensagens/lista.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		<t:navigationMenuItem itemLabel="Par�metros" id="parametros">
			<t:navigationMenuItem itemLabel="Cadastrar Par�metro" id="cadastrarParametro" action="#{ parametrosMBean.novo }"/>
			<t:navigationMenuItem itemLabel="Listar/Alterar Par�metros" id="listarAlterarParametro" action="#{ parametrosMBean.init }"/>
			<t:navigationMenuItem itemLabel="Resetar Par�metros" id="resetarParametro" itemValue="/configuracoes/parametros/resetar.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		
		<t:navigationMenuItem itemLabel="Permiss�es" id="permissoesConfigSistema">
			<t:navigationMenuItem itemLabel="Implantar Permiss�es" id="implantarPermissoesConfigSistema" action="#{ implantarPermissoesMBean.init }"/>
			<t:navigationMenuItem itemLabel="Transferir Permiss�es" id="transferirPermissoesConfigSistema" action="#{ transferirPermissoesMBean.init }"/>
			<t:navigationMenuItem split="true"/>
			<t:navigationMenuItem itemLabel="Gerenciamento de Pap�is" id="gerencimentoPapeis" action="#{ papelMBean.init }"/>
			<t:navigationMenuItem itemLabel="Gestores de Permiss�es" id="gestoresPermissao" itemValue="/configuracoes/gestores/lista.jsf" rendered="#{acessoMenu.gestorPermissoesGlobal}" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Grupos de Pap�is" id="grupoPapeis" itemValue="/configuracoes/grupos/lista.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		
		<t:navigationMenuItem split="true"/>
		
		<t:navigationMenuItem itemLabel="Tipos de Documentos para Templates" id="tipoDocumentoTemplates">
			<t:navigationMenuItem itemLabel="Inserir" id="inseirTipoDocumentoTemplates" itemValue="/configuracoes/TipoDocumentoTemplate/form.jsf" actionListener="#{menu.redirecionar}"/>
			<t:navigationMenuItem itemLabel="Listar/Alterar" id="listarAlterarTipoDocumentoTemplates"  itemValue="/configuracoes/TipoDocumentoTemplate/lista.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		
		<t:navigationMenuItem itemLabel="Templates de Documentos">
			<t:navigationMenuItem itemLabel="Inserir" id="inserirTemplatesDocumentos" action="#{templateDocumentoMBean.direcionarCadastro}" />
			<t:navigationMenuItem itemLabel="Listar/Alterar" id="listarAlterarTemplatesDocumentos" itemValue="/configuracoes/TemplateDocumento/lista.jsf" actionListener="#{menu.redirecionar}"/>
		</t:navigationMenuItem>
		
	</t:navigationMenuItem>

</t:jscookMenu>
</h:form>
</div>
</div>