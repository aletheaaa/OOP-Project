package is442.portfolioAnalyzer.StockSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockSheetService {
    private final StockSheetDAO stockSheetDAO;

    @Autowired
    public StockSheetService(StockSheetDAO stockSheetDAO) {
        this.stockSheetDAO = stockSheetDAO;
    }

    public void populateTableFromExcel(String excelFilePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // Skip the header row
            }

            StockSheet entity = new StockSheet();
            entity.setSymbol(row.getCell(0).getStringCellValue());
            entity.setName(row.getCell(1).getStringCellValue());
            entity.setCountry(row.getCell(2).getStringCellValue());
            entity.setSector(row.getCell(3).getStringCellValue());
            entity.setIndustry(row.getCell(4).getStringCellValue());

            stockSheetDAO.save(entity);
        }

        workbook.close();
        excelFile.close();
    }
}
