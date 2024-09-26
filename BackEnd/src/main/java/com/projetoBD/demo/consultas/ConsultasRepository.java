package com.projetoBD.demo.consultas;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class ConsultasRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ConsultaRowMapper consultaRowMapper;

    public ConsultasRepository(JdbcTemplate jdbcTemplate, ConsultaRowMapper consultaRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.consultaRowMapper = consultaRowMapper;
    }

    public void marcarConsulta(ConsultasEntity consulta) {
        String MARCARCONSULTA = "INSERT INTO Consultas (cpfPaciente, crm, dataConsulta, motivoConsulta, valorConsulta) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(MARCARCONSULTA, consulta.getPaciente().getCpfPaciente(),
                consulta.getMedico().getCrm(), consulta.getDataConsulta(), consulta.getMotivoConsulta(), consulta.getValorConsulta());
    }

    public List<ConsultasEntity> listarTodasConsultas() {
        String LISTARCONSULTAS = "SELECT * FROM Consultas";
        return jdbcTemplate.query(LISTARCONSULTAS, consultaRowMapper);
    }

    public Optional<ConsultasEntity> buscarConsultaPorId(Integer idConsulta) {
        String BUSCARCONSULTAID = "SELECT * FROM Consultas WHERE idConsulta = ?";

        try {
            List<ConsultasEntity> consulta = jdbcTemplate.query(BUSCARCONSULTAID, new Object[]{idConsulta}, consultaRowMapper);
            return consulta.stream().findFirst();
        } catch (EmptyResultDataAccessException e) {
            // se não achar, retorna vazio
            return Optional.empty();
        }
    }

    public List<ConsultasEntity> listarConsultasPorNomePaciente(String nomePaciente) {
        String CONSULTASNOMEPACIENTE = "SELECT * FROM Consultas WHERE cpfPaciente IN (SELECT cpfPaciente FROM Pacientes WHERE LOWER(nomePaciente) LIKE ?)";
        return jdbcTemplate.query(CONSULTASNOMEPACIENTE, consultaRowMapper, "%" + nomePaciente.toLowerCase(Locale.ROOT) + "%");
    }

    public List<ConsultasEntity> listarConsultasPorNomeMedico(String nomeMedico) {
        String CONSULTASNOMEMEDICO = "SELECT * FROM Consultas WHERE crm IN (SELECT crm FROM Medicos WHERE LOWER(nomeMedico) LIKE ?)";
        return jdbcTemplate.query(CONSULTASNOMEMEDICO, consultaRowMapper, "%" + nomeMedico.toLowerCase(Locale.ROOT) + "%");
    }

    public void cancelarConsulta(Integer idConsulta) {
        String DELETARCONSULTA = "DELETE FROM Consultas WHERE idConsulta = ?";
        jdbcTemplate.update(DELETARCONSULTA, idConsulta);
    }

    public List<ConsultasEntity> listarConsultasPorCrmMedico(String crmMedico) {
        String CONSULTASCRM = "SELECT * FROM Consultas WHERE crm = ?";
        return jdbcTemplate.query(CONSULTASCRM, consultaRowMapper, crmMedico);
    }

    public List<ConsultasEntity> listarConsultasPorCpfPaciente(String cpfPaciente) {
        String CONSULTASCPF = "SELECT * FROM Consultas WHERE cpfPaciente = ?";
        return jdbcTemplate.query(CONSULTASCPF, consultaRowMapper, cpfPaciente);
    }

    public List<ConsultasEntity> listarConsultasPorData(LocalDateTime inicioDoDia, LocalDateTime finalDoDia) {
        String CONSULTASDATA = "SELECT * FROM Consultas WHERE dataConsulta BETWEEN ? AND ?";
        return jdbcTemplate.query(CONSULTASDATA, consultaRowMapper, inicioDoDia, finalDoDia);
    }

    public List<ConsultasEntity> listarConsultasMedicosData(LocalDateTime inicioDoDia, LocalDateTime finalDoDia, String crm) {
        String CONSULTASDATAEMEDICO = "SELECT * FROM Consultas WHERE crm = ? AND dataConsulta BETWEEN ? AND ?";
        return jdbcTemplate.query(CONSULTASDATAEMEDICO, consultaRowMapper, crm, inicioDoDia, finalDoDia);
    }

    public List<ConsultasEntity> listarConsultasPacienteDia(LocalDateTime inicioDoDia, LocalDateTime finalDoDia, String cpf) {
        String CONSULTASDATAEPACIENTE = "SELECT * FROM Consultas WHERE cpfPaciente = ? AND dataConsulta BETWEEN ? AND ?";
        return jdbcTemplate.query(CONSULTASDATAEPACIENTE, consultaRowMapper, cpf, inicioDoDia, finalDoDia);
    }

    public List<ConsultasEntity> listarConsultasPacienteMedico(String cpfPaciente, String crm) {
        String CONSULTASCPFCRM = "SELECT * FROM Consultas WHERE cpfPaciente = ? AND crm = ?";
        return jdbcTemplate.query(CONSULTASCPFCRM, consultaRowMapper, cpfPaciente, crm);
    }
}
