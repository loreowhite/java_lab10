package com.example.messagingrabbitmq;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println("Sending message...");
		Scanner scanner = new Scanner(System.in);
		String message;
		while(true){
			message=scanner.nextLine();
			rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.ExchangeName,MessagingRabbitmqApplication.queueName, message);
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		}
	}
}
