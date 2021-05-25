package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.domain.request.ContatoRequest;
import br.com.gerenciamento.estoque.domain.request.FornecedoresRequest;
import br.com.gerenciamento.estoque.domain.response.ContatoResponse;
import br.com.gerenciamento.estoque.services.ContatoService;
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
@RequestMapping(path = "/contatos")
public class ContatoController {


    @Autowired
    private ContatoService contatoService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContatoResponse localizarContato(@PathVariable Long id) throws NotFoundException {

        return contatoService.find(id);
    }

    @GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContatoResponse> localizarContatos() throws NotFoundException {

        return contatoService.findAll();
    }

    @PostMapping(path = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarFornecedor(@RequestBody ContatoRequest contatoRequest) throws NotFoundException {

        contatoService.cadastrar(contatoRequest);
    }

    @PostMapping(path = "/deletar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deletarFornecedores(@RequestParam Long id, Long usuario) throws Exception {
        contatoService.deletar(id, usuario);
    }

    @PostMapping(path = "/alterar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void alterarProduto(@RequestBody ContatoRequest contatoRequest,
                               @RequestParam Long usuario) throws NotFoundException {
        contatoService.alterarProduto(contatoRequest, usuario);
    }
}
