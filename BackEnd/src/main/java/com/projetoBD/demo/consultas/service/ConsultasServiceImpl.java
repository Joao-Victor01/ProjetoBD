package com.projetoBD.demo.consultas.service;

import com.projetoBD.demo.consultas.ConsultasEntity;
import com.projetoBD.demo.consultas.ConsultasRepository;
import com.projetoBD.demo.medicos.MedicosEntity;
import com.projetoBD.demo.medicos.service.MedicosService;
import com.projetoBD.demo.pacientes.PacientesEntity;
import com.projetoBD.demo.pacientes.service.PacientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultasServiceImpl implements ConsultasService {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private PacientesService pacientesService;

    @Autowired
    private MedicosService medicosService;

    @Override
    public void marcarConsulta(ConsultasEntity consulta) {
        // Validar se o DTO é nulo ou se os campos obrigatórios estão vazios
        if (consulta == null || consulta.getPaciente().getCpfPaciente() == null || consulta.getMedico().getCrm() == null) {
            throw new IllegalArgumentException("Dados da consulta incompletos.");
        }

        // Buscar o paciente pelo CPF
        Optional<PacientesEntity> optionalPaciente = pacientesService.buscaPacientePorCpf(consulta.getPaciente().getCpfPaciente());
        PacientesEntity paciente = optionalPaciente.orElseThrow(() -> new RuntimeException("Paciente não encontrado com o CPF fornecido."));

        // Buscar o médico pelo CRM
        Optional<MedicosEntity> optionalMedico = medicosService.buscarMedicoPorCrm(consulta.getMedico().getCrm());
        MedicosEntity medico = optionalMedico.orElseThrow(() -> new RuntimeException("Médico não encontrado com o CRM fornecido."));

        if (verificarDataConsulta(consulta) && verificarDisponibilidadeMedico(consulta)) {
            consultasRepository.marcarConsulta(consulta);
        } else {
            throw new RuntimeException("Não foi possível marcar a consulta. Verifique a data ou a disponibilidade do médico.");
        }

    }

    @Override
    public Optional<ConsultasEntity> buscarConsultaPorId(Integer id) {
        return consultasRepository.buscarConsultaPorId(id);
    }

    @Override
    public List<ConsultasEntity> listarConsultas() {
        return consultasRepository.listarTodasConsultas();
    }

    @Override
    public void deletarConsulta(Integer id) {
        consultasRepository.cancelarConsulta(id);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorNomePaciente(String nomePaciente) {
        return consultasRepository.listarConsultasPorNomePaciente(nomePaciente);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorNomeMedico(String nomeMedico) {
        return consultasRepository.listarConsultasPorNomeMedico(nomeMedico);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorCrmMedico(String crmMedico) {
        return consultasRepository.listarConsultasPorCrmMedico(crmMedico);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorCpfPaciente(String cpfPaciente) {
        return consultasRepository.listarConsultasPorCpfPaciente(cpfPaciente);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorData(LocalDateTime inicioDoDia, LocalDateTime finalDoDia) {
        List<ConsultasEntity> consultas = consultasRepository.listarConsultasPorData(inicioDoDia, finalDoDia);
        if (consultas.isEmpty()){
            return Collections.emptyList();
        }
        return consultas;
    }

    @Override
    public List<LocalTime> buscarHorariosIndisponiveisPorDataEMedico(LocalDateTime inicioDoDia, LocalDateTime finalDoDia, String crm) {
        List<ConsultasEntity> consultasDoMedicoNoDia = consultasRepository.listarConsultasMedicosData(inicioDoDia, finalDoDia, crm);

        if (consultasDoMedicoNoDia.isEmpty()) {
            // Se não houver consultas marcadas, todos os horários do médico estarão disponíveis
            return Collections.emptyList();
        }

        List<LocalTime> horariosIndisponiveis = new ArrayList<>();

        for (ConsultasEntity consultaExistente : consultasDoMedicoNoDia) {
            LocalDateTime dataConsultaExistente = consultaExistente.getDataConsulta();
            horariosIndisponiveis.add(dataConsultaExistente.toLocalTime());
        }

        return horariosIndisponiveis;
    }

    @Override
    public List<ConsultasEntity> consultasPacienteDia(LocalDateTime inicioDoDia,
                                                      LocalDateTime finalDoDia, String cpf){

        List<ConsultasEntity> consultasPaciente =
                consultasRepository.listarConsultasPacienteDia(inicioDoDia, finalDoDia, cpf);

        if(consultasPaciente.isEmpty()){
            return Collections.emptyList();
        }

        return consultasPaciente;
    }

    @Override
    public List<ConsultasEntity> consultasMedicoDia(LocalDateTime inicioDoDia,
                                                    LocalDateTime finalDoDia, String crm){

        List<ConsultasEntity> consultasMedico =
                consultasRepository.listarConsultasMedicosData(inicioDoDia, finalDoDia, crm);

        if(consultasMedico.isEmpty()){
            return Collections.emptyList();
        }

        return consultasMedico;
    }

    private boolean verificarDataConsulta(ConsultasEntity consulta) {
        LocalDateTime dataAtual = LocalDateTime.now();
        LocalDateTime dataConsulta = consulta.getDataConsulta();

        return dataConsulta.isAfter(dataAtual);
    }


    private boolean verificarDisponibilidadeMedico(ConsultasEntity novaConsulta) {
        List<ConsultasEntity> consultasDoMedico = consultasRepository.listarConsultasPorCrmMedico(novaConsulta.getMedico().getCrm());

        for (ConsultasEntity consultaExistente : consultasDoMedico) {
            LocalDateTime dataConsultaExistente = consultaExistente.getDataConsulta();
            LocalDateTime dataNovaConsulta = novaConsulta.getDataConsulta();

            // Se as consultas sao iguais (mesmo paciente, medico e data), permite que mantenha a hora de antes
            //para atualizacao de consulta
            if (consultaExistente.equals(novaConsulta)) {
                continue;
            }


            if (dataConsultaExistente.toLocalDate().isEqual(dataNovaConsulta.toLocalDate())
                    && dataConsultaExistente.toLocalTime().equals(dataNovaConsulta.toLocalTime())) {
                return false;
            }


            if ((dataConsultaExistente.isEqual(dataNovaConsulta)) &&(Math.abs(dataConsultaExistente.getHour() - dataNovaConsulta.getHour()) < 1)) {
                return false;
            }
        }

        return true;
    }

}