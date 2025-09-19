package stepDefinitions;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.types.Decimal128;
import io.cucumber.java.en.Then;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;   // <-- JUnit 4 asserts

public class ExcelMongoSteps {

  // update for your env
  static final String URI = "mongodb+srv://tharanishp_db_user:dXyaV1L6z5BBv856@cluster0.y58uc3t.mongodb.net/?retryWrites=true&w=majority";
  private static final String DB    = "mybtf_db";
  private static final String COLL  = "customers";
  private static final Path   XLSX  = Paths.get("C:/Users/Tharani/OneDrive/Documents/ATEA-BTF/Week8-Topic 9&10- Data Validation and MongoDB Testing/Sce_Sheet_DataValidation.xlsx");

  @Then("Excel and Mongo should match")
  public void excel_and_mongo_should_match() throws Exception {
    try (MongoClient mc = MongoClients.create(URI);
         FileInputStream in = new FileInputStream(XLSX.toFile());
         Workbook wb = new XSSFWorkbook(in)) {

      MongoCollection<Document> col = mc.getDatabase(DB).getCollection(COLL);
      Sheet sh = wb.getSheetAt(0);
      DataFormatter fmt = new DataFormatter();

      for (int r = 1; r <= sh.getLastRowNum(); r++) {   // skip header row
        Row row = sh.getRow(r);
        if (row == null) continue;

        String scenario = fmt.formatCellValue(row.getCell(0));              // A: Scenario (optional)
        int    id       = Integer.parseInt(fmt.formatCellValue(row.getCell(1))); // B: id
        String name     = fmt.formatCellValue(row.getCell(2));              // C: name
        String email    = fmt.formatCellValue(row.getCell(3));              // D: email
        double bal      = Double.parseDouble(fmt.formatCellValue(row.getCell(4))); // E: balance

        Document d = col.find(Filters.eq("_id", id)).first();
        
        //Print Scenario
        System.out.println("Scenario: " + scenario + "\t");
        System.out.println("-".repeat(50) );
        // print both sides
        System.out.printf("Row %d Excel={id=%d, name=%s, email=%s, balance=%.2f}%n",
                r+1, id, name, email, bal);
        System.out.printf("Row %d Mongo =%s%n", r+1, d);

        // JUnit 4 assertions
        assertNotNull("Row "+(r+1)+" id="+id+" ("+scenario+") not found in Mongo", d);
        assertEquals("name mismatch for id="+id,  name,  d.getString("name"));
        assertEquals("email mismatch for id="+id, email, d.getString("email"));
        assertEquals("balance mismatch for id="+id, bal, d.getDouble("balance"), 0.01);
      }
    }
  }

}
