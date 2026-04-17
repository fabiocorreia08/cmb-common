package br.gov.cmb.commons.certificado.oid;

public class OIDFormatField {
	private String name;
	private int size;
	
	public OIDFormatField(String name, int size) {
		this.name = name;
		this.size = size;
	}
	
	public static OIDFormatField getInstance(String name, int size) {
		return new OIDFormatField(name,size);
	}

	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
}
