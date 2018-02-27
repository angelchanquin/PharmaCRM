import { BaseEntity } from './../../shared';

export class DetalleDeRecepcionDeCompra implements BaseEntity {
    constructor(
        public id?: number,
        public cantidadOrdenada?: number,
        public cantidadRecibida?: number,
        public precio?: number,
        public noDeLote?: string,
        public fechaDeVencimiento?: any,
        public producto?: BaseEntity,
        public recepcionDeCompra?: BaseEntity,
    ) {
    }
}
