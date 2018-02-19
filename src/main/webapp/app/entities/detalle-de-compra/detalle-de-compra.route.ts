import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DetalleDeCompraPopupComponent } from './detalle-de-compra-dialog.component';
import { DetalleDeCompraDeletePopupComponent } from './detalle-de-compra-delete-dialog.component';

export const detalleDeCompraRoute: Routes = [];

export const detalleDeCompraPopupRoute: Routes = [
    {
        path: 'detalle-de-compra-new/:ordenId',
        component: DetalleDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-de-compra/:id/edit',
        component: DetalleDeCompraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'detalle-de-compra/:id/delete',
        component: DetalleDeCompraDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pharmacrmApp.detalleDeCompra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
