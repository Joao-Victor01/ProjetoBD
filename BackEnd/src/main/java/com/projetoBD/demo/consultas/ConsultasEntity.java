package com.projetoBD.demo.consultas;

import com.projetoBD.demo.medicos.MedicosEntity;
import com.projetoBD.demo.pacientes.PacientesEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class ConsultasEntity {
    private Integer idConsulta;
    private PacientesEntity paciente;
    private MedicosEntity medico;
    private LocalDateTime dataConsulta;
    private String motivoConsulta;

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public PacientesEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacientesEntity paciente) {
        this.paciente = paciente;
    }

    public MedicosEntity getMedico() {
        return medico;
    }

    public void setMedico(MedicosEntity medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

}


