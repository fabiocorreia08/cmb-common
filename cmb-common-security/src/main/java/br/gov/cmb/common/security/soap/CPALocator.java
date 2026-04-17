/**
 * CPALocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.cmb.common.security.soap;

public class CPALocator extends org.apache.axis.client.Service implements br.gov.cmb.common.security.soap.CPA {

    public CPALocator() {
    }


    public CPALocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CPALocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CPAPort
    private java.lang.String CPAPort_address = "http://localhost:8082/";

    public java.lang.String getCPAPortAddress() {
        return CPAPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CPAPortWSDDServiceName = "CPAPort";

    public java.lang.String getCPAPortWSDDServiceName() {
        return CPAPortWSDDServiceName;
    }

    public void setCPAPortWSDDServiceName(java.lang.String name) {
        CPAPortWSDDServiceName = name;
    }

    public br.gov.cmb.common.security.soap.CPAPortType getCPAPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CPAPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCPAPort(endpoint);
    }

    public br.gov.cmb.common.security.soap.CPAPortType getCPAPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.gov.cmb.common.security.soap.CPABindingStub _stub = new br.gov.cmb.common.security.soap.CPABindingStub(portAddress, this);
            _stub.setPortName(getCPAPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCPAPortEndpointAddress(java.lang.String address) {
        CPAPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.gov.cmb.common.security.soap.CPAPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                br.gov.cmb.common.security.soap.CPABindingStub _stub = new br.gov.cmb.common.security.soap.CPABindingStub(new java.net.URL(CPAPort_address), this);
                _stub.setPortName(getCPAPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CPAPort".equals(inputPortName)) {
            return getCPAPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:CPA", "CPA");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:CPA", "CPAPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CPAPort".equals(portName)) {
            setCPAPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
