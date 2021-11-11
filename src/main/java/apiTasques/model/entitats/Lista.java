package apiTasques.model.entitats;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Lista {
    @Id
    private String idLista;
    private String nom;
    private String type;

    @OneToMany()
    private List<Tasca> tasques;
}
