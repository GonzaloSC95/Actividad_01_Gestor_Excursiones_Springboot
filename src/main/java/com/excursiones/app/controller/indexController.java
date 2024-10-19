package com.excursiones.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excursiones.app.dao.ExcursionDao;
import com.excursiones.app.javabean.Excursion;

@Controller
public class indexController {

	@Autowired
	private ExcursionDao edao;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("excursiones", edao.findAll());
		return "index";
	}

	@GetMapping("/destacados")
	public String indexDestacados(Model model) {
		model.addAttribute("excursiones", edao.findByDestacados());
		return "filter";
	}

	@GetMapping("/creados")
	public String indexCreados(Model model) {
		model.addAttribute("excursiones", edao.findByActivos());
		return "filter";
	}

	@GetMapping("/terminados")
	public String indexTerminados(Model model) {
		model.addAttribute("excursiones", edao.findByCancelados());
		return "filter";
	}

	@GetMapping("/busqueda")
	public String buscarExcursiones(@RequestParam(required = false) String origen,
			@RequestParam(required = false) String destino,
			@RequestParam(required = false) Double precioUnitario1,
			@RequestParam(required = false) Double precioUnitario2,
			Model model) {

		origen = origen == null ? "" : origen;
		destino = destino == null ? "" : destino;
		List<Excursion> excursiones = edao.findByCriteria(origen, destino,
				precioUnitario1, precioUnitario2);
		if (!excursiones.isEmpty()) {
			model.addAttribute("excursiones", excursiones);
		}
		return "busqueda";
	}
}
