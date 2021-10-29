package apiTasques.model.entitats;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Tasca {
    @Id
    private String idTasca;
    private String nom;
    private String type;
}
