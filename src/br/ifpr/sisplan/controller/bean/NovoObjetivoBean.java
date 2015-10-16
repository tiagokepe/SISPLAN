package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.Unidade;

@Component
@Scope("session")
public class NovoObjetivoBean extends NovoCadastro<TreeNodeCadastroAbstract> {
	private static final long serialVersionUID = -4222480778726435646L;
	private static NovoObjetivoBean instance;
	private String unidadeName;
	private Unidade unidadeSelected;
	List<SelectItem> listUnidades;
	
	public NovoObjetivoBean() {
		this.buildListUnidade();
		if(listUnidades.size() == 1)
			this.setUnidadeName(listUnidades.get(0).getLabel());
		else
			this.setUnidadeName("Selecione Unidade");
	}
	
	public static NovoObjetivoBean getInstance() {
		if(instance == null)
			instance = new NovoObjetivoBean();
		
		return instance;
	}
	
	public String getUnidadeName() {
		return this.unidadeName;
	}
	
	public void setUnidadeName(String unidadeName) {
		this.unidadeName = unidadeName;
	}
	
	public String getObjetivoEstrategicoName() {
		return this.parent.getName();
	}
	
	public String getObjetivoEstrategicoDesc() {
		return this.parent.getDesc();
	}

	public void save() {
		final ObjetivoEspecifico objEspecifico = getDAO(ObjetivoEspecificoDao.class).insertObj(this.name);
		getDAO(ObjetivoEspecificoDao.class).insertUnidadeObjetivos(this.getUnidadeSelected().getId(), this.parent.getMyID(), objEspecifico.getId());
		
		this.parent.addTreeNodeChild(new ObjetivoEspecificoTreeNode(this.parent, objEspecifico, this.parent.getChildCount() + 1));
		this.returnMainPage();
	}

	public Unidade getUnidadeSelected() {
		if(unidadeSelected == null)
			this.setUnidadeSelected();
		return this.unidadeSelected;
	}

	public void setUnidadeSelected() {
		this.unidadeSelected = PDIControllerCached.getInstance().getUnidade(this.unidadeName);
	}
	
	public List<SelectItem> getListUnidades() {
		return this.listUnidades;
	}
	
	public void buildListUnidade() {
		this.listUnidades = new ArrayList<SelectItem>();
		for(Unidade u: PDIControllerCached.getInstance().getListUnidades())
			this.listUnidades.add(new SelectItem(u.toString(), u.toString()));
	}
	
	public void unidadeSelectedListener(ValueChangeEvent e) {
		this.unidadeName = (String)e.getNewValue();
	}

	public String getDefaultUnidadeName() {
		if(listUnidades.size() == 1)
			return listUnidades.get(0).getLabel();
		return "Selecione Unidade";
	}
}
