import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Inventario } from './inventario.model';
import { InventarioPopupService } from './inventario-popup.service';
import { InventarioService } from './inventario.service';
import { Producto, ProductoService } from '../producto';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inventario-dialog',
    templateUrl: './inventario-dialog.component.html'
})
export class InventarioDialogComponent implements OnInit {

    inventario: Inventario;
    isSaving: boolean;

    productos: Producto[];
    fechaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private inventarioService: InventarioService,
        private productoService: ProductoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productoService.query()
            .subscribe((res: ResponseWrapper) => { this.productos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inventario.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inventarioService.update(this.inventario));
        } else {
            this.subscribeToSaveResponse(
                this.inventarioService.create(this.inventario));
        }
    }

    private subscribeToSaveResponse(result: Observable<Inventario>) {
        result.subscribe((res: Inventario) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Inventario) {
        this.eventManager.broadcast({ name: 'inventarioListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-inventario-popup',
    template: ''
})
export class InventarioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventarioPopupService: InventarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inventarioPopupService
                    .open(InventarioDialogComponent as Component, params['id']);
            } else {
                this.inventarioPopupService
                    .open(InventarioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
