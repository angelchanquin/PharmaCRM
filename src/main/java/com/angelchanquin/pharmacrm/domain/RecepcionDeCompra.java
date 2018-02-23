package com.angelchanquin.pharmacrm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.angelchanquin.pharmacrm.domain.enumeration.TipoDeRecepcionDeCompra;

/**
 * A RecepcionDeCompra.
 */
@Entity
@Table(name = "recepcion_de_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "recepciondecompra")
public class RecepcionDeCompra extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "no_de_recibo", nullable = false)
    private String noDeRecibo;

    @NotNull
    @Column(name = "fecha_de_recepcion", nullable = false)
    private LocalDate fechaDeRecepcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoDeRecepcionDeCompra tipo;

    @Column(name = "notas")
    private String notas;

    @ManyToOne(optional = false)
    @NotNull
    private OrdenDeCompra ordenDeCompra;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoDeRecibo() {
        return noDeRecibo;
    }

    public RecepcionDeCompra noDeRecibo(String noDeRecibo) {
        this.noDeRecibo = noDeRecibo;
        return this;
    }

    public void setNoDeRecibo(String noDeRecibo) {
        this.noDeRecibo = noDeRecibo;
    }

    public LocalDate getFechaDeRecepcion() {
        return fechaDeRecepcion;
    }

    public RecepcionDeCompra fechaDeRecepcion(LocalDate fechaDeRecepcion) {
        this.fechaDeRecepcion = fechaDeRecepcion;
        return this;
    }

    public void setFechaDeRecepcion(LocalDate fechaDeRecepcion) {
        this.fechaDeRecepcion = fechaDeRecepcion;
    }

    public TipoDeRecepcionDeCompra getTipo() {
        return tipo;
    }

    public RecepcionDeCompra tipo(TipoDeRecepcionDeCompra tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoDeRecepcionDeCompra tipo) {
        this.tipo = tipo;
    }

    public String getNotas() {
        return notas;
    }

    public RecepcionDeCompra notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public RecepcionDeCompra ordenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
        return this;
    }

    public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecepcionDeCompra recepcionDeCompra = (RecepcionDeCompra) o;
        if (recepcionDeCompra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recepcionDeCompra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecepcionDeCompra{" +
            "id=" + getId() +
            ", noDeRecibo='" + getNoDeRecibo() + "'" +
            ", fechaDeRecepcion='" + getFechaDeRecepcion() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", notas='" + getNotas() + "'" +
            "}";
    }
}
