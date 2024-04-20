package com.projetoBD.demo.medicos;

import com.projetoBD.demo.medicos.service.MedicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/medicos")
public class MedicosController {

    @Autowired
    private MedicosService medicosService;

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity<String> cadastrarMedico (@RequestBody MedicosEntity medico) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            medicosService.cadastrarNovoMedico(medico);
            return ResponseEntity.ok().headers(headers).body("Novo medico cadastrado!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).headers(headers).body("Erro ao cadastrar medico!");
        }
    }

    @GetMapping("/listar/{nomeMedico}")
    @ResponseBody
    public List<MedicosEntity> listarMedicosPorNome (@PathVariable String nomeMedico){
        return medicosService.buscarMedicosPorNome(nomeMedico);
    }

    @GetMapping("/buscar")
    @ResponseBody
    public Optional<MedicosEntity> buscarMedicoPorCrm (@RequestParam String crm){
        return medicosService.buscarMedicoPorCrm(crm);
    }

    @PutMapping("/atualizar")
    @ResponseBody
    public ResponseEntity<String> atualizarDadosMedico(@RequestParam String crm, @RequestBody MedicosEntity novoMedico) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Optional<MedicosEntity> medicoExistente = medicosService.buscarMedicoPorCrm(crm);
        if (medicoExistente.isPresent()) {
            novoMedico.setCrm(crm);
            medicosService.atualizarMedico(novoMedico);
            return ResponseEntity.ok().headers(headers).body("Dados do médico atualizados com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarMedico (@RequestParam String crm){
        try {
            medicosService.deletarMedico(crm);
            return  ResponseEntity.ok("Médico deletado da base de dados!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar médico.");
        }
    }

    @GetMapping("/listar-todos")
    @ResponseBody
    public List<MedicosEntity> listarMedicos(){
        return medicosService.listarTodosMedicos();
    }
}
