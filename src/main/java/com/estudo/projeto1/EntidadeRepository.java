package com.estudo.projeto1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntidadeRepository extends JpaRepository<Modelo, Long> {
    public List<Modelo> findByNomeContaining( String nome);
}
