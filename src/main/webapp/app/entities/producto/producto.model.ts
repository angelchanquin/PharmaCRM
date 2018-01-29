import { BaseEntity } from './../../shared';

export const enum EstadoDeProducto {
    'ACTIVO',
    'INACTIVO'
}

export class Producto implements BaseEntity {
    constructor(
        public id?: number,
        public sku?: number,
        public nombre?: string,
        public upc?: number,
        public precioDeVenta?: number,
        public precioDeVenta2?: number,
        public precioDeVenta3?: number,
        public precioDeCompra?: number,
        public unidadesEnStock?: number,
        public estado?: EstadoDeProducto,
        public fechaDeExpiracion?: any,
        public presentacion?: BaseEntity,
        public proveedor?: BaseEntity,
    ) {
    }
}
