package br.com.gerenciamento.estoque.services.impl;

import br.com.gerenciamento.estoque.entity.Acesso;
import br.com.gerenciamento.estoque.repository.AcessoRepository;
import br.com.gerenciamento.estoque.repository.FornecedoresRepository;
import br.com.gerenciamento.estoque.repository.MovimentacaoProdutoRepository;
import br.com.gerenciamento.estoque.repository.ProdutoRepository;
import br.com.gerenciamento.estoque.services.RelatoriosService;
import javassist.NotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class RelatoriosServiceImpl implements RelatoriosService {


    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    @Autowired
    private MovimentacaoProdutoRepository movimentacaoProdutoRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Override
    public void criar(Long produto, Long fornecedor) throws NotFoundException {
    }

    @Override
    public void relatorioUsuarios(String tipo) throws NotFoundException {

        List<Acesso> acessos = acessoRepository.findAll();
        testeJasper(acessos, tipo);

    }

    public void testeJasper(List<Acesso> acesso, String tipo) {

        try {

            var file = ResourceUtils.getFile("classpath:RelatorioUsuario.jrxml");
            var jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JasperPrint relatorioPreenchido =
                    JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(acesso));

            if (tipo.equalsIgnoreCase("html")) {
                JasperExportManager.exportReportToHtmlFile(relatorioPreenchido, "C:\\Users\\Brally\\Desktop\\Report.html");
            } else if (tipo.equalsIgnoreCase("pdf")) {
                JasperExportManager.exportReportToPdfFile(relatorioPreenchido, "C:\\Users\\Brally\\Desktop\\Report.pdf");
            }
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
