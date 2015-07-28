package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ufrn.arq.dao.GenericDAOImpl;

public class ObjetivoEspecificoDao extends GenericDAOImpl {
	
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public ObjetivoEspecificoDao() {
		super();
	}
	
	public List selectObjetivosByUnidade(int id_unidade, int id_obj_estrategico) {
		String sqlObjetivos = "SELECT id_especifico FROM sisplan.unidade_objetivos WHERE id_unidade="+id_unidade+" AND id_estrategico="+id_obj_estrategico;
		String sql = "SELECT * FROM ("+sqlObjetivos+") AS objetivos_ids, sisplan.objetivo_especifico AS objetivo WHERE objetivo.id=objetivos_ids.id_especifico ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}

}
