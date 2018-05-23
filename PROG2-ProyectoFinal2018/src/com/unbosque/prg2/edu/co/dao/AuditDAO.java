package com.unbosque.prg2.edu.co.dao;

import java.util.List;

import com.unbosque.prg2.edu.co.entity.Audit;



public interface AuditDAO {

	public void save(Audit auditoria);

	public List<Audit> list();
}
