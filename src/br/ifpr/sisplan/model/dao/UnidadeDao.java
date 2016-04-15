package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.Data;
import br.ifpr.sisplan.model.table.Estrategia;
import br.ifpr.sisplan.model.table.Unidade;
import br.ufrn.arq.dao.GenericDAOImpl;


public class UnidadeDao extends GenericDAOImpl {
	private class UnidadeExtractor implements ResultSetExtractor {
		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			Unidade result = new Unidade();
			while(rs.next()) {
				final int id = rs.getInt(rs.findColumn("id"));
				final String name = rs.getString(rs.findColumn("name"));
				result.setId(id);
				result.setName(name);
			}
			return result;
		}
	}

	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
	public UnidadeDao() {
		super();
	}
	
	public List selectUnidadesByObjetivoEstrategico(int id_obj_estrategico) {
		String sqlUnidades = "SELECT id_unidade FROM sisplan.unidade_objetivos WHERE id_estrategico="+id_obj_estrategico;
		String sql = "SELECT * FROM ("+sqlUnidades+") AS unidade_ids, sisplan.unidade AS unidade WHERE unidade.id=unidade_ids.id_unidade ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
	
	public List selectAll() {
		String sql = "SELECT * FROM sisplan.unidade ORDER BY name ASC";
		return sisplanDao.queryForList(sql);
	}

	public Unidade selectUnidade(int id_unidade) {
		String sql = "SELECT * FROM sisplan.unidade where id="+id_unidade;
		Unidade u = (Unidade)this.sisplanDao.query(sql, new UnidadeExtractor());
		return u;
	}
	
	public boolean checkIfObjetivoIsBoundToUnidade(int id_obj_estrategico, int id_unidade) {
		String sql = "SELECT * FROM sisplan.unidade_objetivos WHERE id_estrategico="+id_obj_estrategico+" and id_unidade="+id_unidade;
		if(sisplanDao.queryForList(sql).isEmpty())
			return false;
		return true;
	}
	
	public List selectAllUnidades() {
		String sql = "SELECT * FROM sisplan.unidade";
		return this.sisplanDao.queryForList(sql);
	}
	
	public Unidade selectUnidadeByObjEspecifico(int id_obj_especifico) {
		String sqlUnidadeID = "(SELECT id_unidade AS id FROM sisplan.unidade_objetivos where id_especifico="+id_obj_especifico+")";
		String sql = "SELECT * FROM sisplan.unidade AS unidade, "+sqlUnidadeID+" AS unidade_obj WHERE unidade.id=unidade_obj.id";
				
		Unidade u = (Unidade)this.sisplanDao.query(sql, new UnidadeExtractor());
		return u;
	}
	
	public Unidade selectUnidadeOfResponsavel(int id_res) {
		String sql = "SELECT uni.id, uni.name FROM sisplan.responsavel AS res"
				   + " JOIN sisplan.unidade AS uni ON res.id_unidade = uni.id"
				   + " WHERE res.id="+ id_res;
		Unidade u = (Unidade)this.sisplanDao.query(sql, new UnidadeExtractor());
		return u;
	}

}
