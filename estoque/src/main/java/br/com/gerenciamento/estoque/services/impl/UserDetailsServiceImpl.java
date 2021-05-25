package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AcessoRepository acessoRepository;

    @Override
    public UserDetails loadUserByUsername(String nomeDoLogin) {
        var acesso = acessoRepository.findByLogin(nomeDoLogin);

        if (acesso == null) {
            throw new UsernameNotFoundException("login invalido " + nomeDoLogin);
        }
        return new UserSS(acesso.getId(), acesso.getLogin(), acesso.getSenha(), acesso.getPerfis());
    }

}
