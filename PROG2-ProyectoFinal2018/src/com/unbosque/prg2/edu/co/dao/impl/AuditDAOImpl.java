package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.util.HibernateUtil;


public class AuditDAOImpl implements AuditDAO {

	public void save(Audit auditoria) {
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
	public List listaConUsuario(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		Query query=session.createSQLQuery("select user.userName,audit.* from audit, user where user.id=audit.userId");
		List lista = query.list();
		return lista;

	}


}
