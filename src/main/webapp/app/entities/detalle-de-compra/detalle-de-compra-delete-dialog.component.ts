import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DetalleDeCompra } from './detalle-de-compra.model';
import { DetalleDeCompraPopupService } from './detalle-de-compra-popup.service';
import { DetalleDeCompraService } from './detalle-de-compra.service';

@Component({
    selector: 'jhi-detalle-de-compra-delete-dialog',
    templateUrl: './detalle-de-compra-delete-dialog.component.html'
})
export class DetalleDeCompraDeleteDialogComponent {

    detalleDeCompra: DetalleDeCompra;

    constructor(
        private detalleDeCompraService: DetalleDeCompraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detalleDeCompraService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ordenDeCompraListModification',
                content: 'Deleted an detalleDeCompra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detalle-de-compra-delete-popup',
    template: ''
})
export class DetalleDeCompraDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detalleDeCompraPopupService: DetalleDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.detalleDeCompraPopupService
                .open(DetalleDeCompraDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
