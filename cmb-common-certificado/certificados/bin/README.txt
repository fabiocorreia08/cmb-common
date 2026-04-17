* addCertificate2Java7.sh

  - Atualiza cadeia de certificados no Java (JVM)

Modo de uso:
  
   $ ./addCertificate2Java7 SERVER_HOSTNAME ALIAS

 Exemplo:
   $ ./addCertificate2Java7 novaintranet.stefanini.com novaintranet_stf_2017


************************************ 
  httpsclient.jar
************************************

Valida o acesso https em um servidor.
Este utilitário foi criado para testar se a JVM possui no arquivo cacerts (arquivo de certificados válidos) o certificado do servidor.
Caso a JVM não possua o certificado, pode-se importar o certificado usando o utilitário addCertificate2Java7.sh.


Este aplicativo executa uma conexão https para o URL informado retornando RESPONSE da requisição e DEBUG da conexão SSL.


Modo de Uso:

Efetua requisição GET no URL especificado ignorando validação SSL

   $ java -jar httpsclient.jar https://novaintranet.stefanini.com true

Efetua requisição GET no URL especificado efetuando validação SSL

$ java -jar httpsclient.jar https://novaintranet.stefanini.com false

