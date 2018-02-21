import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RecepcionDeCompra } from './recepcion-de-compra.model';
import { RecepcionDeCompraService } from './recepcion-de-compra.service';

@Component({
    selector: 'jhi-recepcion-de-compra-detail',
    templateUrl: './recepcion-de-compra-detail.component.html'
})
export class RecepcionDeCompraDetailComponent implements OnInit, OnDestroy {

    recepcionDeCompra: RecepcionDeCompra;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private recepcionDeCompraService: RecepcionDeCompraService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRecepcionDeCompras();
    }

    load(id) {
        this.recepcionDeCompraService.find(id).subscribe((recepcionDeCompra) => {
            this.recepcionDeCompra = recepcionDeCompra;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRecepcionDeCompras() {
        this.eventSubscriber = this.eventManager.subscribe(
            'recepcionDeCompraListModification',
            (response) => this.load(this.recepcionDeCompra.id)
        );
    }
}
