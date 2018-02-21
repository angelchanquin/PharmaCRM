import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RecepcionDeCompra } from './recepcion-de-compra.model';
import { RecepcionDeCompraService } from './recepcion-de-compra.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-recepcion-de-compra',
    templateUrl: './recepcion-de-compra.component.html'
})
export class RecepcionDeCompraComponent implements OnInit, OnDestroy {
recepcionDeCompras: RecepcionDeCompra[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private recepcionDeCompraService: RecepcionDeCompraService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.recepcionDeCompraService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.recepcionDeCompras = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.recepcionDeCompraService.query().subscribe(
            (res: ResponseWrapper) => {
                this.recepcionDeCompras = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRecepcionDeCompras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RecepcionDeCompra) {
        return item.id;
    }
    registerChangeInRecepcionDeCompras() {
        this.eventSubscriber = this.eventManager.subscribe('recepcionDeCompraListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
