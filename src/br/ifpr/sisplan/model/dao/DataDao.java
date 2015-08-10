package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.Data;
import br.ufrn.arq.dao.GenericDAOImpl;

public class DataDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
	private class DataEtractor implements ResultSetExtractor {
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			Data dt = new Data();
			rs.next();
			dt.setId(rs.getInt("id"));
			dt.setDataInicioPrevista(rs.getDate("data_inicio_prevista"));
			dt.setDataInicioEfetiva(rs.getDate("data_inicio_efetiva"));
			dt.setDataFimPrevista(rs.getDate("data_fim_prevista"));
			dt.setDataFimEfetiva(rs.getDate("data_fim_efetiva"));
			return dt;
		}
	}
	
	public DataDao() {
		super();
	}
	
	public Data selectDataById(int data_id) {
		String sql = "select * from sisplan.data where id="+data_id;
		return (Data) sisplanDao.query(sql, new DataEtractor());
	}

	public Data selectDataByEtapa(int etapa_id) {
		String sql_id_data = "SELECT id_data FROM sisplan.etapa_datas WHERE id_etapa="+etapa_id+" order by id_data desc limit 1";
		return this.selectDataById(sisplanDao.queryForInt(sql_id_data));
	}
	
	public Data selectDataByProjeto(int projeto_id) {
		String sql_id_data = "SELECT id_data FROM sisplan.projeto_datas WHERE id_projeto="+projeto_id+" order by id_data desc limit 1";
		return this.selectDataById(sisplanDao.queryForInt(sql_id_data));
	}
	
	public void updateData(Data dt) {
		String update = "UPDATE sisplan.data SET "
					  + "data_inicio_prevista='"+dt.getDataInicioPrevista()+"' "
					  + "data_inicio_efetiva='"+dt.getDataInicioEfetiva()+"' "
					  + "data_fim_prevista='"+dt.getDataFimPrevista()+"' "
					  + "data_fim_efetiva='"+dt.getDataFimEfetiva()+"'";
		this.update(update);
	}
}
