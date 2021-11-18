package apiTasques.model.repositoris;

import apiTasques.model.entitats.Tasca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoriTasques extends JpaRepository<Tasca, Long> {


}
