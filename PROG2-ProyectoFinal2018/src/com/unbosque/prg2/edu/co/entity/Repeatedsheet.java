package com.unbosque.prg2.edu.co.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the repeatedsheets database table.
 * 
 */
@Entity
@Table(name="repeatedsheets")
public class Repeatedsheet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int countSheets;

	private int numberSheets;

	private int userId;

	public Repeatedsheet() {
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name="id",unique=true,nullable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "countSheets", length = 2,nullable=false)
	public int getCountSheets() {
		return this.countSheets;
	}

	public void setCountSheets(int countSheets) {
		this.countSheets = countSheets;
	}
	@Column(name = "numberSheets", length = 2,nullable=false)
	public int getNumberSheets() {
		return this.numberSheets;
	}

	public void setNumberSheets(int numberSheets) {
		this.numberSheets = numberSheets;
	}
	@Column(name = "userId", length = 5,nullable=false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}