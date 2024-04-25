package com.projetoBD.demo.util;

import com.projetoBD.demo.consultas.ConsultasEntity;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class ExcelGeneratorUtil {
    private List<ConsultasEntity> consultas;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGeneratorUtil (List<ConsultasEntity> consultas) {
        this.consultas = consultas;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Student");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        createCell(row, 0, "ID da Consulta", style);
        createCell(row, 1, "Nome do Médico", style);
        createCell(row, 2, "Nome do Paciente", style);
        createCell(row, 3, "Tipo de Consulta", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (ConsultasEntity consultasEntity: consultas) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, consultasEntity.getIdConsulta(), style);
            createCell(row, columnCount++, consultasEntity.getMedico().getNomeMedico(), style);
            createCell(row, columnCount++, consultasEntity.getPaciente().getNomePaciente(), style);
            //createCell(row, columnCount++, consultasEntity.getDataConsulta(), style);
            createCell(row, columnCount++, consultasEntity.getMotivoConsulta(), style);
        }
    }
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}