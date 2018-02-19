import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {EstadoDeProducto, Producto} from './producto.model';
import { ProductoService } from './producto.service';

@Injectable()
export class ProductoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productoService: ProductoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.productoService.find(id).subscribe((producto) => {
                    if (producto.fechaDeExpiracion) {
                        producto.fechaDeExpiracion = {
                            year: producto.fechaDeExpiracion.getFullYear(),
                            month: producto.fechaDeExpiracion.getMonth() + 1,
                            day: producto.fechaDeExpiracion.getDate()
                        };
                    }
                    this.ngbModalRef = this.productoModalRef(component, producto);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    const producto = new Producto();
                    producto.estado = EstadoDeProducto.ACTIVO;
                    producto.unidadesEnStock = 0;
                    this.ngbModalRef = this.productoModalRef(component, producto);
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    productoModalRef(component: Component, producto: Producto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.producto = producto;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
