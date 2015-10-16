package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.tree.PDITreeNode;
import br.ifpr.sisplan.model.dao.PDIDao;
import br.ifpr.sisplan.model.dao.UnidadeDao;
import br.ifpr.sisplan.model.table.PDI;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

public class PDIControllerCached extends AbstractController {
	private static final long serialVersionUID = -24243561012048004L;
	private static PDIControllerCached instance;
	private List<TreeNode> pdis;
	private PDITreeNode currentPDI;
	private List<Unidade> listUnidades = new ArrayList<Unidade>();
	private final Unidade unidadeAll = new Unidade("Todas"); 

	private PDIControllerCached() {
		this.setListUnidades();
	}
	
	public static PDIControllerCached getInstance() {
		if(instance == null)
			instance = new PDIControllerCached();
		return instance;
	}
	
    public PDITreeNode getCurrentPDI() {
    	if(this.currentPDI == null)
    		this.getPdis();
		return currentPDI;
	}
    
    public void buildTree() {
		final List<PDI> listPDIs = ConverterToList.convertListMappedToList(getDAO(PDIDao.class).selectAll(), PDI.class);
		this.pdis = new ArrayList<TreeNode>();
		for(PDI pdi: listPDIs) {
			PDITreeNode pdiTree = new PDITreeNode(pdi);
			pdis.add(pdiTree);
		}
		if(pdis.size() > 0)
			this.setCurrentPDI((PDITreeNode)pdis.get(pdis.size()-1));
    }
    
    private void setListUnidades() {
    	this.listUnidades = ConverterToList.convertListMappedToList(getDAO(UnidadeDao.class).selectAll(), Unidade.class);
    }
    
    public List<Unidade> getListUnidades() {
    	return this.listUnidades;
    }

    public Unidade getUnidade(String unidadeName) {
    	for(Unidade u: this.listUnidades)
    		if(u.getName().equals(unidadeName))
    			return u;
		return null;
    }
    
	public Unidade getUnidadeAll() {
		return unidadeAll;
	}

	public void setCurrentPDI(PDITreeNode currentPDI) {
		this.currentPDI = currentPDI;
	}
	
	public List<TreeNode> getPdis() {
		if(this.pdis == null)
			this.buildTree();
		return this.pdis;
	}
}