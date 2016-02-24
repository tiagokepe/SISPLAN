package br.ifpr.sisplan.controller.converters;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.ufrn.arq.web.jsf.AbstractController;

public class BigDecimalConverter extends AbstractController implements Converter {
	public static BigDecimal bigDecEmpty = new BigDecimal(-1);

	public Object getAsObject(FacesContext arg0, UIComponent component, String value) {
		if(value == null) {
/*			if(!component.getId().contains("id_Custo_Previsto"))
				return bigDecEmpty;
			
			addMensagemErro("O valor do custo previsto deve ser informado.");				
			return null;*/
			return bigDecEmpty;
	    }
		if(value.isEmpty()) {
			return bigDecEmpty;
/*			if(!component.getId().contains("id_Custo_Previsto"))
				return bigDecEmpty;
			
			addMensagemErro("O valor do custo previsto deve ser informado.");				
			return null;*/
	    }

		if(!Pattern.matches("[0-9]+\\.[0-9]{2}$", value)) {
			addMensagemErro("O valor do custo deve seguir o padrão, ex: 500.00.");
			return null;
		}
		BigDecimal bigD = new BigDecimal(value);
		return bigD;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value == null)
			return null;
		if(value instanceof BigDecimal) {
			if(value.equals(bigDecEmpty))
				return "";
			return value.toString();
		}
		return null;
	}

}
