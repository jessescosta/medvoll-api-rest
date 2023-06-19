package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(dados);
        repository.save(medico);
        // Metodo era Void, incluido 2 linhas abaixo. ResponseEntity com uri para lançar retorno correto trazendo os dados detalhados
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); //HTTP STATUS 201 Created
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

   // @GetMapping
   // public List<DadosListagemMedico> listar(Pageable paginacao){
   //     return repository.findAll(paginacao).stream().map(DadosListagemMedico::new).toList();
   // }

    @GetMapping                             //http://localhost:8080/medicos?sort=nome,asc&size=10&page=0
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        // Inclurido linha abaixo para tratar retorno correto
        return ResponseEntity.ok(page); //HTTP STATUS 200 OK
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); //HTTP STATUS 200 OK
    }

/*    \\ Alteração de metodo invocando retorno para resposta personalizada na requisição
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        //repository.deleteById(id);
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
*/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build(); //HTTP STATUS 204 No Content
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){

        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); //HTTP STATUS 200 OK
    }
}

