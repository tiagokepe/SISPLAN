package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ufrn.arq.dao.GenericDAOImpl;

public class EtapaDao extends GenericDAOImpl {

	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
	public EtapaDao() {
		super();
	}
	
	public List selectEtapaByProject(int project_id) {
		String sql = "select * from sisplan.etapa where id_projeto="+project_id;
		return sisplanDao.queryForList(sql);
	}
}
