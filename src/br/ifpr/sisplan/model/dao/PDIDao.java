package br.ifpr.sisplan.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import br.ifpr.sisplan.model.table.parent.DescriptionNode;
import br.ufrn.arq.dao.GenericDAOImpl;

/**
 * Era para extender GenericDAOImpl, mas teria que alterar a biblioteca arq.jar
 */
@Component
public class PDIDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	private final static String TABLE_NAME = "sisplan.pdi";
	
	public PDIDao() {
		super();
	}
	
	public List selectAll() {
		String sql = "SELECT * FROM "+ TABLE_NAME +" ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
	
	public void updatePDI(DescriptionNode pdi) {
		String sql = "UPDATE " + TABLE_NAME + " SET descricao='"+pdi.getDescricao()+"' WHERE id="+pdi.getId();
		sisplanDao.update(sql);
	}

}