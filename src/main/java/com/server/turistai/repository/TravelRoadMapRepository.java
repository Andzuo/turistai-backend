package com.server.turistai.repository;

import com.server.turistai.entities.Travel;
import com.server.turistai.entities.TravelRoadMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRoadMapRepository extends JpaRepository<TravelRoadMap, Long> {

    List<TravelRoadMap> findByTravelId(Long travelId);

    Object findById(Travel travel);
}
