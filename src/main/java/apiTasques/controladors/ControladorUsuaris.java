package apiTasques.controladors;

import apiTasques.model.entitats.Lista;
import apiTasques.model.entitats.Usuari;
import apiTasques.model.serveis.ServeiUsuari;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/users")
    public ResponseEntity<?> crearUsuari(@RequestBody Usuari nou){
        serveiUsuari.afegirUsuari(nou);
        return ResponseEntity.status(HttpStatus.CREATED).body(nou);
    }

    @DeleteMapping("/users/{idUsuari}")
    public ResponseEntity<?> eliminarUsuari(@PathVariable long idUsuari){
        Usuari u = serveiUsuari.consultarUsuari(idUsuari);
        if (u == null){
            return ResponseEntity.notFound().build();
        }else{
            serveiUsuari.eliminarUsuari(idUsuari);
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }
    }

    @PutMapping("/users")
    public ResponseEntity<?> modificarLista(@RequestBody Usuari mod){
        Usuari us = serveiUsuari.modificarUsuari(mod);
        if (us != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    public Usuari agefirLlista(Lista l, long id){
        return serveiUsuari.afegitLlista(l,id);
    }

}
