package br.gov.cmb.commons.certificado.oid;


public class OIDFactory {

	public static OIDGeneric createInstance(String oid) throws Exception {
		String packageName =  OIDFactory.class.getPackage().getName();
		String className = packageName + ".OID_" + oid.replaceAll("[.]", "_");
        OIDGeneric oidInstanceClass;
        try {
            oidInstanceClass = (OIDGeneric) Class.forName(className).newInstance();
            oidInstanceClass.setOid(oid);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new Exception("Nao foi possivel instanciar a classe '" + className + "'.", e);
        } catch (ClassNotFoundException e) {
            oidInstanceClass = new OIDGeneric();
        }
        
        return oidInstanceClass;
	}
}
