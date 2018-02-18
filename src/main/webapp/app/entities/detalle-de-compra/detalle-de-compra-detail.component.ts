import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DetalleDeCompra } from './detalle-de-compra.model';
import { DetalleDeCompraService } from './detalle-de-compra.service';

@Component({
    selector: 'jhi-detalle-de-compra-detail',
    templateUrl: './detalle-de-compra-detail.component.html'
})
export class DetalleDeCompraDetailComponent implements OnInit, OnDestroy {

    detalleDeCompra: DetalleDeCompra;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detalleDeCompraService: DetalleDeCompraService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetalleDeCompras();
    }

    load(id) {
        this.detalleDeCompraService.find(id).subscribe((detalleDeCompra) => {
            this.detalleDeCompra = detalleDeCompra;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetalleDeCompras() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detalleDeCompraListModification',
            (response) => this.load(this.detalleDeCompra.id)
        );
    }
}
