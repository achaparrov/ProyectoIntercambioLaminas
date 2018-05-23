package com.unbosque.prg2.edu.co.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;

import com.unbosque.prg2.edu.co.entity.Parameter;

@ManagedBean
@SessionScoped
public class ParameterBean implements Serializable{
	
	@ManagedProperty("#{userBean}")
	private UserBean userBean;
	
	private Parameter parameter;
	private DataModel listaParameter;


}

