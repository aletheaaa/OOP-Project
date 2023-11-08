package is442.portfolioAnalyzer.Stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockDAO stockDAO;

    @Autowired
    public StockService(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public void populateTableFromExcel(String excelFilePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // Skip the header row
            }

            Stock entity = new Stock();
            entity.setSymbol(row.getCell(0).getStringCellValue());
            entity.setName(row.getCell(1).getStringCellValue());
            entity.setCountry(row.getCell(2).getStringCellValue());
            entity.setSector(row.getCell(3).getStringCellValue());
            entity.setIndustry(row.getCell(4).getStringCellValue());

            stockDAO.save(entity);
        }

        workbook.close();
        excelFile.close();
    }
        // Get stock by symbol
        public Stock getStockBySymbol(String symbol){
        return stockDAO.findBySymbol(symbol);
        }


    public List<String> getAllStockSymbols(){
        return stockDAO.getAllStockSymbols();
    }
}
