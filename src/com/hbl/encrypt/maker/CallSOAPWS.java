package com.hbl.encrypt.maker;
import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import oracle.iam.platform.context.ContextManager;
import oracle.iam.platform.Platform;
import org.apache.http.HttpEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
public class CallSOAPWS {
    public CallSOAPWS() {
        super();
    }
    public static void main(String arg[])
    {
        CallSOAPWS smsSend = new CallSOAPWS();
        String ss = smsSend.getEncryptedValue("Hello@123");
        System.out.println(ss);
        System.out.println(smsSend.getDecryptedValue(ss));
        
    }
    Connection connection =null;
    public String getAs400Encrypted(String userKey)
    {
        String as4ID = null;
        try{
            String requestID = ContextManager.getOrigUser().toString().trim();
            
            connection = Platform.getOperationalDS().getConnection();
            PreparedStatement stmt = null;
                        String sql = "select USR_UDF_AS400_USERID, USR_LOGIN from USR where USR_LOGIN ='"+requestID+"' and USR_STATUS !='Deleted'";
                        stmt = connection.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        as4ID = rs.getString(1).toString().trim();
        }catch(Exception ex){
            }
       
        
        return  getEncryptedValue(as4ID);
    }
    public String getMakerEncrypted()
    {
        String as4ID = null;
        try{
            String requestID = ContextManager.getOrigUser().toString().trim();
            
            connection = Platform.getOperationalDS().getConnection();
            PreparedStatement stmt = null;
                        String sql = "select USR_UDF_AS400_USERID, USR_LOGIN from USR where USR_LOGIN ='"+requestID+"' and USR_STATUS !='Deleted'";
                        stmt = connection.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        as4ID = rs.getString(1).toString().trim();
        }catch(Exception ex){
            }
       
        
        return  getEncryptedValue(as4ID);
    }
    public String getEncryptedValue(String value)  {
         String encryptedValue = null;
         // Create a StringEntity for the SOAP XML.
         try {   
           String body ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"><soapenv:Header/><soapenv:Body><tem:getEncryptedValue><!--Optional:--><tem:value>"+value+"</tem:value></tem:getEncryptedValue></soapenv:Body></soapenv:Envelope>";
           StringEntity stringEntity = new StringEntity(body, "UTF-8");
           stringEntity.setChunked(true);
           
           // Request parameters and other properties.
           HttpPost httpPost = new HttpPost("http://10.9.166.58/WebService1.asmx?WSDL");
           httpPost.setEntity(stringEntity);

           httpPost.addHeader("Service", "WebService1");
           httpPost.addHeader("Port", "WebService1Soap");
           httpPost.addHeader("SOAPAction", "http://tempuri.org/getEncryptedValue");
           httpPost.addHeader("Accept", "text/xml");
           httpPost.addHeader("Content-Type", "text/xml");
           

           // Execute and get the response.
           HttpClient httpClient = new DefaultHttpClient();
           HttpResponse response;
        
             response = httpClient.execute(httpPost);
         
         
         HttpEntity entity = response.getEntity();
        

           String strResponse = null;
           if (entity != null) {
               //System.out.println(entity+"\n"+response);
              strResponse = EntityUtils.toString(entity);
              entity.getContent();
              
               encryptedValue = getFullNameFromXml(strResponse,"getEncryptedValueResult").toString();
              encryptedValue = encryptedValue.replace("[","");
                encryptedValue = encryptedValue.replace("]","");
              System.out.println(encryptedValue);
           }
     }
         catch (Exception e) {
                    System.out.println(e.toString());
                }
          return encryptedValue.trim().toString();
     }
    
    public String getDecryptedValue(String value)  {
         String decryptedValue = null;
         // Create a StringEntity for the SOAP XML.
         try {   
           String body ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"><soapenv:Header/><soapenv:Body><tem:getDecryptedValue><!--Optional:--><tem:value>"+value+"</tem:value></tem:getDecryptedValue></soapenv:Body></soapenv:Envelope>";
           StringEntity stringEntity = new StringEntity(body, "UTF-8");
           stringEntity.setChunked(true);
           
           // Request parameters and other properties.
           HttpPost httpPost = new HttpPost("http://10.9.166.58/WebService1.asmx?WSDL");
           httpPost.setEntity(stringEntity);

           httpPost.addHeader("Service", "WebService1");
           httpPost.addHeader("Port", "WebService1Soap");
           httpPost.addHeader("SOAPAction", "http://tempuri.org/getDecryptedValue");
           httpPost.addHeader("Accept", "text/xml");
           httpPost.addHeader("Content-Type", "text/xml");
           

           // Execute and get the response.
           HttpClient httpClient = new DefaultHttpClient();
           HttpResponse response;
        
             response = httpClient.execute(httpPost);
         
         
         HttpEntity entity = response.getEntity();
        

           String strResponse = null;
           if (entity != null) {
               //System.out.println(entity+"\n"+response);
              strResponse = EntityUtils.toString(entity);
              entity.getContent();
              
               decryptedValue = getFullNameFromXml(strResponse,"getDecryptedValueResult").toString();
              decryptedValue = decryptedValue.replace("[","");
                decryptedValue = decryptedValue.replace("]","");
              
           }
     }
         catch (Exception e) {
                    System.out.println(e.toString());
                }
          return decryptedValue.trim().toString();
     }
    
    
    public static List<String> getFullNameFromXml(String response, String tagName) throws Exception {
        Document xmlDoc = loadXMLString(response);
        NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
        List<String> ids = new ArrayList<String>(nodeList.getLength());
        for(int i=0;i<nodeList.getLength(); i++) {
            Node x = nodeList.item(i);
            ids.add(x.getFirstChild().getNodeValue());             
            //System.out.println(nodeList.item(i).getFirstChild().getNodeValue());
        }
        
        
        return ids;
    }
    public static Document loadXMLString(String response) throws Exception
    {
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(response));
        
        return db.parse(is);
    }
}
