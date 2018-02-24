import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RecepcionDeCompra, TipoDeRecepcionDeCompra } from './recepcion-de-compra.model';
import { RecepcionDeCompraService } from './recepcion-de-compra.service';

@Injectable()
export class RecepcionDeCompraPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private recepcionDeCompraService: RecepcionDeCompraService

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
                this.recepcionDeCompraService.find(id).subscribe((recepcionDeCompra) => {
                    if (recepcionDeCompra.fechaDeRecepcion) {
                        recepcionDeCompra.fechaDeRecepcion = {
                            year: recepcionDeCompra.fechaDeRecepcion.getFullYear(),
                            month: recepcionDeCompra.fechaDeRecepcion.getMonth() + 1,
                            day: recepcionDeCompra.fechaDeRecepcion.getDate()
                        };
                    }
                    this.ngbModalRef = this.recepcionDeCompraModalRef(component, recepcionDeCompra);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    const recepcionDeCompra = new RecepcionDeCompra();
                    const today = new Date();
                    recepcionDeCompra.fechaDeRecepcion = {
                        year: today.getFullYear(),
                        month: today.getMonth() + 1,
                        day: today.getDate()
                    };
                    recepcionDeCompra.tipo = TipoDeRecepcionDeCompra.TOTAL;
                    this.ngbModalRef = this.recepcionDeCompraModalRef(component, recepcionDeCompra);
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    recepcionDeCompraModalRef(component: Component, recepcionDeCompra: RecepcionDeCompra): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.recepcionDeCompra = recepcionDeCompra;
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
