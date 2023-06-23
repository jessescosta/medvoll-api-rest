package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

    @Autowired
    private ConsultaRepository repository;


    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var horaAtual = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(horaAtual, consulta.getData()).toHours();

        if (diferencaEmHoras < 24){
            throw new ValidacaoException("Consultas só podem ser canceladas com antecedência mínima de 24h!");
        }
    }
}
