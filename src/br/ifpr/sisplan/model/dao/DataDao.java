package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.Data;
import br.ufrn.arq.dao.GenericDAOImpl;

public class DataDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
/*	private class DataExtractor implements ResultSetExtractor {
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			Data result = new Data();
			while(rs.next()) {
				final int id = rs.getInt(rs.findColumn("id"));
				final String dataInicioPrevista = rs.getString(rs.findColumn("data_inicio_prevista"));
				final String dataInicioEfetiva = rs.getString(rs.findColumn("data_inicio_efetiva"));
				final String dataFimPrevista = rs.getString(rs.findColumn("data_fim_prevista"));
				final String dataFimEfetiva = rs.getString(rs.findColumn("data_fim_efetiva"));
				result.setId(id);
				result.setDataInicioPrevista(DateUtil.stringFromDBToDate(dataInicioPrevista));
				result.setDataInicioEfetiva(DateUtil.stringFromDBToDate(dataInicioEfetiva));
				result.setDataFimPrevista(DateUtil.stringFromDBToDate(dataFimPrevista));
				result.setDataFimEfetiva(DateUtil.stringFromDBToDate(dataFimEfetiva));
			}
			return result;
	}*/
	
	
	private class DataExtractor implements ResultSetExtractor {
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
		return (Data) sisplanDao.query(sql, new DataExtractor());
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
		String update = "UPDATE sisplan.data SET ";
		update += "data_inicio_prevista=";
		update += (dt.getDataInicioPrevista() != null &&
				   !dt.getDataInicioPrevista().toString().isEmpty())
				   ? "'"+dt.getDataInicioPrevista()+"', " : "null, ";
		
		update += "data_inicio_efetiva=";
		update += (dt.getDataInicioEfetiva() != null &&
				   !dt.getDataInicioEfetiva().toString().isEmpty())
				   ? "'"+dt.getDataInicioEfetiva()+"', " : "null, ";

		update += "data_fim_prevista=";
		update += (dt.getDataFimPrevista() != null &&
				   !dt.getDataFimPrevista().toString().isEmpty())
				   ? "'"+dt.getDataFimPrevista()+"', " : "null, ";
		
		update += "data_fim_efetiva=";
		update += (dt.getDataFimEfetiva() != null &&
				   !dt.getDataFimEfetiva().toString().isEmpty())
				   ? "'"+dt.getDataFimEfetiva()+"'" : "null";
		
		update += " WHERE id="+dt.getId();
		
		this.sisplanDao.update(update);
	}
	
	public Data insertData(Date dataInicioPrevista, Date dataInicioEfetiva, Date dataFimPrevista, Date dataFimEfetiva) {
		String sql = "INSERT INTO sisplan.data(data_inicio_prevista, data_inicio_efetiva, data_fim_prevista, data_fim_efetiva) VALUES(?,?,?,?)";
		this.sisplanDao.insert(sql, new Object[] {dataInicioPrevista, dataInicioEfetiva, dataFimPrevista, dataFimEfetiva});
		sql = "select * from sisplan.data where id=(select max(id) from sisplan.data)";
		Data data = (Data)this.sisplanDao.query(sql, new DataExtractor());
		return data;
	}
	
	/**
	 * 
	 * @param mapFieldValue This parameter contains the table field name and its value
	 * @return
	 */
	public Data insertData(Map<String, Date> mapFieldValue) {
		StringBuilder values = new StringBuilder("Values(");
		StringBuilder sql = new StringBuilder("INSERT INTO sisplan.data(");
		Iterator<String> it = mapFieldValue.keySet().iterator();
		while(it.hasNext()) {
			sql.append(it.next()+",");
			values.append("?,");
		}
		int i = values.lastIndexOf(",");
		sql.replace(sql.lastIndexOf(","), sql.lastIndexOf(",")+1, ") ");
		values.replace(values.lastIndexOf(","), values.lastIndexOf(",")+1, ")");
		sql.append(values);
		
		this.sisplanDao.insert(sql.toString(), mapFieldValue.values().toArray());
		String strSql = "select * from sisplan.data where id=(select max(id) from sisplan.data)";
		Data data = (Data)this.sisplanDao.query(strSql, new DataExtractor());
		return data;
	}
	
	public void deleteData(Data dt) {
		String sql = "DELETE FROM sisplan.data WHERE id="+dt.getId();
		sisplanDao.update(sql);
	}
}
