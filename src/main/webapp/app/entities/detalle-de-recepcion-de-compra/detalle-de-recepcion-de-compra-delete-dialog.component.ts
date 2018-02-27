import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DetalleDeRecepcionDeCompra } from './detalle-de-recepcion-de-compra.model';
import { DetalleDeRecepcionDeCompraPopupService } from './detalle-de-recepcion-de-compra-popup.service';
import { DetalleDeRecepcionDeCompraService } from './detalle-de-recepcion-de-compra.service';

@Component({
    selector: 'jhi-detalle-de-recepcion-de-compra-delete-dialog',
    templateUrl: './detalle-de-recepcion-de-compra-delete-dialog.component.html'
})
export class DetalleDeRecepcionDeCompraDeleteDialogComponent {

    detalleDeRecepcionDeCompra: DetalleDeRecepcionDeCompra;

    constructor(
        private detalleDeRecepcionDeCompraService: DetalleDeRecepcionDeCompraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detalleDeRecepcionDeCompraService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'detalleDeRecepcionDeCompraListModification',
                content: 'Deleted an detalleDeRecepcionDeCompra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detalle-de-recepcion-de-compra-delete-popup',
    template: ''
})
export class DetalleDeRecepcionDeCompraDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detalleDeRecepcionDeCompraPopupService: DetalleDeRecepcionDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.detalleDeRecepcionDeCompraPopupService
                .open(DetalleDeRecepcionDeCompraDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
