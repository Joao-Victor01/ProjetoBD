package com.projetoBD.demo.consultas.service;

import com.projetoBD.demo.consultas.ConsultasEntity;
import com.projetoBD.demo.consultas.ConsultasRepository;
import com.projetoBD.demo.descontos.service.DescontosService;
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

    //TODO filtro que recebe o nome do paciente e o crm. Retorna as consultas

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private PacientesService pacientesService;

    @Autowired
    private MedicosService medicosService;

    @Autowired
    private DescontosService descontosService;

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
            Integer valorConsulta = aplicaDesconto(consulta);
            consulta.setValorConsulta(valorConsulta);
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
        List<ConsultasEntity> consultas = consultasRepository.listarTodasConsultas();
        if(consultas.isEmpty()){
            return Collections.emptyList();
        }

        return consultas;
    }

    @Override
    public void deletarConsulta(Integer id) {
        consultasRepository.cancelarConsulta(id);
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorNomePaciente(String nomePaciente) {
        List<ConsultasEntity> consultas = consultasRepository.listarConsultasPorNomePaciente(nomePaciente);
        if(consultas.isEmpty()){
            return Collections.emptyList();
        }
        return consultas;
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorNomeMedico(String nomeMedico) {
        List<ConsultasEntity> consultas = consultasRepository.listarConsultasPorNomeMedico(nomeMedico);
        if(consultas.isEmpty()){
            return Collections.emptyList();
        }
        return consultas;
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorCrmMedico(String crmMedico) {
        List<ConsultasEntity> consultas = consultasRepository.listarConsultasPorCrmMedico(crmMedico);
        if(consultas.isEmpty()){
            return Collections.emptyList();
        }
        return consultas;
    }

    @Override
    public List<ConsultasEntity> buscarConsultasPorCpfPaciente(String cpfPaciente) {
        List<ConsultasEntity> consultas = consultasRepository.listarConsultasPorCpfPaciente(cpfPaciente);
        if(consultas.isEmpty()){
            return Collections.emptyList();
        }
        return consultas;
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
    public List<ConsultasEntity> buscarConsultasPacienteMedico(String cpfPaciente, String crm){
        List<ConsultasEntity> consultas = consultasRepository.listarConsultasPacienteMedico(cpfPaciente, crm);
        if(consultas.isEmpty()){
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

    private Integer aplicaDesconto(ConsultasEntity consulta) {
        Integer valorConsulta = ConsultasEntity.getValorPadrao();

        if (descontosService.descontoFlamengo(consulta.getPaciente().getCpfPaciente())) {
            valorConsulta -= ConsultasEntity.getDescontoFlamengo();
        }
        if (descontosService.descontoSouza(consulta.getPaciente().getCpfPaciente())) {
            valorConsulta -= ConsultasEntity.getDescontoSouza();
        }
        if (descontosService.descontoOnePiece(consulta.getPaciente().getCpfPaciente())) {
            valorConsulta -= ConsultasEntity.getDescontoOnePiece();
        }

        return valorConsulta;
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

            // Se as consultas são iguais (mesmo paciente, medico e data), permite que mantenha a hora de antes
            // para atualizacao de consulta
            if (consultaExistente.equals(novaConsulta)) {
                continue;
            }

            if (dataConsultaExistente.toLocalDate().isEqual(dataNovaConsulta.toLocalDate())
                    && dataConsultaExistente.toLocalTime().equals(dataNovaConsulta.toLocalTime())) {
                return false;
            }

            // Verifica se a diferença entre as horas é menor que 30 minutos
            if (dataConsultaExistente.isEqual(dataNovaConsulta)
                    && Math.abs(dataConsultaExistente.getHour() - dataNovaConsulta.getHour()) < 1
                    && Math.abs(dataConsultaExistente.getMinute() - dataNovaConsulta.getMinute()) < 30) {
                return false;
            }
        }

        return true;
    }


}