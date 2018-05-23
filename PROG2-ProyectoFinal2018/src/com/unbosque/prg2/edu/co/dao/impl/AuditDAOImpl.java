package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.beans.AuditBean;
import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.util.HibernateUtil;


public class AuditDAOImpl implements AuditDAO {

	static final Logger logger = Logger.getLogger(AuditDAOImpl.class);
	
	public void save(Audit auditoria) {
		
		logger.warn("Se necesita un objeto de tipo Audit o auditoria para poder guardarse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(auditoria);
		t.commit();
	}

	public List<Audit> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Audit order by id DESC").list();
		t.commit();
		return lista;
	}

}
