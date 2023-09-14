package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Especialidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        System.out.println(dados);
        var dto = agenda.agendar(dados);

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        System.out.println(dados);
        var dto = agenda.cancelar(dados);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemAgendamento>> listar() {
        List<DadosListagemAgendamento> lista = repository.findAllConsultasInfo().stream()
                .map(this::mapToDadosListagemAgendamento)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<DadosListagemAgendamento>> listarPorId(@PathVariable Long id) {
        List<DadosListagemAgendamento> lista = repository.findConsultasById(id).stream()
                .map(this::mapToDadosListagemAgendamento)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    private DadosListagemAgendamento mapToDadosListagemAgendamento(Object[] result) {
        Long id = (Long) result[0];
        LocalDateTime data = (LocalDateTime) result[1];
        String nomePaciente = (String) result[2];
        String cpf = (String) result[3];
        Especialidade especialidade = (Especialidade) result[4];
        String nomeMedico = (String) result[5];
        String email = (String) result[6];
        MotivoCancelamento motivoCancelamento = (MotivoCancelamento) result[7];

        return new DadosListagemAgendamento(id, data, nomePaciente, cpf, especialidade, nomeMedico, email, motivoCancelamento);
    }




}
