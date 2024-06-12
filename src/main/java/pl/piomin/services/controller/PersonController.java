package pl.piomin.services.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.domain.Person;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final Logger LOG = LoggerFactory.getLogger(PersonController.class);
    private final List<Person> objs = new ArrayList<>();

    @GetMapping
    public List<Person> findAll() {
        return objs;
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id) {
        Person obj = objs.stream().filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();
        LOG.info("Found: {}", obj.getId());
        return obj;
    }

    @PostMapping
    public Person add(@RequestBody Person obj) {
        obj.setId((long) (objs.size() + 1));
        LOG.info("Added: {}", obj);
        objs.add(obj);
        return obj;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Person obj = objs.stream().filter(it -> it.getId().equals(id)).findFirst().orElseThrow();
        objs.remove(obj);
        LOG.info("Removed: {}", id);
    }

    @PutMapping
    public void update(@RequestBody Person obj) {
        Person objTmp = objs.stream()
                .filter(it -> it.getId().equals(obj.getId()))
                .findFirst()
                .orElseThrow();
        objs.set(objs.indexOf(objTmp), obj);
        LOG.info("Updated: {}", obj.getId());
    }

}