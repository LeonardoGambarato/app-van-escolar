package com.appvan.backend.repository;

import com.appvan.backend.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    List<Aluno> findByStatusPagamento(String statusPagamento);

    List<Aluno> findByUsuarioId(Integer usuarioId);

    List<Aluno> findByMotoristaId(Integer motoristaId);
}
