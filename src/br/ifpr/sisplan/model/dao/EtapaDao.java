package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.Etapa;
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
	
	public Etapa insertEtapa(String desc, int id_projeto, int id_responsavel) {
		String sql = "INSERT INTO sisplan.etapa(descricao, id_projeto, id_responsavel) VALUES(?,?,?)";
		this.sisplanDao.insert(sql, new Object[] {desc, id_projeto, id_responsavel});
		sql = "select * from sisplan.etapa where id=(select max(id) from sisplan.etapa)";
		Etapa etapa = 
				(Etapa)this.sisplanDao.query(sql, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Etapa result = new Etapa();
						while(rs.next()) {
							final int id = rs.getInt(rs.findColumn("id"));
							final String desc = rs.getString(rs.findColumn("descricao"));
							final int id_projeto = rs.getInt(rs.findColumn("id_projeto"));
							final int id_res = rs.getInt(rs.findColumn("id_responsavel"));
							result.setId(id);
							result.setDescricao(desc);
							result.setId_projeto(id_projeto);
							result.setIdResponsavel(id_res);
						}
						return result;
					}});
		return etapa;
	}
	
	public void insertEtapaAndData(int idEtapa, int idData) {
		String sql = "INSERT INTO sisplan.etapa_datas VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {idEtapa, idData});
	}
	
	public void deleteEtapa(int id_etapa) {
		String sql = "DELETE FROM sisplan.etapa WHERE id="+id_etapa;
		this.sisplanDao.update(sql);
	}
}
