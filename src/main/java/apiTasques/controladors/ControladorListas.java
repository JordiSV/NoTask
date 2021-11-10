package apiTasques.controladors;

import apiTasques.model.entitats.Lista;
import apiTasques.model.entitats.Tasca;
import apiTasques.model.serveis.ServeiListas;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorListas {

    private final ServeiListas serveiListas;

    //TODO
    //Amb l'exemple de l'altre controlador cal canviar el retorn d'aquests endpoints
    //pel seu corresponent retornant un ResponseEntity


    @GetMapping("/todolists")
    public List<Lista> llistarListas(){
        return serveiListas.llistarListas();
    }

    @GetMapping("/todolists/{id}")
    public ResponseEntity<?> consultarLista(@PathVariable String id)
    {
        Lista l = serveiListas.consultarLista(id);
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

    @DeleteMapping("/todolists/{id}")
    public ResponseEntity<?> eliminarLista(@PathVariable String id){
        Lista l = serveiListas.consultarLista(id);
        if (l == null)
            return ResponseEntity.notFound().build();
        else{
            serveiListas.eliminarLista(id);
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
}
