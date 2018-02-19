package com.angelchanquin.pharmacrm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.angelchanquin.pharmacrm.domain.enumeration.TipoDeMovimiento;

/**
 * A Inventario.
 */
@Entity
@Table(name = "inventario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "inventario")
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_movimiento", nullable = false)
    private TipoDeMovimiento tipoDeMovimiento;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio", nullable = false)
    private Double precio;

    @ManyToOne(optional = false)
    @NotNull
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Inventario fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Inventario cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public TipoDeMovimiento getTipoDeMovimiento() {
        return tipoDeMovimiento;
    }

    public Inventario tipoDeMovimiento(TipoDeMovimiento tipoDeMovimiento) {
        this.tipoDeMovimiento = tipoDeMovimiento;
        return this;
    }

    public void setTipoDeMovimiento(TipoDeMovimiento tipoDeMovimiento) {
        this.tipoDeMovimiento = tipoDeMovimiento;
    }

    public Double getPrecio() {
        return precio;
    }

    public Inventario precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public Inventario producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
        Inventario inventario = (Inventario) o;
        if (inventario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inventario{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", cantidad=" + getCantidad() +
            ", tipoDeMovimiento='" + getTipoDeMovimiento() + "'" +
            ", precio=" + getPrecio() +
            "}";
    }
}
