package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ifpr.sisplan.controller.Permission;
import br.ifpr.sisplan.model.dao.UnidadeDao;
import br.ifpr.sisplan.model.table.Unidade;
import br.ifpr.sisplan.util.ConverterToList;
import br.ufrn.arq.web.jsf.AbstractController;

@Component
@Scope("session")
public class SisplanUser extends AbstractController {
	private static final long serialVersionUID = 940364573344164102L;
	List<Unidade> unidades;
	public SisplanUser() throws Exception {
		this.setUnidades();
	}
	
	public List<Unidade> getUnidades() {
		return unidades;
	}
	private void setUnidades() throws Exception {
		if(Permission.PLANNING_MANAGER.checkPermission(this.getUsuarioLogado().getPapeis())) {
			this.unidades = ConverterToList.convertListMappedToList(getDAO(UnidadeDao.class).
													selectAll(), Unidade.class);
			
		}
		else if (Permission.CAMPUS_MANAGER.checkPermission(this.getUsuarioLogado().getPapeis())) {
			final int id_unidade = this.getUsuarioLogado().getUnidade().getId();
			this.unidades = ConverterToList.convertListMappedToList(getDAO(UnidadeDao.class).selectUnidade(id_unidade), Unidade.class);
			if(unidades.isEmpty())
				throw new Exception("The Unidade="+this.getUsuarioLogado().getUnidade().getNome()+" is not registered.");
		
		}
		else {
			this.unidades = new ArrayList<Unidade>();
		}
	}
}