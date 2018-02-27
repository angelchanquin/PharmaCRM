package com.angelchanquin.pharmacrm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DetalleDeRecepcionDeCompra.
 */
@Entity
@Table(name = "detalle_de_recepcion_de_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "detallederecepciondecompra")
public class DetalleDeRecepcionDeCompra extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_ordenada", nullable = false)
    private Integer cantidadOrdenada;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_recibida", nullable = false)
    private Integer cantidadRecibida;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio", nullable = false)
    private Double precio;

    @NotNull
    @Column(name = "no_de_lote", nullable = false)
    private String noDeLote;

    @NotNull
    @Column(name = "fecha_de_vencimiento", nullable = false)
    private LocalDate fechaDeVencimiento;

    @ManyToOne(optional = false)
    @NotNull
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    private RecepcionDeCompra recepcionDeCompra;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadOrdenada() {
        return cantidadOrdenada;
    }

    public DetalleDeRecepcionDeCompra cantidadOrdenada(Integer cantidadOrdenada) {
        this.cantidadOrdenada = cantidadOrdenada;
        return this;
    }

    public void setCantidadOrdenada(Integer cantidadOrdenada) {
        this.cantidadOrdenada = cantidadOrdenada;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public DetalleDeRecepcionDeCompra cantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
        return this;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Double getPrecio() {
        return precio;
    }

    public DetalleDeRecepcionDeCompra precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNoDeLote() {
        return noDeLote;
    }

    public DetalleDeRecepcionDeCompra noDeLote(String noDeLote) {
        this.noDeLote = noDeLote;
        return this;
    }

    public void setNoDeLote(String noDeLote) {
        this.noDeLote = noDeLote;
    }

    public LocalDate getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    public DetalleDeRecepcionDeCompra fechaDeVencimiento(LocalDate fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
        return this;
    }

    public void setFechaDeVencimiento(LocalDate fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public Producto getProducto() {
        return producto;
    }

    public DetalleDeRecepcionDeCompra producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public RecepcionDeCompra getRecepcionDeCompra() {
        return recepcionDeCompra;
    }

    public DetalleDeRecepcionDeCompra recepcionDeCompra(RecepcionDeCompra recepcionDeCompra) {
        this.recepcionDeCompra = recepcionDeCompra;
        return this;
    }

    public void setRecepcionDeCompra(RecepcionDeCompra recepcionDeCompra) {
        this.recepcionDeCompra = recepcionDeCompra;
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
        DetalleDeRecepcionDeCompra detalleDeRecepcionDeCompra = (DetalleDeRecepcionDeCompra) o;
        if (detalleDeRecepcionDeCompra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleDeRecepcionDeCompra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleDeRecepcionDeCompra{" +
            "id=" + getId() +
            ", cantidadOrdenada=" + getCantidadOrdenada() +
            ", cantidadRecibida=" + getCantidadRecibida() +
            ", precio=" + getPrecio() +
            ", noDeLote='" + getNoDeLote() + "'" +
            ", fechaDeVencimiento='" + getFechaDeVencimiento() + "'" +
            "}";
    }
}
