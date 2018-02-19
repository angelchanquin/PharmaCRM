import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Inventario } from './inventario.model';
import { InventarioPopupService } from './inventario-popup.service';
import { InventarioService } from './inventario.service';

@Component({
    selector: 'jhi-inventario-delete-dialog',
    templateUrl: './inventario-delete-dialog.component.html'
})
export class InventarioDeleteDialogComponent {

    inventario: Inventario;

    constructor(
        private inventarioService: InventarioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventarioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inventarioListModification',
                content: 'Deleted an inventario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventario-delete-popup',
    template: ''
})
export class InventarioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventarioPopupService: InventarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inventarioPopupService
                .open(InventarioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
