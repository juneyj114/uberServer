package com.juney.webservice.domain.ride;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juney.webservice.domain.user.User;

public interface rideRepository extends JpaRepository<Ride, Long>{

	@Query("select r from Ride as r where r.status = 'Requesting'")
	List<Ride> findNearbyRides();

	Ride findByPassengerIdAndDriverId(Long passengerId, Long driverId);

	Ride findByPassengerIdAndStatus(Long id, Status requesting);

}
