package com.suporte.demo.LAYERS.repositories;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.suporte.demo.LAYERS.entities.Equipamento;



public interface EquipamentoRepository extends JpaRepository <Equipamento,Long> {

    Optional<Equipamento> findByTombamento(String tombamento);
    Equipamento findByNome(String nome);
}
