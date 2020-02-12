package com.juney.webservice.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>{

	Chat findByDriverIdOrPassengerId(Long driverId, Long passengerId);

}
