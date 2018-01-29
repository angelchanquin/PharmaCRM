import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PresentacionDeProducto } from './presentacion-de-producto.model';
import { PresentacionDeProductoService } from './presentacion-de-producto.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-presentacion-de-producto',
    templateUrl: './presentacion-de-producto.component.html'
})
export class PresentacionDeProductoComponent implements OnInit, OnDestroy {
presentacionDeProductos: PresentacionDeProducto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private presentacionDeProductoService: PresentacionDeProductoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.presentacionDeProductoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.presentacionDeProductos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPresentacionDeProductos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PresentacionDeProducto) {
        return item.id;
    }
    registerChangeInPresentacionDeProductos() {
        this.eventSubscriber = this.eventManager.subscribe('presentacionDeProductoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
