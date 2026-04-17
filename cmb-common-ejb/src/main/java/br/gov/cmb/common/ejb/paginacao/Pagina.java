package br.gov.cmb.common.ejb.paginacao;

import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

import br.gov.cmb.common.ejb.vo.ModeloVO;

public class Pagina<T> {

	private List<T> registros;
	private Integer tamanho;
	private Integer totalDeRegistros;
	private Integer primeiroRegistro;
	private String campoOrdenacao;
	private EOrdem ordem;

	private ModeloVO modeloVO;

	private enum EOrdem {
		ASC, DESC;
	}

	public Pagina() {
	}

	public Pagina(ModeloVO modelVO, Integer primeiroRegistro, Integer tamanho) {
		this.modeloVO = modelVO;
		this.primeiroRegistro = primeiroRegistro;
		this.tamanho = tamanho;
	}

	public Map<String, Object> getParametros() {
		return modeloVO.getParametros();
	}

	public boolean possuiOrdenacao() {
		return !Strings.isNullOrEmpty(campoOrdenacao) && ordem != null;
	}

	public String getCampoOrdenacao() {
		return campoOrdenacao;
	}

	public void setCampoOrdenacao(String campoOrdenacao) {
		this.campoOrdenacao = campoOrdenacao;
	}

	public EOrdem getOrdem() {
		return ordem;
	}

	public void ordenarAscendente(String campoOrdenacao) {
		this.ordenar(campoOrdenacao, EOrdem.ASC);
	}

	public void ordenarDecrescente(String campoOrdenacao) {
		this.ordenar(campoOrdenacao, EOrdem.DESC);
	}

	private void ordenar(String campoOrdenacao, EOrdem ordem) {
		this.setCampoOrdenacao(campoOrdenacao);
		this.ordem = ordem;
	}

	public List<T> getRegistros() {
		return registros;
	}

	public void setRegistros(List<T> registros) {
		this.registros = registros;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public Integer getTotalDeRegistros() {
		return totalDeRegistros;
	}

	public void setTotalDeRegistros(Integer totalDeRegistros) {
		this.totalDeRegistros = totalDeRegistros;
	}

	public Integer getPrimeiroRegistro() {
		return primeiroRegistro;
	}

	public void setPrimeiroRegistro(Integer primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}

	public ModeloVO getModelVO() {
		return modeloVO;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T getModeloVO(Class<T> clazz) {
		return (T) modeloVO;
	}

	public void setModelVO(ModeloVO modelVO) {
		this.modeloVO = modelVO;
	}

}
