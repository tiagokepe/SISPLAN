package br.ifpr.sisplan.controller.bean;

import br.ifpr.sisplan.controller.ifaces.TreeNodeActions;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastro;
import br.ufrn.arq.web.jsf.AbstractController;

public abstract class NovoCadastro extends AbstractController implements TreeNodeActions {
	private static final long serialVersionUID = -7552682724209700348L;
	protected TreeNodeCadastro parent;
	protected String name;

	public void setTreeNodeParent(TreeNodeCadastro parent) {
		this.parent = parent;
	}

	public void cancel() {
		this.redirect("/portal/index.jsf");
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
}