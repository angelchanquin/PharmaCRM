import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PresentacionDeProductoComponent } from './presentacion-de-producto.component';
import { PresentacionDeProductoDetailComponent } from './presentacion-de-producto-detail.component';
import { PresentacionDeProductoPopupComponent } from './presentacion-de-producto-dialog.component';
import { PresentacionDeProductoDeletePopupComponent } from './presentacion-de-producto-delete-dialog.component';

export const presentacionDeProductoRoute: Routes = [
    {
        path: 'presentacion-de-producto',
        component: PresentacionDeProductoComponent,
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
