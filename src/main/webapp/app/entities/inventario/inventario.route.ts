import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InventarioComponent } from './inventario.component';
import { InventarioDetailComponent } from './inventario-detail.component';
import { InventarioPopupComponent } from './inventario-dialog.component';
import { InventarioDeletePopupComponent } from './inventario-delete-dialog.component';

@Injectable()
export class InventarioResolvePagingParams implements Resolve<any> {

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

export const inventarioRoute: Routes = [
    {
        path: 'inventario',
        component: InventarioComponent,
        resolve: {
            'pagingParams': InventarioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.inventario.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inventario/:id',
        component: InventarioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.inventario.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventarioPopupRoute: Routes = [
    {
        path: 'inventario-new',
        component: InventarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.inventario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventario/:id/edit',
        component: InventarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.inventario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventario/:id/delete',
        component: InventarioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.inventario.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
