package com.usemodj.forum;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.ibatis.io.Resources;
import org.apache.log4j.Logger;

public class Utils {
	private static Logger logger = Logger.getLogger( Utils.class);

	public static String sanitizeUser(String username, boolean strict) {
		//String rawUsername = username;
		username = stripTags( username, "");
		//Kill octets
		username = username.replaceAll("%([a-fA-F0-9][a-fA-F0-9])", "");
		username = username.replaceAll("&.+?;", ""); // Kill entities
		
		if( strict)
			username = username.replaceAll("(?i)[^a-z0-9 _.@-]", ""); // ignore case
		//Consolidate contiguous whitespace
		username = username.replaceAll("\\s+", " ");
		return username;
	}
	
    public static String stripTags(String text, String allowedTags) {
        String[] tag_list = allowedTags.split(",");
        Arrays.sort(tag_list);
 
        final Pattern p = Pattern.compile("<[/!]?([^\\\\s>]*)\\\\s*[^>]*>",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
 
        StringBuffer out = new StringBuffer();
        int lastPos = 0;
        while (m.find()) {
            String tag = m.group(1);
            // if tag not allowed: skip it
            if (Arrays.binarySearch(tag_list, tag) < 0) {
                out.append(text.substring(lastPos, m.start())).append(" ");
 
            } else {
                out.append(text.substring(lastPos, m.end()));
            }
            lastPos = m.end();
        }
        if (lastPos > 0) {
            out.append(text.substring(lastPos));
            return out.toString().trim();
        } else {
            return text;
        }
    }
    
    public static void sendMail(String subject, String text, String to) throws IOException, MessagingException{
    	Properties props = new Properties();
    	Reader reader = Resources.getResourceAsReader("mail.properties");    
    	props.load(reader);
    	//logger.debug("-- mail.properties mail.smtp.host: " + prop.getProperty("mail.smtp.host"));
    	String mailer = "sendhtml";
    	String from =  props.getProperty("mail.smtp.from");
    	//Get a Session object
    	Session session = Session.getInstance(props, null);
    	// construct the message
	    Message msg = new MimeMessage(session);
	    if (from != null)
	    	msg.setFrom( new InternetAddress(from.trim()));
	    else
	    	msg.setFrom();

	    msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse( to, false));
//	    if (cc != null)
//		msg.setRecipients(Message.RecipientType.CC,
//					InternetAddress.parse(cc, false));
//	    if (bcc != null)
//		msg.setRecipients(Message.RecipientType.BCC,
//					InternetAddress.parse(bcc, false));

	    msg.setSubject(subject);

	    collect(text, msg);

	    msg.setHeader("X-Mailer", mailer);
	    msg.setSentDate(new Date());

	    // send the thing off
	    Transport.send(msg);

	    System.out.println("\nMail was sent successfully.");

    }

	private static void collect(String text, Message msg) throws MessagingException, IOException {
		String subject = msg.getSubject();
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append(subject + "\n");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n");

		sb.append("<BODY>\n");
		sb.append("<H2>" + subject + "</H2>" + "\n");
		sb.append( text);
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		msg.setDataHandler(new DataHandler(
			new ByteArrayDataSource( sb.toString(), "text/html")));
	}
}
