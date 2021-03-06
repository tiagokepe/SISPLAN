package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.parent.DescriptionNode;
import br.ufrn.arq.dao.GenericDAOImpl;

public class EstrategiaDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public final static String TABLE_NAME = "sisplan.estrategia";
	
	public EstrategiaDao() {
		super();
	}
	
	public List selectEstrategiaByObjetivo(int id_obj) {
		String sql = "SELECT id, descricao FROM sisplan.estrategia AS est "
					 +"JOIN (SELECT * FROM sisplan.objetivo_esp_estrategias WHERE id_objetivo_esp="+id_obj+") AS obj_est "
					 +"ON est.id=obj_est.id_estrategia"
					 +" WHERE est.ativo=true";
		return sisplanDao.queryForList(sql);
	}
	
	public Estrategia insertEstrategia(String desc) {
		String sql = "INSERT INTO sisplan.estrategia(descricao) VALUES(?)";
		this.sisplanDao.insert(sql, new Object[] {desc});
		sql = "SELECT * FROM sisplan.estrategia WHERE descricao='"+desc+"'";
		Estrategia est = 
				(Estrategia)this.sisplanDao.query(sql, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Estrategia result = new Estrategia();
						while(rs.next()) {
							final int id = rs.getInt(rs.findColumn("id"));
							final String desc = rs.getString(rs.findColumn("descricao"));
							result.setId(id);
							result.setDescricao(desc);
							result.setName(desc);
						}
						return result;
					}});
		return est;
	}
	
	public void insertRelationshipObjetivoEstrategia(int id_obj, int id_est) {
		String sql = "INSERT INTO sisplan.objetivo_esp_estrategias VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {id_obj, id_est});
	}
	
	public void insertRelationshipEstrategiaProjeto(int id_estrategia, int id_projeto) {
		String sql = "INSERT INTO sisplan.estrategia_projetos VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {id_estrategia, id_projeto});
	}
	
	public void deleteEstrategia(int id_estrategia) {
		String sql = "DELETE FROM sisplan.estrategia WHERE id="+id_estrategia;
		sisplanDao.update(sql);
	}
	
	public int countObjEspecificoLinks(int id_estrategia) {
		String sql = "SELECT COUNT(*) FROM sisplan.objetivo_esp_estrategias WHERE id_estrategia="+id_estrategia;
		return this.sisplanDao.queryForInt(sql);
	}
	
	public void updateEstrategia(DescriptionNode estrategia) {
		String sql = "UPDATE sisplan.estrategia SET descricao='"+estrategia.getDescricao()+"' where id="+estrategia.getId();
		sisplanDao.update(sql);
	}
}
