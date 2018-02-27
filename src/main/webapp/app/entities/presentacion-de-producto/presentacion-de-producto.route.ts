import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PresentacionDeProductoComponent } from './presentacion-de-producto.component';
import { PresentacionDeProductoDetailComponent } from './presentacion-de-producto-detail.component';
import { PresentacionDeProductoPopupComponent } from './presentacion-de-producto-dialog.component';
import { PresentacionDeProductoDeletePopupComponent } from './presentacion-de-producto-delete-dialog.component';

@Injectable()
export class PresentacionDeProductoResolvePagingParams implements Resolve<any> {

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

export const presentacionDeProductoRoute: Routes = [
    {
        path: 'presentacion-de-producto',
        component: PresentacionDeProductoComponent,
        resolve: {
            'pagingParams': PresentacionDeProductoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.presentacionDeProducto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'presentacion-de-producto/:id',
        component: PresentacionDeProductoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.presentacionDeProducto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const presentacionDeProductoPopupRoute: Routes = [
    {
        path: 'presentacion-de-producto-new',
        component: PresentacionDeProductoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.presentacionDeProducto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'presentacion-de-producto/:id/edit',
        component: PresentacionDeProductoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.presentacionDeProducto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'presentacion-de-producto/:id/delete',
        component: PresentacionDeProductoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.presentacionDeProducto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
