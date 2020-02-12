package com.juney.webservice.domain.verification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification, Long>{

	Verification findByPayload(String payload);

	Verification findByPayloadAndKi(String phoneNumber, String key);

	boolean existsByPayloadAndVerifiedTrue(String phoneNumber);

}
