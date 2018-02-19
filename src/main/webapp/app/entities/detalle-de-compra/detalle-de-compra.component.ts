import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetalleDeCompra } from './detalle-de-compra.model';
import { DetalleDeCompraService } from './detalle-de-compra.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-detalle-de-compra',
    templateUrl: './detalle-de-compra.component.html'
})
export class DetalleDeCompraComponent implements OnInit, OnDestroy {
    detalleDeCompras: DetalleDeCompra[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private detalleDeCompraService: DetalleDeCompraService,
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
            this.detalleDeCompraService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.detalleDeCompras = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.detalleDeCompraService.query().subscribe(
            (res: ResponseWrapper) => {
                this.detalleDeCompras = res.json;
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
        this.registerChangeInDetalleDeCompras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DetalleDeCompra) {
        return item.id;
    }
    registerChangeInDetalleDeCompras() {
        this.eventSubscriber = this.eventManager.subscribe('detalleDeCompraListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
