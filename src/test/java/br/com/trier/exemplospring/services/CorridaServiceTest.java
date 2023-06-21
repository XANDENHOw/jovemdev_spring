package br.com.trier.exemplospring.services;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.trier.exemplospring.BaseTests;
import jakarta.transaction.Transactional;

@Transactional
public class CorridaServiceTest extends BaseTests{

	@Autowired
	CorridaService service;
}
