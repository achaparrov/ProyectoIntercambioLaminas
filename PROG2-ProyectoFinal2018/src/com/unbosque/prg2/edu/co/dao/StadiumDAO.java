package com.unbosque.prg2.edu.co.dao;

import java.util.List;

import com.unbosque.prg2.edu.co.entity.Stadium;

public interface StadiumDAO {
	public void save (Stadium stadium);	
    public List<Stadium>list();
    public void remove(Stadium stadium);
    public void update(Stadium stadium);
}
