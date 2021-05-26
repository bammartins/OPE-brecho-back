package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.services.RelatoriosService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {

    @Autowired
    private RelatoriosService relatoriosService;

    @PostMapping(path = "/criar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(
            @RequestParam Long produto,
            @RequestParam Long fornecedor) throws NotFoundException {
        relatoriosService.criar(produto, fornecedor);
    }

    @PostMapping(path = "/acessos/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void relatorioAcessos(
            @PathVariable String tipo,
            @RequestParam String caminho) throws NotFoundException {
        relatoriosService.relatorioUsuarios(tipo);
    }


}
