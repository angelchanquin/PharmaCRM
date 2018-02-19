import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrdenDeCompra } from './orden-de-compra.model';
import { OrdenDeCompraService } from './orden-de-compra.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-orden-de-compra',
    templateUrl: './orden-de-compra.component.html'
})
export class OrdenDeCompraComponent implements OnInit, OnDestroy {
ordenDeCompras: OrdenDeCompra[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private ordenDeCompraService: OrdenDeCompraService,
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
            this.ordenDeCompraService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.ordenDeCompras = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.ordenDeCompraService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ordenDeCompras = res.json;
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
        this.registerChangeInOrdenDeCompras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: OrdenDeCompra) {
        return item.id;
    }
    registerChangeInOrdenDeCompras() {
        this.eventSubscriber = this.eventManager.subscribe('ordenDeCompraListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
