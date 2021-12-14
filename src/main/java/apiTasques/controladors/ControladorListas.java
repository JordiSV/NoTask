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
    private final ControladorUsuaris controladorUsuaris;

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
        Lista l = serveiListas.consultarLista(idLlista);
        if (l == null)
            return ResponseEntity.notFound().build();
        else{
            serveiListas.eliminarLista(idLlista);
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }
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
        List<Lista> listas = serveiUsuari.consultarTotesLesLlistes(idUsuari);
        if (listas != null){
            return ResponseEntity.status(HttpStatus.OK).body(listas);
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/users/{idUsuari}/todolists/{idLlista}")
    public ResponseEntity<?> consultarLlista(@PathVariable long idUsuari, @PathVariable long idLlista){
        Lista l = serveiUsuari.consultarLlista(idUsuari, idLlista);
        if (l != null){
            return ResponseEntity.status(HttpStatus.OK).body(l);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/users/{idUsuari}/todolists")
    public ResponseEntity<?> crearLlista(@PathVariable long idUsuari, @RequestBody Lista nou){
        serveiListas.afegirLista(nou);
        Usuari u = controladorUsuaris.agefirLlista(nou, idUsuari);
        if (u != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(nou);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
