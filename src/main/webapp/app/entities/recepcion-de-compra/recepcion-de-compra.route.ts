import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RecepcionDeCompraComponent } from './recepcion-de-compra.component';
import { RecepcionDeCompraDetailComponent } from './recepcion-de-compra-detail.component';
import { RecepcionDeCompraPopupComponent } from './recepcion-de-compra-dialog.component';
import { RecepcionDeCompraDeletePopupComponent } from './recepcion-de-compra-delete-dialog.component';

export const recepcionDeCompraRoute: Routes = [
    {
        path: 'recepcion-de-compra',
        component: RecepcionDeCompraComponent,
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
