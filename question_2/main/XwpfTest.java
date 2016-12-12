package main;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.xml.namespace.QName;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTObject;
/**
 * Created by Jimmy on 2016/12/10.
 */

public class XwpfTest {

    public void testReadByDoc() throws Exception{
        String pre_name = "name";
        String pre_price = "price";
        String pre_image1 = "image1";
        String pre_image2 = "image2";
        String pro_name = "cake";
        String pro_price = "5.0";
        String pro_image1 = "E:/workspace/JavaCourse/image1.jpg";
        String pro_image2 = "E:/workspace/JavaCourse/image2.jpeg";

        String docPath = "E:/workspace/JavaCourse/docTest.docx";

        Map<String, String> params = new HashMap<String, String>();
        params.put(pre_name, pro_name);
        params.put(pre_price, pro_price);
        params.put(pre_image1, pro_image1);
        params.put(pre_image2, pro_image2);

        InputStream is = new FileInputStream(docPath);
        XWPFDocument doc = new XWPFDocument(is);
        //�滻��������ı���
        this.replaceInPara(doc, params);
        //�滻�������ı���
        this.replaceInTable(doc, params);

        OutputStream os = new FileOutputStream("E:/workspace/JavaCourse/docTest1.docx");
        doc.write(os);
        this.close(os);
        this.close(is);
    }

    /**
     * �滻��������ı���
     *
     * @param doc    Ҫ�滻���ĵ�
     * @param params ����
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
     * �滻��������ı���
     *
     * @param para   Ҫ�滻�Ķ���
     * @param params ����
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
                    //ֱ�ӵ���XWPFRun��setText()���������ı�ʱ���ڵײ�����´���һ��XWPFRun�����ı������ڵ�ǰ�ı����棬
                    //�������ǲ���ֱ����ֵ����Ҫ��ɾ����ǰrun,Ȼ�����Լ��ֶ�����һ���µ�run��
                    if(getPictureType(runText) == -1) {
                        para.removeRun(i);
                        para.insertNewRun(i).setText(runText);
                    }
                    else {
                        try {
                            para.removeRun(i);
                            para.insertNewRun(i).addPicture(new FileInputStream(runText),
                                    getPictureType(runText), runText, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
                            para.insertNewRun(i).addBreak(BreakType.PAGE);
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
     * �滻�������ı���
     *
     * @param doc    Ҫ�滻���ĵ�
     * @param params ����
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
     * ����ƥ���ַ���
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$(.*)\\$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * �ر�������
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
     * �ر������
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