package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ifpr.sisplan.model.table.Eixo;
import br.ufrn.arq.dao.GenericDAOImpl;

public class EixoDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public EixoDao() {
		super();
	}
	
	public List selectEixosByPDI(int id_pdi) {
		String sqlPDI = "SELECT id_eixo FROM sisplan.pdi_eixo WHERE id_pdi="+id_pdi;
		String sql = "SELECT id, name FROM ("+sqlPDI+") AS pdi_eixo, sisplan.eixo AS eixo WHERE eixo.id=pdi_eixo.id_eixo ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
}