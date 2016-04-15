package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.model.table.Projeto;
import br.ifpr.sisplan.model.table.parent.DateNode;
import br.ufrn.arq.dao.GenericDAOImpl;

public class ProjetoDao extends GenericDAOImpl {
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	private final static String TABLE_NAME = "sisplan.projeto";
	private final static String ESTRATEGIA_JOIN_TABLE = "sisplan.estrategia_projetos";
	
	public ProjetoDao() {
		super();
	}
	
	public List selectProjetosByEstrategia(int id_estrategia) {
		String sqlProjetos = "SELECT * FROM sisplan.estrategia_projetos WHERE id_estrategia="+id_estrategia;
		String sql = "SELECT * FROM ("+sqlProjetos+") AS projeto_ids, sisplan.projeto AS projeto "
				+ "WHERE projeto.id=projeto_ids.id_projeto AND projeto.ativo='true' ORDER BY id ASC";
		return sisplanDao.queryForList(sql);	
	}
	
	public List selectProjetosByEstrategiaAndResponsavel(int id_estrategia, int id_responsavel) {
		String sqlEstrategia = "SELECT * FROM "+ESTRATEGIA_JOIN_TABLE+" WHERE id_estrategia="+id_estrategia;
		
		String sqlProjetos = "SELECT * FROM "+TABLE_NAME+" WHERE id_responsavel="+id_responsavel;
		
		String sqlJoin = "SELECT * FROM ("+sqlEstrategia+") AS EST JOIN ("+sqlProjetos+") AS PROJ ON EST.id_projeto = PROJ.id";
		return sisplanDao.queryForList(sqlJoin);	
	}
	
	public void updateDescricao(DateNode p) {
		String update = "UPDATE "+TABLE_NAME+" SET descricao='"+p.getDescricao()+"' where id="+p.getId();
		sisplanDao.update(update);
	}
	
	public void updateName(DateNode p) {
		String update = "UPDATE "+TABLE_NAME+" SET name='"+p.getDescricao()+"' where id="+p.getId();
		sisplanDao.update(update);
	}
	
	public Projeto insertProjeto(String name, String desc, int id_responsavel) {
		String sql = "INSERT INTO sisplan.projeto(name, descricao, id_responsavel) VALUES(?,?,?)";
		this.sisplanDao.insert(sql, new Object[] {name, desc, id_responsavel});
		sql = "select * from sisplan.projeto where id=(select max(id) from sisplan.projeto)";
		Projeto proj = 
				(Projeto)this.sisplanDao.query(sql, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Projeto result = new Projeto();
						while(rs.next()) {
							final int id = rs.getInt(rs.findColumn("id"));
							final String name = rs.getString(rs.findColumn("name"));
							final String desc = rs.getString(rs.findColumn("descricao"));
							final int id_responsavel = rs.getInt(rs.findColumn("id_responsavel"));
							result.setId(id);
							result.setName(name);
							result.setDescricao(desc);
							result.setIdResponsavel(id_responsavel);
						}
						return result;
					}});
		return proj;
	}
	
	public void insertProjetoAndData(int idProjeto, int idData) {
		String sql = "INSERT INTO sisplan.projeto_datas VALUES(?,?)";
		this.sisplanDao.insert(sql, new Object[] {idProjeto, idData});
	}
	
	public void deleteProjeto(int id_projeto) {
		String sql = "DELETE FROM sisplan.projeto WHERE id="+id_projeto;
		sisplanDao.update(sql);
	}
	
	public int countEstrategiaLinks(int id_projeto) {
		String sql = "SELECT COUNT(*) FROM sisplan.estrategia_projetos WHERE id_projeto="+id_projeto;
		return this.sisplanDao.queryForInt(sql);
	}
	
	public void updateResponsavel(int id_projeto, int id_responsavel) {
		String sql = "UPDATE " + TABLE_NAME + " SET id_responsavel=" +id_responsavel
				   + "WHERE id="+id_projeto;
		this.sisplanDao.update(sql);
	}
}
