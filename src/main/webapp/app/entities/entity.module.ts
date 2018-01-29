import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PharmacrmProveedorModule } from './proveedor/proveedor.module';
import { PharmacrmPresentacionDeProductoModule } from './presentacion-de-producto/presentacion-de-producto.module';
import { PharmacrmProductoModule } from './producto/producto.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PharmacrmProveedorModule,
        PharmacrmPresentacionDeProductoModule,
        PharmacrmProductoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmEntityModule {}
