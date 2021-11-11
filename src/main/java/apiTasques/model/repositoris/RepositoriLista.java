package apiTasques.model.repositoris;

import apiTasques.model.entitats.Lista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoriLista extends JpaRepository<Lista, String> {
}
