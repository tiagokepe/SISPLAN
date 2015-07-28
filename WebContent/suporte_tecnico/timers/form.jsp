<%@include file="/WEB-INF/jsp/include/cabecalho.jsp"%>
<f:view>
	<%@include file="/portal/menu_administracao.jsp"%>
	<admin:caminho titulo="Configurações de Timers" />
	<h:form id="form">

		<table class="formulario" width="70%">
			<caption>Informações do Timer</caption>

			<tr>
				<th width="153" class="obrigatorio">Classe: </th>
				<td><h:inputText id="classe" value="#{tarefaTimerMBean.classe}" size="80"/> </td>
			</tr>
			<tr>
				<th class="obrigatorio">Servidor: </th>
				<td><h:inputText id="servidor" value="#{tarefaTimerMBean.servidor}" size="50"/> </td>
			</tr>
			<tr>
				<th class="obrigatorio">Ativa: </th>
				<td><h:selectOneMenu id="ativa" value="#{tarefaTimerMBean.ativa}">
					<f:selectItems value="#{tarefaTimerMBean.simNao }"/>
				</h:selectOneMenu> </td>
			</tr>
			<tr>
				<td colspan="2">
					<table class="subFormulario" width="100%">
						<caption>Configurações de Periodicidade</caption>
						<tr>
							<th width="150" class="obrigatorio">Expressão: </th>
							<td>
								<h:inputText value="#{tarefaTimerMBean.expressao }"/>
							</td>
						</tr>
						<tr>
						<th valign="top">Como configurar:</th>
						<td valign="top">
						<p>
							A expressão de periodicidade possui o mesmo formato utilizado pelo CRON. Ela é composta pelos seguintes 
							7 itens separados por espaços em branco, em ordem: Segundos, Minutos, Horas, Dia do mês, Mês, Dia da semana, 
							Ano. Para cada item, utilizamos um valor numérico ou então operadores para flexibilizar o cronograma. 
						</p>
						<br/>
						<p>
							Os operadores são o '*', o '?', o '-' e a '/'. O '*' significa todos, ou qualquer. O '?' significa nenhum
							ou não se aplica. O '-' indica um intervalo de valores e o '/' indica incremento.   
						</p>
						<br/>
						<p>
							Assim, se desejarmos que uma tarefa seja executada todos os dias, às 15:00h, devemos utilizar a seguinte 
							expressão: <strong>* 0 15 * * ? *</strong>, ou seja, quaisquer segundos, aos 0 minutos, às 15 horas, de qualquer dia do mês,
							qualquer mês, não importa o dia da semana, qualquer ano.   
						</p>
						<br/>
						<p>
							Caso seja necessário que uma tarefa seja executada a cada 30 minutos, apenas de segunda a sexta, usamos
							<strong>* */30 * ? MON-FRI *</strong>, ou seja, quaisquer segundos, quaisquer minutos, mas com incrementos de 30 minutos,
							qualquer hora, não importa o dia do mês, de segunda a sexta, em qualquer ano.    
						</p>							
						<br/> 
						<p>
							O ano é o único campo opcional. Não se pode definir ao mesmo tempo o dia do mês e o dia da semana. Se definirmos
							um, o outro deverá ser ?. 
						</p>
						</ul>
						
						
						
						</td>
					</table>
				</td>
			</tr>
			<tfoot>
				<tr><td colspan="2">
					<h:commandButton action="#{tarefaTimerMBean.salvar}" value="Salvar" id="salvar" />
					<h:commandButton action="#{tarefaTimerMBean.cancelar}" value="Cancelar" id="cancelar" onclick="#{confirm}"/>
				</td></tr>
			</tfoot>
		</table>

		<h:inputHidden value="#{tarefaTimerMBean.id}"/>

	</h:form>
</f:view>
<%@include file="/WEB-INF/jsp/include/rodape.jsp"%>
