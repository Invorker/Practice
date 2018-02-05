package mail;

import java.util.Date;
import java.util.Properties;

/**
 * 邮件发送类
 * 
 * @author jeff
 * 
 */
public class Mail {
	private String host;
	private String auth;
	private String username;
	private String domainUser;
	private String password;
	private Constant c;

	public boolean send(String[] to, String[] cc, String[] bcc, String subject, String content)
			throws javax.mail.MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", auth);
		javax.mail.Session s = javax.mail.Session.getInstance(props);
		// s.setDebug(true);

		javax.mail.internet.MimeMessage message = new javax.mail.internet.MimeMessage(s);

		javax.mail.internet.InternetAddress from = new javax.mail.internet.InternetAddress(username);
		message.setFrom(from);
		javax.mail.internet.InternetAddress[] Toaddress = new javax.mail.internet.InternetAddress[to.length];
		for (int i = 0; i < to.length; i++)
			Toaddress[i] = new javax.mail.internet.InternetAddress(to[i]);
		message.setRecipients(javax.mail.Message.RecipientType.TO, Toaddress);

		if (cc != null) {
			javax.mail.internet.InternetAddress[] Ccaddress = new javax.mail.internet.InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++)
				Ccaddress[i] = new javax.mail.internet.InternetAddress(cc[i]);
			message.setRecipients(javax.mail.Message.RecipientType.CC, Ccaddress);
		}

		if (bcc != null) {
			javax.mail.internet.InternetAddress[] Bccaddress = new javax.mail.internet.InternetAddress[bcc.length];
			for (int i = 0; i < bcc.length; i++)
				Bccaddress[i] = new javax.mail.internet.InternetAddress(bcc[i]);
			message.setRecipients(javax.mail.Message.RecipientType.BCC, Bccaddress);
		}
		// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
		message.setSentDate(new Date());

		javax.mail.BodyPart mdp = new javax.mail.internet.MimeBodyPart();
		mdp.setHeader("Content-Transfer-Encoding", "base64");
		mdp.setContent(content, "text/plain;charset=GBK");
		javax.mail.Multipart mm = new javax.mail.internet.MimeMultipart();
		mm.addBodyPart(mdp);
		message.setContent(mm);
		message.setSubject(subject);

		message.saveChanges();
		javax.mail.Transport transport = s.getTransport("smtp");
		transport.connect(host, (null == domainUser) ? username : domainUser, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		return true;
	}

	/* 构造读取配置文件 */
	public Mail() {
		super();
		c = c.getInstance();
		this.host = c.getMailServer();
		this.auth = "true";
		this.domainUser = c.getMailCount();
		this.username = c.getMailAddress();
		this.password = c.getMailPassword();
	}

	public static void main(String[] args) throws Exception {
		Mail m = new Mail();
		m.send(new String[] { "ChenTao@sansi.com" }, null, null, "放假安排", "10月1日到10月8日放假");

	}
}