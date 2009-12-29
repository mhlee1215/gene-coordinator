
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailManager extends HtmlEmail {
	private String host = "ml.ssu.ac.kr";
	private String id = "lmh";
	private String pwd = "mkaist2009";

	public String send() {
		setHostName(host);
		setAuthentication(id, pwd);
		try {
			return super.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] argv) {

		String receiver = "";
		String sender = "";
		String senderTitle = "";
		String subject = "";
		String content = "";

		if (argv.length != 5) {
			System.out
					.println("Usage : binFile <receiver> <sender> <senderTitle> <subject> <content>");
			return;
		}

		receiver = argv[0];
		sender = argv[1];
		senderTitle = argv[2];
		subject = argv[3];
		content = argv[4];

		// Create the email message
		EmailManager email = new EmailManager();
		try {
			email.addTo(receiver, "user");

			email.setFrom(sender, senderTitle);
			email.setSubject(subject);

			// embed the image and get the content id
			// URL url = new
			// URL("http://www.apache.org/images/asf_logo_wide.gif");
			// String cid = email.embed(url, "Apache logo");

			// set the html message
			// email.setHtmlMsg("<html>The apache logo - <img src=\"cid:"+cid+"\"></html>");

			// set the alternative message
			email.setTextMsg(content);

			// send the email
			email.send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
