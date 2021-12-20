package apiTasques.controladors;

import apiTasques.model.entitats.Lista;
import apiTasques.model.entitats.Usuari;
import apiTasques.model.serveis.ServeiListas;
import apiTasques.model.serveis.ServeiUsuari;
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
    private final ServeiUsuari serveiUsuari;

    //
    @GetMapping("/todoitems")
    public ResponseEntity<?> llistarTasques() {
        List<Tasca> l = serveiTasques.llistarTasques();
        if (l != null) {
            return ResponseEntity.ok(l);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> llistarTasquesLista(@PathVariable long idLlista) {
        List<Tasca> tascas = serveiListas.consultarTasquesFromLlista(idLlista);
        if (tascas != null) {
            return ResponseEntity.status(HttpStatus.OK).body(tascas);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/todolists/{idLlista}/todoitems/{idTasca}")
    public ResponseEntity<?> consultarTasca(@PathVariable long idLlist, @PathVariable long idTasca) {
        Tasca t = serveiListas.consultarTasca(idTasca, idLlist);
        if (t != null) {
            return ResponseEntity.status(HttpStatus.OK).body(t);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> crearTasca(@PathVariable long idLlista, @RequestBody Tasca nou) {
        serveiTasques.afegirTasca(nou);
        Lista l = serveiListas.afegirTasca(nou, idLlista);
        if (l != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(nou);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/todolists/{idLlista}/todoitems/{idTasca}")
    public ResponseEntity<?> eliminarTasca(@PathVariable long idLlista, @PathVariable long idTasca) {
        Lista l = serveiListas.eliminarTasca(idTasca, idLlista);
        if (l == null)
            return ResponseEntity.notFound().build();
        else {
            Tasca t = serveiTasques.eliminarTasca(idTasca);
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }
    }

    //per modificar una tasca existent
    @PutMapping("/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> modificarTasca(@PathVariable long idLlista, @RequestBody Tasca mod) {
        Tasca t = serveiTasques.modificarTasca(mod);
        if (t != null) {
            Lista l = serveiListas.modificarTasca(idLlista, mod);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{idUsuari}/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> llistarTasquesLlistesUsuari(@PathVariable long idUsuari, @PathVariable long idLlista) {
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            boolean inside = false;
            for (int i = 0; i < us.getListas().size(); i++) {
                if (idLlista == us.getListas().get(i).getIdLista()){
                    inside = true;
                }
            }
            if (inside) {
                List<Tasca> tascas = serveiUsuari.consultarTasquesLListaUsuari(idUsuari, idLlista);
                if (tascas != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(tascas);
                } else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/users/{idUsuari}/todolists/{idLlista}/todoitems/{idTasca}")
    public ResponseEntity<?> llistarTascaLlistesUsuari(@PathVariable long idUsuari, @PathVariable long idLlista, @PathVariable long idTasca) {
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            boolean inside = false;
            for (int i = 0; i < us.getListas().size(); i++) {
                if (idLlista == us.getListas().get(i).getIdLista()){
                    inside = true;
                }
            }
            if (inside){
                Tasca t = serveiListas.consultarTasca(idTasca, idLlista);
                if (t != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(t);
                } else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/users/{idUsuari}/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> crearTascaLlistaUsuari(@PathVariable long idUsuari, @PathVariable long idLlista, @RequestBody Tasca nou) {
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            Lista l = serveiListas.afegirTasca(nou, idLlista);
            if (l != null) {
                serveiTasques.afegirTasca(nou);
                return ResponseEntity.status(HttpStatus.CREATED).body(nou);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/users/{idUsuari}/todolists/{idLlista}/todoitems/{idTasca}")
    public ResponseEntity<?> deleteTascaLlistaUsuari(@PathVariable long idUsuari, @PathVariable long idLlista, @PathVariable long idTasca) {
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            boolean inside = false;
            for (int i = 0; i < us.getListas().size(); i++) {
                if (idLlista == us.getListas().get(i).getIdLista()){
                    inside = true;
                }
            }
            if (inside) {
                inside = false;
                Lista delete = serveiListas.eliminarTasca(idTasca, idLlista);
                if (delete == null)
                    return ResponseEntity.notFound().build();
                else {
                    for (int i = 0; i < delete.getTasques().size(); i++) {
                        if (idTasca == delete.getTasques().get(i).getIdTasca()){
                            inside = true;
                        }
                    }
                    if (inside) {
                        Tasca t = serveiTasques.eliminarTasca(idTasca);
                        if (t != null) {
                            return ResponseEntity.noContent().header("Content-Length", "0").build();
                        } else
                            return ResponseEntity.notFound().build();
                    }else {
                        return ResponseEntity.notFound().build();
                    }
                }
            } else
                return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/users/{idUsuari}/todolists/{idLlista}/todoitems")
    public ResponseEntity<?> modificarTascaLlistaUsuari(@PathVariable long idUsuari, @PathVariable long idLlista, @RequestBody Tasca mod) {
        Usuari us = serveiUsuari.consultarUsuari(idUsuari);
        if (us != null) {
            boolean inside = false;
            for (int i = 0; i < us.getListas().size(); i++) {
                if (idLlista == us.getListas().get(i).getIdLista()){
                    inside = true;
                }
            }
            if (inside) {
                Tasca t = serveiTasques.modificarTasca(mod);
                if (t != null) {
                    Lista l = serveiListas.modificarTasca(idLlista, mod);
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
