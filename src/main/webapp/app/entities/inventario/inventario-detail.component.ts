import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Inventario } from './inventario.model';
import { InventarioService } from './inventario.service';

@Component({
    selector: 'jhi-inventario-detail',
    templateUrl: './inventario-detail.component.html'
})
export class InventarioDetailComponent implements OnInit, OnDestroy {

    inventario: Inventario;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inventarioService: InventarioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInventarios();
    }

    load(id) {
        this.inventarioService.find(id).subscribe((inventario) => {
            this.inventario = inventario;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInventarios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inventarioListModification',
            (response) => this.load(this.inventario.id)
        );
    }
}
