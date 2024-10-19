package com.excursiones.app.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.excursiones.app.javabean.Excursion;

@Repository
public class ExcursionDaoImplList implements ExcursionDao {

	// Atributos
	private List<Excursion> lista;

	// Constructor
	public ExcursionDaoImplList() {
		this.lista = new ArrayList<Excursion>();
		cargarDatos();
	}

	// Metodos
	/* Método para inicializar la lista de excursiones */
	private void cargarDatos() {
		Calendar calendar = Calendar.getInstance();

		// Excursion 1:
		lista.add(new Excursion("Excursion por la costa", "Barcelona", "Costa Brava", calendar.getTime(), 8,
				"CREADA", "S", 30, 10, 50.0, ""));

		// Excursion 2:
		calendar.add(Calendar.DAY_OF_YEAR, 5);
		lista.add(new Excursion("Ruta cultural", "Madrid", "Toledo", calendar.getTime(), 5,
				"CREADA", "N", 20, 5, 35.0, ""));

		// Excursion 3:
		calendar.add(Calendar.DAY_OF_YEAR, 10);
		lista.add(new Excursion("Aventura en la montaña", "Zaragoza", "Pirineos", calendar.getTime(), 7,
				"CANCELADA", "N", 15, 6, 80.0, ""));

		// Excursion 4:
		calendar.add(Calendar.DAY_OF_YEAR, 3);
		lista.add(new Excursion("Tour por el vino", "Sevilla", "Jerez", calendar.getTime(), 6,
				"CREADA", "S", 25, 8, 45.0, ""));

		// Excursion 5:
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		lista.add(new Excursion("Nado con delfines", "Cancún", "Isla Mujeres", calendar.getTime(), 4,
				"CREADA", "S", 12, 4, 120.0, ""));

		// Excursion 6:
		calendar.add(Calendar.DAY_OF_YEAR, 14);
		lista.add(new Excursion("Safari fotográfico", "Nairobi", "Serengeti", calendar.getTime(), 10,
				"CREADA", "N", 18, 8, 150.0, ""));

		// Excursion 7:
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		lista.add(new Excursion("Visita a museos", "París", "Louvre", calendar.getTime(), 3,
				"CREADA", "N", 20, 5, 60.0, ""));

		// Excursion 8:
		calendar.add(Calendar.DAY_OF_YEAR, 30);
		lista.add(new Excursion("Crucero por las islas griegas", "Atenas", "Santorini", calendar.getTime(), 14,
				"CREADA", "S", 100, 50, 500.0, ""));
	}

	// Implementación de los métodos de la interfaz ExcursionDao
	@Override
	public int getNextId() {
		return (lista.size()+1);
	}

	@Override
	public Excursion findById(int idExcursion) {
		return lista.stream().filter(e -> e.getIdExcursion() == idExcursion).findFirst().orElse(null);
	}

	@Override
	public List<Excursion> findAll() {
		return lista;
	}

	@Override
	public int insertOne(Excursion excursion) {
		return lista.add(excursion) ? 1 : 0;
	}

	@Override
	public int updateOne(Excursion excursion) {
		return lista.set(lista.indexOf(excursion), excursion) != null ? 1 : 0;
	}

	@Override
	public List<Excursion> findByActivos() {
		List<Excursion> listaAux = new ArrayList<>();
		lista.stream()
				.filter(e -> e.getEstado().equals("CREADA"))
				.forEach(e -> listaAux.add(e));
		return listaAux;
	}

	@Override
	public List<Excursion> findByCancelados() {
		List<Excursion> listaAux = new ArrayList<>();
		for (Excursion e : lista) {
			if (e.getEstado().equals("CANCELADA")
					|| e.getEstado().equals("TERMINADA")) {
				listaAux.add(e);
			}
		}
		return listaAux;
	}

	@Override
	public List<Excursion> findByDestacados() {
		List<Excursion> listaAux = new ArrayList<>();
		lista.stream()
				.filter(e -> e.getDestacado().equals("S"))
				.forEach(e -> listaAux.add(e));
		return listaAux;
	}

	/* Método para cancelar una excursión de la lista */
	@Override
	public int cancelarExcursion(int idExcursion) {
		Excursion excursion = findById(idExcursion);
		if (excursion != null) {
			excursion.setEstado("CANCELADA");
			return updateOne(excursion);
		}
		return 0;
	}

	@Override
	public List<Excursion> findByCriteria(String origen, String destino, Double precioUnitario1,
			Double precioUnitario2) {

		// Lista auxiliar para almacenar las excursiones
		List<Excursion> listaAux = new ArrayList<>();

		if ((!origen.isEmpty()) || (!destino.isEmpty()) || (precioUnitario1 != null) || (precioUnitario2 != null)) {

			lista.stream()
					.filter(e -> (origen.isEmpty()
							|| e.getOrigen().toLowerCase().contains(origen.toLowerCase())))
					.filter(e -> (destino.isEmpty()
							|| e.getDestino().toLowerCase().contains(destino.toLowerCase())))
					.filter(e -> (precioUnitario1 == null || e.getPrecioUnitario() >= precioUnitario1))
					.filter(e -> (precioUnitario2 == null || e.getPrecioUnitario() <= precioUnitario2))
					.filter(e -> !listaAux.contains(e))
					.forEach(e -> listaAux.add(e));
		}

		return listaAux;
	}

}
