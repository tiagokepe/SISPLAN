<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>

<style type="text/css">
.colunaAtivo { color: green; }
.colunaAgendado { color: blue; }
.colunaParado { color: red; }
.colunaCentralizada { text-align: center; }
</style>

<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Configurações de Timers" />


<div class="descricaoOperacao">
Esta operação tem como objetivo o acompanhamento da situação das tarefas periódicas dos sistemas. A partir dessa tela,
é possível cadastrar, iniciar, parar e consultar logs das tarefas que estão sendo executadas periodicamente em background.
<br/><br/>
<strong>Atenção!</strong> Se uma tarefa for cancelada, tudo o que estiver sendo feito durante execução atual será desfeito.
</div>

	<h:form prependId="false">
		<!-- Customização Específica SIGREF - Tarefa 34049 -->
		<table class="formulario" width="40%">
		<caption class="listagem">Consulta de Timers</caption>
			<tr>
				<td> <h:selectBooleanCheckbox value="#{ tarefaTimerMBean.somenteAtivos }" id="somenteAtivos"/> </td>
				<td colspan="2" style="text-align: left;">Listar somente timers ativos</td>
			</tr>
			<tfoot>
				<tr>
					<td colspan="3" align="center">
						<h:commandButton value="Buscar" action="submit()" id="btBuscar"/> 
						<h:commandButton value="Cancelar" onclick="#{confirm}" action="#{ tarefaTimerMBean.cancelarBusca }" id="btCancelar"/>
					</td>
				</tr>
			</tfoot>
		</table>
		<br />
		<br />
		<!-- Fim Customização Específica SIGREF - Tarefa 34049 -->
		<div class="infoAltRem" style="width: 100%">
		<img src="/shared/img/adicionar.gif" style="overflow: visible;"/>:
		<a href="${ctx}/suporte_tecnico/timers/form.jsf">Cadastrar Novo Timer</a> 
		<img src="/admin/img/clock_play.png" style="overflow: visible;"/> Executar Tarefa
		<img src="/admin/img/clock_stop.png" style="overflow: visible;"/> Interromper Tarefa
		<img src="/shared/img/lupa.gif" style="overflow: visible;"/> Ver Execuções 
		<img src="/shared/img/alterar.gif" style="overflow: visible;"/> Alterar 
		<!-- Customização Específica SIGREF - Tarefa 34049 -->
		<img src="/admin/img/check.png" style="overflow: visible;"/> Ativar 
		<img src="/admin/img/check_cinza.png" style="overflow: visible;"/> Desativar
		<!-- Fim Customização Específica SIGREF - Tarefa 34049 -->
<%-- CUSTOMIZACAO ESPECIFICA SIGREF - TAREFA 34323 --%>
		<br/>
		<c:if test="${tarefaTimerMBean.permitidoSuspender}">
			<img src="/admin/img/geral/stop.png" style="overflow: visible;"/> Suspender
		</c:if>
<%-- FIM DA CUSTOMIZACAO ESPECIFICA SIGREF - TAREFA 34323 --%>
		</div>
		
		<h:dataTable id="timers" value="#{ tarefaTimerMBean.all }" var="tt" styleClass="listagem" rowClasses="linhaPar,linhaImpar">
		
			<f:facet name="caption">
				<h:outputText value="Tarefas Periódicas Encontradas"/>
			</f:facet>
		
			<h:column>
				<f:facet name="header">
					<h:outputText value="Classe"/>
				</f:facet>
				<h:outputText value="#{ tt['classe'] }"/>
			</h:column>
			
			<h:column>
				<f:facet name="header">
					<h:outputText value="Repetição"/>
				</f:facet>
				<center>
				<h:outputText value="Diária" rendered="#{ tt['tipo_repeticao'] == 'D' }"/>
				<h:outputText value="Mensal" rendered="#{ tt['tipo_repeticao'] == 'M' }"/>
				<h:outputText value="Semanal" rendered="#{ tt['tipo_repeticao'] == 'S' }"/>
				<h:outputText value="Tempo" rendered="#{ tt['tipo_repeticao'] == 'T' }"/>
				</center>
			</h:column>
			
			<h:column> 
				<f:facet name="header">
					<h:outputText value="Última Execução"/>
				</f:facet>
				<h:outputText value="#{ tt['ultima_execucao'] }">
					<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss"/>
				</h:outputText>
			</h:column>
		
			<h:column>
				<f:facet name="header">
					<h:outputText value="Servidor"/>
				</f:facet>
				<h:outputText value="#{ tt['servidor_restricao'] }"/>
			</h:column>
			
			<h:column>
				<f:facet name="header">
					<h:outputText value="Status"/>
				</f:facet>
				<f:verbatim rendered="#{ tt['em_execucao'] }">
				<center><span class="colunaCentralizada colunaAtivo">
				</f:verbatim>
				<center><f:verbatim rendered="#{ tt['executar_agora'] && !tt['em_execucao'] }">
				<span class="colunaCentralizada colunaAgendado">
				</f:verbatim>
				<center><f:verbatim rendered="#{ !tt['executar_agora'] && !tt['em_execucao'] }">
				<span class="colunaCentralizada colunaParado">
				</f:verbatim>
					<h:outputText value="Ativo" rendered="#{ tt['em_execucao'] }"/>
					<h:outputText value="Agendado" rendered="#{ tt['executar_agora'] && !tt['em_execucao'] }"/>
					<h:outputText value="Parado" rendered="#{ !tt['executar_agora'] && !tt['em_execucao'] }"/>
				<f:verbatim>		
				</span></center>
				</f:verbatim>
			</h:column>
			
			<h:column>
				<h:commandLink action="#{ tarefaTimerMBean.executar }" rendered="#{ !tt['em_execucao'] }">
					<f:verbatim><img src="/admin/img/clock_play.png" alt="Executar Tarefa" title="Executar Tarefa"/></f:verbatim>
					<f:param name="id" value="#{ tt['id'] }" />
				</h:commandLink>
								
				<h:commandLink action="#{ tarefaTimerMBean.interromper }" rendered="#{ tt['em_execucao'] }" onclick="return(confirm('Tem certeza que deseja interromper a execução da tarefa?'))">
					<f:verbatim><img src="/admin/img/clock_stop.png" alt="Interromper Tarefa" title="Interromper Tarefa"/></f:verbatim>
					<f:param name="id" value="#{ tt['id'] }" />
				</h:commandLink>
			</h:column>
			
			<h:column>
				<h:commandLink action="#{ tarefaTimerMBean.execucoes }">
					<f:verbatim><img src="/shared/img/lupa.gif" alt="Ver Execuções" title="Ver Execuções"/></f:verbatim>
					<f:param name="id" value="#{ tt['id'] }" />
				</h:commandLink>
			</h:column>
			
			<h:column>
				<h:commandLink action="#{ tarefaTimerMBean.editar }" id="alterar">
					<f:verbatim><img src="/shared/img/alterar.gif" alt="Alterar" title="Alterar"/></f:verbatim>
					<f:param name="id" value="#{ tt['id'] }" />
				</h:commandLink>
			</h:column>
			
			<h:column>
				<!-- Customização Específica SIGREF - Tarefa 34049 -->
				<h:commandLink action="#{ tarefaTimerMBean.ativarDesativar }" id="ativarDesativar">
					<f:verbatim rendered="#{ tt['ativa'] }"><img src="/admin/img/check_cinza.png" alt="Desativar" title="Desativar"/></f:verbatim>
					<f:verbatim rendered="#{ !tt['ativa'] }"><img src="/admin/img/check.png" alt="Ativar" title="Ativar"/></f:verbatim>
				<!-- Fim Customização Específica SIGREF - Tarefa 34049 -->
					<f:param name="id" value="#{ tt['id'] }" />
				</h:commandLink>
			</h:column>
<%-- CUSTOMIZACAO ESPECIFICA SIGREF - TAREFA 34323 --%>
			<h:column>
				<h:commandLink action="#{tarefaTimerMBean.suspender}" id="suspender" rendered="#{tarefaTimerMBean.permitidoSuspender}">
					<f:verbatim><img src="/admin/img/geral/stop.png" alt="Suspender" title="Suspender"/></f:verbatim>
					<f:param name="id" value="#{ tt['id'] }" />
				</h:commandLink>
			</h:column>
<%-- FIM DA CUSTOMIZACAO ESPECIFICA SIGREF - TAREFA 34323 --%>
		</h:dataTable>
		
		<a4j:poll reRender="timers" interval="10000"/>
		
	</h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
