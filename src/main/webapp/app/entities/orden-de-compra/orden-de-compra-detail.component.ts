import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OrdenDeCompra } from './orden-de-compra.model';
import { OrdenDeCompraService } from './orden-de-compra.service';
import {DetalleDeCompra, DetalleDeCompraService} from '../detalle-de-compra';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-orden-de-compra-detail',
    templateUrl: './orden-de-compra-detail.component.html'
})
export class OrdenDeCompraDetailComponent implements OnInit, OnDestroy {

    ordenDeCompra: OrdenDeCompra;
    detalleDeCompras: DetalleDeCompra[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ordenDeCompraService: OrdenDeCompraService,
        private detalleDeCompraService: DetalleDeCompraService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrdenDeCompras();
    }

    load(id) {
        this.ordenDeCompraService.find(id).subscribe((ordenDeCompra) => {
            this.ordenDeCompra = ordenDeCompra;
            this.detalleDeCompraService.findByOrderId(ordenDeCompra.id).subscribe( (res: ResponseWrapper) => {
                this.detalleDeCompras = res.json;
            } );
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrdenDeCompras() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ordenDeCompraListModification',
            (response) => this.load(this.ordenDeCompra.id)
        );
    }
}
