package it.bank.FabrickTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.bank.FabrickTest.entity.Transazione;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, String> {

}
