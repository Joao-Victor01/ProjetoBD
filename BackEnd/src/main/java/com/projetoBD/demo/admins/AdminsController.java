package com.projetoBD.demo.admins;

import com.projetoBD.demo.admins.service.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/admins")
public class AdminsController {

    @Autowired
    AdminsService adminsService;

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity<String> cadastrarAdmin (@RequestBody AdminsEntity admin) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            adminsService.cadastrarNovoAdmin(admin);
            return ResponseEntity.ok().headers(headers).body("Novo admin Cadastrado!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).headers(headers).body("Erro ao cadastrar admin!");
        }
    }

    @GetMapping("/buscar/{cpfAdmin}")
    @ResponseBody
    public Optional<AdminsEntity> buscarAdminPorCpf (@PathVariable String cpfAdmin){
        return adminsService.buscarAdminPorCpf(cpfAdmin);
    }

    @PutMapping("/atualizar/{cpfAdmin}")
    @ResponseBody
    public ResponseEntity<String> atualizarDadosAdmin(@PathVariable String cpfAdmin, @RequestBody AdminsEntity novoAdmin) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Optional<AdminsEntity> adminExistente = adminsService.buscarAdminPorCpf(cpfAdmin);
        if (adminExistente.isPresent()) {
            novoAdmin.setCpfAdmin(cpfAdmin);
            adminsService.atualizarAdmin(novoAdmin);
            return ResponseEntity.ok().headers(headers).body("Dados do Admin atualizados com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/deletar/{cpfAdmin}")
    public ResponseEntity<String> deletarAdmin (@PathVariable String cpfAdmin){
        try {
            adminsService.deletarAdmin(cpfAdmin);
            return  ResponseEntity.ok("Admin deletado da base de dados!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar paciente.");
        }
    }
}
