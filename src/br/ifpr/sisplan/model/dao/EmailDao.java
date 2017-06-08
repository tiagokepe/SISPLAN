package br.ifpr.sisplan.model.dao;

import br.ufrn.arq.dao.GenericDAOImpl;
import br.ufrn.arq.dao.JdbcTemplate;
import br.ufrn.comum.dominio.Sistema;

public class EmailDao extends GenericDAOImpl {
	private JdbcTemplate jt;
	public EmailDao() {
		super(Sistema.COMUM);
		jt = getJdbcTemplate();
	}
	
	public String selectEmailPlanningDirector(int id_unidade) {
		String sql = "SELECT DISTINCT usuario.email " +
					 "FROM comum.usuario as usuario " +
					 "inner join rh.servidor as servidor on usuario.id_pessoa = servidor.id_pessoa " +
					 "inner join comum.responsavel_unidade as resp_unid on servidor.id_servidor = resp_unid.id_servidor " +
					 "inner join comum.unidade as unidade on resp_unid.id_unidade = unidade.id_unidade " +
					 "WHERE unidade.nome like '%DIRETORIA DE PLANEJAMENTO E ADMINISTRAÇÃO%' AND " +
					 "unidade.unidade_responsavel = " + id_unidade + " AND " +
					 "resp_unid.nivel_responsabilidade = 'C' AND " +
					 "resp_unid.data_fim is null";
		String res = (String) this.jt.queryForObject(sql, String.class);
		return res;
	}
	

}
