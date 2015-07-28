package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ufrn.arq.dao.GenericDAOImpl;

public class ObjetivoEstrategicoDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public ObjetivoEstrategicoDao() {
		super();
	}
	
	public List selectObjetivosByDiretriz(int id_diretriz) {
		String sqlObjetivos = "SELECT id_objetivo FROM sisplan.diretriz_objetivo_estrategico WHERE id_diretriz="+id_diretriz;
		String sql = "SELECT * FROM ("+sqlObjetivos+") AS objetivos_ids, sisplan.objetivo_estrategico AS objetivo WHERE objetivo.id=objetivos_ids.id_objetivo ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
}
