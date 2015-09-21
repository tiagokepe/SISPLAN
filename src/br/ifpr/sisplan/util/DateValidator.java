package br.ifpr.sisplan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class DateValidator implements Validator {
	
	private FacesMessage dispatchMessage(UIComponent component) {
		String id = (String) component.getAttributes().get("id");
		UIComponent sibling =  component.getParent().getChildren().get(0);
		String label = (String) component.getAttributes().get("label");
		//id = id.replace("id_set", "").replace("_", " ");
        FacesMessage message = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Data inválida, deve seguir a seguinte formatação 'dia/mês/ano'.", null);
        FacesContext.getCurrentInstance().addMessage(id, message);
        return message;
	}

	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String strDate = String.valueOf(value);
		if(strDate.isEmpty()) {
			throw new ValidatorException(this.dispatchMessage(component));
		}
		Pattern p = Pattern.compile("^[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]$");
		Matcher m = p.matcher(strDate);
		if(!m.matches()) {
			throw new ValidatorException(this.dispatchMessage(component));
		}		
		String[] arrayDate = strDate.split("/");
		int day = Integer.parseInt(arrayDate[0]);
		int month = Integer.parseInt(arrayDate[1]);
		if(day > 31) {
			throw new ValidatorException(this.dispatchMessage(component));
		}
		if(month > 12) {
			throw new ValidatorException(this.dispatchMessage(component));
		}
	}
}