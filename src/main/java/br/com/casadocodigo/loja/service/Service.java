package br.com.casadocodigo.loja.service;

import java.io.Serializable;
import java.util.List;

public interface Service<T, ID extends Serializable> {

	T findOne(ID id);
	
	List<T> findAll();
	
	void save(T data); 
	
	void remove(ID id); 
	
}
