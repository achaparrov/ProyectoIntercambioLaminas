package com.unbosque.prg2.edu.co.dao;

import java.util.List;

import com.unbosque.prg2.edu.co.entity.Repeatedsheet;

public interface RepeatedsheetDAO {
	public void save (Repeatedsheet repeatedsheet);
	public Repeatedsheet getUserId(int userId);
	public List<Repeatedsheet>listaUsuariosCambio(int pUserId);
	public List<Repeatedsheet>list(int pUserId);
    public void remove(Repeatedsheet repeatedsheet);
    public void update(Repeatedsheet repeatedsheet);
}
