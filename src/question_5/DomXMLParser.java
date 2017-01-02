package question_5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Created by Jimmy on 2016/12/24.
 */
public class DomXMLParser {
    public static void main(String args[]) {
        ReadXml("E:/workspace/JavaCourse/test.xml");
    }

    private static void ReadXml(String FilePath) {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //Create a new instance of DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Load document
            Document document = db.parse(FilePath);

            NodeList students = document.getChildNodes();

            double max = 0;
            //<java-student>
            for (int i = 0; i < students.getLength(); i++) {

                Node user = students.item(i);
                NodeList studentInfo = user.getChildNodes();
                //<student>
                for (int j = 0; j < studentInfo.getLength(); j++) {

                    Node node = studentInfo.item(j);
                    NodeList studentMeta = node.getChildNodes();
                    //<name> and <score>
                    for (int k = 0; k < studentMeta.getLength(); k++) {

                        if (studentMeta.item(k).getNodeName() != "#text")
                            if(studentMeta.item(k).getNodeName()=="score") {
                                double temp = Double.parseDouble(studentMeta.item(k).getTextContent());
                                if(temp > max){
                                    max = temp;
                                }
                            }
                    }

                }

            }

            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println(df.format(max));

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (ParserConfigurationException e) {

            e.printStackTrace();

        } catch (SAXException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}