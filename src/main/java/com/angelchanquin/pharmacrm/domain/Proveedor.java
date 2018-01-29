package com.angelchanquin.pharmacrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

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
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "nombre_de_contacto", nullable = false)
    private String nombreDeContacto;

    @NotNull
    @Column(name = "apellido_de_contacto", nullable = false)
    private String apellidoDeContacto;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @NotNull
    @Column(name = "direccion_de_facturacion", nullable = false)
    private String direccionDeFacturacion;

    @Column(name = "direccion_de_envio")
    private String direccionDeEnvio;

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Producto> productos = new HashSet<>();

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

    public String getNombreDeContacto() {
        return nombreDeContacto;
    }

    public Proveedor nombreDeContacto(String nombreDeContacto) {
        this.nombreDeContacto = nombreDeContacto;
        return this;
    }

    public void setNombreDeContacto(String nombreDeContacto) {
        this.nombreDeContacto = nombreDeContacto;
    }

    public String getApellidoDeContacto() {
        return apellidoDeContacto;
    }

    public Proveedor apellidoDeContacto(String apellidoDeContacto) {
        this.apellidoDeContacto = apellidoDeContacto;
        return this;
    }

    public void setApellidoDeContacto(String apellidoDeContacto) {
        this.apellidoDeContacto = apellidoDeContacto;
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

    public String getDireccionDeFacturacion() {
        return direccionDeFacturacion;
    }

    public Proveedor direccionDeFacturacion(String direccionDeFacturacion) {
        this.direccionDeFacturacion = direccionDeFacturacion;
        return this;
    }

    public void setDireccionDeFacturacion(String direccionDeFacturacion) {
        this.direccionDeFacturacion = direccionDeFacturacion;
    }

    public String getDireccionDeEnvio() {
        return direccionDeEnvio;
    }

    public Proveedor direccionDeEnvio(String direccionDeEnvio) {
        this.direccionDeEnvio = direccionDeEnvio;
        return this;
    }

    public void setDireccionDeEnvio(String direccionDeEnvio) {
        this.direccionDeEnvio = direccionDeEnvio;
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
            ", nombreDeContacto='" + getNombreDeContacto() + "'" +
            ", apellidoDeContacto='" + getApellidoDeContacto() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", celular='" + getCelular() + "'" +
            ", sitioWeb='" + getSitioWeb() + "'" +
            ", direccionDeFacturacion='" + getDireccionDeFacturacion() + "'" +
            ", direccionDeEnvio='" + getDireccionDeEnvio() + "'" +
            "}";
    }
}
