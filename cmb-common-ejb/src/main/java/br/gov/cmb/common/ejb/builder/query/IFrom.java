package br.gov.cmb.common.ejb.builder.query;

import br.gov.cmb.common.ejb.vo.ModeloVO;

public interface IFrom extends IJPQLBuilder {

	IOrder order(String... campos);

	IFrom innerJoin(String relacionamento, String alias);

	IFrom innerJoinFetch(String relacionamento, String alias);

	IFrom leftJoin(String relacionamento, String alias);

	IFrom leftJoinFetch(String relacionamento, String alias);

	IWhere where(ModeloVO modeloVO, String clausula);

	IWhere where(String clausula);

	IWhere where(ModeloVO modeloVO, String clausula, boolean opcional);
}
