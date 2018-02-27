import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacrmSharedModule } from '../../shared';
import {
    OrdenDeCompraService,
    OrdenDeCompraPopupService,
    OrdenDeCompraComponent,
    OrdenDeCompraDetailComponent,
    OrdenDeCompraDialogComponent,
    OrdenDeCompraPopupComponent,
    OrdenDeCompraDeletePopupComponent,
    OrdenDeCompraDeleteDialogComponent,
    ordenDeCompraRoute,
    ordenDeCompraPopupRoute,
    OrdenDeCompraResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ordenDeCompraRoute,
    ...ordenDeCompraPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrdenDeCompraComponent,
        OrdenDeCompraDetailComponent,
        OrdenDeCompraDialogComponent,
        OrdenDeCompraDeleteDialogComponent,
        OrdenDeCompraPopupComponent,
        OrdenDeCompraDeletePopupComponent,
    ],
    entryComponents: [
        OrdenDeCompraComponent,
        OrdenDeCompraDialogComponent,
        OrdenDeCompraPopupComponent,
        OrdenDeCompraDeleteDialogComponent,
        OrdenDeCompraDeletePopupComponent,
    ],
    providers: [
        OrdenDeCompraService,
        OrdenDeCompraPopupService,
        OrdenDeCompraResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmOrdenDeCompraModule {}
