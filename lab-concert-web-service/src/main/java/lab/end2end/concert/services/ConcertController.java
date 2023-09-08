package lab.end2end.concert.services;

import java.util.List;
import java.util.Optional;

import lab.end2end.concert.domain.Concert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConcertController {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcertController.class);

    // TODO: add repository
    @Autowired
    private ConcertRepository concertRepository;


    // TODO: add @GET and @Path
    @GetMapping("/concerts/{id}")
    public ResponseEntity<Concert> retrieveConcert(@PathVariable long id) { // TODO: add @PathVariable for id

        // TODO: find concert by ID suing em.find(...
        Optional<Concert> optionalConcert = concertRepository.findById(id);
        // TODO: Handle the case when no entity is found

        if (optionalConcert.isPresent()) {
            return new ResponseEntity<>(optionalConcert.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // TODO: add @GET and @Path
    @GetMapping("/concerts")
    public ResponseEntity<List<Concert>> retrieveAllConcert() {
        // TODO: get all concert
        List<Concert> concerts = concertRepository.findAll();
        return new ResponseEntity<>(concerts, HttpStatus.OK);

    }

    // TODO: add proper annotation Post verb
    @PostMapping("/concerts")
    public ResponseEntity<String> createConcert(@RequestBody Concert concert) { // add @ResponseBody

        // TODO save concert to database using repository
        if (concert != null) {
            concertRepository.save(concert);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/concerts")
    public ResponseEntity<String> updateConcert(@RequestBody Concert concert) { // add @ResponseBody

        // TODO update concert using em.merge(..
        if (concert != null && concert.getId() != null) {
            if (concertRepository.existsById(concert.getId())) {
                concertRepository.save(concert);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: add annotation for Delete verb and and @Path for id
    @DeleteMapping("/concerts/{id}")
    public ResponseEntity<String> delete( @PathVariable long id) { // TODO: add @PathVariable for id

        // TODO: delete concert using em.remove

        // TODO: Return a HTTP 404 response if the specified Concert isn't found.

        if (concertRepository.existsById(id)) {
            concertRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // TODO: add annotation for Delete verb
    @DeleteMapping("/concerts")
    public ResponseEntity<String> deleteAllConcerts() {

        // TODO: query to get all concerts into a list using guideline in the reference

        concertRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
