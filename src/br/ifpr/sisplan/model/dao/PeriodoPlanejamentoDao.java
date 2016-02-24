package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.PeriodoPlanejamento;
import br.ufrn.arq.dao.GenericDAOImpl;

public class PeriodoPlanejamentoDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	private static String TABLE_NAME = "sisplan.periodo_planejamento";
	private static String LINK_TABLE_PDI = "sisplan.pdi_periodo_plan";
	private static String LINK_TABLE_UNIDADE = "sisplan.unidade_periodo_plan";
	
	private class PeriodoPlanejamentoExtractor implements ResultSetExtractor {
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()) {
				PeriodoPlanejamento periodoPlan = new PeriodoPlanejamento();
				periodoPlan.setId(rs.getInt("id"));
				periodoPlan.setDataInicio(rs.getDate("data_inicio"));
				periodoPlan.setDataFim(rs.getDate("data_fim"));
				return periodoPlan;
			}
			return null;
		}
	}
	
	public PeriodoPlanejamento insertPeriodoPlan(Date dataIni, Date dataFim) {
		String sql = "INSERT INTO "+TABLE_NAME+"(data_inicio, data_fim) VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[]{dataIni, dataFim});
		sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=(SELECT max(id) FROM "+TABLE_NAME+")";
		return (PeriodoPlanejamento)this.sisplanDao.query(sql, new PeriodoPlanejamentoExtractor()); 
	}
	
	public void linkPdiAndPeriodoPlan(int id_pdi, int id_periodo) {
		String sql = "INSERT INTO "+LINK_TABLE_PDI+" VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[]{id_pdi, id_periodo});
	}
	
	public PeriodoPlanejamento selectPeriodoPlanByPDI(int id_pdi) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=(SELECT max(id_periodo) FROM "+LINK_TABLE_PDI+" WHERE id_pdi="+id_pdi+")";
		return (PeriodoPlanejamento)this.sisplanDao.query(sql, new PeriodoPlanejamentoExtractor());
	}
	
	public PeriodoPlanejamento selectPeriodoPlanByUnidade(int id_unidade) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=(SELECT max(id_periodo) FROM "+LINK_TABLE_UNIDADE+" WHERE id_unidade="+id_unidade+")";
		return (PeriodoPlanejamento)this.sisplanDao.query(sql, new PeriodoPlanejamentoExtractor());
	}

	public void linkUnidadeAndPeriodoPlan(int id_unidade, int id_periodo) {
		String sql = "INSERT INTO "+LINK_TABLE_UNIDADE+" VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[]{id_unidade, id_periodo});
	}
	
}
