import { BaseEntity } from './../../shared';
import {OrdenDeCompra} from '../orden-de-compra';
import {Producto} from '../producto';

export class DetalleDeCompra implements BaseEntity {
    constructor(
        public id?: number,
        public cantidad?: number,
        public precio?: number,
        public subTotal?: number,
        public ordenDeCompra?: OrdenDeCompra,
        public producto?: Producto,
    ) {
    }
}
