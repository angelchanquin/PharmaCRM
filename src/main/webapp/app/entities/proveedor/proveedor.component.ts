import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Proveedor } from './proveedor.model';
import { ProveedorService } from './proveedor.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-proveedor',
    templateUrl: './proveedor.component.html'
})
export class ProveedorComponent implements OnInit, OnDestroy {
proveedors: Proveedor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private proveedorService: ProveedorService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.proveedorService.query().subscribe(
            (res: ResponseWrapper) => {
                this.proveedors = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProveedors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Proveedor) {
        return item.id;
    }
    registerChangeInProveedors() {
        this.eventSubscriber = this.eventManager.subscribe('proveedorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
