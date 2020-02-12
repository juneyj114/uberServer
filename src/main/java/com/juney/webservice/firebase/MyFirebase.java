package com.juney.webservice.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.juney.webservice.domain.ride.Status;
import com.juney.webservice.web.dto.RideRequestMessageDto;
import com.juney.webservice.web.dto.RideSaveRequestDto;
import com.juney.webservice.web.dto.RideResponseDto;

@Service
public class MyFirebase {

	@PostConstruct
	public void StartFirebase() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(new FileInputStream(
						"C:/workspace-sts4/Springboot-UberClone/uberclone-8c7ac-firebase-adminsdk-ezghp-e9f180d5e0.json")))
				.setDatabaseUrl("https://uberclone-8c7ac.firebaseio.com").build();

		FirebaseApp.initializeApp(options);
	}

	public void sendMessage() throws FirebaseMessagingException {
		// This registration token comes from the client FCM SDKs.
		String registrationToken = "dezyVRiwpGk:APA91bE_RVmvl_3yJPUjChKJiJb-Rztmhfe3oBwIGW4WJRhe7HgzMkOYCrBGOilXlO2Z8qArHkrpOigScTTnQXbix9FsUoVUZgv-VezJc4-Te6TOrKlsrQer9GT6yNRU4Y7FopNm9taW";

		// See documentation on defining a message payload.
		Message message = Message.builder().putData("score", "850").putData("time", "2:45").setToken(registrationToken)
				.build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response = FirebaseMessaging.getInstance().send(message);
		// Response is a message ID string.
		System.out.println("Successfully sent message: " + response);
	}

	public void sendRideRequestMessage(List<String> registrationTokens, RideResponseDto dto)
			throws FirebaseMessagingException {
		for (String registrationToken : registrationTokens) {
			Message message = Message.builder().putData("fullName", dto.getPassenger().getFirstName()+" "+dto.getPassenger().getLastName())
					.setToken(registrationToken).build();

			String response = FirebaseMessaging.getInstance().send(message);

			System.out.println("Successfully sent message: " + response);
		}
	}

	public boolean sendStatusUpdateMessage(String registrationToken, Status status) {
		try {
			Message message = Message.builder().putData("status", status.name())
					.setToken(registrationToken).build();
			String response;
			response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Successfully sent message: " + response);
			return true;
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			return false;
		}

		
		
	}

	public boolean sendMessageToChat(String registrationToken, String text) {
		try {
			Message message = Message.builder().putData("text", text)
					.setToken(registrationToken).build();
			String response;
			response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Successfully sent message: " + response);
			return true;
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
