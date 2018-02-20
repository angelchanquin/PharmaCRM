import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Inventario } from './inventario.model';
import { InventarioService } from './inventario.service';

@Injectable()
export class InventarioPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inventarioService: InventarioService

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
                this.inventarioService.find(id).subscribe((inventario) => {
                    if (inventario.fecha) {
                        inventario.fecha = {
                            year: inventario.fecha.getFullYear(),
                            month: inventario.fecha.getMonth() + 1,
                            day: inventario.fecha.getDate()
                        };
                    }
                    this.ngbModalRef = this.inventarioModalRef(component, inventario);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    const inventario = new Inventario();
                    const today = new Date();
                    inventario.fecha = {
                        year: today.getFullYear(),
                        month: today.getMonth() + 1,
                        day: today.getDate()
                    };
                    this.ngbModalRef = this.inventarioModalRef(component, inventario);
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inventarioModalRef(component: Component, inventario: Inventario): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inventario = inventario;
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
