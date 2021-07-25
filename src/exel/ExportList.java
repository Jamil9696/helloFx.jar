package exel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportList {

    private final ArrayList<String> listDe;
    private final ArrayList<String> listFr;
    private Sheet sheet;
    private final String PATH = "C:\\Users\\jamil\\Documents\\ExcelDateien Listen\\";
    private Workbook workbook;

    public ExportList() {
        listDe = new ArrayList<>();
        listFr = new ArrayList<>();
    }
    // get Data from ListStage
    public void setWordDe(String wordDe) {
        listDe.add(wordDe);
    }

    public void setWordFr(String wordFr) { listFr.add(wordFr); }

    public void exportData(String tableName,String fileName) throws IOException {

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(tableName);
        StyleSpreadSheet();
        fillSpreadSheet();
        createFile(fileName);

    }
    public void upgradeData(String tableName,String fileName) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(PATH + fileName + ".xlsx");
        workbook = WorkbookFactory.create(fileInputStream);
        fileInputStream.close();
        sheet = workbook.getSheet(tableName);
        if(sheet == null) {
            sheet = workbook.createSheet(tableName);
        }else {
            readDataOnce();
        }
        StyleSpreadSheet();
        fillSpreadSheet();
        createFile(fileName);
    }
    private void readDataOnce(){

        for (int cellValue = 1; cellValue < sheet.getLastRowNum() + 1; cellValue++){
            Cell cellDe = sheet.getRow(cellValue).getCell(0);
            listDe.add(cellValue + ". " + cellDe.getStringCellValue());
            Cell cellFr = sheet.getRow(cellValue).getCell(1);
            listFr.add(cellValue + ". " + cellFr.getStringCellValue());
        }

    }
    // spreadsheet design
    private void StyleSpreadSheet() {

        String[] listTitle = new String[] { "En allemand", "En francais"};
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short)13);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setBold(true);
        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setFont(headerFont);
        Row colonne = sheet.createRow(0);

        for (int i = 0; i < listTitle.length; i++) {
            Cell carreau = colonne.createCell(i);
            carreau.setCellValue(listTitle[i]);
            carreau.setCellStyle(headCellStyle);
        }
    }

    private void fillSpreadSheet() {
        // rowNum = 0 (heading Name)
        String[] listTitle = new String[] { "En allemand", "En francais"};
        int rowNum = 1;

        for (int i = 0;i < listDe.size();i++ ){

             String wordDe = listDe.get(i).substring(3);
             String wordFr = listFr.get(i).substring(3);

             Row row = sheet.createRow(rowNum++);
             row.createCell(0).setCellValue(wordDe);
             row.createCell(1).setCellValue(wordFr);
        }
        for (int j = 0; j < listTitle.length; j++) {
            //Adjusts the column width to fit the contents.
            sheet.autoSizeColumn(j);
        }
    }

    private void createFile(String fileName) throws IOException {

        FileOutputStream fOutputStream = null;
        try{
         fOutputStream = new FileOutputStream(PATH + fileName + ".xlsx");
            workbook.write(fOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
           if(fOutputStream != null)fOutputStream.close();
           // workbook.close();
        }
    }
}
