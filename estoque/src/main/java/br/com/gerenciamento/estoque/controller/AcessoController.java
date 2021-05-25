package br.com.gerenciamento.estoque.controller;

import br.com.gerenciamento.estoque.domain.request.AcessoRequest;
import br.com.gerenciamento.estoque.domain.response.AcessoResponse;
import br.com.gerenciamento.estoque.services.AcessoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/acesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;


    @PostMapping(path = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarMovimentacao(@RequestBody AcessoRequest request) {
        acessoService.salvarAcesso(request);
    }

    @PostMapping(path = "/deletar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deletarAcesso(@RequestParam Long idAcesso, Long usuario) throws Exception {
        acessoService.deletar(idAcesso, usuario);
    }

    @GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AcessoResponse> findAll(
            @RequestParam Long idUsuario,
            @RequestParam (required = false) boolean isDesativado) throws Exception {
        return acessoService.findAll(idUsuario, isDesativado);
    }
}
