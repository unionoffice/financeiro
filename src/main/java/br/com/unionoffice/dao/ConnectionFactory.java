package br.com.unionoffice.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	public static EntityManagerFactory factory;
	public static EntityManager manager;

	static {
		factory = Persistence.createEntityManagerFactory("financeiroUnion");
	}

	public static EntityManager getManager() {
		return manager = factory.createEntityManager();
	}
}