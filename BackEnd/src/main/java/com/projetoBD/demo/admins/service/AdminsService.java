package com.projetoBD.demo.admins.service;

import com.projetoBD.demo.admins.AdminsEntity;

import java.util.Optional;

public interface AdminsService {
    void cadastrarNovoAdmin (AdminsEntity admin);

    Optional<AdminsEntity> buscarAdminPorCpf(String cpfAdmin);

    void atualizarAdmin(AdminsEntity admin);

    void deletarAdmin(String cpfAdmin);
}
