package jonasRafaelSilvaCavalcanti.model;

import jonasRafaelSilvaCavalcanti.enums.StatusAprovacao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MatriculaTest {
    private static Discente discente;
    private static Disciplina disciplina;
    private static Docente docente;
    private static  Turma turma;

    @BeforeAll
    public static void setup(){
        discente = new Discente();
        discente.setNome("ALUNO DA SILVA");
        discente.setMatricula(123123);

        docente = new Docente();
        docente.setNome("DOCENTE DO SILVA");
        docente.setSiape(12321);

        disciplina = new Disciplina();
        disciplina.setNome("TESTES DE SOFTWARE");
        disciplina.setCodigo("DIM0507");

        turma = new Turma(docente, disciplina);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6.0f,6.0f,6.0f,100,APR",//APROVADO
            "5.9f,5.9f,5.9f,100,REC",//REPOSICAO
            "2.9f,2.9f,2.9f,100,REP",//REPROVADO POR MEDIA
            "10.0f,10.0f,10.0f,74,REPF",//REPROVADO POR FALTA
            "2.9f,2.9f,2.9f,74,REPMF",//REPROVADO POR MEDIA E FALTA
    })
    void deveConsolidarNotasComStatusCorreto(float nota1, float nota2, float nota3,int frequencia,StatusAprovacao statusEsperado) {

        Matricula matricula = new Matricula(discente,turma);
        matricula.cadastrarFrequencia(frequencia);
        matricula.cadastrarNota1(new BigDecimal(nota1));
        matricula.cadastrarNota2(new BigDecimal(nota2));
        matricula.cadastrarNota3(new BigDecimal(nota3));

        matricula.consolidarParcialmente();

        StatusAprovacao statusAluno = matricula.getStatus();
        assertEquals(statusEsperado,statusAluno);

    }
}