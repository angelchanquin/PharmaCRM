import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DetalleDeRecepcionDeCompra } from './detalle-de-recepcion-de-compra.model';
import { DetalleDeRecepcionDeCompraService } from './detalle-de-recepcion-de-compra.service';

@Injectable()
export class DetalleDeRecepcionDeCompraPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private detalleDeRecepcionDeCompraService: DetalleDeRecepcionDeCompraService

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
                this.detalleDeRecepcionDeCompraService.find(id).subscribe((detalleDeRecepcionDeCompra) => {
                    if (detalleDeRecepcionDeCompra.fechaDeVencimiento) {
                        detalleDeRecepcionDeCompra.fechaDeVencimiento = {
                            year: detalleDeRecepcionDeCompra.fechaDeVencimiento.getFullYear(),
                            month: detalleDeRecepcionDeCompra.fechaDeVencimiento.getMonth() + 1,
                            day: detalleDeRecepcionDeCompra.fechaDeVencimiento.getDate()
                        };
                    }
                    this.ngbModalRef = this.detalleDeRecepcionDeCompraModalRef(component, detalleDeRecepcionDeCompra);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.detalleDeRecepcionDeCompraModalRef(component, new DetalleDeRecepcionDeCompra());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    detalleDeRecepcionDeCompraModalRef(component: Component, detalleDeRecepcionDeCompra: DetalleDeRecepcionDeCompra): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.detalleDeRecepcionDeCompra = detalleDeRecepcionDeCompra;
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
