package apiTasques.controladors;

import apiTasques.model.entitats.Lista;
import apiTasques.model.entitats.Tasca;
import apiTasques.model.entitats.Usuari;
import apiTasques.model.serveis.ServeiListas;
import apiTasques.model.serveis.ServeiUsuari;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorListas {
    private final ServeiListas serveiListas;
    private final ServeiUsuari serveiUsuari;

    //TODO
    //Amb l'exemple de l'altre controlador cal canviar el retorn d'aquests endpoints
    //pel seu corresponent retornant un ResponseEntity


    @GetMapping("/todolists")
    public List<Lista> llistarListas(){
        return serveiListas.llistarListas();
    }

    @GetMapping("/todolists/{idLlista}")
    public ResponseEntity<?> consultarLista(@PathVariable long idLlista)
    {
        Lista l = serveiListas.consultarLista(idLlista);
        if (l != null){
            return ResponseEntity.status(HttpStatus.OK).body(l);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/todolists")
    public ResponseEntity<Lista> crearLista(@RequestBody Lista nou){
        serveiListas.afegirLista(nou);
        return ResponseEntity.status(HttpStatus.CREATED).body(nou);
    }

    @DeleteMapping("/todolists/{idLlista}")
    public ResponseEntity<?> eliminarLista(@PathVariable long idLlista){
        serveiListas.eliminarLista(idLlista);
        return ResponseEntity.ok().build();
    }

    //per modificar una tasca existent
    @PutMapping("/todolists")
    public ResponseEntity<?> modificarLista(@RequestBody Lista mod){
        Lista l = serveiListas.modificarLista(mod);
        if (l != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    public Lista afegirTasca(Tasca t, long id) {
        return serveiListas.afegirTasca(t, id);
    }

    @GetMapping("/users/{idUsuari}/todolists")
    public ResponseEntity<?> llistarLlistesUsuari(@PathVariable long idUsuari){
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            List<Lista> listas = serveiUsuari.consultarTotesLesLlistes(idUsuari);
            if (listas != null) {
                return ResponseEntity.status(HttpStatus.OK).body(listas);
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/users/{idUsuari}/todolists/{idLlista}")
    public ResponseEntity<?> consultarLlistaUsuari(@PathVariable long idUsuari, @PathVariable long idLlista){
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            Lista l = serveiUsuari.consultarLlista(idUsuari, idLlista);
            if (l != null) {
                return ResponseEntity.status(HttpStatus.OK).body(l);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/users/{idUsuari}/todolists")
    public ResponseEntity<?> crearLlistaUsuari(@PathVariable long idUsuari, @RequestBody Lista nou){
        serveiListas.afegirLista(nou);
        Usuari u = serveiUsuari.afegitLlista(nou, idUsuari);
        if (u != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(nou);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/users/{idUsuari}/todolists/{idLlista}")
    public ResponseEntity<?> deleteLlistUsuari(@PathVariable long idUsuari, @PathVariable long idLlista){
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            Usuari us1 = serveiUsuari.eliminarLlista(idUsuari, idLlista);
            if (us1 == null) {
                return ResponseEntity.notFound().build();
            } else {
                Lista l = serveiListas.eliminarLista(idLlista);
                if (l != null) {
                    return ResponseEntity.noContent().header("Content-Length", "0").build();
                }else{
                    return ResponseEntity.notFound().build();
                }
            }
        }else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{idUsuari}/todolists")
    public ResponseEntity<?> modificarLlistaUsuari(@PathVariable long idUsuari, @RequestBody Lista mod){
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            Lista l = serveiListas.modificarLista(mod);
            if (l != null) {
                Usuari us1 = serveiUsuari.modificarLlista(idUsuari, mod);
                return ResponseEntity.ok().build();
            } else
                return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.notFound().build();
        }

    }
}
