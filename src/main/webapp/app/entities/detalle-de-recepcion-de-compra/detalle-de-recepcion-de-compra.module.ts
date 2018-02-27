import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacrmSharedModule } from '../../shared';
import {
    DetalleDeRecepcionDeCompraService,
    DetalleDeRecepcionDeCompraPopupService,
    DetalleDeRecepcionDeCompraComponent,
    DetalleDeRecepcionDeCompraDetailComponent,
    DetalleDeRecepcionDeCompraDialogComponent,
    DetalleDeRecepcionDeCompraPopupComponent,
    DetalleDeRecepcionDeCompraDeletePopupComponent,
    DetalleDeRecepcionDeCompraDeleteDialogComponent,
    detalleDeRecepcionDeCompraRoute,
    detalleDeRecepcionDeCompraPopupRoute,
    DetalleDeRecepcionDeCompraResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...detalleDeRecepcionDeCompraRoute,
    ...detalleDeRecepcionDeCompraPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DetalleDeRecepcionDeCompraComponent,
        DetalleDeRecepcionDeCompraDetailComponent,
        DetalleDeRecepcionDeCompraDialogComponent,
        DetalleDeRecepcionDeCompraDeleteDialogComponent,
        DetalleDeRecepcionDeCompraPopupComponent,
        DetalleDeRecepcionDeCompraDeletePopupComponent,
    ],
    entryComponents: [
        DetalleDeRecepcionDeCompraComponent,
        DetalleDeRecepcionDeCompraDialogComponent,
        DetalleDeRecepcionDeCompraPopupComponent,
        DetalleDeRecepcionDeCompraDeleteDialogComponent,
        DetalleDeRecepcionDeCompraDeletePopupComponent,
    ],
    providers: [
        DetalleDeRecepcionDeCompraService,
        DetalleDeRecepcionDeCompraPopupService,
        DetalleDeRecepcionDeCompraResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmDetalleDeRecepcionDeCompraModule {}
