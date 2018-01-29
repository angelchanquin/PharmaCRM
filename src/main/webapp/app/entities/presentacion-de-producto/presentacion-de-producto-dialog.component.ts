import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PresentacionDeProducto } from './presentacion-de-producto.model';
import { PresentacionDeProductoPopupService } from './presentacion-de-producto-popup.service';
import { PresentacionDeProductoService } from './presentacion-de-producto.service';

@Component({
    selector: 'jhi-presentacion-de-producto-dialog',
    templateUrl: './presentacion-de-producto-dialog.component.html'
})
export class PresentacionDeProductoDialogComponent implements OnInit {

    presentacionDeProducto: PresentacionDeProducto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private presentacionDeProductoService: PresentacionDeProductoService,
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
        if (this.presentacionDeProducto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.presentacionDeProductoService.update(this.presentacionDeProducto));
        } else {
            this.subscribeToSaveResponse(
                this.presentacionDeProductoService.create(this.presentacionDeProducto));
        }
    }

    private subscribeToSaveResponse(result: Observable<PresentacionDeProducto>) {
        result.subscribe((res: PresentacionDeProducto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PresentacionDeProducto) {
        this.eventManager.broadcast({ name: 'presentacionDeProductoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-presentacion-de-producto-popup',
    template: ''
})
export class PresentacionDeProductoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private presentacionDeProductoPopupService: PresentacionDeProductoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.presentacionDeProductoPopupService
                    .open(PresentacionDeProductoDialogComponent as Component, params['id']);
            } else {
                this.presentacionDeProductoPopupService
                    .open(PresentacionDeProductoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
