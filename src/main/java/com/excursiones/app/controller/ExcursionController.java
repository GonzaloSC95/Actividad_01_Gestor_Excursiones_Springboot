package com.excursiones.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excursiones.app.dao.ExcursionDao;
import com.excursiones.app.javabean.Excursion;

@Controller
@RequestMapping("/excursion")
public class ExcursionController {

	@Autowired
	private ExcursionDao edao;

	/** Ver en detalle la excursión seleccionada */
	@GetMapping("/detalle/{id}")
	public String detalleExcursion(Model model, @PathVariable int id) {
		Excursion excursion = edao.findById(id);
		if (excursion != null) {
			model.addAttribute("excursion", excursion);
			return "detalle_excursion";
		} else {
			return "forward:/";
		}
	}

	/** Modificar la excursión cambiando el estado */
	@GetMapping("/cancelar/{id}")
	public String cancelarExcursion(RedirectAttributes ratt, @PathVariable int id) {
		int result = edao.cancelarExcursion(id);
		if (result == 1) {
			ratt.addFlashAttribute("mensajeOk", "Excursión cancelada exitosamente");
		} else {
			ratt.addFlashAttribute("mensajeKo", "Error al cancelar la excursión");
		}
		return "redirect:/";
	}

	/** Dar de alta una excursion */
	@GetMapping("/alta")
	public String navigateAltaExcursion(Model model) {
		Excursion excursion = new Excursion();
		excursion.setIdExcursion(edao.getNextId());
		model.addAttribute("excursion", excursion);
		return "alta_excursion";
	}

	/** Dar de alta una excursion */
	@PostMapping("/alta")
	public String altaExcursion(Excursion excursion, RedirectAttributes ratt) {
		int result = edao.insertOne(excursion);
		if (result == 1) {
			ratt.addFlashAttribute("mensajeOk", "Excursión creada exitosamente");
		} else {
			ratt.addFlashAttribute("mensajeKo", "Error al crear la excursión");
		}
		return "redirect:/excursion/alta";
	}

	/** Modificar la excursión cambiando el estado */
	@GetMapping("/modificar/{id}")
	public String navigateModificarExcursion(Model model, @PathVariable int id) {
		Excursion excursion = edao.findById(id);
		if (excursion != null) {
			model.addAttribute("excursion", excursion);
			return "modificar_excursion";
		} else {
			model.addAttribute("mensajeKo", "Error al modificar la excursión, ya no se encentra en la lista");
			return "redirect:/";
		}
	}

	/** Modificar la excursión cambiando el estado */
	@PostMapping("/modificar")
	public String modificarExcursion(Excursion excursion, RedirectAttributes ratt) {
		try {
			int result = edao.updateOne(excursion);
			if (result == 1) {
				ratt.addFlashAttribute("mensajeOk", "Excursión modificada exitosamente");
			} else {
				ratt.addFlashAttribute("mensajeKo", "Error al modificada la excursión");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ratt.addFlashAttribute("mensajeKo", "Error al modificada la excursión");
		}
		return "redirect:/excursion/modificar/" + excursion.getIdExcursion();
	}

	/** Este método sirve para que Spring sepa cómo convertir los datos del form */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateformat, false));
	}

}
