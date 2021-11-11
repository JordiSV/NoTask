package apiTasques.controladors;

import apiTasques.model.entitats.Lista;
import apiTasques.model.serveis.ServeiListas;
import lombok.RequiredArgsConstructor;
import apiTasques.model.entitats.Tasca;
import apiTasques.model.serveis.ServeiTasques;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorTasques {
    private final ServeiTasques serveiTasques;
    private final ServeiListas serveiListas;

    //TODO
    //Amb l'exemple de l'altre controlador cal canviar el retorn d'aquests endpoints
    //pel seu corresponent retornant un ResponseEntity


    @GetMapping("/todoitems")
    public List<Tasca> llistarTasques(){
        return serveiTasques.llistarTasques();
    }

    @GetMapping("/todoitems/{id}")
    public ResponseEntity<?> consultarTasca(@PathVariable String id)
    {
        Tasca t = serveiTasques.consultarTasca(id);
        if (t!=null){
            return ResponseEntity.status(HttpStatus.OK).body(t);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> crearTasca(@PathVariable String idLlista,@RequestBody Tasca nou){
        Lista l = serveiListas.consultarLista(idLlista);
        if (l!=null){
            serveiTasques.afegirTasca(nou);
            l.getTasques().add(nou);
            return ResponseEntity.status(HttpStatus.CREATED).body(nou);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/todoitems/{id}")
    public ResponseEntity<?> eliminarTasca(@PathVariable String id){
        Tasca t = serveiTasques.consultarTasca(id);
        if (t == null)
            return ResponseEntity.notFound().build();
        else{
            serveiTasques.eliminarTasca(id);
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }
    }

    //per modificar una tasca existent
    @PutMapping("/todoitems")
    public ResponseEntity<?> modificarTasca(@RequestBody Tasca mod){
        Tasca t = serveiTasques.modificarTasca(mod);
        if (t != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }
}
