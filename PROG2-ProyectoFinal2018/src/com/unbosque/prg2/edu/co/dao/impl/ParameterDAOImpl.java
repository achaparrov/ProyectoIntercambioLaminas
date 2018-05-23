package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.ParameterDAO;
import com.unbosque.prg2.edu.co.entity.Parameter;
import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class ParameterDAOImpl implements ParameterDAO {

	static final Logger logger = Logger.getLogger(ParameterDAOImpl.class);

	@Override
	public void save(Parameter parameter) {
		logger.warn("Se necesita un objeto de tipo parameter para poder guardarse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(parameter);
		t.commit();

	}

	@Override
	public List<Parameter> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from parameter").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(Parameter parameter) {
		logger.warn("Se necesita un objeto de tipo Parameter para poder removerlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(parameter);
		t.commit();

	}

	@Override
	public void update(Parameter parameter) {
		logger.warn("Se necesita un objeto de tipo Parameter para poder actualizarlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(parameter);
		t.commit();

	}

}
