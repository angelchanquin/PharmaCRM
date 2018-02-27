import { BaseEntity } from './../../shared';

export const enum EstadoDeOrdenDeCompra {
    'ACTIVA',
    'EMITIDA',
    'CANCELADA',
    'CERRADA'
}

export const enum RecibidoOrdenDeCompra {
    'NO_RECIBIDO',
    'PARCIAL',
    'TOTAL'
}

export class OrdenDeCompra implements BaseEntity {
    constructor(
        public id?: number,
        public numeroDeReferencia?: string,
        public fecha?: any,
        public total?: number,
        public fechaDeEntregaEsperada?: any,
        public estado?: EstadoDeOrdenDeCompra,
        public recibido?: RecibidoOrdenDeCompra,
        public detalleDeCompras?: BaseEntity[],
        public recepcionDeCompras?: BaseEntity[],
        public proveedor?: BaseEntity,
    ) {
    }
}
