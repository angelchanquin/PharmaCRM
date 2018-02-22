import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OrdenDeCompraComponent } from './orden-de-compra.component';
import { OrdenDeCompraDetailComponent } from './orden-de-compra-detail.component';
import { OrdenDeCompraPopupComponent } from './orden-de-compra-dialog.component';
import { OrdenDeCompraDeletePopupComponent } from './orden-de-compra-delete-dialog.component';

export const ordenDeCompraRoute: Routes = [
    {
        path: 'orden-de-compra',
        component: OrdenDeCompraComponent,
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
