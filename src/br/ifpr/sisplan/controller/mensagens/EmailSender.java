package br.ifpr.sisplan.controller.mensagens;

import br.ufrn.arq.email.Mail;

public class EmailSender implements Runnable {
	private String subject, message, email;
	
	public EmailSender(String subject, String message, String email) {
		this.subject = subject;
		this.message = message;
		this.email = email;
	}
	
	public void run() {
		 Mail.sendMessage("SISPlan", email, subject, message);
	}
}
