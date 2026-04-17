package br.gov.cmb.common.ejb.configuracao;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SystemConfiguration;

import javax.ejb.Lock;
import javax.ejb.LockType;
import java.util.Arrays;
import java.util.List;


public abstract class GerenciadorConfiguracao implements ConfiguracaoResolver {

	private Configuration configuracao;


	protected void carregarConfiguracao() throws ConfigurationException {
		System.out.println("Iniciou o carregarConfiguracao");
		CompositeConfiguration configuracao = new CompositeConfiguration();
		configuracao.addConfiguration(new SystemConfiguration());
		System.out.println("Executou o addConfiguration");
		adicionarConfiguracoes(configuracao);

		this.configuracao = configuracao;
		System.out.println("Finalizou o carregarConfiguracao");
	}

	private void adicionarConfiguracoes(CompositeConfiguration configuracao) throws ConfigurationException {
		for (Configuration config : buscarConfiguracoes()) {
			configuracao.addConfiguration(config);
		}
	}

	protected abstract List<Configuration> buscarConfiguracoes() throws ConfigurationException;

	@Lock(LockType.READ)
	public int getInteger(String chave) {
		return this.configuracao.getInt(chave);
	}

	@Lock(LockType.READ)
	public int getInteger(String chave, Integer defaultValue) {
		return this.configuracao.getInt(chave, defaultValue);
	}

	@Lock(LockType.READ)
	public long getLong(String chave) {
		return this.configuracao.getLong(chave);
	}

	@Lock(LockType.READ)
	public long getLong(String chave, Long defaultValue) {
		return this.configuracao.getLong(chave, defaultValue);
	}

	@Lock(LockType.READ)
	public String getString(String chave) {
		return this.configuracao.getString(chave);
	}

	@Lock(LockType.READ)
	public String[] getStringArray(String chave) {
		return this.configuracao.getStringArray(chave);
	}
	
	@Lock(LockType.READ)
	public List<String> getStrings(String chave) {
		return Arrays.asList(getStringArray(chave));
	}

	@Lock(LockType.READ)
	public String getValue(String chave) {
		return this.configuracao.getString(chave);
	}

}
