import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    currentSearch: string;

    constructor(
        private inventarioService: InventarioService,
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
            this.inventarioService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.inventarios = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.inventarioService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inventarios = res.json;
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
