//package com.bridgelabz.fundoo.utility;
//
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//import com.bridgelabz.fundoo.utility.Mail;
//@Component
//public class RabbitReceiver {
//	@Autowired
//	private JavaMailSender mailSender;
////	@Autowired
////	EmailService emailservice;
//	
//	@Autowired
//	private JavaMailSender emailSender;
//	
//	 @RabbitListener(queues = "note.queue")
//	public void receiveMessage(Mail mail) {
//		System.out.println("in receiver"+mail);
		//emailservice.sendMail(mail.getFrom(),mail.getTo(), mail.getSubject(), mail.getContent());
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setSubject(mail.getFrom());
//		message.setText(mail.getTo());
//		message.setTo(mail.getSubject());
//		message.setFrom(mail.getContent());
//		emailSender.send(message);
//		MimeMessage msg = mailSender.createMimeMessage();
//		MimeMessageHelper helper;
//		try {
//			helper = new MimeMessageHelper(msg, true);
//			helper.setSubject(mail.getSubject());
//			helper.setTo(mail.getTo());
//			helper.setText(mail.getContent(), true);
//			//rabbitSender.sendToRabbit(msg);
//			mailSender.send(msg);
//			
//			System.out.println("sent mail");
//			
//		} catch (MessagingException | MailException e) {
//			System.out.println("" + e.getMessage());
//		}
//	}
//}
