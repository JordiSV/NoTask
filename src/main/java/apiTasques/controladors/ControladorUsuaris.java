package apiTasques.controladors;

import apiTasques.model.entitats.Lista;
import apiTasques.model.entitats.Usuari;
import apiTasques.model.serveis.ServeiUsuari;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorUsuaris {

    private final ServeiUsuari serveiUsuari;

    @GetMapping("/users")
    public List<Usuari> llistarUsuari(){
        return serveiUsuari.llistarUsuaris();
    }

    @GetMapping("/users/{idUsuari}")
    public ResponseEntity<?> consultarUsuari(@PathVariable long idUsuari){
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null){
            return ResponseEntity.status(HttpStatus.OK).body(us);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
