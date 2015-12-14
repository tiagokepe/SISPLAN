package br.ifpr.sisplan.controller.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.tree.ObjetivoEstrategicoTreeNode;

public class ListShuttleconverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String obj) {
		if(obj == null)
			return null;
		ObjetivoEstrategicoTreeNode objetivo = PDIControllerCached.getInstance().getMapObjEstrategicoTreeNode().get(obj);
		return objetivo;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if(obj == null)
			return null;
		if(obj instanceof ObjetivoEstrategicoTreeNode)
			return ((ObjetivoEstrategicoTreeNode) obj).getDesc();
		if(obj instanceof String)
			return (String)obj;
		else
			throw new IllegalArgumentException("object:" + obj + "of type:" + obj.getClass().getName());  
	}

}
