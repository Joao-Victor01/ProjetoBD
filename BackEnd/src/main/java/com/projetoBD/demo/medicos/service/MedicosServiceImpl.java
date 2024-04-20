package com.projetoBD.demo.medicos.service;

import com.projetoBD.demo.medicos.MedicosEntity;
import com.projetoBD.demo.medicos.MedicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicosServiceImpl implements MedicosService{

    @Autowired
    private MedicosRepository medicosRepository;

    @Override
    public void cadastrarNovoMedico(MedicosEntity novoMedico) {

        if (novoMedico.getDataNascMedico().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data Inválida!");
        }
        if (medicosRepository.buscarMedicoPorCrm(novoMedico.getCrm()).isPresent()){
            throw new MedicoException("CRM já cadastrado!");
        }
        medicosRepository.cadastrarMedico(novoMedico);
    }

    @Override
    public void atualizarMedico(MedicosEntity medico){
        if (medico.getDataNascMedico().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento inválida!");
        }
        medicosRepository.atualizarMedico(medico);
    }

    @Override
    public void deletarMedico(String crm){
        //TODO antes de deletar, verificar se tem alguma consulta marcada com o medico. Se tiver, não permitir deletar
        medicosRepository.deletarMedico(crm);
    }

    @Override
    public List<MedicosEntity> buscarMedicosPorNome(String nomeMedico){
        return medicosRepository.buscarMedicosPorNome(nomeMedico);
    }

    @Override
    public Optional<MedicosEntity> buscarMedicoPorCrm(String crm){
        return medicosRepository.buscarMedicoPorCrm(crm);
    }

    @Override
    public List<MedicosEntity> listarTodosMedicos(){
        return medicosRepository.listarMedicos();
    }

}
