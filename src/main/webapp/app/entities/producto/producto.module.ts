import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacrmSharedModule } from '../../shared';
import {
    ProductoService,
    ProductoPopupService,
    ProductoComponent,
    ProductoDetailComponent,
    ProductoDialogComponent,
    ProductoPopupComponent,
    ProductoDeletePopupComponent,
    ProductoDeleteDialogComponent,
    productoRoute,
    productoPopupRoute,
    ProductoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productoRoute,
    ...productoPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProductoComponent,
        ProductoDetailComponent,
        ProductoDialogComponent,
        ProductoDeleteDialogComponent,
        ProductoPopupComponent,
        ProductoDeletePopupComponent,
    ],
    entryComponents: [
        ProductoComponent,
        ProductoDialogComponent,
        ProductoPopupComponent,
        ProductoDeleteDialogComponent,
        ProductoDeletePopupComponent,
    ],
    providers: [
        ProductoService,
        ProductoPopupService,
        ProductoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmProductoModule {}
