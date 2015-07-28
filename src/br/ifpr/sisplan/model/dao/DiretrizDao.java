package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ufrn.arq.dao.GenericDAOImpl;

public class DiretrizDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public DiretrizDao() {
		super();
	}
	
	public List selectDiretrizesByEixo(int id_eixo) {
		String sqlEixo = "SELECT id_diretriz FROM sisplan.eixo_diretriz WHERE id_eixo="+id_eixo;
		String sql = "SELECT * FROM ("+sqlEixo+") AS eixo_dir, sisplan.diretriz AS dir WHERE dir.id=eixo_dir.id_diretriz ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
}
