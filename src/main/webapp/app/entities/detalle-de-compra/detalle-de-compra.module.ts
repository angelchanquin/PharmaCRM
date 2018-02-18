import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacrmSharedModule } from '../../shared';
import {
    DetalleDeCompraService,
    DetalleDeCompraPopupService,
    DetalleDeCompraComponent,
    DetalleDeCompraDetailComponent,
    DetalleDeCompraDialogComponent,
    DetalleDeCompraPopupComponent,
    DetalleDeCompraDeletePopupComponent,
    DetalleDeCompraDeleteDialogComponent,
    detalleDeCompraRoute,
    detalleDeCompraPopupRoute,
} from './';

const ENTITY_STATES = [
    ...detalleDeCompraRoute,
    ...detalleDeCompraPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DetalleDeCompraComponent,
        DetalleDeCompraDetailComponent,
        DetalleDeCompraDialogComponent,
        DetalleDeCompraDeleteDialogComponent,
        DetalleDeCompraPopupComponent,
        DetalleDeCompraDeletePopupComponent,
    ],
    entryComponents: [
        DetalleDeCompraComponent,
        DetalleDeCompraDialogComponent,
        DetalleDeCompraPopupComponent,
        DetalleDeCompraDeleteDialogComponent,
        DetalleDeCompraDeletePopupComponent,
    ],
    providers: [
        DetalleDeCompraService,
        DetalleDeCompraPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmDetalleDeCompraModule {}
