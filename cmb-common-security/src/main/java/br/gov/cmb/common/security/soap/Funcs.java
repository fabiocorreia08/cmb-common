/**
 * Funcs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.cmb.common.security.soap;

public class Funcs  implements java.io.Serializable {
    private int idSistema;

    private java.lang.String idFuncionalidade;

    public Funcs() {
    }

    public Funcs(
           int idSistema,
           java.lang.String idFuncionalidade) {
           this.idSistema = idSistema;
           this.idFuncionalidade = idFuncionalidade;
    }


    /**
     * Gets the idSistema value for this Funcs.
     * 
     * @return idSistema
     */
    public int getIdSistema() {
        return idSistema;
    }


    /**
     * Sets the idSistema value for this Funcs.
     * 
     * @param idSistema
     */
    public void setIdSistema(int idSistema) {
        this.idSistema = idSistema;
    }


    /**
     * Gets the idFuncionalidade value for this Funcs.
     * 
     * @return idFuncionalidade
     */
    public java.lang.String getIdFuncionalidade() {
        return idFuncionalidade;
    }


    /**
     * Sets the idFuncionalidade value for this Funcs.
     * 
     * @param idFuncionalidade
     */
    public void setIdFuncionalidade(java.lang.String idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Funcs)) return false;
        Funcs other = (Funcs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idSistema == other.getIdSistema() &&
            ((this.idFuncionalidade==null && other.getIdFuncionalidade()==null) || 
             (this.idFuncionalidade!=null &&
              this.idFuncionalidade.equals(other.getIdFuncionalidade())));
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
        if (getIdFuncionalidade() != null) {
            _hashCode += getIdFuncionalidade().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Funcs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:CPA", "funcs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSistema");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSistema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFuncionalidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFuncionalidade"));
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
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
