import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RecepcionDeCompra } from './recepcion-de-compra.model';
import { RecepcionDeCompraPopupService } from './recepcion-de-compra-popup.service';
import { RecepcionDeCompraService } from './recepcion-de-compra.service';

@Component({
    selector: 'jhi-recepcion-de-compra-delete-dialog',
    templateUrl: './recepcion-de-compra-delete-dialog.component.html'
})
export class RecepcionDeCompraDeleteDialogComponent {

    recepcionDeCompra: RecepcionDeCompra;

    constructor(
        private recepcionDeCompraService: RecepcionDeCompraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recepcionDeCompraService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'recepcionDeCompraListModification',
                content: 'Deleted an recepcionDeCompra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recepcion-de-compra-delete-popup',
    template: ''
})
export class RecepcionDeCompraDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recepcionDeCompraPopupService: RecepcionDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.recepcionDeCompraPopupService
                .open(RecepcionDeCompraDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
