import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrdenDeCompra } from './orden-de-compra.model';
import { OrdenDeCompraPopupService } from './orden-de-compra-popup.service';
import { OrdenDeCompraService } from './orden-de-compra.service';

@Component({
    selector: 'jhi-orden-de-compra-dialog',
    templateUrl: './orden-de-compra-dialog.component.html'
})
export class OrdenDeCompraDialogComponent implements OnInit {

    ordenDeCompra: OrdenDeCompra;
    isSaving: boolean;
    fechaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private ordenDeCompraService: OrdenDeCompraService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
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
