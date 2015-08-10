package br.ifpr.sisplan.controller.tree;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeActions;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.controller.ifaces.TreeNodeEvents;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.util.NameNode;
import br.ufrn.arq.web.jsf.AbstractController;

public class EtapaTreeNode extends AbstractController implements TreeNode, TreeNodeInfo, TreeNodeDetails, TreeNodeActions, TreeNodeEvents {
	private static final long serialVersionUID = -9205942028545960131L;
	private ProjetoTreeNode parentProjeto;
	private Etapa myEtapa;
	
	public EtapaTreeNode(ProjetoTreeNode parent, Etapa etapa) {
		this.parentProjeto = parent;
		this.myEtapa = etapa;
	}
	
	public TreeNode getChildAt(int paramInt) {
		return null;
	}

	public int getChildCount() {
		return 0;
	}

	public TreeNode getParent() {
		return this.parentProjeto;
	}

	public int getIndex(TreeNode paramTreeNode) {
		return 0;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public boolean isLeaf() {
		return true;
	}

	public Enumeration<TreeNode> children() {
        return new Enumeration<TreeNode>() {
            public boolean hasMoreElements() {
                return false;
            }
 
            public TreeNode nextElement() {
                return null;
            }
        };
	}
	
	@Override
	public String toString() {
		return myEtapa.toString();
	}

	public String getHint() {
		return "Etapa Hint!";
	}

	public String getType() {
		return this.myEtapa.getType();
	}

	public String getName() {
		return this.myEtapa.getName();
	}
	
	public String getDescricao() {
		return this.myEtapa.getDescricao();
	}
	
	public void setDescricao(String desc) {
		this.myEtapa.setDescricao(desc);
	}
	
	public boolean isRenderedDescription() {
		return false;
	}
	
	public String getHeaderDetails() {
		return "Informações de Etapa";
	}

	public String getDataInicioPrevista() {
		Date dt = this.myEtapa.getData().getDataInicioPrevista();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioPrevista(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(NameNode.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.myEtapa.getData().setDataInicioPrevista(dt);
		}
	}

	public String getDataInicioEfetiva() {
		Date dt = this.myEtapa.getData().getDataInicioEfetiva();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioEfetiva(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(NameNode.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.myEtapa.getData().setDataInicioEfetiva(dt);
		}
	}

	public String getDataFimPrevista() {
		Date dt = this.myEtapa.getData().getDataFimPrevista();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimPrevista(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(NameNode.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.myEtapa.getData().setDataFimPrevista(dt);
		}
	}

	public String getDataFimEfetiva() {
		Date dt = this.myEtapa.getData().getDataFimEfetiva();
		return dt != null? new SimpleDateFormat(NameNode.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimEfetiva(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(NameNode.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.myEtapa.getData().setDataFimEfetiva(dt);
		}
	}

	public void save() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/SISPLAN/portal/index.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancel() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/SISPLAN/portal/index.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void valueChangeListener(ValueChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
}