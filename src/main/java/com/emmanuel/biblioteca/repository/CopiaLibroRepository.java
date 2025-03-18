package com.emmanuel.biblioteca.repository;

import com.emmanuel.biblioteca.entity.CopiaLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopiaLibroRepository extends JpaRepository<CopiaLibro, Integer> {
}
