//package com.bridgelabz.fundoo.config;
//
//
//import javax.sound.midi.Receiver;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//
////import com.bridgelabz.fundoo.utility.RabbitReceiver;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//
//@Configuration
//public class RabbitmqConfigure 
//{
//	//@Value("${fundoo.rabbitmq.queue}")
//	String queueName = "note.queue";
//
//	//@Value("${fundoo.rabbitmq.exchange}")
//	String exchange = "note.exchange";
//
//	//@Value("${fundoo.rabbitmq.routingkey}")
//	private String routingkey = "fundoo";
//
//	@Bean
//	Queue queue() {
//		return new Queue(queueName, false);
//	}
//
//	@Bean
//	DirectExchange exchange() {
//		return new DirectExchange(exchange);
//	}
//
//	@Bean
//	Binding binding(Queue queue, DirectExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
//	}
//
////	@Bean
////	public MessageConverter jsonMessageConverter() {
////		return new Jackson2JsonMessageConverter();
////	}
//	
//	@Bean
//	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
//	    return new Jackson2JsonMessageConverter(objectMapper());
//	}
//	
//	 @Bean public ObjectMapper objectMapper() { return new ObjectMapper(); }
//
//	
////	@Bean
////	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
////	final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
////		rabbitTemplate.setMessageConverter(jsonMessageConverter());
////		return rabbitTemplate;
////	}
////	@Bean
////	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
////	final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
////	rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
////	return rabbitTemplate;
////	}
//
//
//	
//}
