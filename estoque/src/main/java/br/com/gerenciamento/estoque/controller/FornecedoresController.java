package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.domain.request.FornecedoresRequest;
import br.com.gerenciamento.estoque.domain.response.FornecedoresResponse;
import br.com.gerenciamento.estoque.services.FornecedoresService;
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
@RequestMapping("/fornecedores")
public class FornecedoresController {

    @Autowired
    private FornecedoresService fornecedoresService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FornecedoresResponse localizarFornecedor(@PathVariable Long id) throws NotFoundException {

        return fornecedoresService.find(id);
    }

    @GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FornecedoresResponse> localizarFornecedor() throws NotFoundException {

        return fornecedoresService.findAll();
    }

    @PostMapping(path = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarFornecedor(@RequestBody FornecedoresRequest fornecedoresRequest) throws NotFoundException {

        fornecedoresService.cadastrar(fornecedoresRequest);
    }

    @PostMapping(path = "/deletar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deletarFornecedores(@RequestParam Long id, Long usuario) throws NotFoundException {
        fornecedoresService.deletar(id, usuario);
    }

    @PostMapping(path = "/alterar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void alterarProduto(@RequestBody FornecedoresRequest produto,
                               @RequestParam Long usuario) throws NotFoundException {
        fornecedoresService.alterarProduto(produto, usuario);
    }

}
