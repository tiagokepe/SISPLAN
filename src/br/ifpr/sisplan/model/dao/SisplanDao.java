package br.ifpr.sisplan.model.dao;

import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ufrn.arq.dao.JdbcTemplate;

public class SisplanDao {
	
	private static SisplanDao instance;
	private JdbcTemplate jt;
	
	public static SisplanDao getInstance() {
		if(instance == null)
			instance = new SisplanDao();
		return instance;
	}

	private SisplanDao() {
		InitialContext ic = null;
		try {
			ic = new InitialContext();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataSource ds = null;
		try {
			ds = (DataSource)ic.lookup("java:/jdbc/SisplanDB");
		} catch (NamingException e) {
			System.err.println("Base Sisplan não configurada.");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		this.jt = new JdbcTemplate(ds);
	}
	
	public List queryForList(String sql) {
		return this.jt.queryForList(sql);
	}
	
	public List queryForList(String sql, Class targetClazz) {
		return this.jt.queryForList(sql, targetClazz);
	}

	public Object queryForObject(String sql, Class targetClazz) {
		return this.jt.queryForObject(sql, targetClazz);
	}

	public int queryForInt(String sql) {
		return this.jt.queryForInt(sql);
	}
	
	 public Object query(final String sql, final ResultSetExtractor rse) throws DataAccessException {
		 return this.jt.query(sql, rse);
	 }
	 
	 public void update(String sql) {
		 this.jt.update(sql);
	 }
	 
	 public void insert(String sql, Object[] args) throws DataAccessException {
		 if(args.length == 0)
			 this.jt.update(sql);
		 else
			 this.jt.update(sql, args);
	 }
}