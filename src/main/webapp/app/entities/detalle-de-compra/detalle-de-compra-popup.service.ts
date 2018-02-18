import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DetalleDeCompra } from './detalle-de-compra.model';
import { DetalleDeCompraService } from './detalle-de-compra.service';
import {OrdenDeCompraService} from '../orden-de-compra';

@Injectable()
export class DetalleDeCompraPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private detalleDeCompraService: DetalleDeCompraService,
        private ordenDeCompraService: OrdenDeCompraService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any, ordenId?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.detalleDeCompraService.find(id).subscribe((detalleDeCompra) => {
                    this.ngbModalRef = this.detalleDeCompraModalRef(component, detalleDeCompra);
                    resolve(this.ngbModalRef);
                });
            } else if (ordenId) {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ordenDeCompraService.find(ordenId).subscribe( (ordenDeCompra) => {
                        const detalleDeCompra = new DetalleDeCompra();
                        detalleDeCompra.ordenDeCompra = ordenDeCompra;
                        detalleDeCompra.cantidad = 1;
                        detalleDeCompra.precio = 0;
                        detalleDeCompra.subTotal = 0;
                        this.ngbModalRef = this.detalleDeCompraModalRef(component, detalleDeCompra);
                        resolve(this.ngbModalRef);
                    });
                }, 0);
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.detalleDeCompraModalRef(component, new DetalleDeCompra());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    detalleDeCompraModalRef(component: Component, detalleDeCompra: DetalleDeCompra): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.detalleDeCompra = detalleDeCompra;
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
