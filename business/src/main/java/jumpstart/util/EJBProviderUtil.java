package jumpstart.util;


import org.slf4j.Logger;

public class EJBProviderUtil {

	static public EJBProviderEnum detectEJBProvider(Logger logger) {
		return EJBProviderEnum.JBOSS_7_REMOTE;/*
		logger.info("Looking for an EJB provider...");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if ( loader == null ) loader = EJBProviderUtil.class.getClassLoader();
		
		try {
			Class.forName("org.apache.tomcat.JarScanner", true, loader);
			Class.forName("org.apache.openejb.loader.Loader", true, loader);
			logger.info("...found TomCat 7 OpenEJB 4 local.");
			return EJBProviderEnum.TOMCAT_7_OPENEJB_4_LOCAL;
		} 
		catch (Exception e) {
		}

		try {
			Class.forName("org.apache.openejb.loader.Loader", true, loader);
			logger.info("...found OpenEJB 4 local.");
			return EJBProviderEnum.OPENEJB_4_LOCAL;
		}
		catch (Exception e) {
		}

		try {
			Class.forName("org.apache.openejb.client.Client", true, loader);
			logger.info("...found OpenEJB 4 remote.");
			return EJBProviderEnum.OPENEJB_4_REMOTE;
		}
		catch (Exception e) {
		}

		try {
			Class.forName("org.jboss.modules.Main", true, loader);
			logger.info("...found JBoss 7 local.");
			return EJBProviderEnum.JBOSS_7_LOCAL;
		}
		catch (Exception e) {
		}

		try {
			Class.forName("org.jboss.ejb.client.remoting.ClientMapping", true, loader);
			logger.info("...found JBoss 7 remote.");
			return EJBProviderEnum.JBOSS_7_REMOTE;
		}
		catch (Exception e) {
		}

		try {
			Class.forName("com.sun.enterprise.admin.cli.AsadminMain", true, loader);
			logger.info("...found GlassFish 3 local.");
			return EJBProviderEnum.GLASSFISH_3_LOCAL;
		}
		catch (Exception e) {
		}

		try {
			Class.forName("org.glassfish.appclient.client.acc.agent.ACCAgentClassLoader", true, loader);
			logger.info("...found GlassFish 3 remote.");
			return EJBProviderEnum.GLASSFISH_3_REMOTE;
		}
		catch (Exception e) {
		}

		throw new IllegalStateException(
				"Failed to detect a known EJBProvider. Tried OpenEJB, JBoss, and GlassFish.");
				*/
	}
}
