package com.projetoBD.demo.consultas;

import com.projetoBD.demo.consultas.service.ConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/consultas")
public class ConsultasController {

    @Autowired
    private ConsultasService consultasService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<String> marcarConsulta(@RequestBody ConsultasEntity consulta) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            consultasService.marcarConsulta(consulta);
            return ResponseEntity.ok().headers(headers).body("Consulta criada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).headers(headers).body("Erro ao criar consulta.");
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<ConsultasEntity> buscarConsultaPorId(@PathVariable Integer id) {
        return consultasService.buscarConsultaPorId(id);
    }

    @GetMapping("/paciente/{nomePaciente}")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasPorPaciente(@PathVariable String nomePaciente) {
        return consultasService.buscarConsultasPorNomePaciente(nomePaciente);
    }

    @GetMapping("/medico/{nomeMedico}")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasPorMedico(@PathVariable String nomeMedico) {
        return consultasService.buscarConsultasPorNomeMedico(nomeMedico);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<ConsultasEntity> listarConsultas(){
        return consultasService.listarConsultas();
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
//
//    @GetMapping("/medico/{crmMedico}")
//    @ResponseBody
//    public List<ConsultasEntity> buscarConsultasPorCrmMedico(@PathVariable String crmMedico) {
//        return consultasService.buscarConsultasPorCrmMedico(crmMedico);
//    }
//
//    @GetMapping("/paciente/{cpfPaciente}")
//    @ResponseBody
//    public List<ConsultasEntity> buscarConsultasPorCpfPaciente(@PathVariable String cpfPaciente) {
//        return consultasService.buscarConsultasPorCpfPaciente(cpfPaciente);
//    }
//
//    @GetMapping("/data/{data}")
//    @ResponseBody
//    public List<ConsultasEntity> buscarConsultasPorData(@PathVariable String data) {
//        LocalDateTime dataConsulta = LocalDateTime.parse(data);
//        return consultasService.buscarConsultasPorData(dataConsulta);
//    }

    @GetMapping("/horarios-indisponiveis")
    @ResponseBody
    public List<LocalTime> buscarHorariosIndisponiveisPorDataEMedico(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data, @RequestParam String crm) {
        LocalDateTime inicioDoDia = data.atTime(00, 00, 00);
        LocalDateTime finalDoDia = data.atTime(23, 59, 59);
        return consultasService.buscarHorariosIndisponiveisPorDataEMedico(inicioDoDia, finalDoDia, crm);
    }
}
