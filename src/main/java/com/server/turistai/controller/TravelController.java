package com.server.turistai.controller;

import com.server.turistai.config.FileStorageProperties;
import com.server.turistai.controller.dto.CreateTravelDto;
import com.server.turistai.entities.Travel;
import com.server.turistai.entities.User;
import com.server.turistai.repository.TravelRepository;
import com.server.turistai.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/travels")
public class TravelController {

    private final Path fileStorageLocation;
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;

    public TravelController(TravelRepository travelRepository, UserRepository userRepository,
            FileStorageProperties fileStorageProperties) {
        this.travelRepository = travelRepository;
        this.userRepository = userRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    @PostMapping("/criar")
    public ResponseEntity<String> createTravel(@ModelAttribute CreateTravelDto dto,
            @RequestParam(value = "file", required = false) MultipartFile file,
            JwtAuthenticationToken token) {

        var user = userRepository.findById(Integer.valueOf(token.getName()));
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        var travel = new Travel();
        travel.setUser(user.get());
        travel.setTitle(dto.getTitle());
        travel.setDescription(dto.getDescription());
        travel.setDate(dto.getDate());
        travel.setLocation(dto.getLocation());
        travel.setState(dto.getState());

        travelRepository.save(travel);
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = String.valueOf(travel.getId()) + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(fileStorageLocation.toString(), fileName);

                Files.write(filePath, file.getBytes());

                travel.setImage(fileName);
                travelRepository.save(travel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok("Viagem criada com sucesso.");
    }

    @GetMapping("/imagem/{imagem}")
    @ResponseBody
    public byte[] getImage(@PathVariable("imagem") String imagem) throws IOException {
        File imageFile = new File(fileStorageLocation.toString(), imagem);
        {
            return Files.readAllBytes(imageFile.toPath());
        }
    };

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

    @DeleteMapping("delete/{id}")
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
        travel.setLocation(dto.getLocation());
        travel.setState(dto.getState());

        travelRepository.save(travel);

        return ResponseEntity.ok(travel);
    }

}
