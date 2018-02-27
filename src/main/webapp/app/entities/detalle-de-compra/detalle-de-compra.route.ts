import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DetalleDeCompraComponent } from './detalle-de-compra.component';
import { DetalleDeCompraDetailComponent } from './detalle-de-compra-detail.component';
import { DetalleDeCompraPopupComponent } from './detalle-de-compra-dialog.component';
import { DetalleDeCompraDeletePopupComponent } from './detalle-de-compra-delete-dialog.component';

@Injectable()
export class DetalleDeCompraResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const detalleDeCompraRoute: Routes = [];

export const detalleDeCompraPopupRoute: Routes = [
    {
        path: 'detalle-de-compra-new/:ordenId',
        component: DetalleDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.detalleDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-de-compra/:id/edit',
        component: DetalleDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.detalleDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-de-compra/:id/delete',
        component: DetalleDeCompraDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.detalleDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
