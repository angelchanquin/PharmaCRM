import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { InventarioComponent } from './inventario.component';
import { InventarioDetailComponent } from './inventario-detail.component';
import { InventarioPopupComponent } from './inventario-dialog.component';
import { InventarioDeletePopupComponent } from './inventario-delete-dialog.component';

export const inventarioRoute: Routes = [
    {
        path: 'inventario',
        component: InventarioComponent,
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
