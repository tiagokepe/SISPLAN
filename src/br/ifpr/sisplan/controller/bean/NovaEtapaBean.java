package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.tree.DiretrizTreeNode;
import br.ifpr.sisplan.controller.tree.EixoTreeNode;
import br.ifpr.sisplan.controller.tree.EstrategiaTreeNode;
import br.ifpr.sisplan.controller.tree.EtapaTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.controller.tree.ProjetoTreeNode;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ResponsavelDao;
import br.ifpr.sisplan.model.table.Data;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Responsavel;
import br.ifpr.sisplan.util.DateUtil;

@Component
@Scope("session")
public class NovaEtapaBean extends NovoCadastro<ProjetoTreeNode> {
	private static final long serialVersionUID = 7157061703783244442L;
	
	private Date dataInicioPrevista;
	private Date dataInicioEfetiva;
	private Date dataFimPrevista;
	private Date dataFimEfetiva;
	
	private List<Responsavel> responsaveis;
	private Responsavel responsavelSelected;
	private String responsavelNameSelected;
	
	public NovaEtapaBean() {
		System.out.println("------NovaEtapaBean");
	}
	
	public Date getDataInicioPrevista() {
		return dataInicioPrevista;
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

	private Date getDateFromString(String strDate) {
		if(!DateUtil.validateDateFormat(strDate)) {
			return null;
		}
		
		return DateUtil.stringToDate(strDate);
	}
	
	public String getProjetoName() {
		return this.parent.getName();
	}

	public String getProjetoDesc() {
		return this.parent.getDescricao();
	}
	
	public String getResponsavelNameSelected() {
		return responsavelNameSelected;
	}

	public void setResponsavelNameSelected(String responsavelNameSelected) {
		this.responsavelNameSelected = responsavelNameSelected;
	}

	public void setResponsaveis() {
		PDIControllerCached pdiCache =  PDIControllerCached.getInstance();
		this.responsaveis = ResponsavelDao.getInstance().selectResponsavelByUnidade(((ObjetivoEspecificoTreeNode)parent.getParent().getParent()).getUnidadeID());			
	}
	
	public List<SelectItem> getResponsaveis() {
		if(this.responsaveis == null)
			this.setResponsaveis();
		
		List<SelectItem> listRes = new ArrayList<SelectItem>();
		for(Responsavel r: this.responsaveis) {
			listRes.add(new SelectItem(r.getName()));
			
		}
		return listRes;
	}
	
	public Responsavel getResponsavelSelected() {
		if(this.responsavelSelected == null)
			this.responsavelSelected = this.getResponsavel(responsavelNameSelected);
		return responsavelSelected;
	}

	public void setResponsavelSelected(Responsavel responsavelSelected) {
		this.responsavelSelected = responsavelSelected;
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

	public void save() {
		//TODO Atualizar o projeto em todos as estratégias. Um projeto pode estar
		// vinculado a mais de uma estratégia e a etapa deve ser adicionada em
		// cada ProjetoTreeNode do mesmo projeto. 
		if(this.validateFields()) {
			Map<String, Date> mapFieldValue = new HashMap<String, Date>();
			if(dataInicioPrevista != null) mapFieldValue.put("data_inicio_prevista", dataInicioPrevista);
			if(dataInicioEfetiva != null) mapFieldValue.put("data_inicio_efetiva", dataInicioEfetiva);
			if(dataFimPrevista != null) mapFieldValue.put("data_fim_prevista", dataFimPrevista);
			if(dataFimEfetiva != null) mapFieldValue.put("data_fim_efetiva", dataFimEfetiva);
			
			Data dt =  getDAO(DataDao.class).insertData(mapFieldValue);
			
			Etapa etapa = getDAO(EtapaDao.class).insertEtapa(descricao, parent.getMyID(), this.getResponsavelSelected().getId());
			etapa.setData(dt);
			getDAO(EtapaDao.class).insertEtapaAndData(etapa.getId(), dt.getId());
			for(EixoTreeNode eixo: ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getEixosTree())
				for(DiretrizTreeNode dir: eixo.getDiretrizesTree())
					for(ObjetivoEstrategicoTreeNode objEst: dir.getObjetivosTree())
						for(ObjetivoEspecificoTreeNode objEsp: objEst.getAllObjetivos())
							for(EstrategiaTreeNode est: objEsp.getEstrategiasTree())
								for(ProjetoTreeNode proj: est.getProjetosTree())
									if(proj.getMyID() == this.parent.getMyID())
										proj.addTreeNodeChild(new EtapaTreeNode(proj, etapa, proj.getChildCount()));
			
		}
		this.returnMainPage();
	}

	@Override
	protected void initInternalStructure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean validateFields() {
		boolean ret = true;
		if(this.responsavelNameSelected.isEmpty()) {
			addMensagemErro("Responsável pela etapa não selecionado.");
			ret = false;
		}
		if(this.descricao.isEmpty()) {
			addMensagemErro("Descrição está vazia, ela deve ser preenchida.");
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
}