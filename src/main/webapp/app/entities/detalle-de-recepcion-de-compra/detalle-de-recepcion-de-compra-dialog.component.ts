import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetalleDeRecepcionDeCompra } from './detalle-de-recepcion-de-compra.model';
import { DetalleDeRecepcionDeCompraPopupService } from './detalle-de-recepcion-de-compra-popup.service';
import { DetalleDeRecepcionDeCompraService } from './detalle-de-recepcion-de-compra.service';
import { Producto, ProductoService } from '../producto';
import { RecepcionDeCompra, RecepcionDeCompraService } from '../recepcion-de-compra';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-detalle-de-recepcion-de-compra-dialog',
    templateUrl: './detalle-de-recepcion-de-compra-dialog.component.html'
})
export class DetalleDeRecepcionDeCompraDialogComponent implements OnInit {

    detalleDeRecepcionDeCompra: DetalleDeRecepcionDeCompra;
    isSaving: boolean;

    productos: Producto[];

    recepciondecompras: RecepcionDeCompra[];
    fechaDeVencimientoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private detalleDeRecepcionDeCompraService: DetalleDeRecepcionDeCompraService,
        private productoService: ProductoService,
        private recepcionDeCompraService: RecepcionDeCompraService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productoService.query()
            .subscribe((res: ResponseWrapper) => { this.productos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.recepcionDeCompraService.query()
            .subscribe((res: ResponseWrapper) => { this.recepciondecompras = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.detalleDeRecepcionDeCompra.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detalleDeRecepcionDeCompraService.update(this.detalleDeRecepcionDeCompra));
        } else {
            this.subscribeToSaveResponse(
                this.detalleDeRecepcionDeCompraService.create(this.detalleDeRecepcionDeCompra));
        }
    }

    private subscribeToSaveResponse(result: Observable<DetalleDeRecepcionDeCompra>) {
        result.subscribe((res: DetalleDeRecepcionDeCompra) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DetalleDeRecepcionDeCompra) {
        this.eventManager.broadcast({ name: 'detalleDeRecepcionDeCompraListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProductoById(index: number, item: Producto) {
        return item.id;
    }

    trackRecepcionDeCompraById(index: number, item: RecepcionDeCompra) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-detalle-de-recepcion-de-compra-popup',
    template: ''
})
export class DetalleDeRecepcionDeCompraPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detalleDeRecepcionDeCompraPopupService: DetalleDeRecepcionDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.detalleDeRecepcionDeCompraPopupService
                    .open(DetalleDeRecepcionDeCompraDialogComponent as Component, params['id']);
            } else {
                this.detalleDeRecepcionDeCompraPopupService
                    .open(DetalleDeRecepcionDeCompraDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
