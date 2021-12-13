package apiTasques.model.entitats;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Usuari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuari;
    private String user;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lista> listas;
}
