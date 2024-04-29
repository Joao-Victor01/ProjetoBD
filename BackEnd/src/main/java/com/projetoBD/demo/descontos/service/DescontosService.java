package com.projetoBD.demo.descontos.service;

public interface DescontosService {
    boolean descontoFlamengo(String cpfPaciente);

    boolean descontoSouza(String cpfPaciente);

    boolean descontoOnePiece(String cpfPaciente);
}
