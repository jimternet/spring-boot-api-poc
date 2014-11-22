package com.noofinc.inventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

	
	@RequestMapping("/") 
	public String index() {
	      return "redirect:/apidoc.html";
	}
}
