package br.ifpr.sisplan.controller.validators;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class DateValidator implements Validator {
	
	private FacesMessage dispatchMessage(UIComponent component) {
		String id = (String) component.getAttributes().get("id");
        FacesMessage message = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Data inválida, deve seguir a seguinte formatação 'dia/mês/ano'.", null);
        FacesContext.getCurrentInstance().addMessage(id, message);
        return message;
	}
	
	private FacesMessage dispatchMessage(UIComponent component, Object value) {
		String id = (String) component.getAttributes().get("id");
        FacesMessage message = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Data vazia, este campo deve seguir a seguinte formatação 'dia/mês/ano'.", null);
        FacesContext.getCurrentInstance().addMessage(id, message);
        return message;
	}

	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		Date dt = (Date) value;
		String id = (String) component.getAttributes().get("id");
		if(id.replace("_", "").contains("")); //TODO I have to finish the validation, but I need to access other fields from the origin object
		
/*		String strDate = String.valueOf(value);
		if(strDate.isEmpty()) {
			throw new ValidatorException(this.dispatchMessage(component, value));
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
		}*/
	}
}