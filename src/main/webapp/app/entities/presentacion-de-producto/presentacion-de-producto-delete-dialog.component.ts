import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PresentacionDeProducto } from './presentacion-de-producto.model';
import { PresentacionDeProductoPopupService } from './presentacion-de-producto-popup.service';
import { PresentacionDeProductoService } from './presentacion-de-producto.service';

@Component({
    selector: 'jhi-presentacion-de-producto-delete-dialog',
    templateUrl: './presentacion-de-producto-delete-dialog.component.html'
})
export class PresentacionDeProductoDeleteDialogComponent {

    presentacionDeProducto: PresentacionDeProducto;

    constructor(
        private presentacionDeProductoService: PresentacionDeProductoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.presentacionDeProductoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'presentacionDeProductoListModification',
                content: 'Deleted an presentacionDeProducto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-presentacion-de-producto-delete-popup',
    template: ''
})
export class PresentacionDeProductoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private presentacionDeProductoPopupService: PresentacionDeProductoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.presentacionDeProductoPopupService
                .open(PresentacionDeProductoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
