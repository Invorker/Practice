package mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author jeff 完成邮箱相关配置
 */
public class Constant {
	private static Constant instance = null;

	public static synchronized Constant getInstance() {
		if (instance == null) {
			instance = new Constant();
		}
		return instance;
	}

	private String mailAddress;// 邮箱地址
	private String mailCount; // 邮箱用户名
	private String mailPassword;// 邮箱密码
	private String mailServer; // 邮箱smtp服务器

	/**
	 * 单态构造,读取文件邮箱配置信息
	 */
	private Constant() {
		init();
	}

	private void init() {
		Properties p = new Properties();
		try {
			p.load(Constant.class.getResourceAsStream("/email.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mailAddress = p.getProperty("mailAddress");
		mailCount = p.getProperty("mailCount");
		mailPassword = p.getProperty("mailPassword");
		mailServer = p.getProperty("mailServer");
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMailCount() {
		return mailCount;
	}

	public void setMailCount(String mailCount) {
		this.mailCount = mailCount;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	/*
	 * 测试
	 */
	public static void main(String[] args) {
		Constant c = Constant.getInstance();
		System.out.println(c.getMailAddress());
	}

}