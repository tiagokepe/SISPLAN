package br.ifpr.sisplan.controller.tree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.event.ValueChangeEvent;

import br.ifpr.sisplan.controller.ifaces.TreeNodeActions;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCallBackIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeDetails;
import br.ifpr.sisplan.controller.ifaces.TreeNodeEvents;
import br.ifpr.sisplan.model.dao.DataDao;
import br.ifpr.sisplan.model.dao.ProjetoDao;
import br.ifpr.sisplan.model.table.parent.DateDescriptionNode;
import br.ifpr.sisplan.util.DateUtil;

public abstract class TreeNodeCallBack extends TreeNodeGeneric implements TreeNodeCallBackIface, TreeNodeEvents, TreeNodeActions {
	private static final long serialVersionUID = 1L;
	protected Map<Method, Object> mapOfUpdateCallBack = new HashMap<Method, Object>();
	protected boolean changedDatas = false;
	protected boolean changedDescricao = false;
	protected DateDescriptionNode dataNode;

	public TreeNodeCallBack(TreeNodeGeneric parent, DateDescriptionNode kidNode) {
		super(parent, kidNode);
		this.dataNode = kidNode;
	}
	
	protected void addCallBackMethod(String method, Class paramClazz, Object param) {
		try {
			this.mapOfUpdateCallBack.put(TreeNodeCallBackIface.class.getMethod(method, paramClazz), param);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	protected void callBack() {
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
	
	public void setDescricaoCallBack(String desc) {
		this.dataNode.setDescricao(desc);
		this.changedDescricao = true;
	}
	
	public void setDataInicioPrevistaCallBack(Date newDate) {
		this.dataNode.getData().setDataInicioPrevista(newDate);
		this.changedDatas = true;
	}
	
	public void setDataInicioEfetivaCallBack(Date newDate) {
		this.dataNode.getData().setDataInicioEfetiva(newDate);
		this.changedDatas = true;
	}
	
	public void setDataFimPrevistaCallBack(Date newDate) {
		this.dataNode.getData().setDataFimPrevista(newDate);
		this.changedDatas = true;
	}
	
	public void setDataFimEfetivaCallBack(Date newDate) {
		this.dataNode.getData().setDataFimEfetiva(newDate);
		this.changedDatas = true;
	}
	
	public String getDescricao() {
		return this.dataNode.getDescricao();
	}
	
	public void setDescricao(String desc) {
		if(!desc.isEmpty() && !desc.equals(this.dataNode.getDescricao())) {
			this.addCallBackMethod("setDescricaoCallBack", String.class, desc);
		}
	}
	
	public String getDataInicioPrevista() {
		Date dt = this.dataNode.getData().getDataInicioPrevista();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioPrevista(String strDate) {
		if(!DateUtil.validateDateFormat(strDate)) {
			return;
		}
		
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataInicioPrevista() == null ||
		   this.dataNode.getData().getDataInicioPrevista().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataInicioPrevistaCallBack", Date.class, newDate);
		}
	}

	public String getDataInicioEfetiva() {
		Date dt = this.dataNode.getData().getDataInicioEfetiva();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataInicioEfetiva(String strDate) {
		if(!DateUtil.validateDateFormat(strDate))
			return;
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataInicioEfetiva() == null ||
		   this.dataNode.getData().getDataInicioEfetiva().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataInicioEfetivaCallBack", Date.class, newDate);
		}
	}
	
	public String getDataFimPrevista() {
		Date dt = this.dataNode.getData().getDataFimPrevista();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimPrevista(String strDate) {
		if(!DateUtil.validateDateFormat(strDate))
			return;
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataFimPrevista() == null ||
		   this.dataNode.getData().getDataFimPrevista().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataFimPrevistaCallBack", Date.class, newDate);
		}
	}
	
	public String getDataFimEfetiva() {
		Date dt = this.dataNode.getData().getDataFimEfetiva();
		return dt != null? new SimpleDateFormat(DateUtil.DefaultDateFormat).format(dt): "";
	}
	
	public void setDataFimEfetiva(String strDate) {
		if(!DateUtil.validateDateFormat(strDate)) {
			return;
		}
		Date newDate = DateUtil.stringToDate(strDate);
		if(this.dataNode.getData().getDataFimEfetiva() == null ||
		   this.dataNode.getData().getDataFimEfetiva().compareTo(newDate) != 0) {
			
			this.addCallBackMethod("setDataFimEfetivaCallBack", Date.class, newDate);
		}
	}
	
	public void valueChangeListener(ValueChangeEvent e) {
		String id_value = (String)e.getComponent().getAttributes().get("id");
		String newValue = e.getNewValue().toString();
		String methodName = id_value.replace("id_", "").replace("_", "");
		
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

	public void save() {
		this.callBack();
		if(this.changedDescricao)
			this.getDAO(ProjetoDao.class).updateDescricao(this.dataNode);
		if(this.changedDatas)
			this.getDAO(DataDao.class).updateData(this.dataNode.getData());
		this.returnMainPage();
	}

	public void cancel() {
		this.mapOfUpdateCallBack.clear();
		this.redirect("/portal/index.jsf");
	}
	
	public void returnMainPage() {
		this.redirect("/portal/index.jsf");
	}

	public DateDescriptionNode getDataNode() {
		return dataNode;
	}
}