package br.ifpr.sisplan.controller.tree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.joda.time.Days;

import br.ifpr.sisplan.controller.bean.PeriodoPlanControllerBean;
import br.ifpr.sisplan.controller.bean.SisplanUser;
import br.ifpr.sisplan.controller.converters.BigDecimalConverter;
import br.ifpr.sisplan.controller.ifaces.DataPlanningIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeActionsIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCallBackIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetailsIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeEventsIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfoIface;
import br.ifpr.sisplan.controller.mensagens.EmailSender;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EmailDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.model.table.Responsavel;
import br.ifpr.sisplan.model.table.parent.DateNode;
import br.ifpr.sisplan.util.DateUtil;

public abstract class TreeNodeCallBack extends TreeNodeGeneric implements TreeNodeCallBackIface, TreeNodeEventsIface, TreeNodeActionsIface, TreeNodeDetailsIface, DataPlanningIface {
	private static final long serialVersionUID = 1L;
	protected Map<Method, Object> mapOfUpdateCallBack = new HashMap<Method, Object>();
	protected boolean changedDatas = false;
	protected boolean changedCustos = false;
	protected boolean changedDescricao = false;
	protected boolean changedName = false;
	protected boolean changedResponsavel = false;
	protected boolean changedObs = false;
	protected DateNode dataNode;
	
	protected List<Responsavel> responsaveis;
	protected Responsavel responsavel;
	protected String responsavelName;
	protected String oldResName;
	
	public TreeNodeCallBack(TreeNodeGeneric parent, DateNode kidNode, int order) {
		super(parent, kidNode, order);
		this.dataNode = kidNode;
		this.setResponsaveis();
		this.setResponsavel();
	}
	
	protected abstract void setResponsaveis();
	protected abstract void setResponsavel();
	protected abstract void setIdResponsavel();
	protected abstract void updateDBResponsavel();
	public abstract boolean isEnabledObservacao();
	public abstract String getUnidadeName();
	public abstract void setSentEmail(boolean bool);
	
	protected void addCallBackMethod(String method, Class paramClazz, Object param) {
		try {
			this.mapOfUpdateCallBack.put(TreeNodeCallBackIface.class.getMethod(method, paramClazz), param);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	protected void callBack() {
		Method method;
		Object param;
		for(Entry<Method, Object> e: this.mapOfUpdateCallBack.entrySet()) {
			method = e.getKey();
			param = e.getValue();
			try {
				method.invoke(this, param);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void setDescricaoCallBack(String desc) {
		LogHistory.getInstance().log((TreeNodeInfoIface) this, 
				(TreeNodeCadastroIface) this, "Descrição",
				this.dataNode.getDescricao(),desc);
		this.dataNode.setDescricao(desc);
		this.descriptionNode.setDescricao(desc);
		this.changedDescricao = true;
	}
	
	public void setObservacaoCallBack(String obs) {
		LogHistory.getInstance().log((TreeNodeInfoIface) this, 
				(TreeNodeCadastroIface) this, "Observação",
				this.dataNode.getObservacao(),obs);
		this.dataNode.setObservacao(obs);
		this.changedObs = true;
	}
	
	public void setNameCallBack(String name) {
		LogHistory.getInstance().log((TreeNodeInfoIface) this, 
				(TreeNodeCadastroIface) this, "Nome",
				this.dataNode.getName(),name);
		this.dataNode.setName(name);
		this.changedName = true;
	}
	
	public void setDataInicioPrevistaCallBack(Date newDate) {
		if(newDate != null) {
			String oldDate = this.dataNode.getData().getDataInicioPrevista() == null? 
					 "" : DateUtil.dateToString(this.dataNode.getData().getDataInicioPrevista());
			
			LogHistory.getInstance().log((TreeNodeInfoIface) this, 
					(TreeNodeCadastroIface) this, "Data Início Prevista",
					oldDate, DateUtil.dateToString(newDate));
		}
		this.dataNode.getData().setDataInicioPrevista(newDate);
		this.changedDatas = true;
	}
	
	public void setDataInicioEfetivaCallBack(Date newDate) {
		if(newDate != null) {
			String oldDate = this.dataNode.getData().getDataInicioEfetiva() == null? 
							 "" : DateUtil.dateToString(this.dataNode.getData().getDataInicioEfetiva());
			
			LogHistory.getInstance().log((TreeNodeInfoIface) this, 
					(TreeNodeCadastroIface) this, "Data Início Efetiva",
					oldDate, DateUtil.dateToString(newDate));
		}
		this.dataNode.getData().setDataInicioEfetiva(newDate);
		this.changedDatas = true;
	}
	
	public void setDataFimPrevistaCallBack(Date newDate) {
		if(newDate != null) {
			String oldDate = this.dataNode.getData().getDataFimPrevista() == null? 
					 "" : DateUtil.dateToString(this.dataNode.getData().getDataFimPrevista());
			
			LogHistory.getInstance().log((TreeNodeInfoIface) this, 
					(TreeNodeCadastroIface) this, "Data Fim Prevista",
					oldDate, DateUtil.dateToString(newDate));
		}
		this.dataNode.getData().setDataFimPrevista(newDate);
		this.changedDatas = true;
	}
	
	public void setDataFimEfetivaCallBack(Date newDate) {
		if(newDate != null) {
			String oldDate = this.dataNode.getData().getDataFimEfetiva() == null? 
					 "" : DateUtil.dateToString(this.dataNode.getData().getDataFimEfetiva());
			
			LogHistory.getInstance().log((TreeNodeInfoIface) this, 
					(TreeNodeCadastroIface) this, "Data Fim Efetiva",
					oldDate, DateUtil.dateToString(newDate));
		}
		this.dataNode.getData().setDataFimEfetiva(newDate);
		this.changedDatas = true;
	}
	
	public String getDescricao() {
		return this.dataNode.getDescricao();
	}
	
	public void setDescricao(String desc) {
		if(!desc.isEmpty() && !desc.equals(this.dataNode.getDescricao())) {
			this.addCallBackMethod("setDescricaoCallBack", String.class, desc);
		}
	}
	
	public String getObservacao() {
		return this.dataNode.getObservacao();
	}
	
	public void setObservacao(String obs) {
		if(!obs.isEmpty() && !obs.equals(this.dataNode.getObservacao())) {
			this.addCallBackMethod("setObservacaoCallBack", String.class, obs);
		}
	}
	
	public void setName(String name) {
		if(!name.isEmpty() && !name.equals(this.dataNode.getName())) {
			this.addCallBackMethod("setNameCallBack", String.class, name);
		}
	}
	
	public String getStrDataInicioPrevista() {
		Date dt = this.dataNode.getData().getDataInicioPrevista();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public Date getDataInicioPrevista() {
		return this.dataNode.getData().getDataInicioPrevista();
	}
	
	public void setDataInicioPrevista(String strDate) {
		if(!DateUtil.validateDateFormat(strDate)) {
			return;
		}
		
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataInicioPrevista() == null ||
		   this.dataNode.getData().getDataInicioPrevista().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataInicioPrevistaCallBack", Date.class, newDate);
		}
	}
	
	public void setDataInicioPrevista(Date newDate) {
		if(this.dataNode.getData().getDataInicioPrevista() == null ||
		   this.dataNode.getData().getDataInicioPrevista().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataInicioPrevistaCallBack", Date.class, newDate);
		}
	}
	
	public Date getDataInicioEfetiva() {
		return this.dataNode.getData().getDataInicioEfetiva();
	}

	public String getStrDataInicioEfetiva() {
		Date dt = this.dataNode.getData().getDataInicioEfetiva();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioEfetiva(String strDate) {
		if(!DateUtil.validateDateFormat(strDate))
			return;
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataInicioEfetiva() == null ||
		   this.dataNode.getData().getDataInicioEfetiva().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataInicioEfetivaCallBack", Date.class, newDate);
		}
	}
	
	public void setDataInicioEfetiva(Date newDate) {
		if(this.dataNode.getData().getDataInicioEfetiva() == null ||
		   this.dataNode.getData().getDataInicioEfetiva().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataInicioEfetivaCallBack", Date.class, newDate);
		}
	}
	
	public Date getDataFimPrevista() {
		return this.dataNode.getData().getDataFimPrevista();
	}
	
	public String getStrDataFimPrevista() {
		Date dt = this.dataNode.getData().getDataFimPrevista();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimPrevista(String strDate) {
		if(!DateUtil.validateDateFormat(strDate))
			return;
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataFimPrevista() == null ||
		   this.dataNode.getData().getDataFimPrevista().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataFimPrevistaCallBack", Date.class, newDate);
		}
	}
	
	public void setDataFimPrevista(Date newDate) {
		if(this.dataNode.getData().getDataFimPrevista() == null ||
		   this.dataNode.getData().getDataFimPrevista().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataFimPrevistaCallBack", Date.class, newDate);
		}
	}
	
	public Date getDataFimEfetiva() {
		return this.dataNode.getData().getDataFimEfetiva();
	}
	
	public String getStrDataFimEfetiva() {
		Date dt = this.dataNode.getData().getDataFimEfetiva();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimEfetiva(String strDate) {
		if(!DateUtil.validateDateFormat(strDate)) {
			return;
		}
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataFimEfetiva() == null ||
		   this.dataNode.getData().getDataFimEfetiva().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataFimEfetivaCallBack", Date.class, newDate);
		}
	}
	
	public void setDataFimEfetiva(Date newDate) {
		if(this.dataNode.getData().getDataFimEfetiva() == null ||
		   this.dataNode.getData().getDataFimEfetiva().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataFimEfetivaCallBack", Date.class, newDate);
		}
	}
	
/*	public BigDecimal getCustoEfetivo() {
		return this.dataNode.getCustoEfetivo();
	}*/
	
	public void setCustoEfetivo(BigDecimal newCusto) {
		if( !newCusto.equals(BigDecimalConverter.bigDecEmpty) &&
		    (this.dataNode.getCustoEfetivo() == null ||
		    this.dataNode.getCustoEfetivo().compareTo(newCusto) != 0) )
		{
			
			this.addCallBackMethod("setCustoEfetivoCallBack", BigDecimal.class, newCusto);
		}
	}
	
	public void setCustoEfetivoCallBack(BigDecimal newCusto) {
		String strOldCusto = this.dataNode.getCustoEfetivo() == null ? "" : this.dataNode.getCustoEfetivo().toString();
		LogHistory.getInstance().log((TreeNodeInfoIface) this, 
				(TreeNodeCadastroIface) this, "Custo Efetivo",
				strOldCusto, newCusto.toString());
		this.dataNode.setCustoEfetivo(newCusto);
		this.changedCustos = true;
	}

/*	public BigDecimal getCustoPrevisto() {
		return this.dataNode.getCustoPrevisto();
	}*/
	
	public void setCustoPrevisto(BigDecimal newCusto) {
		if( !newCusto.equals(BigDecimalConverter.bigDecEmpty) &&
			(this.dataNode.getCustoPrevisto() == null ||
		    this.dataNode.getCustoPrevisto().compareTo(newCusto) != 0) )
		{
			this.addCallBackMethod("setCustoPrevistoCallBack", BigDecimal.class, newCusto);
		}
	}
	
	public void setCustoPrevistoCallBack(BigDecimal newCusto) {
		String strOldCusto = this.dataNode.getCustoPrevisto() == null ? "" : this.dataNode.getCustoPrevisto().toString();
		LogHistory.getInstance().log((TreeNodeInfoIface) this, 
				(TreeNodeCadastroIface) this, "Custo Previsto",
				strOldCusto, newCusto.toString());
		this.dataNode.setCustoPrevisto(newCusto);
		this.changedCustos = true;
	}
	
	public void valueChangeListener(ValueChangeEvent e) {
		String id_value = (String)e.getComponent().getAttributes().get("id");
		String newValue = e.getNewValue().toString();
		String methodName = id_value.replace("id_", "").replace("_", "");
		
		try {
			this.mapOfUpdateCallBack.put(TreeNodeDetailsIface.class.getMethod(methodName, String.class),
										newValue);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	public void save() {
		this.callBack();
		if(this.changedName) {
			this.getDAO(ProjetoDao.class).updateName(this.dataNode);
			this.changedName = false;
		}
		if(this.changedDescricao) {
			if(this instanceof ProjetoTreeNode)
				this.getDAO(ProjetoDao.class).updateDescricao(this.dataNode);
			else
				this.getDAO(EtapaDao.class).updateDescricao(this.dataNode);
			this.changedDescricao = false;
		}
		if(this.changedObs) {
			if(this instanceof EtapaTreeNode)
				this.getDAO(EtapaDao.class).updateObservacao(this.dataNode);
			this.changedObs = false;
		}
		if(this.changedDatas) {
			if(this.validateDate()) {
				this.getDAO(DataDao.class).updateData(this.dataNode.getData());
			}
			this.changedDatas = false;
		}
		if(this.changedCustos) {
			this.getDAO(EtapaDao.class).updateCustos(this.dataNode);
			this.changedCustos = false;
		}
		if(this.changedResponsavel) {
			this.updateDBResponsavel();
			this.changedResponsavel = false;
		}
		
		this.returnMainPage();
	}

	public void cancel() {
		this.mapOfUpdateCallBack.clear();
		this.redirect("/portal/index.jsf");
	}
	
	public void returnMainPage() {
		this.redirect("/portal/index.jsf");
	}

	public DateNode getDataNode() {
		return dataNode;
	}
	
	public boolean isDisabledDataInicioPrevista() {
		if(this.isPlanningManagerOrPlanningPeriod())
			return false;
		if(this.dataNode.getData().getDataInicioPrevista() == null)
			return false;
		return true;
	}
	public boolean isDisabledDataInicioEfetiva() {
		if(this.isPlanningManagerOrPlanningPeriod())
			return false;
		if(this.dataNode.getData().getDataInicioEfetiva() == null)
			return false;
		return true;
	}
	public boolean isDisabledDataFimPrevista() {
		if(this.isPlanningManagerOrPlanningPeriod())
			return false;
		if(this.dataNode.getData().getDataFimPrevista() == null)
			return false;
		return true;
	}
	public boolean isDisabledDataFimEfetiva() {
		if(this.isPlanningManagerOrPlanningPeriod())
			return false;
		if(this.dataNode.getData().getDataFimEfetiva() == null)
			return false;
		return true;
	}

	public boolean isEnabledCustoPrevisto() {
		if(this instanceof ProjetoTreeNode)
			return false;
		if(this.isPlanningManagerOrPlanningPeriod())
			return true;
		if(this.dataNode.getCustoPrevisto() == null)
			return true;
		return false;
	}
	
	public boolean isEnabledCustoEfetivo() {
		if(this instanceof ProjetoTreeNode)
			return false;
		if(this.isPlanningManagerOrPlanningPeriod())
			return true;
		if(this.dataNode.getCustoEfetivo() == null)
			return true;
		return false;
	}

	protected boolean isPlanningManagerOrPlanningPeriod() {
		if(((SisplanUser)this.getMBean("sisplanUser")).isPlanningManager()
			|| ((PeriodoPlanControllerBean)getMBean("periodoPlanControllerBean")).isPeriodoPlanAtivo() )
			return true;
		
		return false;
	}
	
	public boolean isActive() {
		DateTime dtFimPrev = new DateTime(this.getDataFimPrevista());
		DateTime dtFimEfe = new DateTime(this.getDataFimEfetiva());

		int days = Days.daysBetween(dtFimPrev, dtFimEfe).getDays();
		if(days < 0)
			return false;
		
		return true;
	}
	
	public boolean isProjeto() {
		return (this instanceof ProjetoTreeNode);
	}
	
	public String getResponsavelNameSelected() {
		return responsavelName;
	}

	public void setResponsavelNameSelected(String responsavelNameSelected) {
		this.responsavelName = responsavelNameSelected;
	}
	
	public List<SelectItem> getResponsaveis() {
		List<SelectItem> listRes = new ArrayList<SelectItem>();
		for(Responsavel r: this.responsaveis) {
			listRes.add(new SelectItem(r.getName()));
			
		}
		return listRes;
	}
	
	public void responsavelSelectedListener(ValueChangeEvent e) {
		this.setResponsavelName((String)e.getNewValue());
	}
	
    protected Responsavel getResponsavel(String resName) {
    	for(Responsavel r: this.responsaveis)
    		if(r.getName().equals(resName))
    			return r;
		return null;
    }
    
	public void setResponsavelName(String name) {
		if(!name.isEmpty() && !name.equals(this.responsavelName) ) {
			this.oldResName = this.responsavelName;
			this.addCallBackMethod("setResponsaveNamelCallBack", String.class, name);
		}
	}
    
	public void setResponsaveNamelCallBack(String newName) {
		LogHistory.getInstance().log((TreeNodeInfoIface) this, 
				(TreeNodeCadastroIface) this, "Responsável",
				oldResName, newName);
		this.responsavelName = newName;
		this.responsavel = this.getResponsavel(responsavelName);
		this.setIdResponsavel();
		this.changedResponsavel = true;
	}
	
	public boolean isLate() {
		DateTime dtFimPrev = new DateTime(this.getDataFimPrevista());
		DateTime dtToday = new DateTime(new Date());
		int days = Days.daysBetween(dtToday, dtFimPrev).getDays();
		if(days < 0 && (this.getDataFimEfetiva() == null))
			return true;
		return false;
	}
	
	public void fireEmail() {
		String msg = null;
		if(this instanceof EtapaTreeNode)
			msg = this.getEtapaEmailMsg();
		else
			msg = this.getProjetoEmailMsg();
		String subject = "Atraso na execução do planejamento - SISPlan";
		int unidadeID = ((TreeNodeCadastroIface)this.parentNode).getUnidadeID();
		String email = getDAO(EmailDao.class).selectEmailPlanningDirector(unidadeID);
		
		EmailSender sender = new EmailSender(subject, msg, email);
		Thread emailSenderThread = new Thread(sender);
		emailSenderThread.run();
	}
	
	private String getEtapaEmailMsg() {
		String msg = "A etapa está atrasada, pois a data de fim prevista expirou"
				   + " e não foi cadastrada data efetiva de fim.\n";
		msg += "Nome: " + ((EtapaTreeNode)this).getName() + "\n";
		msg += "Data Fim Prevista: " + ((EtapaTreeNode)this).getDataFimPrevista().toString() + "\n";
		msg += "Projeto: " + ((ProjetoTreeNode)this.parentNode).getName();
		return msg;
	}
	
	private String getProjetoEmailMsg() {
		String msg = "O projeto está atrasado, pois a data de fim prevista expirou"
				   + " e não foi cadastrada data efetiva de fim.\n";
		msg += "Nome: " + ((ProjetoTreeNode)this).getName() + "\n";
		msg += "Data Fim Prevista: " + ((ProjetoTreeNode)this).getDataFimPrevista().toString() + "\n";
		msg += "Estratégia: " + ((EstrategiaTreeNode)this.parentNode).getName();
		return msg;
	}
	
	public void fireUpdateSentEmail() {
		final boolean isProject = (this instanceof ProjetoTreeNode)? true: false;
		final int id = this.getMyID();
		final Date firstEmail = (isProject)? ((Projeto)this.dataNode).getFirstEmail(): ((Etapa)this.dataNode).getFirstEmail(); 
		final boolean isFirstEmail = (firstEmail == null)? true: false;
		if(isFirstEmail) {
			if(isProject)
				((Projeto)this.dataNode).setFirstEmail(new Date());
			else
				((Etapa)this.dataNode).setFirstEmail(new Date());
		}
			
			
		Thread updateDBThread = new Thread(new Runnable() {
			public void run() {
				if(isProject) {
					getDAO(ProjetoDao.class).updateSentEmail(id, true);
					if(isFirstEmail) {
						Date dt = new Date();
						getDAO(ProjetoDao.class).updateFirstEmail(id, dt);
					}
				}
				else {
					getDAO(EtapaDao.class).updateSentEmail(id, true);
					if(isFirstEmail) {
						Date dt = new Date();
						getDAO(EtapaDao.class).updateFirstEmail(id, dt);
					}
				}
				
			}
		});
		
		updateDBThread.run();
	}
}