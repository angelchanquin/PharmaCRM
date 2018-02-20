import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrdenDeCompra } from './orden-de-compra.model';
import { OrdenDeCompraPopupService } from './orden-de-compra-popup.service';
import { OrdenDeCompraService } from './orden-de-compra.service';

@Component({
    selector: 'jhi-orden-de-compra-inventory-dialog',
    templateUrl: './orden-de-compra-inventory-dialog.component.html'
})
export class OrdenDeCompraInventoryDialogComponent {

    ordenDeCompra: OrdenDeCompra;

    constructor(
        private ordenDeCompraService: OrdenDeCompraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordenDeCompraService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ordenDeCompraListModification',
                content: 'Enter to Inventory an ordenDeCompra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-orden-de-compra-inventory-popup',
    template: ''
})
export class OrdenDeCompraInventoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordenDeCompraPopupService: OrdenDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ordenDeCompraPopupService
                .open(OrdenDeCompraInventoryDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
