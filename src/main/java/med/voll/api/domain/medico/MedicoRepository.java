package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select m from Medicos m
            where m.ativo = 1
            and m.especialidade = :especialidade
            and m.id not in(select c.medico_id from Consultas c
                            where c.data = :data)
            order by rand()
            limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.ativo
            from Medicos m
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long idMedico);
}
