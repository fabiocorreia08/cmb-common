package br.gov.cmb.common.web.dataModel;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.google.common.base.Strings;

import br.gov.cmb.common.ejb.paginacao.Pagina;
import br.gov.cmb.common.ejb.vo.ModeloVO;
import br.gov.cmb.common.exception.runtime.DataModelException;
import br.gov.cmb.common.util.CollectionUtils;
import br.gov.cmb.common.util.ReflectionUtils;
import br.gov.cmb.common.web.anotacao.VO;

public abstract class AbstractDataModel<T extends Serializable> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1L;

    protected abstract Pagina<T> acaoBuscar(Pagina<T> pagina);

    @Override
    public List<T> load(int primeiroRegistro, int tamanho, String campoOrdenacao, SortOrder ordem, Map<String, Object> filters) {
        Pagina<T> pagina = new Pagina<>(getVO(), primeiroRegistro, tamanho);
        ordenar(pagina, campoOrdenacao, ordem);

        pagina = acaoBuscar(pagina);

        setRowCount(pagina.getTotalDeRegistros());
        return pagina.getRegistros();
    }

    private void ordenar(Pagina<T> pagina, String campoOrdenacao, SortOrder ordem) {
        if (!Strings.isNullOrEmpty(campoOrdenacao) && ordem != null) {
            if (SortOrder.ASCENDING.equals(ordem)) {
                pagina.ordenarAscendente(campoOrdenacao);
            } else if (SortOrder.DESCENDING.equals(ordem)) {
                pagina.ordenarDecrescente(campoOrdenacao);
            }
        }
    }

    private ModeloVO getVO() {
        List<Field> campos = ReflectionUtils.recuperarCamposAnotadosCom(getClass(), VO.class);

        if (CollectionUtils.isNotEmpty(campos)) {
            if (campos.size() == 1) {
            	return (ModeloVO) ReflectionUtils.recuperarValorCampo(this, campos.get(0).getName());
            }

            throw new DataModelException("Mais de um campo anotado com @VO");
        }

        return null;
    }

}
