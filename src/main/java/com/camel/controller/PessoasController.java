package com.camel.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.domain.Pessoa;
import com.camel.domain.PessoaXML;

@RestController
@RequestMapping("/api")
public class PessoasController {
	
	@GetMapping(value = "/all")
	public List<String> getAllPessoas() {
		return Arrays.asList("Marília", "Carlos", "Bianca", "José");
	}
	
	@GetMapping(value = "/obj")
	public List<Pessoa> getAllPessoasObj() {
		return Arrays.asList(new Pessoa(1, "Marília"),new Pessoa(1, "Carlos"), new Pessoa(1,"Bianca"), new Pessoa(1,"José"));
	}
	
	@GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public PessoaXML getPessoaXML() {
		return new PessoaXML(1,"TesteXML");
	}

}
