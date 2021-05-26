package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.domain.request.CategoriaRequest;
import br.com.gerenciamento.estoque.domain.response.CategoriaResponse;
import br.com.gerenciamento.estoque.services.CategoriaService;
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
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping(path = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarCategoria(@RequestBody CategoriaRequest categoria) {
        service.salvar(categoria);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoriaResponse find(
            @PathVariable Long id) throws NotFoundException {
        return service.find(id);
    }

    @GetMapping(path = "/todas", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoriaResponse> findAll(@RequestParam (required = false) boolean isDesativado) {
        return service.findAll(isDesativado);
    }

    @PostMapping(path = "/deletar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deletarCategoria(@RequestParam Long idCategoria,
                                 @RequestParam Long usuario) throws Exception {
        service.deletar(idCategoria, usuario);
    }

    @PostMapping(path = "/alterar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void alterarCategoria(@RequestBody CategoriaRequest contatoRequest,
                               @RequestParam Long usuario) throws NotFoundException {
        service.alterarContato(contatoRequest, usuario);
    }
}
