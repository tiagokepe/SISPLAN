package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class ListSelectBean {
    private List<SelectItem> capitals;
    private List<String> selectedCapitals;
    
    public ListSelectBean() {
    	this.capitals = new ArrayList<SelectItem>();
    	capitals.add(new SelectItem("AAAA"));
    	capitals.add(new SelectItem("BBBB"));
    	capitals.add(new SelectItem("CCCC"));
    	capitals.add(new SelectItem("DDDD"));
    }
 
    public List<SelectItem> getCapitals() {
        return capitals;
    }
 
    public void setCapitals(List<SelectItem> capitals) {
        this.capitals = capitals;
    }
 
    public List<String> getSelectedCapitals() {
        return selectedCapitals;
    }
 
    public void setSelectedCapitals(List<String> selectedCapitals) {
        this.selectedCapitals = selectedCapitals;
    }
    
    public void save() {
    	System.out.println("SALVAR ---- TETETETETE");
    }
}
