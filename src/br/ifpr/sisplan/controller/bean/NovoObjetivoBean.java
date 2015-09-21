package br.ifpr.sisplan.controller.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.tree.ObjetivoEspecificoTreeNode;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;
import br.ifpr.sisplan.controller.tree.UnidadeTreeNode;
import br.ifpr.sisplan.model.dao.ObjetivoEspecificoDao;
import br.ifpr.sisplan.model.table.ObjetivoEspecifico;

@Component
@Scope("session")
public class NovoObjetivoBean extends NovoCadastro {
	private static final long serialVersionUID = -4222480778726435646L;
	private static NovoObjetivoBean instance;
	
	public static NovoObjetivoBean getInstance() {
		if(instance == null)
			instance = new NovoObjetivoBean();
		
		return instance;
	}
	
	public String getUnidadeName() {
		return this.parent.getName();
	}
	
	public String getObjetivoEstrategicoName() {
		return ((ObjetivoEstrategicoTreeNode)this.parent.getParent()).getName();
	}
	
	public String getObjetivoEstrategicoDesc() {
		return ((ObjetivoEstrategicoTreeNode)this.parent.getParent()).getDesc();
	}

	public void save() {
		final ObjetivoEspecifico objEspecifico = getDAO(ObjetivoEspecificoDao.class).insertObj(this.name);
		final int unidade_id, obj_estrategico_id;
		unidade_id = this.parent.getMyID();
		obj_estrategico_id = ((ObjetivoEstrategicoTreeNode)this.parent.getParent()).getMyID();
		getDAO(ObjetivoEspecificoDao.class).insertUnidadeObjetivos(unidade_id, obj_estrategico_id, objEspecifico.getId());
		
		((UnidadeTreeNode)this.parent).addTreeNodeChild(new ObjetivoEspecificoTreeNode(this.parent, objEspecifico, this.parent.getChildCount() + 1));
		this.returnMainPage();
	}

}
