package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.RepeatedsheetDAO;
import com.unbosque.prg2.edu.co.entity.Missingsheet;
import com.unbosque.prg2.edu.co.entity.Repeatedsheet;
import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class RepeatedsheetDAOImpl implements RepeatedsheetDAO {

	static final Logger logger = Logger.getLogger(RepeatedsheetDAOImpl.class);

	@Override
	public void save(Repeatedsheet repeatedsheet) {
		logger.warn("Se necesita un objeto de tipo Audit o auditoria para poder guardarse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(repeatedsheet);
		t.commit();

	}

	@Override
	public Repeatedsheet getUserId(int userId) {
		logger.warn("Se necesita la id del usuario para poder dar el Repeatedsheet");
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Repeatedsheet) session.load(Repeatedsheet.class, userId);
	}

	public List listaUsuariosCambio(int pUserId){
		logger.warn("Se necesita la id del usuario para poder dar la lista de usuarios");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Query query=session.createSQLQuery("select count(*),user.username, userId,user.emailAddress,user.phoneNumber,user.fullName,(select count(*) from repeatedsheets,user where userId=user.id and numbersheets in ( select numbersheets from missingsheets where userId="+pUserId+") group by userId) from missingsheets,user where userId=user.id and numbersheets in ( select numbersheets from repeatedsheets where userId="+pUserId+") group by userId");
		List lista = query.list();
		return lista;

	}

	@Override
	public List<Repeatedsheet> list(int pUserId) {
		logger.warn("Se necesita la id de un usuario para poder dar la lista de Repeatedsheet");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("from Repeatedsheet where userId = :pUserId");
		query.setParameter("pUserId", pUserId);
		List lista = query.list();
		return lista;
	}

	@Override
	public void remove(Repeatedsheet repeatedsheet) {
		logger.warn("Se necesita un objeto de tipo Repeatedsheet para poder removerlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(repeatedsheet);
		t.commit();

	}

	@Override
	public void update(Repeatedsheet repeatedsheet) {
		logger.warn("Se necesita un objeto de tipo Repeatedsheet para poder actualizarlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(repeatedsheet);
		t.commit();

	}

}
