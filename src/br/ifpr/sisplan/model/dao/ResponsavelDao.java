package br.ifpr.sisplan.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.controller.Permission;
import br.ifpr.sisplan.model.table.Responsavel;
import br.ufrn.arq.dao.Database;
import br.ufrn.arq.dao.GenericDAOImpl;
import br.ufrn.arq.dao.JdbcTemplate;
import br.ufrn.arq.seguranca.log.Logger;

public class ResponsavelDao extends GenericDAOImpl {
	private static ResponsavelDao instance;
	private JdbcTemplate jtComum;
	private SisplanDao sisplanDao = SisplanDao.getInstance();
	
	public static ResponsavelDao getInstance() {
		if(instance == null)
			instance = new ResponsavelDao();
		return instance;
	}

	private ResponsavelDao() {
		this.jtComum = new JdbcTemplate(Database.getInstance().getComumDs());
	}
	
/*	SELECT pessoa.id_pessoa, pessoa.nome, temp.id_unidade FROM comum.pessoa AS pessoa
	JOIN
	  ((SELECT id_usuario FROM comum.permissao WHERE id_papel=22) AS gestor
	  JOIN
	  (SELECT DISTINCT(id_pessoa), id_usuario, id_unidade from comum.usuario WHERE id_unidade=65 AND id_servidor IS NOT NULL ORDER BY id_pessoa) AS usuario
	  ON gestor.id_usuario=usuario.id_usuario) AS temp
	ON pessoa.id_pessoa=temp.id_pessoa
	;*/
	
	public List<Responsavel> selectResponsavelByUnidade(int id_unidade) {
		String sql = "SELECT pessoa.nome, temp.* FROM comum.pessoa AS pessoa"
				   + " JOIN ((SELECT id_usuario FROM comum.permissao"
				   + " WHERE id_papel="+Permission.CAMPUS_MANAGER.getIdPapel()
				   + " OR id_papel="+Permission.RESPONSALVEL_PROJETO_ETAPA.getIdPapel()+") AS gestor"
				   + " JOIN (SELECT DISTINCT(id_pessoa), id_usuario, id_unidade from comum.usuario WHERE id_unidade="+id_unidade
				   + " AND id_servidor IS NOT NULL ORDER BY id_pessoa) AS usuario"
				   + " ON gestor.id_usuario=usuario.id_usuario) AS temp"
				   + " ON pessoa.id_pessoa=temp.id_pessoa";
		List<Responsavel> responsaveis = 
				(List<Responsavel>)this.jtComum.query(sql, new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<Responsavel> result = new ArrayList<Responsavel>();
						while(rs.next()) {
							Responsavel res = new Responsavel();
							res.setId(rs.getInt(rs.findColumn("id_usuario")));
							res.setName(rs.getString(rs.findColumn("nome")));
							res.setId_unidade(rs.getInt(rs.findColumn("id_unidade"))); 
							result.add(res);
						}
						return result;
					}});
		return responsaveis;
	}
	
	public void insertResponsavel(Responsavel res) {
		String sql = "INSERT INTO sisplan.responsavel VALUES(?,?,?)";
		try {
			this.sisplanDao.insert(sql, new Object[] {res.getId(), res.getName(), res.getId_unidade()});
		} catch(DuplicateKeyException e) {
			Logger.warn("SISPLAN - DuplicateKeyException, violation of the primary key constraint,"
					+ "i.e., the Responsavel ("+res.getId()+","+ res.getName()+","+res.getId_unidade()+") already has inserted previously.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Responsavel selectResponsavel(int id_responsavel) {
		String sql = "SELECT * FROM sisplan.responsavel WHERE id="+id_responsavel;
		return (Responsavel)this.sisplanDao.query(sql, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Responsavel res = new Responsavel();
				while(rs.next()) {
					res.setId(rs.getInt(rs.findColumn("id")));
					res.setName(rs.getString(rs.findColumn("name")));
					res.setId_unidade(rs.getInt(rs.findColumn("id_unidade"))); 
				}
				return res;
			}});
	}
}
