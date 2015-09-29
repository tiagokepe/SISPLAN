package br.ifpr.sisplan.controller.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.tree.EtapaTreeNode;
import br.ifpr.sisplan.controller.tree.ProjetoTreeNode;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.Data;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.util.DateUtil;

@Component
@Scope("session")
public class NovaEtapaBean extends NovoCadastro<ProjetoTreeNode> {
	private static final long serialVersionUID = 7157061703783244442L;
	private static NovaEtapaBean instance;
	
	private String desc;
	private Date dataInicioPrevista;
	private Date dataInicioEfetiva;
	private Date dataFimPrevista;
	private Date dataFimEfetiva;
	
	public static NovaEtapaBean getInstance() {
		if(instance == null)
			instance = new NovaEtapaBean();
		
		return instance;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDataInicioPrevista() {
		return dataInicioPrevista != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dataInicioPrevista): "";
	}

	public void setDataInicioPrevista(String dataInicioPrevista) {
		this.dataInicioPrevista = this.getDateFromString(dataInicioPrevista);
	}

	public String getDataInicioEfetiva() {
		return dataInicioEfetiva != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dataInicioEfetiva): "";
	}

	public void setDataInicioEfetiva(String dataInicioEfetiva) {
		this.dataInicioEfetiva = this.getDateFromString(dataInicioEfetiva);
	}

	public String getDataFimPrevista() {
		return dataFimPrevista != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dataFimPrevista): "";
	}

	public void setDataFimPrevista(String dataFimPrevista) {
		this.dataFimPrevista = this.getDateFromString(dataFimPrevista);
	}

	public String getDataFimEfetiva() {
		return dataFimEfetiva != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dataFimEfetiva): "";
	}

	public void setDataFimEfetiva(String dataFimEfetiva) {
		this.dataFimEfetiva = this.getDateFromString(dataFimEfetiva);
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
		return this.parent.getDesc();
	}

	public void save() {
		Data dt =  getDAO(DataDao.class).insertData(dataInicioPrevista, dataInicioEfetiva,
				dataFimPrevista, dataFimEfetiva);
		Etapa etapa = getDAO(EtapaDao.class).insertEtapa(desc, parent.getMyID());
		etapa.setData(dt);
		getDAO(EtapaDao.class).insertEtapaAndData(etapa.getId(), dt.getId());
		this.parent.addTreeNodeChild(new EtapaTreeNode(this.parent, etapa));
		this.returnMainPage();
	}
}
