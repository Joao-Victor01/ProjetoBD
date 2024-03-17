package com.projetoBD.demo.consultas;

import com.projetoBD.demo.consultas.Service.ConsultasService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/consultas")
public class ConsultasController {


    private final ConsultasService consultasService;

    public ConsultasController(ConsultasService consultasService) {
        this.consultasService = consultasService;
    }

    @PostMapping("/create")
    @ResponseBody
    public String marcarConsulta(@RequestBody ConsultasEntity consulta) {
        try {
            consultasService.salvarConsulta(consulta);
            return "Consulta criada com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao criar consulta.";
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ConsultasEntity> visualizarConsulta(@PathVariable Integer id) {
        Optional<ConsultasEntity> consulta = consultasService.buscarConsultaPorId(id);
        return consulta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{nome}")
    @ResponseBody
    public ResponseEntity<ConsultasEntity> consultasPorPaciente(@PathVariable String nome){
        Optional<ConsultasEntity> consultas = consultasService.consultasPorPaciente(nome);
        return consultas.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    @ResponseBody
    public List<ConsultasEntity> listarConsultas(){
        return consultasService.listarTodasConsultas();
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<String> atualizarConsulta(@PathVariable Integer id, @RequestBody ConsultasEntity novaConsulta) {
        Optional<ConsultasEntity> consultaExistente = consultasService.buscarConsultaPorId(id);
        if (consultaExistente.isPresent()) {
            novaConsulta.setIdConsulta(id);
            consultasService.atualizarConsulta(novaConsulta);
            return ResponseEntity.ok("Consulta atualizada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deletarConsulta(@PathVariable Integer id) {
        Optional<ConsultasEntity> consultaExistente = consultasService.buscarConsultaPorId(id);
        if (consultaExistente.isPresent()) {
            consultasService.deletarConsulta(id);
            return ResponseEntity.ok("Consulta deletada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
