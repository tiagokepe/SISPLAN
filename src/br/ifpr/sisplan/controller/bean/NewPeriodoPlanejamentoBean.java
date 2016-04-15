package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.model.dao.PeriodoPlanejamentoDao;
import br.ifpr.sisplan.model.table.PeriodoPlanejamento;
import br.ifpr.sisplan.model.table.Unidade;

@Component
@Scope("request")
public class NewPeriodoPlanejamentoBean extends NovoCadastro<PeriodoPlanejamento> {
	private Date dataIni, dataFim;
	
	public NewPeriodoPlanejamentoBean() {
		super();
		//this.initInternalStructure();
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
/*	public String getUnidadeSelectedName() {
		return this.unidadeSelected.getName();
	}
	
	public Unidade getUnidadeSelected() {
		return unidadeSelected;
	}

	public void setUnidadeSelected(Unidade unidadeSelected) {
		this.unidadeSelected = unidadeSelected;
	}
*/	
	public List<SelectItem> getListUnidades() {
		return this.listUnidades;
	}
	
/*	public Unidade getUnidadeAll() {
		return PDIControllerCached.getInstance().getUnidadeAll();
	}
	
	private void setListUnidades() {
		this.listUnidades = new ArrayList<SelectItem>();
		SisplanUser sisplanUser = (SisplanUser)this.getMBean("sisplanUser");
		if(sisplanUser.isPlanningManager())
			this.listUnidades.add(new SelectItem(getUnidadeAll().toString(), getUnidadeAll().toString()));
		for(Unidade u: sisplanUser.getUnidades())
			this.listUnidades.add(new SelectItem(u.toString(), u.toString()));
	}
	
	public void unidadeSelectedListener(ValueChangeEvent e) {
		this.unidadeName = (String)e.getNewValue();
		this.unidadeSelected = PDIControllerCached.getInstance().getUnidade(unidadeName);
		if(unidadeName.equals(this.getUnidadeAll().getName()))
			this.unidadeSelected = this.getUnidadeAll();
		return;
	}*/

	public void save() {
		if(this.validateFields()) {
			PeriodoPlanejamento periodoPlan = this.getDAO(PeriodoPlanejamentoDao.class).insertPeriodoPlan(dataIni, dataFim);
			if(this.unidadeSelected.equals(PDIControllerCached.getInstance().getUnidadeAll())) {
				int idPdi = ((PDIControllerBean)this.getMBean("pdiControllerBean")).getCurrentPDI().getMyID();
				this.getDAO(PeriodoPlanejamentoDao.class).linkPdiAndPeriodoPlan(idPdi, periodoPlan.getId());
				((PeriodoPlanControllerBean)getMBean("periodoPlanControllerBean")).setPeriodoPlan(periodoPlan);
			}
			else {
				this.getDAO(PeriodoPlanejamentoDao.class).linkUnidadeAndPeriodoPlan(this.unidadeSelected.getId(), periodoPlan.getId());
			}
			
			this.returnMainPage();
		}
	}

	@Override
	protected boolean validateFields() {
		boolean ret = true;
		if(this.unidadeSelected == null || this.unidadeName.equals(this.SELECIONE_UNIDADE)) {
			addMensagemErro("A Unidade não foi selecionada.");
			ret = false;
		}
		if(this.dataIni == null) {
			addMensagemErro("Data início não preenchida.");
			ret = false;
		}
		if(this.dataFim == null) {
			addMensagemErro("Data fim não preenchida.");
			ret = false;
		}

		if(this.dataIni != null && this.dataFim != null) {
			DateTime dtIni = new DateTime(this.dataIni);
			DateTime dtFim = new DateTime(this.dataFim);
			int days = Days.daysBetween(dtIni, dtFim).getDays();
			if(days < 0) {
				addMensagemErro("Data fim é mais antiga que a data de início.");
				ret = false;
			}
		}
		return ret;
	}

	@Override
	protected void initInternalStructure() {
		this.unidadeSelected = this.getUnidadeAll();
	}
	
	@Override
	public void buildListUnidade() {
		this.listUnidades = new ArrayList<SelectItem>();
		SisplanUser sisplanUser = (SisplanUser)this.getMBean("sisplanUser");
		Unidade unidadeAll = PDIControllerCached.getInstance().getUnidadeAll();	
		this.listUnidades.add(new SelectItem(unidadeAll.getName()));
		for(Unidade u: sisplanUser.getUnidades())
			this.listUnidades.add(new SelectItem(u.toString()));
	}
}
