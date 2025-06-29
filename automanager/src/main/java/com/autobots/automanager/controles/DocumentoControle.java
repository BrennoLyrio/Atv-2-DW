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

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.AdicionadorLink;
import com.autobots.automanager.modelo.AdicionadorLinkDocumento;
import com.autobots.automanager.repositorios.DocumentoRepositorio;;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private DocumentoRepositorio repositorio;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;
	
	@GetMapping("/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
		Documento documento = repositorio.findById(id).orElse(null);
		if (documento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(documento);
            return new ResponseEntity<>(documento, HttpStatus.OK);
        }
	}
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(documentos);
            return new ResponseEntity<>(documentos, HttpStatus.OK);
        }
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento) {
		if (documento.getId() == null) {
			repositorio.save(documento);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
		
		Documento documento = repositorio.findById(atualizacao.getId()).orElse(null);
		if (documento != null) {
			documento.setTipo(atualizacao.getTipo());
			documento.setNumero(atualizacao.getNumero());
			repositorio.save(documento);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirDocumento(@RequestBody Documento exclusao) {
		Documento documento = repositorio.findById(exclusao.getId()).orElse(null);
		if (documento != null) {
			repositorio.delete(documento);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
