package com.unbosque.prg2.edu.co.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateNews;
	
	private String shortDescription;
	
	private String largeDescription;
	
	private int idUser;
	
	private String state;
	
	public News() {
	}
	public News(int id) {
		this.id=id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	 @Column(name = "dateNews", nullable=false)
		public Date getDateNews() {
			return this.dateNews;
		}

		public void setDateNews(Date dateNews) {
			this.dateNews = dateNews;
		}
		
		@Column(name = "shortDescription", length = 60, nullable=false)
		public String getShortDescription() {
			return this.shortDescription;
		}

		public void setShortDescription(String shortDescription) {
			this.shortDescription = shortDescription;
		}
		
		@Column(name = "largeDescription", length = 120, nullable=false)
		public String getLargeDescription() {
			return this.largeDescription;
		}

		public void setLargeDescription(String largeDescription) {
			this.largeDescription = largeDescription;
		}
		
		@Column(name = "idUser", length = 5, nullable=false)
		public int getIdUser() {
			return this.idUser;
		}

		public void setIdUser(int idUser) {
			this.idUser = idUser;
		}
		
		@Column(name = "state", length = 1, nullable=false)
		public String getState() {
			return this.state;
		}

		public void setState(String state) {
			this.state = state;
		}
	

}
