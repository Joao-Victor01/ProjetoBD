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
    private Integer valorConsulta;

    private static final Integer valorPadrao = 100;
    private static final Integer descontoSouza = 5;
    private static final Integer descontoFlamengo = 5;
    private static final Integer descontoOnePiece = 5;

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

    public Integer getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(Integer valorConsulta) {
        this.valorConsulta = valorConsulta;
    }



    public static Integer getValorPadrao() {
        return valorPadrao;
    }

    public static Integer getDescontoSouza() {
        return descontoSouza;
    }

    public static Integer getDescontoFlamengo() {
        return descontoFlamengo;
    }

    public static Integer getDescontoOnePiece() {
        return descontoOnePiece;
    }
}


