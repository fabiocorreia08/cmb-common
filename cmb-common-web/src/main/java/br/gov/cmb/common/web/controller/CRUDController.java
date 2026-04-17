package br.gov.cmb.common.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import br.gov.cmb.common.exception.ValidacaoException;
import br.gov.cmb.common.web.util.FacesUtil;
import br.gov.cmb.common.web.util.MessagesUtils;

import com.google.common.base.Strings;

public abstract class CRUDController<E extends Serializable> extends AbstractController {

    private static final String PARAMETRO_ALTERACAO = "idAlteracao";

    private static final long serialVersionUID = 1L;

    protected String chaveMensagemCadastroComSucesso = "label.cadastro.sucesso";
    protected String chaveMensagemRemocaoComSucesso = "label.remocao.sucesso";

    protected abstract void acaoSalvar() throws ValidacaoException;

    protected abstract void acaoRemover(E entidade);

    protected abstract void acaoPrepararAlteracao(String idAlteracao);

    @PostConstruct
    public void iniciar() {
        prepararAlteracao();
    }

    private void prepararAlteracao() {
        String idAlteracao = FacesUtil.getRequestParameter(PARAMETRO_ALTERACAO);
        if (!Strings.isNullOrEmpty(idAlteracao)) {
            acaoPrepararAlteracao(idAlteracao);
        }
    }

    public void salvar() {
        try {
            acaoSalvar();
            MessagesUtils.exibirMensagemSucesso(chaveMensagemCadastroComSucesso);
        } catch (ValidacaoException e) {
            construirMensagemDeErro(e.getErrosDeValidacao());
        }
    }

    public void remover(E entidade) {
        acaoRemover(entidade);
        MessagesUtils.exibirMensagemSucesso(chaveMensagemRemocaoComSucesso);
    }

}
