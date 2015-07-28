package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ufrn.arq.dao.GenericDAOImpl;

public class EstrategiaDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public EstrategiaDao() {
		super();
	}
	
	public List selectEstrategiaByObjetivo(int id_obj) {
		String sql="SELECT * FROM sisplan.estrategia where id_objetivo="+id_obj;
		return sisplanDao.queryForList(sql);
	}
}
