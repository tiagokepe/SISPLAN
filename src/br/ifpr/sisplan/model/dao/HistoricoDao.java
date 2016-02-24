package br.ifpr.sisplan.model.dao;

import java.util.List;

import br.ifpr.sisplan.model.table.Historico;
import br.ufrn.arq.dao.GenericDAOImpl;

public class HistoricoDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	private final static String TABLE_NAME = "sisplan.historico";
	
	public HistoricoDao() {
		super();
	}

	public List selectAll() {
		String sql = "SELECT * FROM "+TABLE_NAME;
		return sisplanDao.queryForList(sql);
	}
	
	public void insert(Historico his) {
		String sql = "INSERT INTO "+TABLE_NAME+"(responsavel, tipo_componente,"
											+ "detalhe,unidade, campo_alterado,"
											+ "de, para) VALUES(?,?,?,?,?,?,?)";
		
		Object[] dados = new Object[] {his.getResponsavel(), his.getTipoComponente(), 
							his.getDetalhe(), his.getUnidade(), his.getCampoAlterado(),
							his.getDe(), his.getPara()};
		
		this.sisplanDao.insert(sql, dados);
	}
	
}
