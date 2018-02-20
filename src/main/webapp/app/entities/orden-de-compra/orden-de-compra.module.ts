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
    OrdenDeCompraInventoryDialogComponent,
    OrdenDeCompraInventoryPopupComponent,
    ordenDeCompraRoute,
    ordenDeCompraPopupRoute,
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
        OrdenDeCompraInventoryDialogComponent,
        OrdenDeCompraInventoryPopupComponent,
    ],
    entryComponents: [
        OrdenDeCompraComponent,
        OrdenDeCompraDialogComponent,
        OrdenDeCompraPopupComponent,
        OrdenDeCompraDeleteDialogComponent,
        OrdenDeCompraDeletePopupComponent,
        OrdenDeCompraInventoryDialogComponent,
        OrdenDeCompraInventoryPopupComponent,
    ],
    providers: [
        OrdenDeCompraService,
        OrdenDeCompraPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmOrdenDeCompraModule {}
