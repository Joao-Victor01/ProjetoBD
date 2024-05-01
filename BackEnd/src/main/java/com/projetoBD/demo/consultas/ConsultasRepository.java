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
        String MARCARCONSULTA = "CALL marcarConsulta(?, ?, ?, ?, ?)";
        jdbcTemplate.update(MARCARCONSULTA, consulta.getPaciente().getCpfPaciente(),
                consulta.getMedico().getCrm(), consulta.getDataConsulta(), consulta.getMotivoConsulta(), consulta.getValorConsulta());
    }

    public List<ConsultasEntity> listarTodasConsultas() {
        String LISTARCONSULTAS = "CALL todasConsultas";
        return jdbcTemplate.query(LISTARCONSULTAS, consultaRowMapper);
    }

    public Optional<ConsultasEntity> buscarConsultaPorId(Integer idConsulta){
        String BUSCARCONSULTAID = "CALL buscarConsultaPorId(?)";

        try {
            List<ConsultasEntity> consulta = jdbcTemplate.query(BUSCARCONSULTAID, new Object[]{idConsulta}, consultaRowMapper);
            return consulta.stream().findFirst();
        } catch (EmptyResultDataAccessException e) {
            // se nao achar, retorna vazio
            return Optional.empty();
        }
    }

    public List<ConsultasEntity> listarConsultasPorNomePaciente(String nomePaciente) {
        String CONSULTASNOMEPACIENTE = "CALL listarConsultasPorNomePaciente(?)";
        return jdbcTemplate.query(CONSULTASNOMEPACIENTE, consultaRowMapper, "%" + nomePaciente.toLowerCase(Locale.ROOT) + "%");
    }

    public List<ConsultasEntity> listarConsultasPorNomeMedico(String nomeMedico) {
        String CONSULTASNOMEMEDICO = "CALL listarConsultasPorNomeMedico(?)";
        return jdbcTemplate.query(CONSULTASNOMEMEDICO, consultaRowMapper, "%" + nomeMedico.toLowerCase(Locale.ROOT) + "%");
    }

    public void cancelarConsulta(Integer idConsulta) {
        String DELETARCONSULTA = "DELETE FROM consultas WHERE idConsulta = ?";
        jdbcTemplate.update(DELETARCONSULTA, idConsulta);
    }

    public List<ConsultasEntity> listarConsultasPorCrmMedico(String crmMedico) {
        String CONSULTASCRM = "CALL listarConsultasPorCrmMedico(?)";
        return jdbcTemplate.query(CONSULTASCRM, consultaRowMapper, crmMedico);
    }

    public List<ConsultasEntity> listarConsultasPorCpfPaciente(String cpfPaciente) {
        String CONSULTASCPF = "CALL listarConsultasPorCpfPaciente(?)";
        return jdbcTemplate.query(CONSULTASCPF, consultaRowMapper, cpfPaciente);
    }

    public List<ConsultasEntity> listarConsultasPorData(LocalDateTime inicioDoDia, LocalDateTime finalDoDia) {
        String CONSULTASDATA = "CALL listarConsultasPorData(?, ?)";
        return jdbcTemplate.query(CONSULTASDATA, consultaRowMapper, inicioDoDia, finalDoDia);
    }

    public List<ConsultasEntity> listarConsultasMedicosData(LocalDateTime inicioDoDia, LocalDateTime finalDoDia, String crm) {
        String CONSULTASDATAEMEDICO = "CALL listarConsultasMedicosData(?, ?, ?)";
        return jdbcTemplate.query(CONSULTASDATAEMEDICO, consultaRowMapper, inicioDoDia, finalDoDia, crm);
    }

    public List<ConsultasEntity> listarConsultasPacienteDia(LocalDateTime inicioDoDia, LocalDateTime finalDoDia, String cpf) {
        String CONSULTASDATAEPACIENTE = "CALL listarConsultasPacienteDia(?, ?, ?)";
        return jdbcTemplate.query(CONSULTASDATAEPACIENTE, consultaRowMapper, inicioDoDia, finalDoDia, cpf);
    }

    public List<ConsultasEntity> listarConsultasPacienteMedico(String cpfPaciente, String crm) {
        String CONSULTASCPFCRM = "CALL listarConsultasPacienteMedico(?, ?)";
        return jdbcTemplate.query(CONSULTASCPFCRM, consultaRowMapper, cpfPaciente, crm);
    }
}