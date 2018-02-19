import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Inventario } from './inventario.model';
import { InventarioService } from './inventario.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inventario',
    templateUrl: './inventario.component.html'
})
export class InventarioComponent implements OnInit, OnDestroy {
inventarios: Inventario[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inventarioService: InventarioService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inventarioService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inventarios = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInventarios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Inventario) {
        return item.id;
    }
    registerChangeInInventarios() {
        this.eventSubscriber = this.eventManager.subscribe('inventarioListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
