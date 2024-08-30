package com.server.turistai.controller;

import com.server.turistai.config.FileStorageProperties;
import com.server.turistai.controller.dto.TravelRoadMapDto;
import com.server.turistai.entities.TravelRoadMap;
import com.server.turistai.repository.TravelRepository;
import com.server.turistai.repository.TravelRoadMapRepository;
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
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TravelRoadMapController {

    private final TravelRepository travelRepository;
    private final TravelRoadMapRepository travelRoadMapRepository;
    private final UserRepository userRepository;
    private final Path fileStorageLocation;

    public TravelRoadMapController(TravelRepository travelRepository,
            TravelRoadMapRepository travelRoadMapRepository,
            UserRepository userRepository,
            FileStorageProperties fileStorageProperties) {
        this.travelRepository = travelRepository;
        this.travelRoadMapRepository = travelRoadMapRepository;
        this.userRepository = userRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    @PostMapping("/roadmap/{travelId}")
    public ResponseEntity<String> addRoadMapItem(@PathVariable Long travelId,
            @ModelAttribute TravelRoadMapDto roadMapDto,
            @RequestParam(value = "file", required = false) MultipartFile file,
            JwtAuthenticationToken token) {

        var user = userRepository.findById(Integer.valueOf(token.getName()));
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        var travel = travelRepository.findById(travelId);
        if (travel.isEmpty() || !travel.get().getUser().equals(user.get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Viagem não encontrada ou não pertence ao usuário.");
        }

        var roadMapItem = new TravelRoadMap();
        roadMapItem.setTitle(roadMapDto.getTitle());
        roadMapItem.setAddres(roadMapDto.getAddres());
        roadMapItem.setTravel(travel.get());

        travelRoadMapRepository.save(roadMapItem);

        // Upload de imagem
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(fileStorageLocation.toString(), fileName);

                if (!Files.exists(fileStorageLocation)) {
                    Files.createDirectories(fileStorageLocation);
                }

                Files.write(path, file.getBytes());

                roadMapItem.setImage(fileName);
                travelRoadMapRepository.save(roadMapItem);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar imagem.");
            }
        }

        return ResponseEntity.ok("Item do roadmap criado com sucesso.");
    }

    @GetMapping("/roadmap/{imagem}")
    @ResponseBody
    public byte[] getImage(@PathVariable("imagem") String imagem) throws IOException {
        File imageFile = new File(fileStorageLocation.toString(), imagem);
        {
            return Files.readAllBytes(imageFile.toPath());
        }
    };
}