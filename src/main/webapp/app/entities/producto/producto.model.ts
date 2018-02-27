import { BaseEntity } from './../../shared';
import {Proveedor} from '../proveedor';
import {PresentacionDeProducto} from '../presentacion-de-producto';

export const enum EstadoDeProducto {
    'ACTIVO',
    'INACTIVO'
}

export class Producto implements BaseEntity {
    constructor(
        public id?: number,
        public sku?: number,
        public nombre?: string,
        public precioDeVenta?: number,
        public precioDeVenta2?: number,
        public precioDeVenta3?: number,
        public precioDeCosto?: number,
        public unidadesEnStock?: number,
        public estado?: EstadoDeProducto,
        public minimoEnExistencia?: number,
        public detalleDeCompras?: BaseEntity[],
        public inventarios?: BaseEntity[],
        public detalleDeRecepcionDeCompras?: BaseEntity[],
        public proveedor?: Proveedor,
        public presentacion?: PresentacionDeProducto,
    ) {
    }
}
