package br.ifpr.sisplan.controller.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroAbstract;
import br.ifpr.sisplan.controller.tree.EstrategiaTreeNode;
import br.ifpr.sisplan.controller.tree.UnidadeTreeNode;
import br.ifpr.sisplan.model.dao.EstrategiaDao;
import br.ifpr.sisplan.model.table.Estrategia;

@Component
@Scope("session")
public class NovaEstrategiaBean extends NovoCadastro<TreeNodeCadastroAbstract> {
	private static final long serialVersionUID = 7471068395949877404L;
	private static NovaEstrategiaBean instance;
	
	public static NovaEstrategiaBean getInstance() {
		if(instance == null)
			instance = new NovaEstrategiaBean();
		
		return instance;
	}

	public String getUnidadeName() {
		return ((UnidadeTreeNode)this.parent.getParent()).getName();
	}
	
	public String getObjetivoName() {
		return this.parent.getName();
	}

	public String getObjetivoDesc() {
		return this.parent.getDesc();
	}

	public void save() {
		int id_obj_especifico = this.parent.getMyID();
		final Estrategia est = getDAO(EstrategiaDao.class).insertEstrategia(this.name, id_obj_especifico);
		this.parent.addTreeNodeChild(new EstrategiaTreeNode(this.parent, est, this.parent.getChildCount() + 1));
		this.returnMainPage();
	}
}
