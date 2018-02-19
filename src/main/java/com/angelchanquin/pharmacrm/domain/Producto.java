package com.angelchanquin.pharmacrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.angelchanquin.pharmacrm.domain.enumeration.EstadoDeProducto;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 1L)
    @Column(name = "sku", nullable = false)
    private Long sku;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_de_venta", nullable = false)
    private Double precioDeVenta;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_de_venta_2", nullable = false)
    private Double precioDeVenta2;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_de_venta_3", nullable = false)
    private Double precioDeVenta3;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_de_costo", nullable = false)
    private Double precioDeCosto;

    @NotNull
    @Min(value = 0)
    @Column(name = "unidades_en_stock", nullable = false)
    private Integer unidadesEnStock;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDeProducto estado;

    @Column(name = "fecha_de_expiracion")
    private LocalDate fechaDeExpiracion;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inventario> inventarios = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalleDeCompra> ordens = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private PresentacionDeProducto presentacion;

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

    public Long getSku() {
        return sku;
    }

    public Producto sku(Long sku) {
        this.sku = sku;
        return this;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioDeVenta() {
        return precioDeVenta;
    }

    public Producto precioDeVenta(Double precioDeVenta) {
        this.precioDeVenta = precioDeVenta;
        return this;
    }

    public void setPrecioDeVenta(Double precioDeVenta) {
        this.precioDeVenta = precioDeVenta;
    }

    public Double getPrecioDeVenta2() {
        return precioDeVenta2;
    }

    public Producto precioDeVenta2(Double precioDeVenta2) {
        this.precioDeVenta2 = precioDeVenta2;
        return this;
    }

    public void setPrecioDeVenta2(Double precioDeVenta2) {
        this.precioDeVenta2 = precioDeVenta2;
    }

    public Double getPrecioDeVenta3() {
        return precioDeVenta3;
    }

    public Producto precioDeVenta3(Double precioDeVenta3) {
        this.precioDeVenta3 = precioDeVenta3;
        return this;
    }

    public void setPrecioDeVenta3(Double precioDeVenta3) {
        this.precioDeVenta3 = precioDeVenta3;
    }

    public Double getPrecioDeCosto() {
        return precioDeCosto;
    }

    public Producto precioDeCosto(Double precioDeCosto) {
        this.precioDeCosto = precioDeCosto;
        return this;
    }

    public void setPrecioDeCosto(Double precioDeCosto) {
        this.precioDeCosto = precioDeCosto;
    }

    public Integer getUnidadesEnStock() {
        return unidadesEnStock;
    }

    public Producto unidadesEnStock(Integer unidadesEnStock) {
        this.unidadesEnStock = unidadesEnStock;
        return this;
    }

    public void setUnidadesEnStock(Integer unidadesEnStock) {
        this.unidadesEnStock = unidadesEnStock;
    }

    public EstadoDeProducto getEstado() {
        return estado;
    }

    public Producto estado(EstadoDeProducto estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoDeProducto estado) {
        this.estado = estado;
    }

    public LocalDate getFechaDeExpiracion() {
        return fechaDeExpiracion;
    }

    public Producto fechaDeExpiracion(LocalDate fechaDeExpiracion) {
        this.fechaDeExpiracion = fechaDeExpiracion;
        return this;
    }

    public void setFechaDeExpiracion(LocalDate fechaDeExpiracion) {
        this.fechaDeExpiracion = fechaDeExpiracion;
    }

    public Set<Inventario> getInventarios() {
        return inventarios;
    }

    public Producto inventarios(Set<Inventario> inventarios) {
        this.inventarios = inventarios;
        return this;
    }

    public Producto addInventario(Inventario inventario) {
        this.inventarios.add(inventario);
        inventario.setProducto(this);
        return this;
    }

    public Producto removeInventario(Inventario inventario) {
        this.inventarios.remove(inventario);
        inventario.setProducto(null);
        return this;
    }

    public void setInventarios(Set<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public Set<DetalleDeCompra> getOrdens() {
        return ordens;
    }

    public Producto ordens(Set<DetalleDeCompra> detalleDeCompras) {
        this.ordens = detalleDeCompras;
        return this;
    }

    public Producto addOrden(DetalleDeCompra detalleDeCompra) {
        this.ordens.add(detalleDeCompra);
        detalleDeCompra.setProducto(this);
        return this;
    }

    public Producto removeOrden(DetalleDeCompra detalleDeCompra) {
        this.ordens.remove(detalleDeCompra);
        detalleDeCompra.setProducto(null);
        return this;
    }

    public void setOrdens(Set<DetalleDeCompra> detalleDeCompras) {
        this.ordens = detalleDeCompras;
    }

    public PresentacionDeProducto getPresentacion() {
        return presentacion;
    }

    public Producto presentacion(PresentacionDeProducto presentacionDeProducto) {
        this.presentacion = presentacionDeProducto;
        return this;
    }

    public void setPresentacion(PresentacionDeProducto presentacionDeProducto) {
        this.presentacion = presentacionDeProducto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Producto proveedor(Proveedor proveedor) {
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
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", sku=" + getSku() +
            ", nombre='" + getNombre() + "'" +
            ", precioDeVenta=" + getPrecioDeVenta() +
            ", precioDeVenta2=" + getPrecioDeVenta2() +
            ", precioDeVenta3=" + getPrecioDeVenta3() +
            ", precioDeCosto=" + getPrecioDeCosto() +
            ", unidadesEnStock=" + getUnidadesEnStock() +
            ", estado='" + getEstado() + "'" +
            ", fechaDeExpiracion='" + getFechaDeExpiracion() + "'" +
            "}";
    }
}
