package question_9;

/**
 * Created by Jimmy on 2016/12/22.
 */

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.FileOutputStream;
import java.util.*;

public class MailClientSend {
    private Session session;
    private Transport transport;
    private String username = "ecnu_zz@163.com";
    private String password = "CoCk1234";

    public void init()throws Exception
    {
        Properties  props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.class", "com.sun.mail.smtp.SMTPTransport");
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");

        session = Session.getInstance(props,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true);

        transport = session.getTransport();
    }

    public void sendMessage(String fromAddr,String toAddr)throws Exception{
        Message msg = createSimpleMessage(fromAddr,toAddr);
        transport.send(msg);
    }

    public MimeMessage createSimpleMessage(String from,String to)throws Exception{

        String root_path = "E:/workspace/JavaCourse/";
        String img_name = "image1.jpg";
        //String body = "<h>Hello</h></br>"+"<img src=\"" + root_path + img_name+"\">";


        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSentDate(new Date());
        msg.setSubject("110张三");
        msg.setText("Hello");

        MimeMultipart multipart = new MimeMultipart("mixed");

        //Text Content
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent("Hello","text/html;charset=gb2312");

        //Image as an attachment
        MimeBodyPart imgBodyPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(root_path + img_name);
        imgBodyPart.setDataHandler(new DataHandler(fds));
        imgBodyPart.setFileName(img_name);


        multipart.addBodyPart(htmlBodyPart);
        multipart.addBodyPart(imgBodyPart);

        msg.setContent(multipart);
        msg.saveChanges();

        msg.writeTo(new FileOutputStream(root_path + "test.eml"));

        return msg;
    }
    public void close()throws Exception
    {
        transport.close();
    }

    public static void main(String[] args)throws Exception {

        MailClientSend client=new MailClientSend();
        client.init();
        client.sendMessage("ecnu_zz@163.com","ecnu_zz@163.com");
        client.close();
    }
}





