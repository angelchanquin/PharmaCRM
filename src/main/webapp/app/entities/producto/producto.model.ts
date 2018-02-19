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
        public precioDeVenta?: number,
        public precioDeVenta2?: number,
        public precioDeVenta3?: number,
        public precioDeCosto?: number,
        public unidadesEnStock?: number,
        public estado?: EstadoDeProducto,
        public fechaDeExpiracion?: any,
        public inventarios?: BaseEntity[],
        public ordens?: BaseEntity[],
        public presentacion?: BaseEntity,
        public proveedor?: BaseEntity,
    ) {
    }
}
