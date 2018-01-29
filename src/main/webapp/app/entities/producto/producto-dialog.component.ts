import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Producto } from './producto.model';
import { ProductoPopupService } from './producto-popup.service';
import { ProductoService } from './producto.service';
import { PresentacionDeProducto, PresentacionDeProductoService } from '../presentacion-de-producto';
import { Proveedor, ProveedorService } from '../proveedor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-producto-dialog',
    templateUrl: './producto-dialog.component.html'
})
export class ProductoDialogComponent implements OnInit {

    producto: Producto;
    isSaving: boolean;

    presentaciondeproductos: PresentacionDeProducto[];

    proveedors: Proveedor[];
    fechaDeExpiracionDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private productoService: ProductoService,
        private presentacionDeProductoService: PresentacionDeProductoService,
        private proveedorService: ProveedorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.presentacionDeProductoService.query()
            .subscribe((res: ResponseWrapper) => { this.presentaciondeproductos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.proveedorService.query()
            .subscribe((res: ResponseWrapper) => { this.proveedors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.producto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productoService.update(this.producto));
        } else {
            this.subscribeToSaveResponse(
                this.productoService.create(this.producto));
        }
    }

    private subscribeToSaveResponse(result: Observable<Producto>) {
        result.subscribe((res: Producto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Producto) {
        this.eventManager.broadcast({ name: 'productoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPresentacionDeProductoById(index: number, item: PresentacionDeProducto) {
        return item.id;
    }

    trackProveedorById(index: number, item: Proveedor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-producto-popup',
    template: ''
})
export class ProductoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productoPopupService: ProductoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productoPopupService
                    .open(ProductoDialogComponent as Component, params['id']);
            } else {
                this.productoPopupService
                    .open(ProductoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
