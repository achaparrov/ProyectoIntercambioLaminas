package com.unbosque.prg2.edu.co.dao;

import java.util.List;

import com.unbosque.prg2.edu.co.entity.Missingsheet;

public interface MissingsheetDAO {
 
	public void save (Missingsheet missingsheet);
	public Missingsheet getUserId(int userId);
    public List<Missingsheet>list(int pUserId);
    public void remove(Missingsheet missingsheet);
    public void update(Missingsheet missingsheet);
}
