package med.voll.api.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente ${dados.idPaciente()} informado não existe!");
        }
        if (dados.idMedico() != null  &&  !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico  ${dados.idMedico()} informado não existe!");
        }

        var medico = escolherMedico(dados);
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        return new Medico();
    }
}
