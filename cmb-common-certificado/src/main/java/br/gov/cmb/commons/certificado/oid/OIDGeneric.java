package br.gov.cmb.commons.certificado.oid;

import org.bouncycastle.asn1.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OIDGeneric {
	private String oid = null;
    private String data = null;
    protected Map<String, String> oidFieldsMap = new HashMap<String, String>();
    
    /**
     * Cria uma instancia do OID inicializando com todos os atributos espec�ficos do OID
     * @param data otherName (type 0) do SubjectAlternativeName do certificado
     * @return Instancia especifica do OID, exemplo OID_2_16_76_1_3_1
     * @throws IOException
     * @throws Exception
     */
    public static OIDGeneric getInstance(byte[] data) throws IOException, Exception {
    	Pair<ASN1ObjectIdentifier, ASN1Primitive> generalName = parseGeneralName(data);
    	ASN1ObjectIdentifier oid = generalName.first;
    	ASN1Primitive value = generalName.second;
		
		OIDGeneric oidInstance = OIDFactory.createInstance(oid.getId());
		  
		getInstance(value, oidInstance);
		
		return oidInstance;
    }


	private static void getInstance(ASN1Primitive value, OIDGeneric oidInstance) {
		if (value != null) {
			if (value instanceof DEROctetString) {
				oidInstance.setData(new String(((DEROctetString) value).getOctets()));
			} else if (value instanceof DERPrintableString) {
				oidInstance.setData(((DERPrintableString) value).getString());
			} else if (value instanceof DERUTF8String) {
				oidInstance.setData(((DERUTF8String) value).getString());
			}else if(value instanceof DERTaggedObject){
				DERTaggedObject taggedObject = (DERTaggedObject) value;
				getInstance(taggedObject.getObject(), oidInstance);
			} else {
			  // IA5
			  oidInstance.setData(((DERIA5String)value).getString());
			}
			
			oidInstance.initialize();
		}
	}

	private static Pair<ASN1ObjectIdentifier, ASN1Primitive> parseGeneralName(byte[] data) throws IOException {
		ASN1InputStream is = new ASN1InputStream(data);
		ASN1Sequence sequence = getSequenceGeneralName(is);

		ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) sequence.getObjectAt(0);
		ASN1Primitive value = ((DERTaggedObject)sequence.getObjectAt(1)).getObject();

		return new Pair<>(oid,value);
	}

	private static ASN1Sequence getSequenceGeneralName(ASN1InputStream is) throws IOException {

    	ASN1Primitive asn1 = is.readObject();
		ASN1Sequence sequence = null;

    	if (asn1 instanceof DERTaggedObject) {
			DERTaggedObject taggedObject = (DERTaggedObject) asn1;
			sequence = (ASN1Sequence) taggedObject.getObject();

		} else {
			sequence =  (ASN1Sequence) asn1;
		}
		is.close();

    	return sequence;
	}
    
    public void setOid(String oid) {
		this.oid = oid;
	}
    
    public String getOid() {
		return oid;
	}
    
    public void setData(String data) {
		this.data = data;
	}
    
    public String getData() {
		return data;
	}
    
    protected void initialize() {   }
    
    /**
     * Inicializa mapa de extras do certificado, conhecendo o formato do campo data do OID especifico
     * @param formatFields formato dos campos no data do OID
     */
    protected void initialize(OIDFormatField[] formatFields) {
        int position = 0;

        if (data != null) {        
	        for (OIDFormatField formatField : formatFields) {
	            String key = formatField.getName();
	            int size = formatField.getSize();
	            oidFieldsMap.put(key, data.substring(position, Math.min(position + size, data.length())));
	            position += size;
	        }
        }
    }

}
