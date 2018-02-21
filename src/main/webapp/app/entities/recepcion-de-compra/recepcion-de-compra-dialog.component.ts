import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RecepcionDeCompra } from './recepcion-de-compra.model';
import { RecepcionDeCompraPopupService } from './recepcion-de-compra-popup.service';
import { RecepcionDeCompraService } from './recepcion-de-compra.service';
import { OrdenDeCompra, OrdenDeCompraService } from '../orden-de-compra';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-recepcion-de-compra-dialog',
    templateUrl: './recepcion-de-compra-dialog.component.html'
})
export class RecepcionDeCompraDialogComponent implements OnInit {

    recepcionDeCompra: RecepcionDeCompra;
    isSaving: boolean;

    ordendecompras: OrdenDeCompra[];
    fechaDeRecepcionDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private recepcionDeCompraService: RecepcionDeCompraService,
        private ordenDeCompraService: OrdenDeCompraService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ordenDeCompraService.query()
            .subscribe((res: ResponseWrapper) => { this.ordendecompras = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.recepcionDeCompra.id !== undefined) {
            this.subscribeToSaveResponse(
                this.recepcionDeCompraService.update(this.recepcionDeCompra));
        } else {
            this.subscribeToSaveResponse(
                this.recepcionDeCompraService.create(this.recepcionDeCompra));
        }
    }

    private subscribeToSaveResponse(result: Observable<RecepcionDeCompra>) {
        result.subscribe((res: RecepcionDeCompra) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RecepcionDeCompra) {
        this.eventManager.broadcast({ name: 'recepcionDeCompraListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-recepcion-de-compra-popup',
    template: ''
})
export class RecepcionDeCompraPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recepcionDeCompraPopupService: RecepcionDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.recepcionDeCompraPopupService
                    .open(RecepcionDeCompraDialogComponent as Component, params['id']);
            } else {
                this.recepcionDeCompraPopupService
                    .open(RecepcionDeCompraDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
