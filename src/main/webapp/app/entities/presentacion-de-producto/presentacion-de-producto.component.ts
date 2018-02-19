import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    currentSearch: string;

    constructor(
        private presentacionDeProductoService: PresentacionDeProductoService,
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
            this.presentacionDeProductoService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.presentacionDeProductos = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.presentacionDeProductoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.presentacionDeProductos = res.json;
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
