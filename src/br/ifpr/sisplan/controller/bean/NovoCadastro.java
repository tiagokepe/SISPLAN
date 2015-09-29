package br.ifpr.sisplan.controller.bean;

import br.ifpr.sisplan.controller.ifaces.TreeNodeActionsIface;
import br.ufrn.arq.web.jsf.AbstractController;

public abstract class NovoCadastro<P> extends AbstractController implements TreeNodeActionsIface {
	private static final long serialVersionUID = -7552682724209700348L;
	protected P parent;
	protected String name;

	public void setTreeNodeParent(P parent) {
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