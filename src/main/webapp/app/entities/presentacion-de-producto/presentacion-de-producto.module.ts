import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacrmSharedModule } from '../../shared';
import {
    PresentacionDeProductoService,
    PresentacionDeProductoPopupService,
    PresentacionDeProductoComponent,
    PresentacionDeProductoDetailComponent,
    PresentacionDeProductoDialogComponent,
    PresentacionDeProductoPopupComponent,
    PresentacionDeProductoDeletePopupComponent,
    PresentacionDeProductoDeleteDialogComponent,
    presentacionDeProductoRoute,
    presentacionDeProductoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...presentacionDeProductoRoute,
    ...presentacionDeProductoPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PresentacionDeProductoComponent,
        PresentacionDeProductoDetailComponent,
        PresentacionDeProductoDialogComponent,
        PresentacionDeProductoDeleteDialogComponent,
        PresentacionDeProductoPopupComponent,
        PresentacionDeProductoDeletePopupComponent,
    ],
    entryComponents: [
        PresentacionDeProductoComponent,
        PresentacionDeProductoDialogComponent,
        PresentacionDeProductoPopupComponent,
        PresentacionDeProductoDeleteDialogComponent,
        PresentacionDeProductoDeletePopupComponent,
    ],
    providers: [
        PresentacionDeProductoService,
        PresentacionDeProductoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmPresentacionDeProductoModule {}
