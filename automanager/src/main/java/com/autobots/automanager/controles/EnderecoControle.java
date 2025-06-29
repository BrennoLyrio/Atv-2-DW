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
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.AdicionadorLink;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private EnderecoRepositorio repositorio;
	
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	
	@GetMapping("/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		Endereco endereco = repositorio.findById(id).orElse(null);
		if (endereco == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(endereco);
            return new ResponseEntity<>(endereco, HttpStatus.OK);
		}
	}
	
	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(enderecos);
            return new ResponseEntity<>(enderecos, HttpStatus.OK);
        }
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
        if (endereco.getId() == null) {
            repositorio.save(endereco);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
        Endereco endereco = repositorio.findById(atualizacao.getId()).orElse(null);
        if (endereco != null) {
            endereco.setEstado(atualizacao.getEstado());
            endereco.setCidade(atualizacao.getCidade());
            endereco.setBairro(atualizacao.getBairro());
            endereco.setRua(atualizacao.getRua());
            endereco.setNumero(atualizacao.getNumero());
            endereco.setCodigoPostal(atualizacao.getCodigoPostal());
            endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
            repositorio.save(endereco);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao) {
        Endereco endereco = repositorio.findById(exclusao.getId()).orElse(null);
        if (endereco != null) {
            repositorio.delete(endereco);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
}
