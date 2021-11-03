package apiTasques.controladors;

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

    //TODO
    //Amb l'exemple de l'altre controlador cal canviar el retorn d'aquests endpoints
    //pel seu corresponent retornant un ResponseEntity


    @GetMapping("/tasques")
    public List<Tasca> llistarTasques(){
        return serveiTasques.llistarTasques();
    }

    @GetMapping("/tasques/{id}")
    public ResponseEntity<?> consultarTasca(@PathVariable String id)
    {
        Tasca t = serveiTasques.consultarTasca(id);
        if (t != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/tasques")
    public ResponseEntity<Tasca> crearUsuari(@RequestBody Tasca nou){
        serveiTasques.afegirTasca(nou);
        return ResponseEntity.status(HttpStatus.CREATED).body(nou);
    }

    @DeleteMapping("/tasques/{id}")
    public ResponseEntity<?> eliminarTasca(@PathVariable String id){
        serveiTasques.eliminarTasca(id);
        return ResponseEntity.noContent().header("Content-Length", "0").build();
    }

    //per modificar una tasca existent
    @PutMapping("/tasques")
    public ResponseEntity<?> modificarTasca(@RequestBody Tasca mod){
        Tasca t = serveiTasques.modificarTasca(mod);
        if (t != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }
}
