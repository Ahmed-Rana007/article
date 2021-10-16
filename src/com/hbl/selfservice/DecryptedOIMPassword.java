package com.hbl.selfservice;

import Thor.API.Security.XLClientSecurityAssociation;

import com.thortech.xl.crypto.tcCryptoException;
import com.thortech.xl.crypto.tcCryptoUtil;
import com.thortech.xl.dataaccess.tcDataBaseClient;

import com.thortech.xl.dataaccess.tcDataProvider;

import com.thortech.xl.dataaccess.tcDataSet;

import com.thortech.xl.dataaccess.tcDataSetException;

import java.util.Hashtable;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import oracle.iam.platform.OIMClient;

 

/**

 * This class gets the OIM Client and uses that to establish a

 * connection to the OIM Schema. You can query the USR table and

 * get the password in plain text. 

 * NOTE: The administrator credential must be used for the OIM Client. 

 */

public class DecryptedOIMPassword 

{

    public  void main(String args) throws tcCryptoException

    {

        tcDataProvider dbProvider = null;

        OIMClient oimClient = null;

 

        try

        {

            String ctxFactory = "weblogic.jndi.WLInitialContextFactory"; //WebLogic Context 

            String oimServerURL = "t3://10.9.166.59:14000/oim";

            final String AUTHWL_PATH = "D:\\E Drive\\HBL\\GUI\\Merged-friday-4-6-2021-final\\SelfService\\WebContent\\WEB-INF\\lib\\authwl.conf";
     	   
            
            final String APPSERVER_TYPE = "wls"; // WebLogic Server
            System.setProperty("APPSERVER_TYPE", APPSERVER_TYPE);
            String username = "xelsysadm"; //OIM Administrator 

            String password = "Hblpoc_1234"; //Administrator Password

 

            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);

            Hashtable<String,String> env = new Hashtable<String,String>(); //use to store OIM environment properties

            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, ctxFactory);

            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, oimServerURL);

            oimClient = new OIMClient(env);

            oimClient.login(username, password.toCharArray()); //login to OIM

 

            XLClientSecurityAssociation.setClientHandle(oimClient);//Needed for database client

            dbProvider = new tcDataBaseClient(); //Connection to OIM Schema

            tcDataSet dataSet = new tcDataSet(); //Stores the result set of an executed query

            String query = "SELECT * FROM USR where USR_LOGIN='ZAHOOR.KHAN'"; //Query Users table

            //String query = "SELECT * FROM PCQ"; //Query Users Challenge Question

            dataSet.setQuery(dbProvider, query); //Set query and database provider

            dataSet.executeQuery(); //execute query and store results into dataSet object

            int records = dataSet.getTotalRowCount(); //Get total records from result set

 

            for(int i = 0; i < records; i++)

            {

                dataSet.goToRow(i); //move pointer to next record

                String plainTextPassword = dataSet.getString("USR_PASSWORD");

                String userLogin = dataSet.getString("USR_LOGIN");

                String userStatus = dataSet.getString("USR_STATUS");

                System.out.printf("User Login: %s\nStatus: %s\nPassword: %s\n\n", userLogin, userStatus, plainTextPassword);  

                String oldPasswordDecrypted = tcCryptoUtil.decrypt(plainTextPassword, "DBSecretKey"); 
                
                System.out.print("oldPasswordDecrypted"+oldPasswordDecrypted);
                

                //Getting user challenge questions and answers

                //String usrKey = dataSet.getString("USR_KEY");

                //String question = dataSet.getString("PCQ_QUESTION");

                //String answer = dataSet.getString("PCQ_ANSWER");

                //System.out.printf("USR_KEY: %s\nQuestion: %s\nAnswer: %s\n", usrKey, question, answer);

            }

        } 

 

        catch (tcDataSetException ex) 

        { 

            Logger.getLogger(DecryptedOIMPassword.class.getName()).log(Level.SEVERE, null, ex);

        }

 

        catch (LoginException ex) 

        {

            Logger.getLogger(DecryptedOIMPassword.class.getName()).log(Level.SEVERE, null, ex);

        }

 

        finally

        {

            //close connections

            try{dbProvider.close();} catch(Exception e){}

            try{XLClientSecurityAssociation.clearThreadLoginSession();} catch(Exception e){}

            try{oimClient.logout();} catch(Exception e) {}

        }     

    }//end main method   

}//end class