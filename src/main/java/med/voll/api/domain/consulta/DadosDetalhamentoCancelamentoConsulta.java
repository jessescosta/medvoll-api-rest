package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoCancelamentoConsulta(Long idConsulta, Long idPaciente, Long IdMedico, LocalDateTime data, MotivoCancelamento motivoCancelamento) {
    public DadosDetalhamentoCancelamentoConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData(), consulta.getMotivoCancelamento());
    }

}
