import { BaseEntity } from './../../shared';

export class OrdenDeCompra implements BaseEntity {
    constructor(
        public id?: number,
        public numeroDeReferencia?: string,
        public fecha?: any,
        public total?: number,
        public detalles?: BaseEntity[],
    ) {
    }
}
