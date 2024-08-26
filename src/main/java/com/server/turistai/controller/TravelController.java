package com.server.turistai.controller;

import com.server.turistai.controller.dto.CreateTravelDto;
import com.server.turistai.entities.Travel;
import com.server.turistai.entities.User;
import com.server.turistai.repository.TravelRepository;
import com.server.turistai.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    private final TravelRepository travelRepository;
    private final UserRepository userRepository;

    public TravelController(TravelRepository travelRepository, UserRepository userRepository) {
        this.travelRepository = travelRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/criar")
    public ResponseEntity<Void> createTravel(@RequestBody CreateTravelDto dto, JwtAuthenticationToken token) {
        var user = userRepository.findById(Integer.valueOf(token.getName()));
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var travel = new Travel();
        travel.setUser(user.get());
        travel.setTitle(dto.getTitle());
        travel.setDescription(dto.getDescription());
        travel.setDate(dto.getDate());

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            travel.setImage(dto.getImage());
        }

        travelRepository.save(travel);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<Travel>> getAllTravels(JwtAuthenticationToken token) {
        Integer userId = Integer.valueOf(token.getName());

        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userOptional.get();
        List<Travel> travels = travelRepository.findByUser(user);

        return ResponseEntity.ok(travels);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravel(@PathVariable Long id, JwtAuthenticationToken token) {
        Integer userId = Integer.valueOf(token.getName());

        Optional<Travel> travelOptional = travelRepository.findById(id);
        if (travelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Travel travel = travelOptional.get();
        if (travel.getUser().getUserId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        travelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("edit/{id}")
    public ResponseEntity<Travel> updateTravel(@PathVariable Long id,
                                               @RequestBody CreateTravelDto dto,
                                               JwtAuthenticationToken token) {
        Integer userId = Integer.valueOf(token.getName());

        Optional<Travel> travelOptional = travelRepository.findById(id);
        if (travelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Travel travel = travelOptional.get();

        if (travel.getUser().getUserId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        travel.setTitle(dto.getTitle());
        travel.setDescription(dto.getDescription());
        travel.setDate(dto.getDate());
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            travel.setImage(dto.getImage());
        }

        travelRepository.save(travel);

        return ResponseEntity.ok(travel);
    }

}
