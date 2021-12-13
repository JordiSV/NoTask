package apiTasques.model.repositoris;

import apiTasques.model.entitats.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoriUsuari extends JpaRepository<Usuari, Long> {
}
