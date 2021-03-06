/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.activemq;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer implements CommandLineRunner {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;

	@Autowired
	private Topic topic;

	@Override
	public void run(String... args) throws Exception {
		send("Sample message");
	}

	public void send(String msg) {
		this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
		this.jmsMessagingTemplate.convertAndSend(this.topic, msg);
		System.out.println("Message was sent to the Queue");
		
		boolean publish = true;
		if (publish) {
			for(int i = 0; i < 2; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				String evt = "msg " + i;
				this.jmsMessagingTemplate.convertAndSend(this.queue, evt);
				this.jmsMessagingTemplate.convertAndSend(this.topic, evt);
				System.out.println("Message was sent to the Queue & Topic");
			}
		}
	}

}
