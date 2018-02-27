import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DetalleDeRecepcionDeCompra } from './detalle-de-recepcion-de-compra.model';
import { DetalleDeRecepcionDeCompraService } from './detalle-de-recepcion-de-compra.service';

@Component({
    selector: 'jhi-detalle-de-recepcion-de-compra-detail',
    templateUrl: './detalle-de-recepcion-de-compra-detail.component.html'
})
export class DetalleDeRecepcionDeCompraDetailComponent implements OnInit, OnDestroy {

    detalleDeRecepcionDeCompra: DetalleDeRecepcionDeCompra;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detalleDeRecepcionDeCompraService: DetalleDeRecepcionDeCompraService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetalleDeRecepcionDeCompras();
    }

    load(id) {
        this.detalleDeRecepcionDeCompraService.find(id).subscribe((detalleDeRecepcionDeCompra) => {
            this.detalleDeRecepcionDeCompra = detalleDeRecepcionDeCompra;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetalleDeRecepcionDeCompras() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detalleDeRecepcionDeCompraListModification',
            (response) => this.load(this.detalleDeRecepcionDeCompra.id)
        );
    }
}
