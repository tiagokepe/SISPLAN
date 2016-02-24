package br.ifpr.sisplan.controller.ifaces;

import br.ifpr.sisplan.controller.tree.TreeNodeGeneric;

public interface TreeNodeCadastroIface {
	public String getCadastroURL();
	public String getCadastroTitle();
	public String getAlterarURL();
	public void addTreeNodeChild(TreeNodeGeneric child);
	public void removeTreeNodeChild(TreeNodeGeneric child);
	public abstract void delete();
	public abstract void deleteFromDB();
	public void save();
	public void cancel();
	public String getUnidadeName();
}
