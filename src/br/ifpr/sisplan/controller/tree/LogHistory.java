package br.ifpr.sisplan.controller.tree;

import br.ifpr.sisplan.controller.bean.SisplanUser;
import br.ifpr.sisplan.controller.ifaces.TreeNodeCadastroIface;
import br.ifpr.sisplan.controller.ifaces.TreeNodeInfoIface;
import br.ifpr.sisplan.model.dao.HistoricoDao;
import br.ifpr.sisplan.model.table.Historico;
import br.ufrn.arq.web.jsf.AbstractController;

public class LogHistory extends AbstractController {
	private static LogHistory instance ;
	private LogHistory() {
	}
	
	public static LogHistory getInstance() {
		if(instance == null)
			instance = new LogHistory();
		return instance;
	}
	
	public void log(TreeNodeInfoIface info, TreeNodeCadastroIface cadastro,
					String campoAlterado, String de, String para) {
		Historico his = new Historico();
		his.setResponsavel(((SisplanUser)getMBean("sisplanUser")).getUserName());
		his.setTipoComponente(info.getType());
		String detalhe = info.getName().equals(info.getDescricao()) ?
						 info.getName() :
					     info.getName() +": "+info.getDescricao();
		his.setDetalhe(detalhe);
		his.setUnidade(cadastro.getUnidadeName());
		his.setCampoAlterado(campoAlterado);
		his.setDe(de);
		his.setPara(para);
		getDAO(HistoricoDao.class).insert(his);
	}

}
