package br.gov.cmb.common.ejb.configuracao;


import javax.enterprise.inject.spi.InjectionPoint;

public abstract class AbstractConfiguracaoProducer {


    protected abstract ConfiguracaoResolver getResolver();

    public String getStringConfigValue(InjectionPoint ip) {

        String fieldQualifyName = ip.getMember().getDeclaringClass().getName() + "." + ip.getMember().getName();

        // Trying with explicit key defined on the field
        String key = ip.getAnnotated().getAnnotation(Configuracao.class).value();
        boolean isKeyDefined = !key.trim().isEmpty();

        boolean valueRequired = ip.getAnnotated().getAnnotation(Configuracao.class).required();

        String value = null;
        if (isKeyDefined) {
            value = getResolver().getValue(key);
        } else {
            // Falling back to fully-qualified field name resolving.
            key = fieldQualifyName;
            value = getResolver().getValue(fieldQualifyName);

            // No luck... so perhaps just the field name?
            if (value == null) {
                key = ip.getMember().getName();
                value = getResolver().getValue(key);
            }
        }

        // No can do - no value found but you've said it's required.
        if (value == null && valueRequired) {
            throw new IllegalStateException("Nenhum valor definido para o campo: " + fieldQualifyName
                    + " mas o campo foi marcado como obrigatório.");
        }

        return value;
    }


    public Double getDoubleConfigValue(InjectionPoint ip) {
        String value = getStringConfigValue(ip);

        return (value != null) ? Double.valueOf(value) : null;
    }

    public Long getLongConfigValue(InjectionPoint ip) {
        String value = getStringConfigValue(ip);

        return (value != null) ? Long.valueOf(value) : null;
    }

    public Integer getIntegerConfigValue(InjectionPoint ip) {
        String value = getStringConfigValue(ip);

        return (value != null) ? Integer.valueOf(value) : null;
    }
}
