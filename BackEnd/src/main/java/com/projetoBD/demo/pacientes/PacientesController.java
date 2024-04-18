package com.projetoBD.demo.pacientes;

import com.projetoBD.demo.pacientes.service.PacientesService;
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

    @GetMapping("/listar/{nomePaciente}")
    @ResponseBody
    public List<PacientesEntity> listarPacientesPorNome (@PathVariable String nomePaciente){
        return pacientesService.buscarPacientesPorNome(nomePaciente);
    }

    @GetMapping("/buscar/{cpfPaciente}")
    @ResponseBody
    public PacientesEntity buscarPacientePorCpf (@PathVariable String cpfPaciente){
        return pacientesService.buscaPacientePorCpf(cpfPaciente);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarPaciente(@RequestBody PacientesEntity paciente) {
        try {
            pacientesService.atualizarPaciente(paciente);
            return ResponseEntity.ok("Dados do paciente " + paciente.getNomePaciente() + "atualizados!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar dados do paciente.");
        }
    }

    @DeleteMapping("/deletar/{cpfPaciente}")
    public ResponseEntity<String> deletarPaciente (@PathVariable String cpfPaciente){
        try {
            pacientesService.deletarPaciente(cpfPaciente);
            return  ResponseEntity.ok("Paciente deletado da base de dados!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar paciente.");
        }
    }

    @GetMapping("/listar-todos")
    @ResponseBody
    public List<PacientesEntity> listarPacientes(){
        return pacientesService.listarTodosPacientes();
    }

}
