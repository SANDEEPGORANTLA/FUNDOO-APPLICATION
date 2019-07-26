//package com.bridgelabz.fundoo.utility;
//
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitSender {
//	
//	@Autowired
//	private AmqpTemplate rabbitTemplate;
//	
//	private String exchange = "note.exchange";
//	
//	private String routingkey = "fundoo";
//	
//	public void sendToRabbit(Mail mail) {
//		System.err.println(mail);
//		rabbitTemplate.convertAndSend(exchange, routingkey, mail);
//	}
//}
