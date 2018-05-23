package com.unbosque.prg2.edu.co.dao;

import java.util.List;

import com.unbosque.prg2.edu.co.entity.User;

public interface UserDAO {
	public void save (User user);
	public User getId(int id);
    public List<User>list();
    public void remove(User user);
    public void update(User user);
	public User buscarUsuario(String pLogin, String pClave);
	public User buscarUsuarioEmail(String pEmail);
    
}
