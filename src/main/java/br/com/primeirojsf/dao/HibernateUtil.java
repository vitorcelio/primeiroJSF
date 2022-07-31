package br.com.primeirojsf.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

	private static EntityManagerFactory factory = null;

	static {
		try {
			if (factory == null) {
				factory = Persistence.createEntityManagerFactory("primeirojsf");
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public static Object getPrimaryKey(Object obj) {
		return factory.getPersistenceUnitUtil().getIdentifier(obj);
	}

}
