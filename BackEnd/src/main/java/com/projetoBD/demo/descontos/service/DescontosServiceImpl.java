package com.projetoBD.demo.descontos.service;

import com.projetoBD.demo.descontos.DescontosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescontosServiceImpl implements DescontosService {

    @Autowired
    private DescontosRepository descontosRepository;
    @Override
    public boolean descontoFlamengo(String cpfPaciente){
        return descontosRepository.descontoFlamengo(cpfPaciente);
    }

    @Override
    public boolean descontoSouza(String cpfPaciente) {
        return descontosRepository.descontoSouza(cpfPaciente);
    }

    @Override
    public boolean descontoOnePiece(String cpfPaciente) {
        return descontosRepository.descontoOnePiece(cpfPaciente);
    }

}
