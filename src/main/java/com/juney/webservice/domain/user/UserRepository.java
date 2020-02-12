package com.juney.webservice.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByFbId(Long fbId);
	
	Optional<User> findByEmail(String email);

	User findByPhoneNumber(String phoneNumber);

	@Query(value = "SELECT * FROM user WHERE isDriving = true", nativeQuery = true)
	List<User> getNearbyDrivers(float lat, float lng);

	List<User> findByRole(Role driver);
	
}
