package br.ifpr.sisplan.controller.bean;

import java.util.ArrayList;
import java.util.List;

import br.ifpr.sisplan.controller.PDIControllerCached;
import br.ifpr.sisplan.controller.Permission;
import br.ifpr.sisplan.model.dao.UnidadeDao;
import br.ifpr.sisplan.model.table.Unidade;
import br.ufrn.arq.web.jsf.AbstractController;
import br.ufrn.comum.dominio.UnidadeGeral;
import br.ufrn.comum.dominio.UsuarioGeral;

public class SisplanUser extends AbstractController {
	private static final long serialVersionUID = 940364573344164102L;
	List<Unidade> unidades;
	private boolean planningManager;
	private UsuarioGeral user = this.getUsuarioLogado();
	
	public SisplanUser() throws Exception {
		this.setUnidades();
	}
	
	public List<Unidade> getUnidades() {
		return unidades;
	}
	private void setUnidades() throws Exception {
		System.out.println("USER ID = "+this.user.getId());
		UsuarioGeral user = this.getUsuarioLogado();
		if(Permission.PLANNING_MANAGER.checkPermission(this.user.getPapeis())) {
			this.unidades = PDIControllerCached.getInstance().getListUnidades();
			this.setPlanningManager(true);
			
		}
		else if (Permission.CAMPUS_MANAGER.checkPermission(this.user.getPapeis())) {
			this.setPlanningManager(false);
			final int id_unidade = this.getUsuarioLogado().getUnidade().getId();
			try {
				this.unidades = new ArrayList<Unidade>();
				this.unidades.add(getDAO(UnidadeDao.class).selectUnidade(id_unidade));
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception ("The Unidade="+this.getUsuarioLogado().getUnidade().getNome()+" is not registered.");
			}
		
		}
		else {
			this.setPlanningManager(false);
			this.unidades = new ArrayList<Unidade>();
		}
	}

	public boolean isPlanningManager() {
		return planningManager;
	}

	private void setPlanningManager(boolean planningManager) {
		this.planningManager = planningManager;
	}
	
	public Unidade getUnidade() {
		return this.unidades.get(0);
	}
}