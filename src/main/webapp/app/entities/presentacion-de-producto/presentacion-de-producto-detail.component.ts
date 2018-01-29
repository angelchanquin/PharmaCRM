import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PresentacionDeProducto } from './presentacion-de-producto.model';
import { PresentacionDeProductoService } from './presentacion-de-producto.service';

@Component({
    selector: 'jhi-presentacion-de-producto-detail',
    templateUrl: './presentacion-de-producto-detail.component.html'
})
export class PresentacionDeProductoDetailComponent implements OnInit, OnDestroy {

    presentacionDeProducto: PresentacionDeProducto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private presentacionDeProductoService: PresentacionDeProductoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPresentacionDeProductos();
    }

    load(id) {
        this.presentacionDeProductoService.find(id).subscribe((presentacionDeProducto) => {
            this.presentacionDeProducto = presentacionDeProducto;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPresentacionDeProductos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'presentacionDeProductoListModification',
            (response) => this.load(this.presentacionDeProducto.id)
        );
    }
}
