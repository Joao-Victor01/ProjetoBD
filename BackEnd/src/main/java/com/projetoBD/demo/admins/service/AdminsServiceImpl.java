package com.projetoBD.demo.admins.service;

import com.projetoBD.demo.admins.AdminsEntity;
import com.projetoBD.demo.admins.AdminsException;
import com.projetoBD.demo.admins.AdminsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AdminsServiceImpl implements AdminsService{

   @Autowired
   AdminsRepository adminsRepository;

   @Override
    public void cadastrarNovoAdmin(AdminsEntity admin){
       if (admin.getDataNascAdmin().isAfter(LocalDate.now())){
           throw new IllegalArgumentException("Data de nascimento inválida!");
       }
       if (adminsRepository.buscarAdminPorCpf(admin.getCpfAdmin()).isPresent()){
           throw new AdminsException("CPF já cadastrado!");
       }
       adminsRepository.cadastrarAdmin(admin);
   }

   @Override
   public Optional<AdminsEntity> buscarAdminPorCpf(String cpfAdmin){
       return adminsRepository.buscarAdminPorCpf(cpfAdmin);
   }

   @Override
    public void atualizarAdmin(AdminsEntity admin){
       if (admin.getDataNascAdmin().isAfter(LocalDate.now())){
           throw new IllegalArgumentException("Data de nascimento inválida!");
       }
       adminsRepository.atualizarAdmin(admin);
   }

   @Override
   public void deletarAdmin(String cpfAdmin){
       adminsRepository.deletarAdmin(cpfAdmin);
   }
}
