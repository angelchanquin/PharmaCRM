import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PresentacionDeProducto } from './presentacion-de-producto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PresentacionDeProductoService {

    private resourceUrl =  SERVER_API_URL + 'api/presentacion-de-productos';

    constructor(private http: Http) { }

    create(presentacionDeProducto: PresentacionDeProducto): Observable<PresentacionDeProducto> {
        const copy = this.convert(presentacionDeProducto);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(presentacionDeProducto: PresentacionDeProducto): Observable<PresentacionDeProducto> {
        const copy = this.convert(presentacionDeProducto);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PresentacionDeProducto> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to PresentacionDeProducto.
     */
    private convertItemFromServer(json: any): PresentacionDeProducto {
        const entity: PresentacionDeProducto = Object.assign(new PresentacionDeProducto(), json);
        return entity;
    }

    /**
     * Convert a PresentacionDeProducto to a JSON which can be sent to the server.
     */
    private convert(presentacionDeProducto: PresentacionDeProducto): PresentacionDeProducto {
        const copy: PresentacionDeProducto = Object.assign({}, presentacionDeProducto);
        return copy;
    }
}
