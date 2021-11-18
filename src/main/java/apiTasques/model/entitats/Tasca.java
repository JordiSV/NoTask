package apiTasques.model.entitats;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Tasca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTasca;
    private String nom;
    private String type;
    private boolean feta;
}
