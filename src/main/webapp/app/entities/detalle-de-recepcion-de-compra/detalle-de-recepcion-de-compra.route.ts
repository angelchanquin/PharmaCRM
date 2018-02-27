import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DetalleDeRecepcionDeCompraComponent } from './detalle-de-recepcion-de-compra.component';
import { DetalleDeRecepcionDeCompraDetailComponent } from './detalle-de-recepcion-de-compra-detail.component';
import { DetalleDeRecepcionDeCompraPopupComponent } from './detalle-de-recepcion-de-compra-dialog.component';
import { DetalleDeRecepcionDeCompraDeletePopupComponent } from './detalle-de-recepcion-de-compra-delete-dialog.component';

@Injectable()
export class DetalleDeRecepcionDeCompraResolvePagingParams implements Resolve<any> {

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

export const detalleDeRecepcionDeCompraRoute: Routes = [
    {
        path: 'detalle-de-recepcion-de-compra',
        component: DetalleDeRecepcionDeCompraComponent,
        resolve: {
            'pagingParams': DetalleDeRecepcionDeCompraResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeRecepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'detalle-de-recepcion-de-compra/:id',
        component: DetalleDeRecepcionDeCompraDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeRecepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detalleDeRecepcionDeCompraPopupRoute: Routes = [
    {
        path: 'detalle-de-recepcion-de-compra-new',
        component: DetalleDeRecepcionDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeRecepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-de-recepcion-de-compra/:id/edit',
        component: DetalleDeRecepcionDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeRecepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-de-recepcion-de-compra/:id/delete',
        component: DetalleDeRecepcionDeCompraDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeRecepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
