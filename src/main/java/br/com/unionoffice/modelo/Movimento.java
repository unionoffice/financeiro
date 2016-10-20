package br.com.unionoffice.modelo;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Movimento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Calendar data;
	@Temporal(TemporalType.DATE)
	private Calendar vencimento;
	private String Emitente;
	private BigDecimal valorParcela;
	private byte numParcela;
	private String referencia;
	private Situacao situacao;
	private String observacao;
	@Temporal(TemporalType.DATE)
	private Calendar dataLiquidacao;
	@ManyToOne
	private Usuario criador;
	@ManyToOne
	private Usuario finalizador;
	private TipoMovimento tipoMovimento;
	private CondPagamento condPagamento;
	@Column(nullable=true)
	private String numero;
	@Column(nullable=true)
	private String comprovante;

	public Movimento() {
		situacao = Situacao.ABERTO;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Usuario getCriador() {
		return criador;
	}

	public void setCriador(Usuario criador) {
		this.criador = criador;
	}

	public Usuario getFinalizador() {
		return finalizador;
	}

	public void setFinalizador(Usuario finalizador) {
		this.finalizador = finalizador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Calendar getVencimento() {
		return vencimento;
	}

	public void setVencimento(Calendar vencimento) {
		this.vencimento = vencimento;
	}

	public String getEmitente() {
		return Emitente;
	}

	public void setEmitente(String emitente) {
		Emitente = emitente;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public byte getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(byte numParcela) {
		this.numParcela = numParcela;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Calendar getDataLiquidacao() {
		return dataLiquidacao;
	}

	public void setDataLiquidacao(Calendar dataLiquidacao) {
		this.dataLiquidacao = dataLiquidacao;
	}

	public CondPagamento getCondPagamento() {
		return condPagamento;
	}

	public void setCondPagamento(CondPagamento condPagamento) {
		this.condPagamento = condPagamento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComprovante() {
		return comprovante;
	}

	public void setComprovante(String comprovante) {
		this.comprovante = comprovante;
	}

}
