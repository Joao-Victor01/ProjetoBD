package com.projetoBD.demo.medicos.service;

import com.projetoBD.demo.medicos.MedicosEntity;

import java.util.List;
import java.util.Optional;

public interface MedicosService {
    void cadastrarNovoMedico(MedicosEntity novoMedico);

    void atualizarMedico(MedicosEntity medico);

    void deletarMedico(String crm);

    List<MedicosEntity> buscarMedicosPorNome(String nomeMedico);

    Optional<MedicosEntity> buscarMedicoPorCrm(String crm);

    List<MedicosEntity> listarTodosMedicos();
}
