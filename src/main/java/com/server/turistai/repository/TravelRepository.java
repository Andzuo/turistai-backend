package com.server.turistai.repository;

import com.server.turistai.entities.Travel;
import com.server.turistai.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findByUser(User user);

    void deleteById(Long id);
}
