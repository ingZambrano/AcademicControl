package com.avior.academic.controller;

import com.avior.academic.service.PrivacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jairo on 18/08/16.
 */
@Controller
public class GeneralesController {

    @Autowired
    @Qualifier("PrivacidadService")
    PrivacidadService avisoPrivacidad;



    @RequestMapping("/privacidad")
    public String getAvisoPrivacidad(Model model){
        model.addAttribute("aviso", avisoPrivacidad.getAvisoPrivacidad());
        return "privacidad";
    }

    @RequestMapping("/tos")
    public String getTerminosServicio(Model model){
        model.addAttribute("aviso", avisoPrivacidad.getTerminosServicio());
        return "privacidad";
    }
}
