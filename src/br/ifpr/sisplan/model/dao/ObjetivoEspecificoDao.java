package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.ObjetivoEspecifico;
import br.ifpr.sisplan.model.table.parent.DescriptionNode;
import br.ufrn.arq.dao.GenericDAOImpl;

public class ObjetivoEspecificoDao extends GenericDAOImpl {
	
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	public ObjetivoEspecificoDao() {
		super();
	}
	
	public List selectObjetivosByUnidadeObjEstrategico(int id_unidade, int id_obj_estrategico) {
		String sqlObjetivos = "SELECT id_especifico FROM sisplan.unidade_objetivos WHERE id_unidade="+id_unidade+" AND id_estrategico="+id_obj_estrategico;
		String sql = "SELECT * FROM ("+sqlObjetivos+") AS objetivos_ids, sisplan.objetivo_especifico AS objetivo WHERE objetivo.id=objetivos_ids.id_especifico ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
	
	public List selectObjetivosEspByEstrategico(int id_obj_estrategico) {
		String sqlObjetivos = "SELECT id_especifico FROM sisplan.unidade_objetivos WHERE id_estrategico="+id_obj_estrategico;
		String sql = "SELECT * FROM ("+sqlObjetivos+") AS objetivos_ids, sisplan.objetivo_especifico AS objetivo"
				+ " WHERE objetivo.id=objetivos_ids.id_especifico AND objetivo.ativo=true ORDER BY id ASC";
		return sisplanDao.queryForList(sql);
	}
	
	public List selectObjetivosByUnidade(int id_unidade) {
		String sql = "select id, descricao from sisplan.objetivo_especifico as A JOIN"
			   + " (select distinct(id_especifico) from sisplan.unidade_objetivos where id_unidade="+id_unidade+") as B"
			   + " ON A.id=B.id_especifico";
		return sisplanDao.queryForList(sql);
	}
	
	public ObjetivoEspecifico insertObj(String desc) {
		String sql = "INSERT INTO sisplan.objetivo_especifico(descricao) VALUES(?)";
		this.sisplanDao.insert(sql, new Object[] {desc});
		sql = "SELECT * FROM sisplan.objetivo_especifico WHERE descricao='"+desc+"'";
		ObjetivoEspecifico obj = 
				(ObjetivoEspecifico)this.sisplanDao.query(sql, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						ObjetivoEspecifico result = new ObjetivoEspecifico();
						while(rs.next()) {
							final int id = rs.getInt(rs.findColumn("id"));
							final String desc = rs.getString(rs.findColumn("descricao"));
							result.setId(id);
							result.setDescricao(desc);
						}
						return result;
					}});
		return obj;
	}
	
	public void insertUnidadeObjetivos(int id_unidade, int id_estrategico, int id_especifico) {
		String sql = "INSERT INTO sisplan.unidade_objetivos VALUES (?, ?, ?)";
		Object[] args = {id_unidade, id_estrategico, id_especifico};
		this.sisplanDao.insert(sql, args);
	}
	
	public void deleteObjEspecifico(int id_objetivo) {
		String sql = "DELETE FROM sisplan.objetivo_especifico WHERE id="+id_objetivo;
		sisplanDao.update(sql);
	}

	public void updateObjetivo(DescriptionNode obj) {
		String sql = "UPDATE sisplan.objetivo_especifico SET descricao='"+obj.getDescricao()+"' where id="+obj.getId();
		sisplanDao.update(sql);
	}
}
