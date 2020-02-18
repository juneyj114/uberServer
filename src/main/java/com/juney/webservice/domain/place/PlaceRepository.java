package com.juney.webservice.domain.place;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>{

	List<Place> findByUserId(Long id);

}
