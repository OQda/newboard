package com.myboard.hiber;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
	static SessionFactory sf;
	static ServiceRegistry sr;
	
	static {
		try {
			Configuration con = new Configuration().configure();
			sr = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
			sf = con.buildSessionFactory(sr);
		}catch(Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}
	
	public static SessionFactory getSf() {
		return sf;
	}
	

}
