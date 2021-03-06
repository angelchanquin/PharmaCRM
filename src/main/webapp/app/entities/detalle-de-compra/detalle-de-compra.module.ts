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
    DetalleDeCompraResolvePagingParams,
} from './';
import {NgSelectModule} from '@ng-select/ng-select';

const ENTITY_STATES = [
    ...detalleDeCompraRoute,
    ...detalleDeCompraPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        NgSelectModule,
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
        DetalleDeCompraResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmDetalleDeCompraModule {}
