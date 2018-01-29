import { BaseEntity } from './../../shared';

export class Proveedor implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public nombreDeContacto?: string,
        public apellidoDeContacto?: string,
        public correoElectronico?: string,
        public telefono?: string,
        public celular?: string,
        public sitioWeb?: string,
        public direccionDeFacturacion?: string,
        public direccionDeEnvio?: string,
        public productos?: BaseEntity[],
    ) {
    }
}
