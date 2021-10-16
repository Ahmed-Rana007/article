package com.hbl.selfservice;

import java.util.Hashtable;
import javax.security.auth.login.LoginException;

import oracle.iam.platform.OIMAuthJndiTemplate;
import oracle.iam.platform.OIMClient;
import oracle.iam.identity.usermgmt.api.UserManager;
public class LoginToOIM {

	 static OIMClient oimClient = null;
	    static UserManager userManager =null;
	    public static OIMClient loggedIntoOIM(String userName, String password) throws LoginException, Exception {

	  final String AUTHWL_PATH = "D:\\E Drive\\HBL\\GUI\\Merged-friday-4-6-2021-final\\SelfService\\WebContent\\WEB-INF\\lib\\authwl.conf";
 // final String AUTHWL_PATH = "app//oracle//product//OIGP4_HOME//idm//server//config//authwl.conf";
	        //app/oracle/product/OIGP4_HOME/idm/server/config
	        final String APPSERVER_TYPE = "wls"; // WebLogic Server
	        System.out.println("Creating client....");
	        String ctxFactory = "weblogic.jndi.WLInitialContextFactory";
	        String serverURL = "t3://10.9.166.59:14000/oim";
	        
	         
	        System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
	        System.setProperty("APPSERVER_TYPE", APPSERVER_TYPE);
	        //String username = "xelsysadm";

	        Hashtable env = new Hashtable();
	        env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, ctxFactory);
	        env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, serverURL);
	         
	        //char[] password1 = "New@12345".toCharArray();
	        oimClient = new OIMClient(env);
	        System.out.println("Logging in");
	        oimClient.login(userName, password.toCharArray());
	        
	        
	        System.out.println("Log in successful");
	        return oimClient;
	        //userManager = oimClient.getService(UserManager.class);

	    }
	    public static void main(String arg[]) {
	        try {
	            loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
	            OIMUtils oimUtils = new OIMUtils();
	            oimUtils.getProcessInstanceKey("MUDASAR.BUTT", oimClient);
	        
	        } catch (LoginException ex) {
	            ex.printStackTrace();
	        }
	        catch (Exception ex) {
	                   ex.printStackTrace();
	               }
	        finally{
	            oimClient.logout();    
	        }

	    }
}
