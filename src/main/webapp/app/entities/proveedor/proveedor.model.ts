import { BaseEntity } from './../../shared';

export class Proveedor implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public contacto?: string,
        public correoElectronico?: string,
        public telefono?: string,
        public celular?: string,
        public sitioWeb?: string,
        public direccion?: string,
        public productos?: BaseEntity[],
        public ordenDeCompras?: BaseEntity[],
    ) {
    }
}
