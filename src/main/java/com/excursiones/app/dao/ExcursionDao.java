package com.excursiones.app.dao;

import java.util.List;

import com.excursiones.app.javabean.Excursion;

public interface ExcursionDao {

	// Metodos
	public int getNextId();

	public Excursion findById(int idExcursion);

	public List<Excursion> findAll();

	public int insertOne(Excursion excursion);

	public int updateOne(Excursion excursion);

	public List<Excursion> findByActivos();

	public List<Excursion> findByCancelados();

	public List<Excursion> findByDestacados();

	public int cancelarExcursion(int idExcursion);

	public List<Excursion> findByCriteria(String origen, String destino, Double precioUnitario1, Double precioUnitario2);
}
