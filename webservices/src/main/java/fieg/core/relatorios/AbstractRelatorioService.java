package fieg.core.relatorios;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractRelatorioService {

    private final ClassLoader loader = Thread.currentThread().getContextClassLoader();

    protected byte[] gerarPdf(String caminhoArquivo, Map<String, Object> parametros, List<?> dadosList) {
        try {
            InputStream jrxmlStream = Objects.requireNonNull(loader.getResourceAsStream(caminhoArquivo));
            JasperReport arquivoRelatorio = JasperCompileManager.compileReport(jrxmlStream);
            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(dadosList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoRelatorio, parametros, datasource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o relatório PDF.", e);
        }
    }

    protected byte[] gerarXls(String caminhoArquivo, Map<String, Object> parametros, List<?> dadosList) {
        try (ByteArrayOutputStream relatorioXls = new ByteArrayOutputStream()) {
            InputStream arquivoRelatorio = Objects.requireNonNull(loader.getResourceAsStream(caminhoArquivo));
            JasperReport arquivoCompilado = JasperCompileManager.compileReport(arquivoRelatorio);
            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(dadosList);

            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoCompilado, parametros, datasource);

            JRXlsxExporter exporter = new JRXlsxExporter();
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);

            exporter.setConfiguration(configuration);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(relatorioXls));
            exporter.exportReport();

            return relatorioXls.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o relatório XLS.", e);
        }
    }

}