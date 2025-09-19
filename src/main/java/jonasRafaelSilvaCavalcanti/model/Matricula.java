package jonasRafaelSilvaCavalcanti.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jonasRafaelSilvaCavalcanti.enums.StatusAprovacao;

public class Matricula {
	private final Discente discente;
	
	private final Turma turma;
	
	private BigDecimal nota1;

	private BigDecimal nota2;

	private BigDecimal nota3;

	private Integer frequencia;
	
	private StatusAprovacao status;

	Matricula(Discente discente, Turma turma) {
		this.discente = discente;
		this.turma = turma;
	}

	public BigDecimal getNota1() {
		return nota1;
	}

	public void cadastrarNota1(BigDecimal nota1) {
		checarNota(nota1);
		this.nota1 = nota1;
	}

	public BigDecimal getNota2() {
		return nota2;
	}

	public void cadastrarNota2(BigDecimal nota2) {
		checarNota(nota2);
		this.nota2 = nota2;
	}

	public BigDecimal getNota3() {
		return nota3;
	}

	public void cadastrarNota3(BigDecimal nota3) {
		checarNota(nota3);
		this.nota3 = nota3;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void cadastrarFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}

	public Discente getDiscente() {
		return discente;
	}

	public Turma getTurma() {
		return turma;
	}


	/**
	 *  Calcula média do aluno, checa frequencia e notas minimas.
	 *  Atribui Status de consolidação ao aluno.
	 */
	public void consolidarParcialmente() {

		BigDecimal quantidaDeNotas = new BigDecimal(3);
		BigDecimal notaMinima = new BigDecimal(4);

		//Indica se possui media maior ou igual a 6
		boolean acimaDaMedia;

		//Indica se possui media para reposicao(>=3 e <6)
		boolean possuiMediaParaReposicao;

		//Indica de possui todas as notas maiores que 4
		boolean possuiNotasMinimas;

		//Indica se possui frequencia maior que 75
		boolean possuiFrequenciaMinima;


		BigDecimal media = (this.nota1
				.add(this.nota2)
				.add(this.nota3))
				.divide(quantidaDeNotas,RoundingMode.HALF_UP);

		acimaDaMedia = media.compareTo(new BigDecimal(6)) >= 0;

		possuiMediaParaReposicao = media.compareTo(new BigDecimal(6)) < 0 && media.compareTo(new BigDecimal(3)) >= 0;

		possuiNotasMinimas = this.nota1.compareTo(notaMinima)>=0 &&
				this.nota2.compareTo(notaMinima)>=0 &&
				this.nota3.compareTo(notaMinima)>=0;

		possuiFrequenciaMinima = this.getFrequencia()>=75;

		//Filtro para atribuicao de Status
		if(acimaDaMedia && possuiNotasMinimas && possuiFrequenciaMinima) {
			this.setStatus(StatusAprovacao.APR);
		}else if(possuiFrequenciaMinima && (possuiMediaParaReposicao || (!possuiNotasMinimas && acimaDaMedia))) {
			this.setStatus(StatusAprovacao.REC);
		}else if(!(acimaDaMedia || possuiMediaParaReposicao)){
			if(possuiFrequenciaMinima){
				this.setStatus(StatusAprovacao.REP);
			}else{
				this.setStatus(StatusAprovacao.REPMF);
			}
		}else{
			this.setStatus(StatusAprovacao.REPF);
		}
	}

	public StatusAprovacao getStatus() {
		return status;
	}

	private void setStatus(StatusAprovacao status) {
		this.status = status;
	}

	/**
	 * Verifica se a nota é MAIOR que 0 e MENOR que 10
	 * @param nota "Nota do aluno"
	 */
	private void checarNota(BigDecimal nota) {
		if(nota.compareTo(BigDecimal.ZERO)<0 || nota.compareTo(BigDecimal.TEN)>0){
			throw new IllegalArgumentException();
		}
	}
}