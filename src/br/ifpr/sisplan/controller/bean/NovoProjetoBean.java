package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
import br.ifpr.sisplan.controller.tree.EstrategiaTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.controller.tree.ProjetoTreeNode;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.dao.ResponsavelDao;
import br.ifpr.sisplan.model.table.Data;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.model.table.Responsavel;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.DateUtil;
import br.ufrn.arq.seguranca.log.Logger;

@Component
@Scope("request")
public class NovoProjetoBean extends NovoCadastro<TreeNodeCadastroAbstract> {
	private static final long serialVersionUID = 7157061703783244442L;
	
	private Date dataInicioPrevista;
	private Date dataInicioEfetiva;
	private Date dataFimPrevista;
	private Date dataFimEfetiva;
	private List<Responsavel> responsaveis;
	private Responsavel responsavelSelected;
	private String responsavelNameSelected;
	
    private List<SelectItem> availableEst; 
    private List<String> selectedEst;
    
	private Map<String, List<EstrategiaTreeNode>> mapEstrategiaTreeNode;
	
	private NovoProjetoBean() {
		this.setResponsaveis();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDataInicioPrevista(Date dataInicioPrevista) {
		this.dataInicioPrevista = dataInicioPrevista;
	}
	
	public Date getDataInicioEfetiva() {
		return dataInicioEfetiva;
	}

	public void setDataInicioEfetiva(Date dataInicioEfetiva) {
		this.dataInicioEfetiva = dataInicioEfetiva;
	}

	public Date getDataFimPrevista() {
		return dataFimPrevista;
	}

	public void setDataFimPrevista(Date dataFimPrevista) {
		this.dataFimPrevista = dataFimPrevista;
	}

	public Date getDataFimEfetiva() {
		return dataFimEfetiva;
	}

	public void setDataFimEfetiva(Date dataFimEfetiva) {
		this.dataFimEfetiva = dataFimEfetiva;
	}

	public Date getDataInicioPrevista() {
		return dataInicioPrevista;
	}

	private Date getDateFromString(String strDate) {
		if(!DateUtil.validateDateFormat(strDate)) {
			return null;
		}
		
		return DateUtil.stringToDate(strDate);
	}
	
	public String getEstrategiaName() {
		return this.parent.getName();
	}

	public String getEstrategiaDesc() {
		return this.parent.getDescricao();
	}
	
	public List<SelectItem> getResponsaveis() {
		List<SelectItem> listRes = new ArrayList<SelectItem>();
		for(Responsavel r: this.responsaveis) {
			listRes.add(new SelectItem(r.getName()));
			
		}
		return listRes;
	}
	
	public void setResponsaveis() {
		PDIControllerCached pdiCache =  PDIControllerCached.getInstance();
		if(!(this.unidadeName.isEmpty() || this.unidadeName.equals(pdiCache.getUnidadeAll().getName())
			|| this.unidadeName.equals(this.SELECIONE_UNIDADE))) {
			Unidade unidadeSelected = pdiCache.getUnidade(unidadeName);
			this.responsaveis = ResponsavelDao.getInstance().selectResponsavelByUnidade(unidadeSelected.getId());			
		}
		else
			this.responsaveis = new ArrayList<Responsavel>();
	}

	public Responsavel getResponsavelSelected() {
		return responsavelSelected;
	}

	public void setResponsavelSelected(Responsavel responsavelSelected) {
		this.responsavelSelected = responsavelSelected;
	}

	public String getResponsavelNameSelected() {
		return responsavelNameSelected;
	}

	public void setResponsavelNameSelected(String responsavelNameSelected) {
		this.responsavelNameSelected = responsavelNameSelected;
	}

	public void save() {
		if(validateFields()) {
			Map<String, Date> mapFieldValue = new HashMap<String, Date>();
			if(dataInicioPrevista != null) mapFieldValue.put("data_inicio_prevista", dataInicioPrevista);
			if(dataInicioEfetiva != null) mapFieldValue.put("data_inicio_efetiva", dataInicioEfetiva);
			if(dataFimPrevista != null) mapFieldValue.put("data_fim_prevista", dataFimPrevista);
			if(dataFimEfetiva != null) mapFieldValue.put("data_fim_efetiva", dataFimEfetiva);
			
			Data dt =  getDAO(DataDao.class).insertData(mapFieldValue);
			
			ResponsavelDao.getInstance().insertResponsavel(this.responsavelSelected);
			Projeto projeto = getDAO(ProjetoDao.class).insertProjeto(name, descricao, this.responsavelSelected.getId());
			projeto.setData(dt);
			getDAO(ProjetoDao.class).insertProjetoAndData(projeto.getId(), dt.getId());
			for(String strEst: this.selectedEst) {
				for(EstrategiaTreeNode est: this.mapEstrategiaTreeNode.get(strEst)) {
					try {
						// Try to insert a relationship between ObjetivoEspecifico e Estrategia, even ignoring primary key constraint
						getDAO(EstrategiaDao.class).insertRelationshipEstrategiaProjeto(est.getMyID(), projeto.getId());
					}
					catch(DuplicateKeyException e) {
						Logger.warn("SISPLAN - DuplicateKeyException, violation of the primary key constraint,"
								+ "i.e., relationship between Estrategia("+est.getMyID()+") and Projeto("+projeto.getId()+") already exist.");
					} catch(Exception e) {
						e.printStackTrace();
					}
					ProjetoTreeNode projTree = new ProjetoTreeNode(est, projeto, est.getChildCount());
					est.addTreeNodeChild(projTree);
				}
			}
			this.returnMainPage();
		}
	}
	
	@Override
	protected boolean validateFields() {
		boolean ret = true; 
		if(this.descricao.isEmpty()) {
			addMensagemErro("Descrição está vazia, ela deve ser preenchida.");
			ret = false;
		}
		if(this.unidadeName.isEmpty() || this.unidadeName.equals(SELECIONE_UNIDADE)
				|| this.unidadeName.equals(PDIControllerCached.getInstance().getUnidadeAll())) {
			addMensagemErro("Unidade não pode ser vazia.");
			ret = false;
		}
		if(!(this.selectedEst != null && !this.selectedEst.isEmpty())) {
			addMensagemErro("Nenhum Objetivo Específico foi selecionado.");
			ret = false;
		}
		if(this.dataInicioPrevista == null) {
			addMensagemErro("Data início prevista não preenchida.");
			ret = false;
		}
		if(this.dataFimPrevista == null) {
			addMensagemErro("Data fim prevista não preenchida.");
			ret = false;
		}

		//TODO Verificar se as datas efetivas são maiores que as previstas
		if(this.dataInicioPrevista != null && this.dataFimPrevista != null) {
			DateTime dtIniPrev = new DateTime(this.dataInicioPrevista);
			DateTime dtFimPrev = new DateTime(this.dataFimPrevista);
			int days = Days.daysBetween(dtIniPrev, dtFimPrev).getDays();
			if(days < 0) {
				addMensagemErro("Data fim prevista é mais antiga que a data de início.");
				ret = false;
			}
		}
		
		if(this.dataInicioEfetiva != null && this.dataFimEfetiva != null) {
			DateTime dtIniEfe = new DateTime(this.dataInicioEfetiva);
			DateTime dtFimEfe = new DateTime(this.dataFimEfetiva);
			int daysEfe = Days.daysBetween(dtIniEfe, dtFimEfe).getDays();
			if(daysEfe < 0) {
				addMensagemErro("Data fim efetiva é mais antiga que a data de início.");
				ret = false;
			}
					
		}
		
		return ret;
	}
	
	public void responsavelSelectedListener(ValueChangeEvent e) {
		this.responsavelNameSelected = (String)e.getNewValue();
		this.responsavelSelected = this.getResponsavel(responsavelNameSelected);
	}
	
    private Responsavel getResponsavel(String resName) {
    	for(Responsavel r: this.responsaveis)
    		if(r.getName().equals(resName))
    			return r;
		return null;
    }

	public List<SelectItem> getAvailableEst() {
		return availableEst;
	}

	public void setAvailableEst(List<SelectItem> availableEst) {
		this.availableEst = availableEst;
	}

	public List<String> getSelectedEst() {
		return selectedEst;
	}

	public void setSelectedEst(List<String> selectedObjEsp) {
		this.selectedEst = selectedObjEsp;
	}
	
	@Override
	protected void initInternalStructure() {
		this.availableEst = new ArrayList<SelectItem>();
		this.mapEstrategiaTreeNode = new LinkedHashMap<String, List<EstrategiaTreeNode>>();
		for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
			for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
				for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree())
					for(ObjetivoEspecificoTreeNode objEsp: objEst.getAllObjetivos())
						for(EstrategiaTreeNode est: objEsp.getEstrategiasTree()) {
							List<EstrategiaTreeNode> listEst = this.mapEstrategiaTreeNode.get(est.getDescricao());
							if(listEst == null) {
								listEst = new ArrayList<EstrategiaTreeNode>();
								this.mapEstrategiaTreeNode.put(est.getDescricao(), listEst);
								this.availableEst.add(new SelectItem(est.getDescricao()));
							}
							listEst.add(est);
						}
	}
	
	@Override
	public void unidadeSelectedListener(ValueChangeEvent e) {
 		this.unidadeName = (String)e.getNewValue();
		this.availableEst.clear();
		String unidadeAll = PDIControllerCached.getInstance().getUnidadeAll().getName();
		
		for(Map.Entry<String, List<EstrategiaTreeNode>> entry: this.mapEstrategiaTreeNode.entrySet()) {
			EstrategiaTreeNode est = entry.getValue().get(0);
			if(est == null)
				continue;
			if( ((ObjetivoEspecificoTreeNode)est.getParent()).getUnidadeName().equals(this.unidadeName) || this.unidadeName.equals(unidadeAll))
				this.availableEst.add(new SelectItem(entry.getKey()));
		}
		if(this.selectedEst != null)
			this.selectedEst.clear();
		
		this.setResponsaveis();
	}
}