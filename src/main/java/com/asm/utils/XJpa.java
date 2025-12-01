package com.asm.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class XJpa {
	private static EntityManagerFactory factory;
	static {
		factory = Persistence.createEntityManagerFactory("PolyOE");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
