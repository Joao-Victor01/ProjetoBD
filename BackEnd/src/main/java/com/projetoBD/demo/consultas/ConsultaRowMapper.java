package com.projetoBD.demo.consultas;

import com.projetoBD.demo.medicos.MedicosEntity;
import com.projetoBD.demo.medicos.MedicosRepository;
import com.projetoBD.demo.pacientes.PacientesEntity;
import com.projetoBD.demo.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class ConsultaRowMapper implements RowMapper<ConsultasEntity> {

    private final PacientesRepository pacientesRepository;
    private final MedicosRepository medicosRepository;

    @Autowired
    public ConsultaRowMapper(PacientesRepository pacientesRepository, MedicosRepository medicosRepository) {
        this.pacientesRepository = pacientesRepository;
        this.medicosRepository = medicosRepository;
    }

    @Override
    public ConsultasEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConsultasEntity consulta = new ConsultasEntity();
        consulta.setIdConsulta(rs.getInt("idConsulta"));
        consulta.setDataConsulta(rs.getObject("dataConsulta", LocalDateTime.class));
        consulta.setMotivoConsulta(rs.getString("motivoConsulta"));
        consulta.setValorConsulta(rs.getInt("valorConsulta"));

        // Mapeia o paciente
        PacientesEntity paciente = pacientesRepository.buscarPacientePorCpf(rs.getString("cpfPaciente")).orElse(null);

        // Mapeia o médico
        MedicosEntity medico = medicosRepository.buscarMedicoPorCrm(rs.getString("crm")).orElse(null);

        // Define o paciente e o médico na consulta
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);

        return consulta;
    }
}
