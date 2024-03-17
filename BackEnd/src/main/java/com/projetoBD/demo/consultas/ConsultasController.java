package com.projetoBD.demo.consultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/consultas")
public class ConsultasController {

    @Autowired
    private ConsultasRepository consultasRepository;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<String> marcarConsulta(@RequestBody ConsultasEntity consulta) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            consultasRepository.marcarConsulta(consulta);
            return ResponseEntity.ok().headers(headers).body("Consulta criada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).headers(headers).body("Erro ao criar consulta.");
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ConsultasEntity> visualizarConsulta(@PathVariable Integer id) {
        Optional<ConsultasEntity> consulta = consultasRepository.buscarConsultaPorId(id);
        return consulta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    @ResponseBody
    public List<ConsultasEntity> listarConsultas(){
        return consultasRepository.listarConsultas();
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<String> atualizarConsulta(@PathVariable Integer id, @RequestBody ConsultasEntity novaConsulta) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Optional<ConsultasEntity> consultaExistente = consultasRepository.buscarConsultaPorId(id);
        if (consultaExistente.isPresent()) {
            novaConsulta.setIdConsulta(id);
            consultasRepository.atualizarConsulta(novaConsulta);
            return ResponseEntity.ok().headers(headers).body("Consulta atualizada com sucesso!");
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deletarConsulta(@PathVariable Integer id) {
        Optional<ConsultasEntity> consultaExistente = consultasRepository.buscarConsultaPorId(id);
        if (consultaExistente.isPresent()) {
            consultasRepository.deletarConsulta(id);
            return ResponseEntity.ok("Consulta deletada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
