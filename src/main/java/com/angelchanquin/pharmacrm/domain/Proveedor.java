package com.angelchanquin.pharmacrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "proveedor")
public class Proveedor extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "contacto", nullable = false)
    private String contacto;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @Column(name = "direccion")
    private String direccion;

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Producto> productos = new HashSet<>();

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrdenDeCompra> ordenDeCompras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Proveedor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public Proveedor contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Proveedor correoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
        return this;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public Proveedor telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public Proveedor celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public Proveedor sitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
        return this;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getDireccion() {
        return direccion;
    }

    public Proveedor direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Proveedor productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Proveedor addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setProveedor(this);
        return this;
    }

    public Proveedor removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setProveedor(null);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public Set<OrdenDeCompra> getOrdenDeCompras() {
        return ordenDeCompras;
    }

    public Proveedor ordenDeCompras(Set<OrdenDeCompra> ordenDeCompras) {
        this.ordenDeCompras = ordenDeCompras;
        return this;
    }

    public Proveedor addOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompras.add(ordenDeCompra);
        ordenDeCompra.setProveedor(this);
        return this;
    }

    public Proveedor removeOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompras.remove(ordenDeCompra);
        ordenDeCompra.setProveedor(null);
        return this;
    }

    public void setOrdenDeCompras(Set<OrdenDeCompra> ordenDeCompras) {
        this.ordenDeCompras = ordenDeCompras;
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
        Proveedor proveedor = (Proveedor) o;
        if (proveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proveedor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", contacto='" + getContacto() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", celular='" + getCelular() + "'" +
            ", sitioWeb='" + getSitioWeb() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
