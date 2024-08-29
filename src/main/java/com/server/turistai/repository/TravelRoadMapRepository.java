package com.server.turistai.repository;

import com.server.turistai.entities.TravelRoadMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRoadMapRepository extends JpaRepository<TravelRoadMap, Long> {
}
