package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.Estrategia;
import br.ufrn.arq.dao.GenericDAOImpl;

public class EstrategiaDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public EstrategiaDao() {
		super();
	}
	
	public List selectEstrategiaByObjetivo(int id_obj) {
		String sql = "SELECT id, name FROM sisplan.estrategia AS est "
					 +"JOIN (SELECT * FROM sisplan.objetivo_estrategias WHERE id_objetivo="+id_obj+") AS obj_est "
					 +"ON est.id=obj_est.id_estrategia";
		return sisplanDao.queryForList(sql);
	}
	
	public Estrategia insertEstrategia(String name) {
		String sql = "INSERT INTO sisplan.estrategia(name) VALUES(?)";
		this.sisplanDao.insert(sql, new Object[] {name});
		sql = "SELECT * FROM sisplan.estrategia WHERE name='"+name+"'";
		Estrategia est = 
				(Estrategia)this.sisplanDao.query(sql, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Estrategia result = new Estrategia();
						while(rs.next()) {
							final int id = rs.getInt(rs.findColumn("id"));
							final String name = rs.getString(rs.findColumn("name"));
							result.setId(id);
							result.setName(name);
						}
						return result;
					}});
		return est;
	}
	
	public void insertRelationshipObjetivoEstrategia(int id_est, int id_obj) {
		String sql = "INSERT INTO sisplan.objetivo_estrategias VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {id_est, id_obj});
	}
	
	public void insertRelationshipEstrategiaProjeto(int id_estrategia, int id_projeto) {
		String sql = "INSERT INTO sisplan.estrategia_projetos VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {id_estrategia, id_projeto});
	}
}
