package apiTasques.model.entitats;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLista;
    private String nom;
    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tasca> tasques;
}
