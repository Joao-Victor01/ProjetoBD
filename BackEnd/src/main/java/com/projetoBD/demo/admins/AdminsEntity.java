package com.projetoBD.demo.admins;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class AdminsEntity {

    private String nomeAdmin, sexoAdmin, cpfAdmin, emailAdmin, celularAdmin;
    private LocalDate dataNascAdmin;
    private Integer tipoUsuario = 2;

    public String getNomeAdmin() {
        return nomeAdmin;
    }

    public void setNomeAdmin(String nomeAdmin) {
        this.nomeAdmin = nomeAdmin;
    }

    public String getSexoAdmin() {
        return sexoAdmin;
    }

    public void setSexoAdmin(String sexoAdmin) {
        this.sexoAdmin = sexoAdmin;
    }

    public String getCpfAdmin() {
        return cpfAdmin;
    }

    public void setCpfAdmin(String cpfAdmin) {
        this.cpfAdmin = cpfAdmin;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public String getCelularAdmin() {
        return celularAdmin;
    }

    public void setCelularAdmin(String celularAdmin) {
        this.celularAdmin = celularAdmin;
    }

    public LocalDate getDataNascAdmin() {
        return dataNascAdmin;
    }

    public void setDataNascAdmin(LocalDate dataNascAdmin) {
        this.dataNascAdmin = dataNascAdmin;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

}
