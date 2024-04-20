package com.projetoBD.demo.pacientes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class PacientesEntity {

    private String nomePaciente;
    private String cpfPaciente;
    private LocalDate dataNascPaciente;
    private String sexoPaciente;
    private int tipoUsuario = 0;



    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public LocalDate getDataNascPaciente() {
        return dataNascPaciente;
    }

    public void setDataNascPaciente(LocalDate dataNascPaciente) {
        this.dataNascPaciente = dataNascPaciente;
    }

    public String getSexoPaciente() {
        return sexoPaciente;
    }

    public void setSexoPaciente(String sexoPaciente) {
        this.sexoPaciente = sexoPaciente;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

}
