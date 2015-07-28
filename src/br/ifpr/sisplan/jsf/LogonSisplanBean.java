package br.ifpr.sisplan.jsf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import br.ufrn.admin.dominio.MensagensHelper;
import br.ufrn.admin.jsf.AcessoMenu;
import br.ufrn.admin.jsf.AdminAbstractController;
import br.ufrn.admin.negocio.AdminListaComando;
import br.ufrn.arq.caixa_postal.Mensagem;
import br.ufrn.arq.dao.PassaporteDao;
import br.ufrn.arq.dominio.ConstantesParametroGeral;
import br.ufrn.arq.erros.ArqException;
import br.ufrn.arq.erros.NegocioException;
import br.ufrn.arq.parametrizacao.ParametroHelper;
import br.ufrn.arq.seguranca.admin.SigaaPapeis;
import br.ufrn.arq.seguranca.admin.SipacPapeis;
import br.ufrn.arq.seguranca.dominio.PassaporteLogon;
import br.ufrn.arq.usuarios.UserAutenticacao;
import br.ufrn.arq.usuarios.UsuarioMov;
import br.ufrn.arq.util.CalendarUtils;
import br.ufrn.arq.util.NetworkUtils;
import br.ufrn.comum.dominio.Sistema;
import br.ufrn.comum.dominio.UsuarioGeral;
import br.ufrn.comum.jsf.VerTelaAvisoLogonMBean;

/**
 * Managed bean para logar no sistema comum.
 * 
 * @author David Pereira
 * 
 */
@Component("logon") @Scope("request")
public class LogonSisplanBean extends AdminAbstractController<Object> {
	
	public LogonSisplanBean() {
		System.out.println("#######LogonSisplanBean");
	}

	private UsuarioGeral usuario = new UsuarioGeral();

	private int screenWidth;

	private int screenHeight;

	private String mensagem;

	/**
	 * Faz o logon do usuário no sistema
	 * JSP: /login.jsp
	 * @throws RemoteException
	 * @throws ArqException
	 * @throws IOException 
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public String logar() throws ArqException, IOException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		UsuarioMov mov = new UsuarioMov();
		mov.setCodMovimento(AdminListaComando.LOGON);
		mov.setIP(getCurrentRequest().getRemoteAddr());
		mov.setIpInternoNat(getCurrentRequest().getHeader("X-FORWARDED-FOR"));
		mov.setUsuario(usuario);
		mov.setHost(NetworkUtils.getLocalName());
		mov.setUserAgent(getCurrentRequest().getHeader("User-Agent"));
		mov.setResolucao(getParameter("width") + "x" + getParameter("height"));
		mov.setRequest(getCurrentRequest());

		getCurrentRequest().setAttribute("NO_LOGGER", Boolean.TRUE);

		// Ação a ser executada após o logon.
		String acao = null;
		
		boolean passaporte = getParameterBoolean("passaporte");
		
		if (passaporte) {
			PassaporteDao dao = getDAO(PassaporteDao.class);
			PassaporteLogon p = dao.findPassaporte(getParameter("login"), Sistema.SIGADMIN);

			if (p == null) {
				return falha("Passaporte não validado");
			} else {
				mov.getUsuario().setLogin(getParameter("login"));
				mov.setPassaporte(p);
				
				acao = p.getAcao();
			}
		}

		try {
			HttpServletRequest req = getCurrentRequest();
			System.out.println("SISTEM  = "+req.getAttribute("sistema"));
			usuario = (UsuarioGeral) execute(mov, getCurrentRequest());
			if (usuario == null) {
				return falha("Usuário/Senha Inválidos");
			} else {
				getCurrentSession().setAttribute("usuario", usuario);
			}
		} catch (NegocioException e) {
			e.printStackTrace();
			getCurrentSession().invalidate();
			getCurrentSession().removeAttribute("usuario");
			return falha(e.getMessage());
		}
		
		if (UserAutenticacao.senhaExpirou(getCurrentRequest(), usuario.getId())) {
			redirect("/alterar_dados.jsf?expirou=true");
			return null;
		}
		
		if (!passaporte && !UserAutenticacao.usuarioAtivo(getCurrentRequest(), Sistema.SIGADMIN, usuario.getId()) && (usuario.getUltimoAcesso() == null || CalendarUtils.calculoMeses(usuario.getUltimoAcesso(), new Date()) <= 3)) {
			getCurrentSession().invalidate();
			return falha("Usuário não autorizado a acessar o sistema.");
		}
		
		// Verificar permissão de acesso ao sistema
		AcessoMenu acesso = getMBean("acessoMenu");
		if ( !acesso.processarPermissoes() && !isAlterandoSenha() && !isRespondendoQuestionario() ) {
			getCurrentSession().invalidate();
			return falha("Usuário não autorizado a acessar este sistema.");
		}
		
		if ( acao != null ) {
			String[] partes = acao.split("\\.");
			Object mbean = getMBean(partes[0]);
			ReflectionUtils.findMethod(mbean.getClass(), partes[1]).invoke(mbean, (Object[]) null);
			return null;
		}
		
		if (passaporte && getParameter("url") != null) {
			redirect(getParameter("url"));
			return null;
		}
		
		VerTelaAvisoLogonMBean telaAviso = getMBean("verTelaAvisoLogonMBean");
		
		if(Sistema.isCaixaPostalAtiva(Sistema.SIGADMIN)) {
			String modoCxPostal = ParametroHelper.getInstance().getParametro(ConstantesParametroGeral.MODO_OPERACAO_CAIXA_POSTAL);
			int qtdNaoLidas = MensagensHelper.qtdNaoLidaCaixaEntrada(usuario.getId());
			
			if ("MOSTRAR_NAO_LIDAS".equals(modoCxPostal)) {
				getCurrentSession().setAttribute("qtdMsgsNaoLidasCxPostal", qtdNaoLidas);
			} else {
				// Verifica se tem mensagens para serem lidas na caixa postal
				if (qtdNaoLidas > 0) {
					// Se tem mensagens para serem lidas, vai para a caixa postal
					getCurrentSession().setAttribute("sistema", Sistema.SIGAA);	
					telaAviso.iniciar("/admin/abrirCaixaPostal.jsf?tipo=" + Mensagem.MENSAGEM + "&sistema=" + Sistema.SIGADMIN, getUsuarioLogado(), Sistema.SIGADMIN, getCurrentRequest(), getCurrentResponse());
					return null;					
				}
			}
			
			
			telaAviso.iniciar("/portal/index.jsf", getUsuarioLogado(), getSistema(), getCurrentRequest(), getCurrentResponse());
			
		} else {
			telaAviso.iniciar("/portal/index.jsf", getUsuarioLogado(), getSistema(), getCurrentRequest(), getCurrentResponse());
		}
		
		
		return null;
	}
	
	/*
	 * Método para identificar quando o usuário está tentando acessar
	 * o SIGAdmin para realizar uma alteração de senha.
	 */
	private boolean isAlterandoSenha() {
		boolean passaporte = getParameterBoolean("passaporte");
		String url = getParameter("url");
		return passaporte && "/admin/public/usuario/alterar_dados.jsf".equals(url);
	}
	
	/*
	 * Método para identificar quando o usuário está tentando acessar
	 * o SIGAdmin para realizar uma alteração de senha.
	 */
	private boolean isRespondendoQuestionario() {
		boolean passaporte = getParameterBoolean("passaporte");
		String url = getParameter("url");
		return passaporte && url != null && url.contains("questionario/responder");
	}

	/**
	 * Grava uma mensagem de erro e volta para a tela de logon do
	 * sistema ou para a tela de logon unificado.
	 * 
	 * @param msg
	 * @return
	 */
	private String falha(String msg) {
		if (getCurrentRequest().getParameter("unificado") != null) {
			getCurrentRequest().setAttribute("mensagem", msg);
			try {
				getCurrentResponse().sendRedirect("/admin/public/loginunificado/index.jsf?selectedTab=sigadmin&mensagem=" + msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			FacesContext.getCurrentInstance().responseComplete();
			return null;
		} else {
			addMensagemErro(msg);
			return null;
		}
	}

	/**
	 * Logon específico para o menu de comunicação
	 * 
	 * @return
	 * @throws ArqException
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public String logarComunicacao() throws ArqException, IOException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		logar();
		return forward("/menu_comunicacao.jsf");
	}
	
	/**
	 * Faz o logOff do usuário no sistema
	 * JSP: /WEB-INF/jsp/include/cabecalho.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String logoff() throws Exception {
		try {
			if (getCurrentSession() != null) {
				UsuarioMov mov = new UsuarioMov();
				mov.setCodMovimento(AdminListaComando.LOGOFF);
				mov.setUsuario(getUsuarioLogado());
				mov.setUsuarioLogado(getUsuarioLogado());

				execute(mov, getCurrentRequest());
				getCurrentSession().invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward("/login.jsf");
	}
	
	public String paginaPrincipal(){
		return redirect("/admin/portal/index.jsf");
	}

	public boolean isAdministrador(){
		return getUsuarioLogado().isUserInRole(SipacPapeis.ADMINISTRADOR_SIPAC, SigaaPapeis.ADMINISTRADOR_SIGAA);
	}
	
	public UsuarioGeral getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioGeral usuario) {
		this.usuario = usuario;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
