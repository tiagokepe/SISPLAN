package br.ifpr.sisplan.controller.tree;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import br.ifpr.sisplan.controller.ifaces.TreeNodeActions;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfo;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.Etapa;

public class EtapaTreeNode extends TreeNodeCallBack implements TreeNode, TreeNodeInfo, TreeNodeDetails {
	private static final long serialVersionUID = -9205942028545960131L;
	private ProjetoTreeNode parentProjeto;
	
	public EtapaTreeNode(ProjetoTreeNode parent, Etapa etapa) {
		super(etapa);
		this.parentProjeto = parent;
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
		return this.dataNode.toString();
	}

	public String getType() {
		return this.dataNode.getType();
	}

	public String getName() {
		return this.dataNode.getName();
	}
	
	public boolean isRenderedDescription() {
		return false;
	}
	
	public String getHeaderDetails() {
		return "Informações de Etapa";
	}

/*	public String getDataInicioPrevista() {
		Date dt = this.dataNode.getData().getDataInicioPrevista();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioPrevista(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(DateUtil.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.dataNode.getData().setDataInicioPrevista(dt);
		}
	}

	public String getDataInicioEfetiva() {
		Date dt = this.dataNode.getData().getDataInicioEfetiva();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioEfetiva(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(DateUtil.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.dataNode.getData().setDataInicioEfetiva(dt);
		}
	}

	public String getDataFimPrevista() {
		Date dt = this.dataNode.getData().getDataFimPrevista();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimPrevista(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(DateUtil.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.dataNode.getData().setDataFimPrevista(dt);
		}
	}

	public String getDataFimEfetiva() {
		Date dt = this.dataNode.getData().getDataFimEfetiva();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimEfetiva(String strData) {
		if(!strData.isEmpty()) {
			Date dt=null;
			try {
				dt = new SimpleDateFormat(DateUtil.DefaultDateFormat).parse(strData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.dataNode.getData().setDataFimEfetiva(dt);
		}
	}*/
}