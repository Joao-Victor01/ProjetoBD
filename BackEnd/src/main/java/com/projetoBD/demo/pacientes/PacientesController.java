package com.projetoBD.demo.pacientes;

import com.projetoBD.demo.consultas.ConsultasEntity;
import com.projetoBD.demo.pacientes.service.PacientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/pacientes")
public class PacientesController {

    @Autowired
    private PacientesService pacientesService;

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity<String> cadastrarPaciente (@RequestBody PacientesEntity paciente) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            pacientesService.cadastrarNovoPaciente(paciente);
            return ResponseEntity.ok().headers(headers).body("Novo paciente Cadastrado!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).headers(headers).body("Erro ao cadastrar paciente!");
        }
    }

}
