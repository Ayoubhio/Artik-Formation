package com.springpart3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(path="/personne")
public class PersonneRestClient
{
    RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/all")
    public List<Personne> getAllPersonnes()
    {
        List<Personne> personnes = restTemplate.getForObject("http://localhost:8088/personne/all", List.class);
        return personnes;
    }

    @GetMapping("/plusJeune")
    public Personne getPlusJeune()
    {
        Personne personne = restTemplate.getForObject("http://localhost:8088/personne/plusJeune", Personne.class);
        return personne;
    }

    @GetMapping("/plusVieille")
    public Personne getPlusVieille()
    {
        Personne personne = restTemplate.getForObject("http://localhost:8088/personne/plusVieille", Personne.class);
        return personne;
    }

}
