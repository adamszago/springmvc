package com.zago.cobranca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.zago.cobranca.model.StatusTitulo;
import com.zago.cobranca.model.Titulo;
import com.zago.cobranca.repository.Titulos;
import com.zago.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
		
	}

	public void excluir(Long codigo) {
		titulos.deleteById(codigo);
		
	}

	public String receber(Long codigo) {
		Optional<Titulo> optionalTitulo = titulos.findById(codigo);
		Titulo titulo = optionalTitulo.get();
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}
}
