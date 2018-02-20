import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetalleDeCompra } from './detalle-de-compra.model';
import { DetalleDeCompraPopupService } from './detalle-de-compra-popup.service';
import { DetalleDeCompraService } from './detalle-de-compra.service';
import { OrdenDeCompra, OrdenDeCompraService } from '../orden-de-compra';
import { Producto, ProductoService } from '../producto';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-detalle-de-compra-dialog',
    templateUrl: './detalle-de-compra-dialog.component.html'
})
export class DetalleDeCompraDialogComponent implements OnInit {

    detalleDeCompra: DetalleDeCompra;
    ordenId: any;
    isSaving: boolean;

    ordendecompras: OrdenDeCompra[];

    productos: Producto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private detalleDeCompraService: DetalleDeCompraService,
        private ordenDeCompraService: OrdenDeCompraService,
        private productoService: ProductoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ordenId = this.detalleDeCompra.ordenDeCompra.id;
        this.ordenDeCompraService.query()
            .subscribe((res: ResponseWrapper) => { this.ordendecompras = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.productoService.query()
            .subscribe((res: ResponseWrapper) => { this.productos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.detalleDeCompra.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detalleDeCompraService.update(this.detalleDeCompra));
        } else {
            this.subscribeToSaveResponse(
                this.detalleDeCompraService.create(this.detalleDeCompra));
        }
    }

    private subscribeToSaveResponse(result: Observable<DetalleDeCompra>) {
        result.subscribe((res: DetalleDeCompra) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DetalleDeCompra) {
        this.eventManager.broadcast({ name: 'ordenDeCompraListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrdenDeCompraById(index: number, item: OrdenDeCompra) {
        return item.id;
    }

    trackProductoById(index: number, item: Producto) {
        return item.id;
    }

    onProductChange(producto) {
        this.detalleDeCompra.precio = producto.precioDeCosto;
        if (this.detalleDeCompra.cantidad) {
            this.detalleDeCompra.subTotal = this.detalleDeCompra.cantidad * this.detalleDeCompra.precio;
        }
    }

    onPriceChange(price) {
        if (this.detalleDeCompra.cantidad) {
            this.detalleDeCompra.subTotal = this.detalleDeCompra.cantidad * price;
        }
    }

    onQuantityChange(qty) {
        if (this.detalleDeCompra.precio) {
            this.detalleDeCompra.subTotal = qty * this.detalleDeCompra.precio;
        }
    }
}

@Component({
    selector: 'jhi-detalle-de-compra-popup',
    template: ''
})
export class DetalleDeCompraPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detalleDeCompraPopupService: DetalleDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.detalleDeCompraPopupService
                    .open(DetalleDeCompraDialogComponent as Component, params['id'], null);
            } else if ( params['ordenId'] ) {
                this.detalleDeCompraPopupService
                    .open(DetalleDeCompraDialogComponent as Component, null, params['ordenId']);
            } else {
                this.detalleDeCompraPopupService
                    .open(DetalleDeCompraDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
