package br.gov.cmb.common.ejb.jpa.converter;

import java.util.Date;

import javax.persistence.AttributeConverter;

import com.google.common.base.Strings;

import br.gov.cmb.common.util.DataUtils;

public class DateAttributeConverter implements AttributeConverter<Date, String> {

	@Override
	public String convertToDatabaseColumn(Date date) {
		String dataFormatada = null;
		if (date != null) {
			dataFormatada = DataUtils.formataryyyyMMdd(date);
		}
		return dataFormatada;
	}

	@Override
	public Date convertToEntityAttribute(String dataFormatada) {
		Date date = null;
		if (!Strings.isNullOrEmpty(dataFormatada.trim())) {
			date = DataUtils.parseyyyyMMdd(dataFormatada);
		}
		return date;
	}

}
