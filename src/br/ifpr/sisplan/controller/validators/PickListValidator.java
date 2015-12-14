package br.ifpr.sisplan.controller.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class PickListValidator implements Validator {

	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		System.out.println("PickListValidator-----------");
	}

}
