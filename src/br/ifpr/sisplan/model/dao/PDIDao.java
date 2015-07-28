package br.ifpr.sisplan.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import br.ufrn.arq.dao.GenericDAOImpl;

/**
 * Era para extender GenericDAOImpl, mas teria que alterar a biblioteca arq.jar
 */
@Component
public class PDIDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
	public PDIDao() {
		super();
	}
	
	public List selectAll() {
		String sql = "SELECT * FROM sisplan.pdi ORDER BY name ASC";
		return sisplanDao.queryForList(sql);
	}

}