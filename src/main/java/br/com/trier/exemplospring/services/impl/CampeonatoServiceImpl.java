package br.com.trier.exemplospring.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.exemplospring.domain.Campeonato;
import br.com.trier.exemplospring.repositories.CampeonatoRepository;
import br.com.trier.exemplospring.services.CampeonatoService;
import br.com.trier.exemplospring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.exemplospring.services.exceptions.ViolacaoIntegridade;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{

	@Autowired
	private CampeonatoRepository repository;
	
	private void verificaCampeonato(Campeonato obj) {
		if(obj == null) {
			throw new ViolacaoIntegridade("Campeonato nulo");
		}
		if(obj.getDescricao() == null || obj.getDescricao().equals("")) {
			throw new ViolacaoIntegridade("Descrição obrigatória");
		}
		verificaAno(obj);
	}

	private void verificaAno(Campeonato campeonato) {
		int anoAtual = LocalDate.now().getYear();
		int anoMaximo = anoAtual + 1;
		if(campeonato.getYear() == null) {
			throw new ViolacaoIntegridade("Ano inválido (null)");
		}
		if (campeonato.getYear() <= 1990 || campeonato.getYear() >= anoMaximo) {
			throw new ViolacaoIntegridade("Ano %s inválido".formatted(campeonato.getYear()));
		}
	}
	
	@Override
	public Campeonato salvar(Campeonato obj) {
		verificaCampeonato(obj);
		return repository.save(obj);
	}

	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado!".formatted(id)));
	}

	@Override
	public Campeonato update(Campeonato obj) {
		Campeonato campeonato = findById(obj.getId());
		verificaCampeonato(obj);
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Campeonato campeonato = findById(id);
		repository.delete(campeonato);
	}

	@Override
	public List<Campeonato> findByYearBetween(Integer start, Integer end) {
		return repository.findByYearBetween(start, end);
	}

	@Override
	public List<Campeonato> findByYear(Integer year) {
		return repository.findByYear(year);
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao) {
		return repository.findByDescricaoContainsIgnoreCase(descricao);
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCaseAndYearEquals(String descricao, Integer year) {
		return repository.findByDescricaoContainsIgnoreCaseAndYearEquals(descricao, year);
	}

}
