package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ufrn.arq.dao.GenericDAOImpl;


public class UnidadeDao extends GenericDAOImpl {

	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
	public UnidadeDao() {
		super();
	}
	
	public List selectUnidadesByObjetivoEstrategico(int id_obj_estrategico) {
		String sqlUnidades = "SELECT id_unidade FROM sisplan.unidade_objetivos WHERE id_estrategico="+id_obj_estrategico;
		String sql = "SELECT * FROM ("+sqlUnidades+") AS unidade_ids, sisplan.unidade AS unidade WHERE unidade.id=unidade_ids.id_unidade ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
	
	public List selectAll() {
		String sql = "SELECT * FROM sisplan.unidade ORDER BY name ASC";
		return sisplanDao.queryForList(sql);
	}

	public List selectUnidade(int id_unidade) {
		String sql = "SELECT * FROM sisplan.unidade where id="+id_unidade;
		return sisplanDao.queryForList(sql);
	}
	
	public boolean checkIfObjetivoIsBoundToUnidade(int id_obj_estrategico, int id_unidade) {
		String sql = "SELECT * FROM sisplan.unidade_objetivos WHERE id_estrategico="+id_obj_estrategico+" and id_unidade="+id_unidade;
		if(sisplanDao.queryForList(sql).isEmpty())
			return false;
		return true;
	}

}
