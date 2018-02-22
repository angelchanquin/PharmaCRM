package com.angelchanquin.pharmacrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.angelchanquin.pharmacrm.domain.enumeration.EstadoDeOrdenDeCompra;

import com.angelchanquin.pharmacrm.domain.enumeration.RecibidoOrdenDeCompra;

/**
 * A OrdenDeCompra.
 */
@Entity
@Table(name = "orden_de_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ordendecompra")
public class OrdenDeCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_de_referencia", nullable = false)
    private String numeroDeReferencia;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @DecimalMin(value = "0")
    @Column(name = "total")
    private Double total;

    @NotNull
    @Column(name = "fecha_de_entrega_esperada", nullable = false)
    private LocalDate fechaDeEntregaEsperada;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDeOrdenDeCompra estado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recibido", nullable = false)
    private RecibidoOrdenDeCompra recibido;

    @OneToMany(mappedBy = "ordenDeCompra")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalleDeCompra> detalles = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Proveedor proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDeReferencia() {
        return numeroDeReferencia;
    }

    public OrdenDeCompra numeroDeReferencia(String numeroDeReferencia) {
        this.numeroDeReferencia = numeroDeReferencia;
        return this;
    }

    public void setNumeroDeReferencia(String numeroDeReferencia) {
        this.numeroDeReferencia = numeroDeReferencia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public OrdenDeCompra fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public OrdenDeCompra total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getFechaDeEntregaEsperada() {
        return fechaDeEntregaEsperada;
    }

    public OrdenDeCompra fechaDeEntregaEsperada(LocalDate fechaDeEntregaEsperada) {
        this.fechaDeEntregaEsperada = fechaDeEntregaEsperada;
        return this;
    }

    public void setFechaDeEntregaEsperada(LocalDate fechaDeEntregaEsperada) {
        this.fechaDeEntregaEsperada = fechaDeEntregaEsperada;
    }

    public EstadoDeOrdenDeCompra getEstado() {
        return estado;
    }

    public OrdenDeCompra estado(EstadoDeOrdenDeCompra estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoDeOrdenDeCompra estado) {
        this.estado = estado;
    }

    public RecibidoOrdenDeCompra getRecibido() {
        return recibido;
    }

    public OrdenDeCompra recibido(RecibidoOrdenDeCompra recibido) {
        this.recibido = recibido;
        return this;
    }

    public void setRecibido(RecibidoOrdenDeCompra recibido) {
        this.recibido = recibido;
    }

    public Set<DetalleDeCompra> getDetalles() {
        return detalles;
    }

    public OrdenDeCompra detalles(Set<DetalleDeCompra> detalleDeCompras) {
        this.detalles = detalleDeCompras;
        return this;
    }

    public OrdenDeCompra addDetalle(DetalleDeCompra detalleDeCompra) {
        this.detalles.add(detalleDeCompra);
        detalleDeCompra.setOrdenDeCompra(this);
        return this;
    }

    public OrdenDeCompra removeDetalle(DetalleDeCompra detalleDeCompra) {
        this.detalles.remove(detalleDeCompra);
        detalleDeCompra.setOrdenDeCompra(null);
        return this;
    }

    public void setDetalles(Set<DetalleDeCompra> detalleDeCompras) {
        this.detalles = detalleDeCompras;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public OrdenDeCompra proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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
        OrdenDeCompra ordenDeCompra = (OrdenDeCompra) o;
        if (ordenDeCompra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordenDeCompra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdenDeCompra{" +
            "id=" + getId() +
            ", numeroDeReferencia='" + getNumeroDeReferencia() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", total=" + getTotal() +
            ", fechaDeEntregaEsperada='" + getFechaDeEntregaEsperada() + "'" +
            ", estado='" + getEstado() + "'" +
            ", recibido='" + getRecibido() + "'" +
            "}";
    }
}
