import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrdenDeCompra } from './orden-de-compra.model';
import { OrdenDeCompraPopupService } from './orden-de-compra-popup.service';
import { OrdenDeCompraService } from './orden-de-compra.service';
import { Proveedor, ProveedorService } from '../proveedor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-orden-de-compra-dialog',
    templateUrl: './orden-de-compra-dialog.component.html'
})
export class OrdenDeCompraDialogComponent implements OnInit {

    ordenDeCompra: OrdenDeCompra;
    isSaving: boolean;

    proveedors: Proveedor[];
    fechaDp: any;
    fechaDeEntregaEsperadaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ordenDeCompraService: OrdenDeCompraService,
        private proveedorService: ProveedorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.proveedorService.query()
            .subscribe((res: ResponseWrapper) => { this.proveedors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ordenDeCompra.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ordenDeCompraService.update(this.ordenDeCompra));
        } else {
            this.subscribeToSaveResponse(
                this.ordenDeCompraService.create(this.ordenDeCompra));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrdenDeCompra>) {
        result.subscribe((res: OrdenDeCompra) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OrdenDeCompra) {
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

    trackProveedorById(index: number, item: Proveedor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-orden-de-compra-popup',
    template: ''
})
export class OrdenDeCompraPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordenDeCompraPopupService: OrdenDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ordenDeCompraPopupService
                    .open(OrdenDeCompraDialogComponent as Component, params['id']);
            } else {
                this.ordenDeCompraPopupService
                    .open(OrdenDeCompraDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
