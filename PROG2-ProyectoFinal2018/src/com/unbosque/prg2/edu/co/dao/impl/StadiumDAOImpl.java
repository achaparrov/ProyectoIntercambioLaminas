package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.StadiumDAO;
import com.unbosque.prg2.edu.co.entity.News;
import com.unbosque.prg2.edu.co.entity.Stadium;
import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class StadiumDAOImpl implements StadiumDAO {

	@Override
	public void save(Stadium stadium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(stadium);
		t.commit();
	}
	
	@Override
	public Stadium getId(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Stadium) session.load(Stadium.class, id);
	}

	@Override
	public List<Stadium> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Stadium order by id DESC").list();
		t.commit();
		return lista;	
	
	}

	@Override
	public void remove(Stadium stadium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(stadium);
		t.commit();
	}

	@Override
	public void update(Stadium stadium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(stadium);
		t.commit();
		
	}

}
