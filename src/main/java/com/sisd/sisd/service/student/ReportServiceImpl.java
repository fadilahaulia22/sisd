package com.sisd.sisd.service.student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisd.sisd.entity.Student;
import com.sisd.sisd.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService{
    @Autowired
    StudentRepository studentRepository;

    @Override
    public byte[] generateReport() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet();

        CellStyle titleCellStyle = createCellStyle(workbook, true, false, false);
        CellStyle headerCellStyle = createCellStyle(workbook, false, true, false);
        CellStyle tableCellStyle = createCellStyle(workbook, false, false, true);

        generateTitle(sheet, titleCellStyle);
        generateHeader(sheet, headerCellStyle);

        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            generateEmptyData(sheet, tableCellStyle);
        } else {
            int currentRowIndex = 3;
            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(currentRowIndex);
                generateTabelData(row, student, tableCellStyle, rowNum);
                currentRowIndex++;
                rowNum++;
            }
        }

        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(byteArrayOutputStream);
        workbook.close();

        return byteArrayOutputStream.toByteArray();
    }

    private CellStyle createCellStyle(Workbook workbook,
                                      boolean isHeader, boolean isTitle, boolean isTable) {
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");

        if (isHeader) {
            font.setFontHeightInPoints((short) 20);
            font.setBold(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        if (isTitle) {
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        if (isTable) {
            font.setFontHeightInPoints((short) 12);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.DASHED);
            cellStyle.setBorderBottom(BorderStyle.DASHED);
            cellStyle.setBorderLeft(BorderStyle.DASHED);
            cellStyle.setBorderRight(BorderStyle.DASHED);
        }

        cellStyle.setFont(font);
        return cellStyle;
    }

    private void generateTitle(Sheet sheet, CellStyle cellStyle) {
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Laporan Penerimaan Siswa");
        titleCell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0, 7));
    }

    private void generateHeader(Sheet sheet, CellStyle cellStyle) {
        Row headerRow = sheet.createRow(2);
        int currentCellIndex = 0;
        headerRow.createCell(currentCellIndex).setCellValue("No");
        headerRow.createCell(++currentCellIndex).setCellValue("NISN");
        headerRow.createCell(++currentCellIndex).setCellValue("Nama Siswa");
        headerRow.createCell(++currentCellIndex).setCellValue("Jenis Kelamin");
        headerRow.createCell(++currentCellIndex).setCellValue("Tanggal Registrasi");
        headerRow.createCell(++currentCellIndex).setCellValue("Nama Orang Tua");
        headerRow.createCell(++currentCellIndex).setCellValue("Alamat Orang Tua");
        headerRow.createCell(++currentCellIndex).setCellValue("Nomor HP Orang Tua");

        for (int i=0; i < currentCellIndex; i++) {
            headerRow.getCell(i).setCellStyle(cellStyle);
        }
    }

    private void generateEmptyData(Sheet sheet, CellStyle cellStyle) {
        Row emptyRow = sheet.createRow(3);
        emptyRow.createCell(0).setCellValue("No Data");
        sheet.addMergedRegion(new CellRangeAddress(3,3,0, 7));
        emptyRow.getCell(0).setCellStyle(cellStyle);
    }

    private void generateTabelData(Row row, Student student, CellStyle cellStyle,
                                   int rowNum) {
        int currentCellIndex = 0;

        Cell numberRow = row.createCell(currentCellIndex);
        numberRow.setCellValue(rowNum);
        numberRow.setCellStyle(cellStyle);

        Cell nisnRow = row.createCell(++currentCellIndex);
        nisnRow.setCellValue(student.getNisn() == null ? "-" : student.getNisn());
        nisnRow.setCellStyle(cellStyle);

        Cell nameRow = row.createCell(++currentCellIndex);
        nameRow.setCellValue(student.getStudentName());
        nameRow.setCellStyle(cellStyle);

        Cell genderRow = row.createCell(++currentCellIndex);
        genderRow.setCellValue(student.getGender());
        genderRow.setCellStyle(cellStyle);

        Cell registerDateRow = row.createCell(++currentCellIndex);
        registerDateRow.setCellValue(student.getRegisterDate().format(DateTimeFormatter.ISO_DATE));
        registerDateRow.setCellStyle(cellStyle);

        Cell parentNameRow = row.createCell(++currentCellIndex);
        parentNameRow.setCellValue(student.getParent().getParentName());
        parentNameRow.setCellStyle(cellStyle);

        Cell parentAddressRow = row.createCell(++currentCellIndex);
        parentAddressRow.setCellValue(student.getParent().getAddress());
        parentAddressRow.setCellStyle(cellStyle);

        Cell parentPhoneRow = row.createCell(++currentCellIndex);
        parentPhoneRow.setCellValue(student.getParent().getPhoneNumber());
        parentPhoneRow.setCellStyle(cellStyle);
    }
}
