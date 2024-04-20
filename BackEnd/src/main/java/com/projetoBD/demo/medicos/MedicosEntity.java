package com.projetoBD.demo.medicos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
public class MedicosEntity {
    private String nomeMedico, crm, sexoMedico, celularMedico, emailMedico;
    private LocalDate dataNascMedico;
    private Integer tipoUsuario = 1;

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getSexoMedico() {
        return sexoMedico;
    }

    public void setSexoMedico(String sexoMedico) {
        this.sexoMedico = sexoMedico;
    }

    public String getCelularMedico() {
        return celularMedico;
    }

    public void setCelularMedico(String celularMedico) {
        this.celularMedico = celularMedico;
    }

    public String getEmailMedico() {
        return emailMedico;
    }

    public void setEmailMedico(String emailMedico) {
        this.emailMedico = emailMedico;
    }

    public LocalDate getDataNascMedico() {
        return dataNascMedico;
    }

    public void setDataNascMedico(LocalDate dataNascMedico) {
        this.dataNascMedico = dataNascMedico;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

}
