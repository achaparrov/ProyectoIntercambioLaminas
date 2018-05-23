package com.unbosque.prg2.edu.co.dao;

import java.util.List;

import com.unbosque.prg2.edu.co.entity.Parameter;

public interface ParameterDAO {
	
	public void save (Parameter parameter);
    public List<Parameter>list();
    public void remove(Parameter parameter);
    public void update(Parameter parameter);
}
