package apiTasques.model.serveis;

import antlr.collections.impl.LList;
import apiTasques.model.entitats.Lista;
import apiTasques.model.entitats.Tasca;
import apiTasques.model.entitats.Usuari;
import apiTasques.model.repositoris.RepositoriUsuari;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServeiUsuari {

    private final RepositoriUsuari repoUsuari;

    //llistar tots els items
    public List<Usuari> llistarUsuaris(){
        return repoUsuari.findAll();
    }

    //llistar un item de la llista
    public Usuari consultarUsuari(long id){
        return repoUsuari.findById(id).orElse(null);
    }

    //afegit item
    public Usuari afegirUsuari(Usuari us){
        return repoUsuari.save(us);
    }

    //modificar sencer, si existeix el canvia, sino retorna null
    public Usuari modificarUsuari(Usuari us){
        List<Lista> ul = repoUsuari.getById(us.getIdUsuari()).getListas();
        Usuari aux = null;
        if (repoUsuari.existsById(us.getIdUsuari())){
            us.setListas(ul);
            aux = repoUsuari.save(us);
        }
        return aux;
    }

    //eliminar ítem per id
    //si no existeix id retorna null
    public Usuari eliminarUsuari(long id){
        Usuari res = repoUsuari.findById(id).orElse(null);
        if (res != null) repoUsuari.deleteById(id);
        return res;
    }

    public Usuari afegitLlista(Lista l, long idUsu){
        Usuari us = repoUsuari.findById(idUsu).orElse(null);
        if (us != null){
            us.getListas().add(l);
            modificarUsuari(us);
        }
        return us;
    }

    public Usuari eliminarLlista(long idLista, long idUsuari){
        Usuari us = repoUsuari.getById(idUsuari);
        us.getListas().removeIf(t -> t.getIdLista() == idLista);
        return us;
    }

    public Lista consultarLlista(long idLlista, long idUsuari){
        Usuari us = repoUsuari.getById(idUsuari);
        Lista l = null;
        for (Lista list : us.getListas()){
            if (list.getIdLista() == idLlista){
                l = list;
            }
        }
        return l;
    }

    public List<Lista> consultarTotesLesLlistes(long idUsuari){
        return repoUsuari.findById(idUsuari).get().getListas();
    }

}
