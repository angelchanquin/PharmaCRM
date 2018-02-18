import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PharmacrmProveedorModule } from './proveedor/proveedor.module';
import { PharmacrmPresentacionDeProductoModule } from './presentacion-de-producto/presentacion-de-producto.module';
import { PharmacrmProductoModule } from './producto/producto.module';
import { PharmacrmOrdenDeCompraModule } from './orden-de-compra/orden-de-compra.module';
import { PharmacrmDetalleDeCompraModule } from './detalle-de-compra/detalle-de-compra.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PharmacrmProveedorModule,
        PharmacrmPresentacionDeProductoModule,
        PharmacrmProductoModule,
        PharmacrmOrdenDeCompraModule,
        PharmacrmDetalleDeCompraModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PharmacrmEntityModule {}
