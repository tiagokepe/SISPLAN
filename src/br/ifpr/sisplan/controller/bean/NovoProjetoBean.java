package br.ifpr.sisplan.controller.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ajax4jsf.event.AjaxEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.util.DateUtil;

@Component
@Scope("session")
public class NovoProjetoBean extends NovoCadastro implements org.ajax4jsf.event.AjaxListener {
	private static final long serialVersionUID = 7157061703783244442L;
	private static NovoProjetoBean instance;
	
	private String name;
	private String desc;
	private Date dataInicioPrevista;
	private Date dataInicioEfetiva;
	private Date dataFimPrevista;
	private Date dataFimEfetiva;
	
	public static NovoProjetoBean getInstance() {
		if(instance == null)
			instance = new NovoProjetoBean();
		
		return instance;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getEstrategiaName() {
		return this.parent.getName();
	}

	public String getEstrategiaDesc() {
		return this.parent.getDesc();
	}
	
	public void processAjax(AjaxEvent event) {
		System.out.println("AJAX");
	}

	public void save() {
		// TODO Auto-generated method stub
	}
}