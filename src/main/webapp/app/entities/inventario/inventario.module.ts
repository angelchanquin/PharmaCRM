import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PharmacrmSharedModule } from '../../shared';
import {
    InventarioService,
    InventarioPopupService,
    InventarioComponent,
    InventarioDetailComponent,
    InventarioDialogComponent,
    InventarioPopupComponent,
    InventarioDeletePopupComponent,
    InventarioDeleteDialogComponent,
    inventarioRoute,
    inventarioPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inventarioRoute,
    ...inventarioPopupRoute,
];

@NgModule({
    imports: [
        PharmacrmSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InventarioComponent,
        InventarioDetailComponent,
        InventarioDialogComponent,
        InventarioDeleteDialogComponent,
        InventarioPopupComponent,
        InventarioDeletePopupComponent,
    ],
    entryComponents: [
        InventarioComponent,
        InventarioDialogComponent,
        InventarioPopupComponent,
        InventarioDeleteDialogComponent,
        InventarioDeletePopupComponent,
    ],
    providers: [
        InventarioService,
        InventarioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmInventarioModule {}
