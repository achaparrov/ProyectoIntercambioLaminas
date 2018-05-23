package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.MissingsheetDAO;
import com.unbosque.prg2.edu.co.entity.Missingsheet;

import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class MissingsheetDAOImpl implements MissingsheetDAO {

	@Override
	public void save(Missingsheet missingsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(missingsheet);
		t.commit();
	}

	@Override
	public Missingsheet getUserId(int userId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Missingsheet) session.load(Missingsheet.class, userId);
	}

	@Override
	public List<Missingsheet> list(int pUserId) {
		Session session = HibernateUtil.getSessionFactory().openSession();	

		Query query = session.createQuery("from Missingsheet where userId = :pUserId");
		query.setParameter("pUserId", pUserId);
		List lista = query.list();
		return lista;
	}

	@Override
	public void remove(Missingsheet missingsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(missingsheet);
		t.commit();

	}

	@Override
	public void update(Missingsheet missingsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(missingsheet);
		t.commit();

	}

}
