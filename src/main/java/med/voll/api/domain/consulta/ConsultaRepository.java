package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

    @Query("""
                select
                                c.id as id,
                                c.data as data,
                                p.nome as nomePaciente,
                                p.cpf as cpf,
                                m.especialidade as especialidade,
                                m.nome as nomeMedico,
                                m.email as email,
                                c.motivoCancelamento as motivoCancelamento
                            from Consulta c
                            join c.paciente p
                            join c.medico m
                            order by m.nome, c.data
            """)
    //List<DadosListagemAgendamento> findAllConsultasInfo();
    List<Object[]> findAllConsultasInfo();

    @Query("""
                select
                                c.id as id,
                                c.data as data,
                                p.nome as nomePaciente,
                                p.cpf as cpf,
                                m.especialidade as especialidade,
                                m.nome as nomeMedico,
                                m.email as email,
                                c.motivoCancelamento as motivoCancelamento
                            from Consulta c
                            join c.paciente p
                            join c.medico m
                            where c.id = :id
                            order by m.nome, c.data
            """)
        //List<DadosListagemAgendamento> findAllConsultasInfo();
    List<Object[]> findConsultasById(Long id);

}
