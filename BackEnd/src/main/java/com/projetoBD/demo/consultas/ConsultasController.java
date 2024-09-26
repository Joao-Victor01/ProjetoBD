package com.projetoBD.demo.consultas;

import com.projetoBD.demo.consultas.service.ConsultasService;
import com.projetoBD.demo.medicos.MedicosEntity;
import com.projetoBD.demo.medicos.service.MedicosService;
import com.projetoBD.demo.util.ExcelGeneratorUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/consultas")
public class ConsultasController {

    @Autowired
    private ConsultasService consultasService;

    @Autowired
    private MedicosService medicosService;

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
            return ResponseEntity.status(500).headers(headers).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
        public Optional<ConsultasEntity> buscarConsultaPorId(@PathVariable Integer id) {
        return consultasService.buscarConsultaPorId(id);
    }

    @GetMapping("/listar/paciente/{nomePaciente}")
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

    @GetMapping("/medico")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasPorCrmMedico(@RequestParam String crm) {
        return consultasService.buscarConsultasPorCrmMedico(crm);
    }

    @GetMapping("/paciente/{cpfPaciente}")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasPorCpfPaciente(@PathVariable String cpfPaciente) {
        return consultasService.buscarConsultasPorCpfPaciente(cpfPaciente);
    }


    @GetMapping("/data")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        LocalDateTime inicioDoDia = data.atStartOfDay();
        LocalDateTime finalDoDia = data.atTime(23, 59, 59);
        return consultasService.buscarConsultasPorData(inicioDoDia, finalDoDia);
    }


    @GetMapping("/horarios-indisponiveis")
    @ResponseBody
    public List<LocalTime> buscarHorariosIndisponiveisPorDataEMedico(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data, @RequestParam String crm) {
        LocalDateTime inicioDoDia = data.atTime(00, 00, 00);
        LocalDateTime finalDoDia = data.atTime(23, 59, 59);
        return consultasService.buscarHorariosIndisponiveisPorDataEMedico(inicioDoDia, finalDoDia, crm);
    }

    @GetMapping("/paciente/dia")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasPacienteDia(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
                                                            @RequestParam String cpfPaciente) {
        LocalDateTime inicioDoDia = data.atTime(00, 00, 00);
        LocalDateTime finalDoDia = data.atTime(23, 59, 59);
        return consultasService.consultasPacienteDia(inicioDoDia, finalDoDia, cpfPaciente);
    }

    @GetMapping("/medico/dia")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasMedicoDia(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
                                                          @RequestParam String crm) {
        LocalDateTime inicioDoDia = data.atTime(00, 00, 00);
        LocalDateTime finalDoDia = data.atTime(23, 59, 59);
        return consultasService.consultasMedicoDia(inicioDoDia, finalDoDia, crm);
    }

    @GetMapping("/paciente-medico")
    @ResponseBody
    public List<ConsultasEntity> buscarConsultasPacienteMedico (@RequestParam String cpfPaciente, @RequestParam String crm){
        return consultasService.buscarConsultasPacienteMedico(cpfPaciente, crm);
    }

    @GetMapping("/exportar-tabela")
    public void exportarTabelaConsultas (HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String dataAtual = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=consultas.xlsx";
        response.setHeader(headerKey, headerValue);

        List <ConsultasEntity> listaConsultas = consultasService.listarConsultas();
        ExcelGeneratorUtil generator = new ExcelGeneratorUtil(listaConsultas);
        generator.generateExcelFile(response);
    }

    @GetMapping("/medico/exportar-tabela")
    public void exportarTabelaConsultasMedico(HttpServletResponse response,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim,
                                              @RequestParam String crm) throws IOException
    {
        response.setContentType("application/octet-stream");



        LocalDateTime dataInicial = dataInicio.atTime(00, 00, 00);
        LocalDateTime dataFinal = dataFim.atTime(23, 59, 59);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=consultas_medico.xlsx";
        response.setHeader(headerKey, headerValue);

        List <ConsultasEntity> listaConsultas = consultasService.consultasMedicoDia(dataInicial, dataFinal, crm);
        ExcelGeneratorUtil generator = new ExcelGeneratorUtil(listaConsultas);
        generator.generateExcelFile(response);
    }
}
