import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacrmSharedModule } from '../../shared';
import {
    RecepcionDeCompraService,
    RecepcionDeCompraPopupService,
    RecepcionDeCompraComponent,
    RecepcionDeCompraDetailComponent,
    RecepcionDeCompraDialogComponent,
    RecepcionDeCompraPopupComponent,
    RecepcionDeCompraDeletePopupComponent,
    RecepcionDeCompraDeleteDialogComponent,
    recepcionDeCompraRoute,
    recepcionDeCompraPopupRoute,
    RecepcionDeCompraResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...recepcionDeCompraRoute,
    ...recepcionDeCompraPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RecepcionDeCompraComponent,
        RecepcionDeCompraDetailComponent,
        RecepcionDeCompraDialogComponent,
        RecepcionDeCompraDeleteDialogComponent,
        RecepcionDeCompraPopupComponent,
        RecepcionDeCompraDeletePopupComponent,
    ],
    entryComponents: [
        RecepcionDeCompraComponent,
        RecepcionDeCompraDialogComponent,
        RecepcionDeCompraPopupComponent,
        RecepcionDeCompraDeleteDialogComponent,
        RecepcionDeCompraDeletePopupComponent,
    ],
    providers: [
        RecepcionDeCompraService,
        RecepcionDeCompraPopupService,
        RecepcionDeCompraResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmRecepcionDeCompraModule {}
