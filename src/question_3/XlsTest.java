package question_3;

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
        HashMap <Integer, Double> marks = new HashMap<Integer, Double>();
        InputStream ExcelFileToRead = new FileInputStream("/Users/zhuzheng/workspace/JavaCourse/test.xls");
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
            Integer id = 0;
            boolean flag = false;
            //read cells one by one
            while (cells.hasNext())
            {
                cell = (HSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                {
                    if(cell.getStringCellValue().equals("语文")){
                        flag = true;
                        //id = cell.getStringCellValue();
                        id = id +1;
                    }

                }
                //if the variable in cell is number
                else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                {
                    if(flag) {
                        mark = cell.getNumericCellValue();
                        flag = false;
                    }
                }
                else
                {
                    System.out.println("Error in cellType");
                }
            }

            marks.put(id, mark);
        }
        double max = 0;
        double temp = 0;
        for(Integer key : marks.keySet()){
            temp = marks.get(key);
            if(temp>max){
                max = temp;
            }
        }
        System.out.println(max);
        //System.out.println(sum/count);
    }
}
