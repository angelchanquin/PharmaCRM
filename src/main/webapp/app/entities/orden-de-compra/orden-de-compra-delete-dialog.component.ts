import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrdenDeCompra } from './orden-de-compra.model';
import { OrdenDeCompraPopupService } from './orden-de-compra-popup.service';
import { OrdenDeCompraService } from './orden-de-compra.service';

@Component({
    selector: 'jhi-orden-de-compra-delete-dialog',
    templateUrl: './orden-de-compra-delete-dialog.component.html'
})
export class OrdenDeCompraDeleteDialogComponent {

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
                content: 'Deleted an ordenDeCompra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-orden-de-compra-delete-popup',
    template: ''
})
export class OrdenDeCompraDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordenDeCompraPopupService: OrdenDeCompraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ordenDeCompraPopupService
                .open(OrdenDeCompraDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
