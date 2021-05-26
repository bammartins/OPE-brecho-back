package br.com.gerenciamento.estoque.controller;


import br.com.gerenciamento.estoque.domain.request.ProdutoRequest;
import br.com.gerenciamento.estoque.domain.response.ProdutoResponse;
import br.com.gerenciamento.estoque.services.ProdutoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoResponse find(
            @PathVariable Long id) throws NotFoundException {

        return service.find(id);
    }

    @GetMapping(path = "/todas", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProdutoResponse> findAll(
            @RequestParam(required = false) boolean isDesativado
    ) throws NotFoundException {

        return service.findAll(isDesativado);
    }

    @PostMapping(path = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarProduto(@RequestBody ProdutoRequest produto,
                                 @RequestParam Long acesso) throws NotFoundException {
        service.salvar(produto, acesso);
    }

    @PostMapping(path = "/deletar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deletarProduto(@RequestParam Long id,
                               @RequestParam Long usuario) throws NotFoundException {
        service.deletar(id, usuario);
    }

    @PostMapping(path = "/alterar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void alterarProduto(@RequestBody ProdutoRequest produto,
                               @RequestParam Long usuario,
                               @RequestParam Long produtoId
    ) throws NotFoundException {
        service.alterarProduto(produto, usuario, produtoId);
    }
}
