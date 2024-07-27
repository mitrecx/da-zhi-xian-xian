package top.mitrecx.dazhixianxian.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 测试
 */
public class ExcelUtils {
    public static void main(String[] args) {
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.reader();
    }

    public void reader() {
        try (FileInputStream file = new FileInputStream(new File("/Users/chenxing/Downloads/wordword.xlsx"));
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            System.out.println("replace into t_english2_word( word_order, word, phonetic_symbol, chinese, frequency) values ");
            for (int i = 3; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
//                System.out.print(getCellValue(row.getCell(0)) + "\t"
//                        +getCellValue(row.getCell(1)) + "\t"
//                        +getCellValue(row.getCell(2)) + "\t"
//                        +getCellValue(row.getCell(3)) + "\t"
//                        +getCellValue(row.getCell(4)) + "\t"
//                );
                System.out.println("("+getCellValue(row.getCell(0)) + ",'"+getCellValue(row.getCell(1))+"','"+
                        getCellValue(row.getCell(2))+"','"+getCellValue(row.getCell(3))+"',"
                        +getCellValue(row.getCell(4))+"),");


            }
            System.out.println(")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getCellValue(Cell cell){
        String res = "";
        // 根据单元格类型处理数据
        switch (cell.getCellType()) {
            case STRING:
                res=cell.getStringCellValue();
                break;
            case NUMERIC:
                res = String.valueOf((int)cell.getNumericCellValue());
                break;
            case BOOLEAN:
                res = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                res = cell.getCellFormula();
                break;
            case BLANK:
                break;
            default:
                System.out.print("Error!");
        }

        res = res.replace("'", "\\'");

        return res;
    }
}
