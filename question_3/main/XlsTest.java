package main;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Jimmy on 2016/12/11.
 */
public class XlsTest {
    public static void main(String[] args){
        try {
            readXLSFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readXLSFile() throws IOException
    {
        HashMap <String, Double> marks = new HashMap<String, Double>();
        InputStream ExcelFileToRead = new FileInputStream("E:/workspace/JavaCourse/xlstest.xls");
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();

        rows.next();

        //read rows one by one
        while (rows.hasNext())
        {
            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            double mark = 0;
            String id = null;

            //read cells one by one
            while (cells.hasNext())
            {
                cell = (HSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                {
                    id = cell.getStringCellValue();
                }
                //if the variable in cell is number
                else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                {
                    mark = cell.getNumericCellValue();
                }
                else
                {
                    System.out.println("Error in cellType");
                }
            }
            marks.put(id, mark);
        }
        double sum = 0;
        double count = 0;
        for(String key : marks.keySet()){
            sum += marks.get(key);
            count++;
        }
        System.out.println(sum/count);
    }
}
