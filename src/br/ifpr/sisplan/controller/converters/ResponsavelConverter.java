package br.ifpr.sisplan.controller.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.ifpr.sisplan.controller.bean.NovoProjetoBean;
import br.ifpr.sisplan.model.table.Responsavel;

public class ResponsavelConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		FacesContext fc = FacesContext.getCurrentInstance();
		NovoProjetoBean projetoBean = (NovoProjetoBean)fc.getELContext().getELResolver().getValue(fc.getELContext(), null, "novoProjetoBean");
/*		for(Responsavel r: projetoBean.getResponsaveis())
			if(r.getName().equals(value))
				return r;*/
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value == null)
			return null;
		return ((Responsavel) value).getName();
	}

}
