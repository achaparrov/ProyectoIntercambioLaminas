package com.unbosque.prg2.edu.co.dao;

import java.util.List;

import com.unbosque.prg2.edu.co.entity.News;


public interface NewsDAO {
	
	public void save (News news);
	public News getId(int id);
    public List<News>list();
    public void remove(News news);
    public void update(News news);

}
