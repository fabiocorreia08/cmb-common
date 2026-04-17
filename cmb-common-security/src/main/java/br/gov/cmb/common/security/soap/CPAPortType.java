/**
 * CPAPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.cmb.common.security.soap;

public interface CPAPortType extends java.rmi.Remote {

    /**
     * Says hello to the caller
     */
    public java.lang.String login(br.gov.cmb.common.security.soap.Args args) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String loginSGDOC(br.gov.cmb.common.security.soap.Args args) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String loginASP(java.lang.String idSistema, java.lang.String login, java.lang.String pwd) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String dadosUsuario(br.gov.cmb.common.security.soap.Matr matr) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String dadosLotacao(java.lang.String ccusto) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String usuaWithPerm(br.gov.cmb.common.security.soap.Funcs funcs) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String isUser(br.gov.cmb.common.security.soap.Args args) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String validate(java.lang.String user, java.lang.String pass) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String loadUser(br.gov.cmb.common.security.soap.Args args) throws java.rmi.RemoteException;

    /**
     * Says hello to the caller
     */
    public java.lang.String retornaPermissoesSGDOC(br.gov.cmb.common.security.soap.Args identifier) throws java.rmi.RemoteException;
}
