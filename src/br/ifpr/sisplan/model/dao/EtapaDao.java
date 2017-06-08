package br.ifpr.sisplan.model.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.ifpr.sisplan.controller.converters.BigDecimalConverter;
import br.ifpr.sisplan.model.table.Etapa;
import br.ifpr.sisplan.model.table.parent.DateNode;
import br.ufrn.arq.dao.GenericDAOImpl;

public class EtapaDao extends GenericDAOImpl {

	private SisplanDao sisplanDao = SisplanDao.getInstance();
	private final static String TABLE_NAME="sisplan.etapa"; 
	
	public EtapaDao() {
		super();
	}
	
	public List selectEtapaByProject(int project_id) {
		String sql = "SELECT * FROM sisplan.etapa "
					+ "	WHERE id_projeto="+project_id + " AND ativo=true ORDER BY id";
		return sisplanDao.queryForList(sql);
	}
	
	public Etapa insertEtapa(String desc, int id_projeto, int id_responsavel, BigDecimal custoPrev, BigDecimal custoEfet) {
		String sql = "INSERT INTO sisplan.etapa(descricao, id_projeto, id_responsavel, custo_previsto, custo_efetivo) VALUES(?,?,?,?,?)";
		this.sisplanDao.insert(sql, new Object[] {desc, id_projeto, id_responsavel, custoPrev, custoEfet});
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
							final BigDecimal custoPrev = rs.getBigDecimal(rs.findColumn("custo_previsto"));
							final BigDecimal custoEfet = rs.getBigDecimal(rs.findColumn("custo_efetivo"));
							result.setId(id);
							result.setDescricao(desc);
							result.setName(desc);
							result.setId_projeto(id_projeto);
							result.setIdResponsavel(id_res);
							result.setCustoPrevisto(custoPrev);
							result.setCustoEfetivo(custoEfet);
						}
						return result;
					}});
		return etapa;
	}
	
	public Etapa insertEtapa(String desc, int id_projeto, int id_responsavel, BigDecimal custoPrev) {
		String sql = "INSERT INTO sisplan.etapa(descricao, id_projeto, id_responsavel, custo_previsto) VALUES(?,?,?,?)";
		this.sisplanDao.insert(sql, new Object[] {desc, id_projeto, id_responsavel, custoPrev});
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
							final BigDecimal custoPrev = rs.getBigDecimal(rs.findColumn("custo_previsto"));
							result.setId(id);
							result.setDescricao(desc);
							result.setName(desc);
							result.setId_projeto(id_projeto);
							result.setIdResponsavel(id_res);
							result.setCustoPrevisto(custoPrev);
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
	
	public void updateCustos(DateNode etapa) {
		String update = "UPDATE "+TABLE_NAME+" SET ";
		String custos = "";
		if( etapa.getCustoPrevisto() != null &&
		    !etapa.getCustoPrevisto().equals(BigDecimalConverter.bigDecEmpty) ) {
			
			custos += " custo_previsto="+etapa.getCustoPrevisto();
		}
		
		if( etapa.getCustoEfetivo() != null &&
		    !etapa.getCustoEfetivo().equals(BigDecimalConverter.bigDecEmpty) ) {
			
			if(custos.isEmpty())
				custos = " custo_efetivo="+etapa.getCustoEfetivo();
			else
				custos += " , custo_efetivo="+etapa.getCustoEfetivo(); 
		}
		
		update += custos + " WHERE id="+etapa.getId();
		
		this.sisplanDao.update(update);
	}
	
	public void updateResponsavel(int id_etapa, int id_responsavel) {
		String sql = "UPDATE " + TABLE_NAME + " SET id_responsavel=" +id_responsavel
				   + " WHERE id="+id_etapa;
		this.sisplanDao.update(sql);
	}
	
	public void updateDescricao(DateNode p) {
		String update = "UPDATE "+TABLE_NAME+" SET descricao='"+p.getDescricao()+"' WHERE id="+p.getId();
		sisplanDao.update(update);
	}
	
	public void updateObservacao(DateNode p) {
		String update = "UPDATE "+TABLE_NAME+" SET observacao='"+p.getObservacao()+"' WHERE id="+p.getId();
		sisplanDao.update(update);
	}
	
	public void updateSentEmail(int id_etapa, boolean sentEmail) {
		String sql = "UPDATE " + TABLE_NAME + " SET sent_email='" +sentEmail + "' "
				   + "WHERE id="+id_etapa;
		this.sisplanDao.update(sql);
	}
	
	public void updateFirstEmail(int id_etapa, Date dt) {
		String sql = "UPDATE " + TABLE_NAME + " SET first_email='" +dt + "' "
				   + "WHERE id="+id_etapa;
		this.sisplanDao.update(sql);
	}
}
