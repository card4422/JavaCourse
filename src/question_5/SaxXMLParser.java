package question_5;

/**
 * Created by Jimmy on 2017/1/2.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxXMLParser {
    public static void main(String[] args) throws SAXException, IOException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        MySAXHandler scoreHandler = (new SaxXMLParser()).new MySAXHandler();
        parser.setContentHandler(scoreHandler);
        parser.parse("E:/workspace/JavaCourse/test.xml");
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(scoreHandler.getmaxScore()));
        //  parserXml("E:/workspace/JavaCourse/test.xml");
    }
/*
    public static void parserXml(String fileName) {

        SAXParserFactory saxfac = SAXParserFactory.newInstance();

        try {

            SAXParser saxparser = saxfac.newSAXParser();
            InputStream is = new FileInputStream(fileName);
            saxparser.parse(is, new MySAXHandler());

        } catch (ParserConfigurationException e) {

            e.printStackTrace();

        } catch (SAXException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
*/


    class MySAXHandler extends DefaultHandler {

        private boolean hasAttribute = false;
        //private Attributes attributes = null;
        private double maxScore = 0;

        public double getmaxScore() {
            return maxScore;
        }

        public void startDocument() throws SAXException {
            //System.out.println("文档开始打印了");
        }

        public void endDocument() throws SAXException {
            //System.out.println("文档打印结束了");
        }

        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
      /*
        if (qName.equals("java-student")) {
            return;
        }
        if (qName.equals("student")) {
            return;
        }
        if (attributes.getLength() > 0) {
            this.attributes = attributes;
            this.hasAttribute = true;
        }
      */
            if (qName.equals("score")) {
                hasAttribute = true;
            }
        }

        public void endElement(String uri, String localName, String qName)
                throws SAXException {
        /*
        if (hasAttribute && (attributes != null)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(attributes.getQName(0) + ":"
                        + attributes.getValue(0));
            }
        }
        */
            if (hasAttribute) {
                hasAttribute = false;
            }
        }

        /**
         * @param ch
         * @param start
         * @param length
         * @throws SAXException
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            //System.out.print(new String(ch, start, length));
            if (hasAttribute) {
                double studentScore = Double.parseDouble(new String(ch, start, length));
                if(maxScore < studentScore){
                    maxScore = studentScore;
                }
            }
        }
    }
}
