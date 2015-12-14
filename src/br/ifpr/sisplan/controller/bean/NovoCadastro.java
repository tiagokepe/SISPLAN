package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.ifaces.TreeNodeActionsIface;
import br.ifpr.sisplan.model.table.Unidade;
import br.ufrn.arq.web.jsf.AbstractController;

public abstract class NovoCadastro<P> extends AbstractController implements TreeNodeActionsIface {
	private static final long serialVersionUID = -7552682724209700348L;
	protected P parent;
	protected String name;
	protected String descricao = "";
	protected String unidadeName = "";
	private Unidade unidadeSelected;
	protected List<SelectItem> listUnidades;
	protected final String SELECIONE_UNIDADE = "Selecione Unidade"; 

	public NovoCadastro() {
		this.buildListUnidade();
		if(listUnidades.size() == 1)
			this.setUnidadeName(listUnidades.get(0).getLabel());
		else
			this.setUnidadeName(SELECIONE_UNIDADE);
		this.initInternalStructure();
	}
	
	protected abstract void initInternalStructure();
	protected abstract boolean validateFields();


	public void setTreeNodeParent(P parent) {
		this.parent = parent;
	}

	public void cancel() {
		this.forward("/portal/index.jsf");
	}

	public void returnMainPage() {
		this.redirect("/portal/index.jsf");		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getUnidadeName() {
		return this.unidadeName;
	}
	
	public void setUnidadeName(String unidadeName) {
		this.unidadeName = unidadeName;
	}
	
	public Unidade getUnidadeSelected() {
		if(unidadeSelected == null)
			this.setUnidadeSelected();
		return this.unidadeSelected;
	}

	public void setUnidadeSelected() {
		this.unidadeSelected = PDIControllerCached.getInstance().getUnidade(this.unidadeName);
	}
	
	public void unidadeSelectedListener(ValueChangeEvent e) {
		this.unidadeName = (String)e.getNewValue();
	}
	
	public List<SelectItem> getListUnidades() {
		return this.listUnidades;
	}
	
	public void buildListUnidade() {
		this.listUnidades = new ArrayList<SelectItem>();
		SisplanUser sisplanUser = (SisplanUser)this.getMBean("sisplanUser");
		if(sisplanUser.isPlanningManager()) {
			Unidade unidadeAll = PDIControllerCached.getInstance().getUnidadeAll();	
			this.listUnidades.add(new SelectItem(unidadeAll.getName()));
		}
		for(Unidade u: sisplanUser.getUnidades())
			this.listUnidades.add(new SelectItem(u.toString()));
	}

}