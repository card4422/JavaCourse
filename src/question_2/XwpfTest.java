package question_2;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;

/**
 * Created by Jimmy on 2016/12/10.
 */

public class XwpfTest {

    public void testReadByDoc() throws Exception{
    	//the list of variable need to be replace
        String pre_name = "name";
        String pre_price = "price";
        String pre_image1 = "image1";
        String pre_image2 = "image2";
        String pro_name = "cake";
        String pro_price = "5.0";
        String pro_image1 = "E:/workspace/JavaCourse/image1.jpg";
        String pro_image2 = "E:/workspace/JavaCourse/image2.jpeg";

        String docPath = "E:/workspace/JavaCourse/docTest.docx";

        //Store all variables in the params
        Map<String, String> params = new HashMap<String, String>();
        params.put(pre_name, pro_name);
        params.put(pre_price, pro_price);
        params.put(pre_image1, pro_image1);
        params.put(pre_image2, pro_image2);

        InputStream is = new FileInputStream(docPath);
        XWPFDocument doc = new XWPFDocument(is);
        //replace the param in paragraph
        this.replaceInPara(doc, params);
        //replace the param in table
        this.replaceInTable(doc, params);

        OutputStream os = new FileOutputStream("E:/workspace/JavaCourse/docTest1.docx");
        doc.write(os);
        this.close(os);
        this.close(is);
    }

    /**
     * replace the variables in paragraph
     *
     * @param doc    the doc we read
     * @param params the variables we need to replace
     */
    private void replaceInPara(XWPFDocument doc, Map<String, String> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    /**
     *
     * @param imgFile
     * @return
     */
    private static int getPictureType(String imgFile){
        int format = -1;
        if(imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
        else if(imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
        else if(imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
        else if(imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
        else if(imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
        else if(imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
        else if(imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
        else if(imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
        else if(imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
        else if(imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
        else if(imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
        else {
            //System.out.println("Unsupported picture: " + imgFile + ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
        }
        return format;
    }
    /**
     * replace the variables in paragraph
     *
     * @param para   the paragraph we read
     * @param params the variables we need to replace
     */
    private void replaceInPara(XWPFParagraph para, Map<String, String> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        String str = para.getParagraphText();
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = this.matcher(runText);
                if (matcher.find()) {
                    while ((matcher = this.matcher(runText)).find()) {
                            runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    if(getPictureType(runText) == -1) {
                        para.removeRun(i);
                        para.insertNewRun(i).setText(runText);
                    }
                    else {
                        try {
                            para.removeRun(i);
                            para.insertNewRun(i).addPicture(new FileInputStream(runText),
                                    getPictureType(runText), runText, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
                        } catch (InvalidFormatException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    
     * @param params the variables we need to replace
     */
    private void replaceInTable(XWPFDocument doc, Map<String, String> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
    	//Using regular expression to match the key word
        Pattern pattern = Pattern.compile("\\$(.*)\\$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 
     *
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     *
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}