package exel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ImportList {

    private Workbook workbook;
    private final ArrayList<String> listDe = new ArrayList<>();
    private final ArrayList<String> listFr = new ArrayList<>();

    //import exel file
    private void importWorkbook(File file) throws IOException {
        workbook = null;
        try (FileInputStream fInputStream = new FileInputStream(file)) {
            workbook = WorkbookFactory.create(fInputStream);
        } finally {
            if(workbook != null)workbook.close();
        }
    }
    // // file check

    public void addListToChoiceBox(String fileName, Text displayedText) throws IOException {

        String path = "C:\\Users\\jamil\\Documents\\ExcelDateien Listen\\";
        String pathName = path + fileName + ".xlsx";
        File file = new File(pathName);

        importWorkbook(file);
        displayedText.setText("one specific spreadsheet or all of them? ");
    }
    // update the spreadsheet list of an exel file
    public void updateSpreadSheetChoiceBox(ChoiceBox<String> choiceBox) {

        // remove the old ones except the default[0] -> all spreadsheets
        for (int i = 1; i < choiceBox.getItems().size(); i++) {
            choiceBox.getItems().remove(i);
        }

        for (int sheets = 0; sheets < workbook.getNumberOfSheets(); sheets++) {
            String item = workbook.getSheetName(sheets);
            choiceBox.getItems().add(item);
        }
    }
    // Figure out which Spreadsheet has been chosen
    public void searchSpecificSheet(int index) {
        listDe.clear();
        listFr.clear();

        if (index >= 0) {
            Sheet sheet = workbook.getSheetAt(index);
            int lastRowNum = sheet.getLastRowNum();
            readCellValue(sheet, lastRowNum);

            // isListImported (&& index < 0) -> read all spreadsheets
        }else if(index < 0){

            for (int sheets = 0; sheets < workbook.getNumberOfSheets(); sheets++) {
                Sheet sheet = workbook.getSheetAt(sheets);
                int lastRowNum = sheet.getLastRowNum();
                readCellValue(sheet, lastRowNum);
            }
        }
    }
    // fill arraylists
    private void readCellValue(Sheet sheet,int lastRowNum) {

        for (int j = 1; j < lastRowNum; j++) {

            Cell cell1 = sheet.getRow(j).getCell(0);
            String wortDe = cell1.getStringCellValue();
            listDe.add(wortDe);
            Cell cell2 = sheet.getRow(j).getCell(1);
            String wortFr = cell2.getStringCellValue();
            listFr.add(wortFr);
        }
    }

    public ArrayList<String> getListDe() {
        return listDe;
    }
    public ArrayList<String> getListFr() {
        return listFr;
    }

}