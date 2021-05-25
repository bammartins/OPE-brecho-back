package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.domain.dto.CidadeDto;
import br.com.gerenciamento.estoque.domain.dto.EstadoDto;
import br.com.gerenciamento.estoque.entity.Cidade;
import br.com.gerenciamento.estoque.entity.Estado;
import br.com.gerenciamento.estoque.services.CidadeService;
import br.com.gerenciamento.estoque.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EstadoDto>> findAll() {
        List<Estado> objList = service.findAll();
        List<EstadoDto> estadoDTOList = objList.stream().map(EstadoDto::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadoDTOList);
    }

    @GetMapping(value = "/{estado_id}/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CidadeDto>> findCidades(

            @PathVariable Integer estado_id) {
        List<Cidade> list = cidadeService.findByEstado(estado_id);
        List<CidadeDto> cidadeDTOList = list.stream().map(CidadeDto::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadeDTOList);
    }
}
