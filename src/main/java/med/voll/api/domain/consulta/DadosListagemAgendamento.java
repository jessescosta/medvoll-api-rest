package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosListagemAgendamento(Long id, LocalDateTime data, String nomePaciente, String cpf, Especialidade especialidade, String nomeMedico, String email, MotivoCancelamento motivoCancelamento) {

    public DadosListagemAgendamento(Long id, LocalDateTime data, String nomePaciente, String cpf, Especialidade especialidade, String nomeMedico, String email, MotivoCancelamento motivoCancelamento) {
        this.id = id;
        this.data = data;
        this.nomePaciente = nomePaciente;
        this.cpf = cpf;
        this.especialidade = especialidade;
        this.nomeMedico = nomeMedico;
        this.email = email;
        this.motivoCancelamento = motivoCancelamento;
    }

}
