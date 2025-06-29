package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	
	@Autowired
    private AdicionadorLinkTelefone adicionadorLink;
	
	@GetMapping("/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
		Telefone telefone = repositorio.findById(id).orElse(null);
		if (telefone == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			adicionadorLink.adicionarLink(telefone);
			return new ResponseEntity<> (telefone, HttpStatus.OK);
		}
	}
	
	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> obterTelefones() {
		List<Telefone> telefones = repositorio.findAll();
		if (telefones.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			adicionadorLink.adicionarLink(telefones);
			return new ResponseEntity<>(telefones, HttpStatus.OK);
		}
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
		if (telefone.getId() == null) {
			repositorio.save(telefone);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacao) {
		Telefone telefone = repositorio.findById(atualizacao.getId()).orElse(null);
		if (telefone != null) {
			telefone.setDdd(atualizacao.getDdd());
			telefone.setNumero(atualizacao.getNumero());
			repositorio.save(telefone);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirTelefone(@RequestBody Telefone exclusao) {
		Telefone telefone = repositorio.findById(exclusao.getId()).orElse(null);
		if (telefone != null) {
			repositorio.delete(telefone);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
