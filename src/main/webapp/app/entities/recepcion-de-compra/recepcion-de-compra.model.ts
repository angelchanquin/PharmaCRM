import { BaseEntity } from './../../shared';

export const enum TipoDeRecepcionDeCompra {
    'TOTAL',
    ' PARCIAL'
}

export class RecepcionDeCompra implements BaseEntity {
    constructor(
        public id?: number,
        public noDeRecibo?: string,
        public fechaDeRecepcion?: any,
        public tipo?: TipoDeRecepcionDeCompra,
        public notas?: string,
        public ordenDeCompra?: BaseEntity,
    ) {
    }
}
