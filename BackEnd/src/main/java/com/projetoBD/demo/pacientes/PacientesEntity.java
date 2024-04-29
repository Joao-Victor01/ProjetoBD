package com.projetoBD.demo.pacientes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class PacientesEntity {

    private String nomePaciente;
    private String cpfPaciente;
    private LocalDate dataNascPaciente;
    private String sexoPaciente;
    private int tipoUsuario = 0;
    private boolean flamengo, onepiece, souza;



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

    public boolean isFlamengo() {
        return flamengo;
    }

    public void setFlamengo(boolean flamengo) {
        this.flamengo = flamengo;
    }

    public boolean isOnepiece() {
        return onepiece;
    }

    public void setOnepiece(boolean onepiece) {
        this.onepiece = onepiece;
    }

    public boolean isSouza() {
        return souza;
    }

    public void setSouza(boolean souza) {
        this.souza = souza;
    }
}
