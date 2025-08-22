package fieg.modulos.tarefa;

import com.jcraft.jsch.*;
import fieg.modulos.cieloJobs.arquivo.UtilLerArquivo;
import fieg.modulos.cr5.model.CredencialCielo;
import fieg.modulos.cr5.repository.CredencialCieloRepository;
import fieg.modulos.tarefa.dto.ArquivoEntradaCieloDTO;
import fieg.modulos.tarefa.dto.ArquivoFTPDTO;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@ApplicationScoped
public class DownloadArquivosCieloJob {

    @Inject
    UtilLerArquivo utilLerArquivo;

    @Inject
    CredencialCieloRepository credencialCieloRepository;

    @ConfigProperty(name = "cr5-jobs.cielo.sfpt.host")
    String hostSfpt;

    @ConfigProperty(name = "cr5-jobs.cielo.sfpt.porta")
    Integer portaSfpt;

    @ConfigProperty(name = "cr5-jobs.cielo.job-download.ativo", defaultValue = "true")
    Boolean jobAtivado;

    private static final int SESSION_TIMEOUT = 30000;
    private static final int CHANNEL_TIMEOUT = 30000;

    public void executaJob() {
        Log.info("Iniciando download de arquivos da Cielo");
//        if (!jobAtivado) {
//            Log.info("O job esta desativado via configuracoes. Nao eh bom que esse job rode em teste pois se os arquivos" +
//                    " forem baixados em teste, devem ser redisponibilizados no site da Cielo ou nao serao conciliados.");
//            return;
//        }
        List<CredencialCielo> credenciais = credencialCieloRepository.getAll();

        if (credenciais.isEmpty()) {
            Log.info("Nenhuma credencial Cielo encontrada");
            return;
        }

        for (CredencialCielo credencialCielo : credenciais) {
            List<ArquivoEntradaCieloDTO> arquivosBaixados = leArquivosPagamentoEVendaCielo(credencialCielo.getUsuario(), credencialCielo.getSenha());
            utilLerArquivo.escreveArquivosEntradaCielo(arquivosBaixados);
        }

        Log.info("Download de arquivos da Cielo finalizado");
    }

    private List<ArquivoEntradaCieloDTO> leArquivosPagamentoEVendaCielo(String usuario, String senha) {
        Session session = null;
        ChannelSftp sftpChannel = null;

        try (InputStream knownHosts = getClass().getResourceAsStream("/cielo/known_hosts")) {
            var jsch = new JSch();
            jsch.setKnownHosts(knownHosts);

            Log.infof("Conectando no host da cielo em host %s:%d, usuario: %s", hostSfpt, portaSfpt, usuario);
            session = jsch.getSession(usuario, hostSfpt, portaSfpt);
            session.setPassword(senha);
            session.connect(SESSION_TIMEOUT);
            Log.infof("Conectado com sucesso com usuario %s", usuario);

            Log.info("Abrindo canal sftp");
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect(CHANNEL_TIMEOUT);
            Log.info("Canal sftp aberto com sucesso");

            List<ArquivoFTPDTO> todosOsArquivos = leTodosOsArquivos(sftpChannel);

            Log.infof("%d arquivos encontrados (alguns podem nao ser de venda/pagamento)", todosOsArquivos.size());

            final ChannelSftp finalSftpChannel = sftpChannel;
            return todosOsArquivos.stream()
                    .map(arquivo -> fazDownloadDoArquivo(arquivo, finalSftpChannel))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

        } catch (JSchException | SftpException | IOException e) {
            Log.error("Erro ao fazer donwload de arquivos da Cielo do usuario " + usuario, e);
            return new ArrayList<>();
        } finally {
            if (session != null) {
                session.disconnect();
            }

            if (sftpChannel != null) {
                sftpChannel.disconnect();
            }
        }
    }

    private Optional<ArquivoEntradaCieloDTO> fazDownloadDoArquivo(ArquivoFTPDTO arquivo, ChannelSftp channelSftp) {
        String nome = arquivo.lsEntry().getFilename();
        String caminhoCompleto = arquivo.caminho() + "/" + nome;
        boolean isvendaOuPagamento = nome.contains("CIELO03") || nome.contains("CIELO04");

        try {
            var conteudo = new ByteArrayOutputStream();

            Log.infof("Fazendo download do arquivo %s", nome);

            channelSftp.get(caminhoCompleto, conteudo);

            return Optional.of(new ArquivoEntradaCieloDTO(nome, conteudo.toString(), isvendaOuPagamento));
        } catch (Exception e) {
            Log.error("Erro ao fazer download do arquivo " + caminhoCompleto + ". Venda ou pagamento: " + isvendaOuPagamento, e);
            return Optional.empty();
        }
    }

    private List<ArquivoFTPDTO> leTodosOsArquivos(ChannelSftp sftpChannel) throws SftpException {
        List<ArquivoFTPDTO> todosOsArquivos = new ArrayList<>();
        Vector<ChannelSftp.LsEntry> arquivosRaiz = sftpChannel.ls("/");

        for (var arquivo : arquivosRaiz) {
            leTodosOsArquivosRecursivo(todosOsArquivos, sftpChannel, arquivo, "/");
        }

        return todosOsArquivos;
    }

    private void leTodosOsArquivosRecursivo(
            List<ArquivoFTPDTO> arquivos,
            ChannelSftp sftpChannel,
            ChannelSftp.LsEntry raiz,
            String caminhoAteAqui
    ) throws SftpException {
        if (raiz.getAttrs().isDir()) {
            String novoCaminho = caminhoAteAqui + raiz.getFilename();
            List<ChannelSftp.LsEntry> subArquivos = sftpChannel.ls(novoCaminho);

            for (var arquivo : subArquivos) {
                leTodosOsArquivosRecursivo(arquivos, sftpChannel, arquivo, novoCaminho + "/");
            }
        } else {
            arquivos.add(new ArquivoFTPDTO(caminhoAteAqui, raiz));
        }
    }
}
