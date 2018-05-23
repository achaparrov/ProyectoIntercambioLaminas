package com.unbosque.prg2.edu.co.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.UserDAO;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.Missingsheet;
import com.unbosque.prg2.edu.co.entity.User;
import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class UserDAOImpl implements UserDAO {

	@Override
	public void save(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(user);
		t.commit();
		
		
		
		
	}

	@Override
	public User getId(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (User) session.load(Missingsheet.class, id);
	}

	@Override
	public User buscarUsuario(String pLogin, String pClave) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from User where userName = :pLogin and password= :pClave ");
	
		        query.setParameter("pLogin", pLogin);
		        query.setParameter("pClave", pClave);
		        List<?> list = query.list();		     
		        User u=null;
		        if(list.size()>0){
		         u = (User)list.get(0);		 
		        }
		return u;
	}
	
	@Override
	public User buscarUsuarioEmail(String pEmail) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from User where emailAddress = :pEmail ");
	
		        query.setParameter("pEmail", pEmail);		        
		        List<?> list = query.list();		     
		        User u=null;
		        if(list.size()>0){
		         u = (User)list.get(0);		 
		        }
		return u;
	}
	
	@Override
	public List<User> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from User order by id DESC").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(user);
		t.commit();
		
	}

	@Override
	public void update(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(user);
		t.commit();
		
	}

	@Override
	public User verificaUsuario(String usu, String pass) {
	
		Session session=HibernateUtil.getSessionFactory().openSession();
		return (User)session.createQuery("FROM User u WHERE u.userName = :usu AND u.password =:pass").uniqueResult();
	}

	@Override
	public List<User> buscarUserLogin(String userName) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<User> lista = session.createQuery("from User where userName = '"+userName+"'").list();
		t.commit();
		return lista;
		
	}

	
}
