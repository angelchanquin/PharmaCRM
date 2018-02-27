import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RecepcionDeCompraComponent } from './recepcion-de-compra.component';
import { RecepcionDeCompraDetailComponent } from './recepcion-de-compra-detail.component';
import { RecepcionDeCompraPopupComponent } from './recepcion-de-compra-dialog.component';
import { RecepcionDeCompraDeletePopupComponent } from './recepcion-de-compra-delete-dialog.component';

@Injectable()
export class RecepcionDeCompraResolvePagingParams implements Resolve<any> {

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

export const recepcionDeCompraRoute: Routes = [
    {
        path: 'recepcion-de-compra',
        component: RecepcionDeCompraComponent,
        resolve: {
            'pagingParams': RecepcionDeCompraResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.recepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'recepcion-de-compra/:id',
        component: RecepcionDeCompraDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.recepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recepcionDeCompraPopupRoute: Routes = [
    {
        path: 'recepcion-de-compra-new',
        component: RecepcionDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.recepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recepcion-de-compra/:id/edit',
        component: RecepcionDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.recepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recepcion-de-compra/:id/delete',
        component: RecepcionDeCompraDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.recepcionDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
