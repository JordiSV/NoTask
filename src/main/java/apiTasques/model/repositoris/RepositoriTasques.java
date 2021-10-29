package apiTasques.model.repositoris;

import apiTasques.model.entitats.Tasca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoriTasques extends JpaRepository<Tasca, String> {

}
