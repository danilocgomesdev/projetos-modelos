package fieg.externos.cadin.parcelacadin.repository;

import fieg.modulos.cobrancacliente.model.CobrancaCliente;

import java.util.List;

public interface ParcelaCadinRepository {

    List<Integer> getCobrancasDeOrigem(CobrancaCliente cobrancaCliente);
}
