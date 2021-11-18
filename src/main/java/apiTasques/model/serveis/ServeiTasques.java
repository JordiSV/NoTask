package apiTasques.model.serveis;

import apiTasques.model.entitats.Lista;
import lombok.RequiredArgsConstructor;
import apiTasques.model.entitats.Tasca;
import apiTasques.model.repositoris.RepositoriTasques;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServeiTasques {
    private final RepositoriTasques repoTasques;

    //llistar tots els ítems
    public List<Tasca> llistarTasques(){
        return repoTasques.findAll();
    }

    //consultar ítem per id
    public Tasca consultarTasca(long id){
        return repoTasques.findById(id).orElse(null);
    }

    //afegir ítem
    public Tasca afegirTasca(Tasca it){
        return repoTasques.save(it);
    }

    //modificar sencer, si existeix el canvia, sino retorna null
    public Tasca modificarTasca(Tasca it){
        Tasca aux=null;
        if(repoTasques.existsById(it.getIdTasca())) aux= repoTasques.save(it);
        return aux;
    }

    //eliminar ítem per id
    //si no existeix id retorna null
    public Tasca eliminarTasca(long id){
        Tasca res= repoTasques.findById(id).orElse(null);
        if(res!=null)
            repoTasques.deleteById(id);
        return res;
    }

}
