#!/bin/bash


today=$(date +%Y%m%d)
SERVER_URL="$1"
CAS_ALIAS="$2"

echo "Obter certificado de $SERVER_URL"
./retrieve-cert.sh "$SERVER_URL" > server_certificate.pem

echo "Converter certificado .pem para .der"
openssl x509 -outform der -in server_certificate.pem -out server_certificate.der

echo "Backup Java cacerts file"
cp /usr/lib/jvm/java-7-oracle/jre/lib/security/cacerts cacerts_"$today".orig

echo "Adicionar certificado no truststore do Java"
echo "Senha: changeit"
keytool -importcert -trustcacerts -file server_certificate.der -alias "$CAS_ALIAS" -keystore "/usr/lib/jvm/java-7-oracle/jre/lib/security/cacerts"



