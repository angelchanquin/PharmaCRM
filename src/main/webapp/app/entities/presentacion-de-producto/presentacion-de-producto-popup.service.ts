import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PresentacionDeProducto } from './presentacion-de-producto.model';
import { PresentacionDeProductoService } from './presentacion-de-producto.service';

@Injectable()
export class PresentacionDeProductoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private presentacionDeProductoService: PresentacionDeProductoService

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
                this.presentacionDeProductoService.find(id).subscribe((presentacionDeProducto) => {
                    this.ngbModalRef = this.presentacionDeProductoModalRef(component, presentacionDeProducto);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.presentacionDeProductoModalRef(component, new PresentacionDeProducto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    presentacionDeProductoModalRef(component: Component, presentacionDeProducto: PresentacionDeProducto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.presentacionDeProducto = presentacionDeProducto;
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
