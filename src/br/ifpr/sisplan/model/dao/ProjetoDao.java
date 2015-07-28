package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ufrn.arq.dao.GenericDAOImpl;

public class ProjetoDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
	public ProjetoDao() {
		super();
	}
	
	public List selectProjetosByEstrategia(int id_estrategia) {
		String sqlProjetos = "SELECT * FROM sisplan.estrategia_projetos WHERE id_estrategia="+id_estrategia;
		String sql = "SELECT * FROM ("+sqlProjetos+") AS projeto_ids, sisplan.projeto AS projeto WHERE projeto.id=projeto_ids.id_projeto ORDER BY id ASC";
		return sisplanDao.queryForList(sql);	
	}

}
