insert into comum.sistema values(21, 'SisPlan', 't', 'f', 't', 't', 'java:/jdbc/SisplanDB', 'f', 't', 'f', 't');

insert into comum.subsistema values(21, 21, 'Sisplan Planejamento PDI', '', 't');

insert into comum.dados_institucionais values(21,'siglaSisplan','SISPlan');

insert into comum.papel (id, id_sistema, id_subsistema, descricao, nome, tipo_autorizacao) values (21, 21, 21, 'PLANNING_MANAGER', 'PLANNING_MANAGER', 1);

insert into comum.papel (id, id_sistema, id_subsistema, descricao, nome, tipo_autorizacao) values (22, 21, 21, 'CAMPUS_MANAGER', 'CAMPUS_MANAGER', 1);

insert into comum.papel (id, id_sistema, id_subsistema, descricao, nome, tipo_autorizacao) values (23, 21, 21, 'RESPONSALVEL_PROJETO_ETAPA', 'RESPONSALVEL_PROJETO_ETAPA', 1);

-- login: luiz.nardelli, id=3700
insert into comum.permissao (id, id_sistema, id_usuario, id_papel, autorizada) values(212121, 21, 3700, 21, 't');

-- login: marcio.gomes, id=159
insert into comum.permissao (id, id_sistema, id_usuario, id_papel, autorizada) values(212122, 21, 159, 22, 't');
-- id_unidade=162 (PROPLAN) para id_unidade=65 (CAMPUS CURITIBA)
update comum.usuario set id_unidade=65 where login='marcio.gomes';