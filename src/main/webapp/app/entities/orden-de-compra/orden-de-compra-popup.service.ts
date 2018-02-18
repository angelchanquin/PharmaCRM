import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrdenDeCompra } from './orden-de-compra.model';
import { OrdenDeCompraService } from './orden-de-compra.service';

@Injectable()
export class OrdenDeCompraPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ordenDeCompraService: OrdenDeCompraService

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
                this.ordenDeCompraService.find(id).subscribe((ordenDeCompra) => {
                    if (ordenDeCompra.fecha) {
                        ordenDeCompra.fecha = {
                            year: ordenDeCompra.fecha.getFullYear(),
                            month: ordenDeCompra.fecha.getMonth() + 1,
                            day: ordenDeCompra.fecha.getDate()
                        };
                    }
                    this.ngbModalRef = this.ordenDeCompraModalRef(component, ordenDeCompra);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    const ordenDeCompra = new OrdenDeCompra()
                    ;
                    const today = new Date();
                    ordenDeCompra.fecha = {
                        year: today.getFullYear(),
                        month: today.getMonth() + 1,
                        day: today.getDate()
                    };
                    this.ngbModalRef = this.ordenDeCompraModalRef(component, ordenDeCompra);
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ordenDeCompraModalRef(component: Component, ordenDeCompra: OrdenDeCompra): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ordenDeCompra = ordenDeCompra;
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
