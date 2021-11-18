package apiTasques.model.serveis;

import apiTasques.model.entitats.Lista;
import apiTasques.model.entitats.Tasca;
import apiTasques.model.repositoris.RepositoriLista;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServeiListas {

    private final RepositoriLista repoLista;

    //llistar tots els ítems
    public List<Lista> llistarListas(){
        return repoLista.findAll();
    }

    //consultar ítem per id
    public Lista consultarLista(long id){
        return repoLista.findById(id).orElse(null);
    }

    //afegir ítem
    public Lista afegirLista(Lista it){
        return repoLista.save(it);
    }

    //modificar sencer, si existeix el canvia, sino retorna null
    public Lista modificarLista(Lista it){
        List<Tasca> tl = repoLista.getById(it.getIdLista()).getTasques();
        Lista aux = null;
        if(repoLista.existsById(it.getIdLista())){
            it.setTasques(tl);
            aux = repoLista.save(it);
        }
        return aux;
    }

    //eliminar ítem per id
    //si no existeix id retorna null
    public Lista eliminarLista(long id){
        Lista res= repoLista.findById(id).orElse(null);
        if(res!=null) repoLista.deleteById(id);
        return res;
    }

    public Lista afegirTasca(Tasca t, long idLista) {
        Lista list = repoLista.findById(idLista).orElse(null);
        if (list != null) {
            list.getTasques().add(t);
            modificarLista(list);
        }
        return list;
    }

    public Lista eliminarTasca(long idTasca, long idLista) {
        Lista l = repoLista.getById(idLista);
        l.getTasques().removeIf(t -> t.getIdTasca() == idTasca);
        modificarLista(l);
        return l;
    }

    public Tasca consultarTasca(long id, long idLista){
        Lista l = repoLista.getById(idLista);
        Tasca t = null;
        for (Tasca task : l.getTasques()) {
            if (task.getIdTasca() == id)
                t = task;
        }
        return t;
    }

    public List<Tasca> consultarTasquesFromLlista(long idLista) {
        return repoLista.findById(idLista).get().getTasques();
    }

    public Lista modificarTasca(long id, Tasca t) {
        Lista l = repoLista.getById(id);

        for (Tasca tascaOld : l.getTasques()) {
            if (tascaOld.getIdTasca() == id)
                tascaOld = t;
        }
        return l;
    }
}
