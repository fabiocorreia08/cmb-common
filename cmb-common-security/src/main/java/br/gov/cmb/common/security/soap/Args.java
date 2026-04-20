/**
 * Args.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.cmb.common.security.soap;

public class Args implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idSistema;

	private java.lang.String login;

	private java.lang.String pwd;

	public Args() {
	}

	public Args(int idSistema, java.lang.String login, java.lang.String pwd) {
		this.idSistema = idSistema;
		this.login = login;
		this.pwd = pwd;
	}

	/**
	 * Gets the idSistema value for this Args.
	 * 
	 * @return idSistema
	 */
	public int getIdSistema() {
		return idSistema;
	}

	/**
	 * Sets the idSistema value for this Args.
	 * 
	 * @param idSistema
	 */
	public void setIdSistema(int idSistema) {
		this.idSistema = idSistema;
	}

	/**
	 * Gets the login value for this Args.
	 * 
	 * @return login
	 */
	public java.lang.String getLogin() {
		return login;
	}

	/**
	 * Sets the login value for this Args.
	 * 
	 * @param login
	 */
	public void setLogin(java.lang.String login) {
		this.login = login;
	}

	/**
	 * Gets the pwd value for this Args.
	 * 
	 * @return pwd
	 */
	public java.lang.String getPwd() {
		return pwd;
	}

	/**
	 * Sets the pwd value for this Args.
	 * 
	 * @param pwd
	 */
	public void setPwd(java.lang.String pwd) {
		this.pwd = pwd;
	}

	private java.lang.Object __equalsCalc = null;

	@SuppressWarnings("unused")
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Args))
			return false;
		Args other = (Args) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && this.idSistema == other.getIdSistema()
				&& ((this.login == null && other.getLogin() == null)
						|| (this.login != null && this.login.equals(other.getLogin())))
				&& ((this.pwd == null && other.getPwd() == null)
						|| (this.pwd != null && this.pwd.equals(other.getPwd())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		_hashCode += getIdSistema();
		if (getLogin() != null) {
			_hashCode += getLogin().hashCode();
		}
		if (getPwd() != null) {
			_hashCode += getPwd().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Args.class,
			true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:CPA", "args"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idSistema");
		elemField.setXmlName(new javax.xml.namespace.QName("", "idSistema"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("login");
		elemField.setXmlName(new javax.xml.namespace.QName("", "login"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("pwd");
		elemField.setXmlName(new javax.xml.namespace.QName("", "pwd"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
			@SuppressWarnings("rawtypes") java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
			@SuppressWarnings("rawtypes") java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
