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
    public Lista consultarLista(String id){
        return repoLista.findById(id).orElse(null);
    }

    //afegir ítem
    public Lista afegirLista(Lista it){
        return repoLista.save(it);
    }

    //modificar sencer, si existeix el canvia, sino retorna null
    public Lista modificarLista(Lista it){
        Lista aux = null;
        if(repoLista.existsById(it.getIdLista())) aux = repoLista.save(it);
        return aux;
    }

    //eliminar ítem per id
    //si no existeix id retorna null
    public Lista eliminarLista(String id){
        Lista res= repoLista.findById(id).orElse(null);
        if(res!=null) repoLista.deleteById(id);
        return res;
    }
}
