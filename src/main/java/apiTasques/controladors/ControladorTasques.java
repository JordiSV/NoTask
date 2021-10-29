package apiTasques.controladors;

import lombok.RequiredArgsConstructor;
import apiTasques.model.entitats.Tasca;
import apiTasques.model.serveis.ServeiTasques;
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
    public Tasca consultarTasca(@PathVariable String id)
    {
        return serveiTasques.consultarTasca(id);
    }

    @PostMapping("/tasques")
    public Tasca crearUsuari(@RequestBody Tasca nou){
        return serveiTasques.afegirTasca(nou);
    }

    @DeleteMapping("/tasques/{id}")
    public Tasca eliminarTasca(@PathVariable String id){
        return serveiTasques.eliminarTasca(id);
    }

    //per modificar una tasca existent
    @PutMapping("/tasques")
    public Tasca modificarTasca(@RequestBody Tasca mod){
        return serveiTasques.modificarTasca(mod);
    }
}
