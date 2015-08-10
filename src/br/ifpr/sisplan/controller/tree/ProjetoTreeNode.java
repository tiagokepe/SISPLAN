package br.ifpr.sisplan.controller.tree;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeActions;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCallBack;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.EtapaDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.util.ConverterToList;
import br.ifpr.sisplan.util.NameNode;
import br.ufrn.arq.web.jsf.AbstractController;

import com.google.common.collect.Iterators;

public class ProjetoTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeDetails, TreeNodeActions, TreeNodeCallBack {
	private static final long serialVersionUID = -7787388029320598005L;
	private EstrategiaTreeNode parentEstrategia;
	private Projeto myProjeto;
	private List<EtapaTreeNode> etapasTree = new ArrayList<EtapaTreeNode>();
	private boolean changedDatas = false;
	private boolean changedDescricao = false;
	
	//TODO ver como implementa CALL BACK
	private Map<Method, Object> mapOfUpdateCallBack = new HashMap<Method, Object>();
	
	public ProjetoTreeNode(EstrategiaTreeNode parent, Projeto projeto) {
		this.parentEstrategia = parent;
		this.myProjeto = projeto;
		this.setEtapasTree();
	}
	
	public TreeNode getChildAt(int paramInt) {
		return etapasTree.get(paramInt);
	}

	public int getChildCount() {
		return this.etapasTree.size();
	}

	public TreeNode getParent() {
		return this.parentEstrategia;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return this.etapasTree.indexOf(paramTreeNode);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return this.etapasTree.isEmpty();
	}

	public Enumeration children() {
		return Iterators.asEnumeration(this.etapasTree.iterator());
	}
	
	public List<EtapaTreeNode> getEtapasTree() {
		return etapasTree;
	}

	public void setEtapasTree() {
		final List<Etapa> etapas = ConverterToList.convertListMappedToList(getDAO(EtapaDao.class).selectEtapaByProject(this.myProjeto.getId()), Etapa.class);

		for(Etapa e: etapas) {
			this.setDataEtapa(e);
			final EtapaTreeNode etapaTree = new EtapaTreeNode(this, e);
			this.etapasTree.add(etapaTree);
		}
	}
	
	private void setDataEtapa(Etapa e) {
		e.setData(getDAO(DataDao.class).selectDataByEtapa(e.getId()));
	}

	@Override
	public String toString() {
		return myProjeto.toString();
	}

	public String getType() {
		return this.myProjeto.getType();
	}

	public String getName() {
		return this.myProjeto.getName();
	}
	
	public String getHint() {
		return "Projeto Hint!";
	}

	public String getDescricao() {
		return this.myProjeto.getDescricao();
	}
	
	public void setDescricao(String desc) {
		if(!desc.isEmpty() && !desc.equals(this.myProjeto.getDescricao())) {
			this.addCallBackMethod("setDescricaoCallBack", String.class, desc);
		}
	}
	
	public void setDescricaoCallBack(String desc) {
		this.myProjeto.setDescricao(desc);
		this.changedDescricao = true;
	}
	
	public boolean isRenderedDescription() {
		return true;
	}

	public String getHeaderDetails() {
		return "Informações de Projeto";
	}

	public String getDataInicioPrevista() {
		Date dt = this.myProjeto.getData().getDataInicioPrevista();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioPrevista(String strDate) {
		if(strDate.isEmpty())
			return;
		Date newDate = this.stringToDate(strDate);
		if(this.myProjeto.getData().getDataInicioPrevista().compareTo(newDate) != 0) {
			this.addCallBackMethod("setDataInicioPrevistaCallBack", Date.class, newDate);
		}
	}

	public void setDataInicioPrevistaCallBack(Date newDate) {
		this.myProjeto.getData().setDataInicioPrevista(newDate);
		this.changedDatas = true;
	}

	public String getDataInicioEfetiva() {
		Date dt = this.myProjeto.getData().getDataInicioEfetiva();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioEfetiva(String strDate) {
		if(strDate.isEmpty())
			return;
		Date newDate = this.stringToDate(strDate);
		if(this.myProjeto.getData().getDataInicioEfetiva().compareTo(newDate) != 0) {
			this.addCallBackMethod("setDataInicioEfetivaCallBack", Date.class, newDate);
		}
	}
	
	public void setDataInicioEfetivaCallBack(Date newDate) {
		this.myProjeto.getData().setDataInicioEfetiva(newDate);
		this.changedDatas = true;
	}
	
	public String getDataFimPrevista() {
		Date dt = this.myProjeto.getData().getDataFimPrevista();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimPrevista(String strDate) {
		if(strDate.isEmpty())
			return;
		Date newDate = this.stringToDate(strDate);
		if(this.myProjeto.getData().getDataFimPrevista().compareTo(newDate) != 0) {
			this.addCallBackMethod("setDataFimPrevistaCallBack", Date.class, newDate);
		}
	}
	
	public void setDataFimPrevistaCallBack(Date newDate) {
		this.myProjeto.getData().setDataFimPrevista(newDate);
		this.changedDatas = true;
	}

	public String getDataFimEfetiva() {
		Date dt = this.myProjeto.getData().getDataFimEfetiva();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimEfetiva(String strDate) {
		if(strDate.isEmpty())
			return;
		Date newDate = this.stringToDate(strDate);
		if(this.myProjeto.getData().getDataFimEfetiva().compareTo(newDate) != 0) {
			this.addCallBackMethod("setDataFimEfetivaCallBack", Date.class, newDate);
		}
	}
	
	public void setDataFimEfetivaCallBack(Date newDate) {
		this.myProjeto.getData().setDataFimEfetiva(newDate);
		this.changedDatas = true;
	}

	public void redirectToIndex() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/SISPLAN/portal/index.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save() {
		this.callBack();
		if(this.changedDescricao)
			this.getDAO(ProjetoDao.class).updateDescricao(this.myProjeto);
		if(this.changedDatas)
			this.getDAO(DataDao.class).updateData(this.myProjeto.getData());
		
		this.redirect("/portal/index.jsf");
	}

	public void cancel() {
		this.redirect("/portal/index.jsf");
	}
	
	private Date stringToDate(String strDate) {
		Date dt=null;
		try {
			dt = new SimpleDateFormat(NameNode.DefaultDateFormat).parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
	
	private void addCallBackMethod(String method, Class paramClazz, Object param) {
		try {
			this.mapOfUpdateCallBack.put(TreeNodeCallBack.class.getMethod(method, paramClazz), param);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void callBack() {
		Method method;
		Object param;
		for(Entry<Method, Object> e: this.mapOfUpdateCallBack.entrySet()) {
			method = e.getKey();
			param = e.getValue();
			try {
				method.invoke(this, param);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void valueChangeListener(ValueChangeEvent e) {
		String id_value = (String)e.getComponent().getAttributes().get("id");
		String newValue = e.getNewValue().toString();
		String methodName = id_value.replace("id_", "");
		
		try {
			this.mapOfUpdateCallBack.put(TreeNodeDetails.class.getMethod(methodName, String.class),
										newValue);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}