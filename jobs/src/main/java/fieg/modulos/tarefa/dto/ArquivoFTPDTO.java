package fieg.modulos.tarefa.dto;

import com.jcraft.jsch.ChannelSftp;

public record ArquivoFTPDTO(String caminho, ChannelSftp.LsEntry lsEntry) {
}
