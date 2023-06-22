package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {

    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendade nesse mesmo horário!");
        }
    }
}
