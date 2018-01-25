package com.tlkble.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		/* RabbitMQ As Broker, using CloudAMQP as a cloud service */
		config.enableStompBrokerRelay("/queue", "/topic").setRelayHost("swan.rmq.cloudamqp.com")
				/* System admin login */
				.setSystemLogin("yxoxlrve").setSystemPasscode("toqH15Q0O4HtRetpTny2ccULd0QXwOaV")
				.setVirtualHost("yxoxlrve")
				/* for presentation purposes, client can login as system admin */
				.setClientLogin("yxoxlrve").setClientPasscode("toqH15Q0O4HtRetpTny2ccULd0QXwOaV");
		config.setApplicationDestinationPrefixes("/app");
	}

	/*
	 * When we create a connection from client, this is the URL which clients
	 * connect to Websocket URL prefix
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/events").withSockJS().setSessionCookieNeeded(true);
	}
}