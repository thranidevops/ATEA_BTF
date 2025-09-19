import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.io.FileInputStream;
import java.nio.file.Path;

public class ValidateMongoScenarios {
  // update these:
  static final String URI = "mongodb+srv://tharanishp_db_user:dXyaV1L6z5BBv856@cluster0.y58uc3t.mongodb.net/?retryWrites=true&w=majority";
  static final String DB = "mybtf_db";
  static final String COLL = "customers";
  static final Path EXCEL = Path.of("C:/Users/Tharani/OneDrive/Documents/ATEA-BTF/Week8-Topic 9&10- Data Validation and MongoDB Testing/Sce_Sheet_DataValidation.xlsx");

  public static void main(String[] args) throws Exception {
    try (MongoClient mc = MongoClients.create(URI);
         FileInputStream in = new FileInputStream(EXCEL.toFile());
         Workbook wb = new XSSFWorkbook(in)) {

      MongoCollection<Document> col = mc.getDatabase(DB).getCollection(COLL);
      Sheet sh = wb.getSheetAt(0);   // headers in row 0: id, name, email, balance

      System.out.printf("%-7s |%-7s | %-60s | %-60s | %s%n",
    		  "Scenario", "Row", "Excel", "Mongo", "Result");
          System.out.println("-".repeat(7) +"-".repeat(7) + "-+-" + "-".repeat(60) + "-+-" + "-".repeat(60) + "-+-" + "-".repeat(8));

      
      for (int r = 1; r <= sh.getLastRowNum(); r++) {
        Row row = sh.getRow(r);
        if (row == null) continue;

        String sce = row.getCell(0).getStringCellValue();         // A: Scenario 
        int id = (int) row.getCell(1).getNumericCellValue();      // B: id
        String name = row.getCell(2).getStringCellValue();        // C: name
        String email = row.getCell(3).getStringCellValue();       // D: email
        double bal = row.getCell(4).getNumericCellValue();        // E: balance
        
        // Excel print
        String excelSce=sce;
        String excelStr = String.format("{id=%d, name=%s, email=%s, balance=%.2f}",
        		 id, name, email, bal);

     // Mongo fetch + print
        Document d = col.find(Filters.eq("_id", id)).first();
        String mongoStr;
        boolean pass = false;
        if (d == null) {
          mongoStr = "{not found}";
        } else {
          String mName = d.getString("name");
          String mEmail = d.getString("email");
          double mBal = d.getDouble("balance");
          mongoStr = String.format("{id=%d, name=%s, email=%s, balance=%.2f}",
                  d.getInteger("_id"), mName, mEmail, mBal);
          pass = name.equals(mName) && email.equals(mEmail) && Math.abs(bal - mBal) < 0.001;
        }

        System.out.printf("%-7s |%-7s | %-45s | %-45s | %s%n",
        		excelSce,(r + 1), excelStr, mongoStr, pass ? "PASS" : "FAIL");
      }
    }
  }


}