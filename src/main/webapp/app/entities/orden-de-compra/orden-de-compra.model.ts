import { BaseEntity } from './../../shared';

export const enum EstadoDeOrden {
    'ACTIVA',
    ' EMITIDA',
    ' CANCELADA',
    ' CERRADA'
}

export const enum EstadoRecibidoDeOrden {
    'NO_RECIBIDO',
    ' PARCIAL',
    ' TOTAL'
}

export class OrdenDeCompra implements BaseEntity {
    constructor(
        public id?: number,
        public numeroDeReferencia?: string,
        public fecha?: any,
        public total?: number,
        public fechaDeEntregaEsparada?: any,
        public estado?: EstadoDeOrden,
        public estadoRecibido?: EstadoRecibidoDeOrden,
        public detalles?: BaseEntity[],
        public proveedor?: BaseEntity,
    ) {
    }
}
