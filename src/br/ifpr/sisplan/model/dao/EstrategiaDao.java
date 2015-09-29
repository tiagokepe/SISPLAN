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
		String sql="SELECT * FROM sisplan.estrategia where id_objetivo="+id_obj;
		return sisplanDao.queryForList(sql);
	}
	
	public Estrategia insertEstrategia(String name, int id_obj_especifico) {
		String sql = "INSERT INTO sisplan.estrategia(name, id_objetivo) VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {name, id_obj_especifico});
		sql = "SELECT * FROM sisplan.estrategia WHERE name='"+name+"' AND id_objetivo="+id_obj_especifico;
		Estrategia est = 
				(Estrategia)this.sisplanDao.query(sql, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Estrategia result = new Estrategia();
						while(rs.next()) {
							final int id = rs.getInt(rs.findColumn("id"));
							final String name = rs.getString(rs.findColumn("name"));
							final int id_objetivo = rs.getInt(rs.findColumn("id_objetivo"));
							result.setId(id);
							result.setName(name);
							result.setId_objetivo(id_objetivo);
						}
						return result;
					}});
		return est;
	}
	
	public void insertLinkEstrategiaProjeto(int id_estrategia, int id_projeto) {
		String sql = "INSERT INTO sisplan.estrategia_projetos VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {id_estrategia, id_projeto});
	}
}
