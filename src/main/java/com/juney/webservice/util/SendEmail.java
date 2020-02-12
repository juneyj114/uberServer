package com.juney.webservice.util;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.*;

@Service
public class SendEmail {
	
	public void sendEmail(String key) throws IOException {
		Email from = new Email("acto2002@naver.com");
		String subject = "Verify your email";
		Email to = new Email("acto2002@naver.com");
		Content content = new Content("text/plain", "Please Copy And Paste \n" + key);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}

}
