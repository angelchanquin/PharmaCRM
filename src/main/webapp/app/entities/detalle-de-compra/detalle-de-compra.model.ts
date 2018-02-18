import { BaseEntity } from './../../shared';

export class DetalleDeCompra implements BaseEntity {
    constructor(
        public id?: number,
        public cantidad?: number,
        public precio?: number,
        public subTotal?: number,
        public ordenDeCompra?: BaseEntity,
        public producto?: BaseEntity,
    ) {
    }
}
