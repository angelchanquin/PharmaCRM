import { BaseEntity } from './../../shared';

export class PresentacionDeProducto implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public productos?: BaseEntity[],
    ) {
    }
}
