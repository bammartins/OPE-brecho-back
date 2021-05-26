package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.domain.request.FornecedorRequest;
import br.com.gerenciamento.estoque.domain.response.FornecedorResponse;
import br.com.gerenciamento.estoque.services.FornecedorService;
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
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FornecedorResponse localizarFornecedor(@PathVariable Long id) throws NotFoundException {

        return fornecedorService.find(id);
    }

    @GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FornecedorResponse> localizarTodosFornecedor(
            @RequestParam (required = false) boolean isDesativado
    ) throws NotFoundException {

        return fornecedorService.findAll(isDesativado);
    }

    @PostMapping(path = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarFornecedor(@RequestBody FornecedorRequest fornecedorRequest) throws NotFoundException {

        fornecedorService.cadastrar(fornecedorRequest);
    }

    @PostMapping(path = "/deletar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deletarFornecedor(@RequestParam Long id, Long usuario) throws NotFoundException {
        fornecedorService.deletar(id, usuario);
    }

    @PostMapping(path = "/alterar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void alterarFornecedor(@RequestBody FornecedorRequest produto,
                                  @RequestParam Long usuario,
                                  @RequestParam Long fornecedorId) throws NotFoundException {
        fornecedorService.alterar(produto, usuario, fornecedorId);
    }

}
