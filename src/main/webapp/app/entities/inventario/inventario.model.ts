import { BaseEntity } from './../../shared';

export const enum TipoDeMovimiento {
    'INGRESO',
    'EGRESO',
    'CORRECCION'
}

export class Inventario implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public cantidad?: number,
        public tipoDeMovimiento?: TipoDeMovimiento,
        public precio?: number,
        public detalles?: string,
        public producto?: BaseEntity,
    ) {
    }
}
