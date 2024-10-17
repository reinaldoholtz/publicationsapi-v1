package io.github.publications.publicationsapi.aplication.publications;

import io.github.publications.publicationsapi.domain.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/publications")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
public class PublicationController {

    private final PublicationService service;
    private final PublicationMapper mapper;

//    @PostMapping
//    public ResponseEntity save(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("name") String name,
//            @RequestParam("tags") List<String> tags) throws IOException {
//        log.info("Imagem recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());
//
//        Publication publication = mapper.mapToPublication(file,name,tags);
//        Publication savePublication = service.save(publication);
//        URI publicationUri = buildPublicationURL(savePublication);
//
//        return ResponseEntity.created(publicationUri).build();
//    }

    @GetMapping("{id}")
    public ResponseEntity<PublicationDTO> getPublication(@PathVariable String id){
        var possiblePublication = service.getById(id);
        if (possiblePublication.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var publication = possiblePublication.get();
        return  ResponseEntity.ok(mapper.publicationToDTO(publication));
    }

    // localhost:8080/v1/publications?query=Nature
    @GetMapping
    public ResponseEntity<List<PublicationDTO>> search(
             @RequestParam(value = "query", required = false) String query){

        var result = service.getByDocument(query);

        var publications= result.stream().map(publication -> {
            return  mapper.publicationToDTO(publication);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(publications);
    }
}
