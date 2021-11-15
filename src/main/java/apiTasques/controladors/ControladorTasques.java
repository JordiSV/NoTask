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
    private final ControladorListas controladorListas;

    //
    @GetMapping("/todoitems")
    public ResponseEntity<?> llistarTasques(){
        List<Tasca> l = serveiTasques.llistarTasques();
        if (l != null){
            return ResponseEntity.ok(l);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/todolists/{idLista}/todoitems")
    public ResponseEntity<?> llistarTasquesLista(@PathVariable String idLista){
        List<Tasca> tascas = serveiListas.consultarTasquesFromLlista(idLista);
        if (tascas != null) {
            return ResponseEntity.status(HttpStatus.OK).body(tascas);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/todolists/{idLlista}/todoitems/{id}")
    public ResponseEntity<?> consultarTasca(@PathVariable String idList, @PathVariable String id)
    {
        Tasca t = serveiListas.consultarTasca(id, idList);
        if (t != null) {
            return ResponseEntity.status(HttpStatus.OK).body(t);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> crearTasca(@PathVariable String idLlista,@RequestBody Tasca nou){
        serveiTasques.afegirTasca(nou);
        Lista l = controladorListas.afegirTasca(nou, idLlista);
        if (l != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(nou);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/todolists/{idLlista}/todoitems/{id}")
    public ResponseEntity<?> eliminarTasca(@PathVariable String idLlista, @PathVariable String id){
        Lista l = serveiListas.eliminarTasca(id, idLlista);
        if (l == null)
            return ResponseEntity.notFound().build();
        else{
            Tasca t = serveiTasques.eliminarTasca(id);
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }
    }

    //per modificar una tasca existent
    @PutMapping("/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> modificarTasca(@PathVariable String idLlista, @RequestBody Tasca mod){
        Tasca t = serveiTasques.modificarTasca(mod);
        if (t != null) {
            Lista l = serveiListas.modificarTasca(idLlista, mod);
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.notFound().build();
    }
}
