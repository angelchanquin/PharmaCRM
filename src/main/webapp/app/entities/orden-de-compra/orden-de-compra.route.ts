import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OrdenDeCompraComponent } from './orden-de-compra.component';
import { OrdenDeCompraDetailComponent } from './orden-de-compra-detail.component';
import { OrdenDeCompraPopupComponent } from './orden-de-compra-dialog.component';
import { OrdenDeCompraDeletePopupComponent } from './orden-de-compra-delete-dialog.component';

@Injectable()
export class OrdenDeCompraResolvePagingParams implements Resolve<any> {

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

export const ordenDeCompraRoute: Routes = [
    {
        path: 'orden-de-compra',
        component: OrdenDeCompraComponent,
        resolve: {
            'pagingParams': OrdenDeCompraResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.ordenDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'orden-de-compra/:id',
        component: OrdenDeCompraDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.ordenDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordenDeCompraPopupRoute: Routes = [
    {
        path: 'orden-de-compra-new',
        component: OrdenDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.ordenDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'orden-de-compra/:id/edit',
        component: OrdenDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.ordenDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'orden-de-compra/:id/delete',
        component: OrdenDeCompraDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'pharmacrmApp.ordenDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
