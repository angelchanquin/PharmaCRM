import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DetalleDeCompra } from './detalle-de-compra.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DetalleDeCompraService {

    private resourceUrl =  SERVER_API_URL + 'api/detalle-de-compras';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/detalle-de-compras';

    constructor(private http: Http) { }

    create(detalleDeCompra: DetalleDeCompra): Observable<DetalleDeCompra> {
        const copy = this.convert(detalleDeCompra);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(detalleDeCompra: DetalleDeCompra): Observable<DetalleDeCompra> {
        const copy = this.convert(detalleDeCompra);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DetalleDeCompra> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }
    findByOrderId(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/orden/${id}`)
            .map((res: Response) => this.convertResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
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
     * Convert a returned JSON object to DetalleDeCompra.
     */
    private convertItemFromServer(json: any): DetalleDeCompra {
        const entity: DetalleDeCompra = Object.assign(new DetalleDeCompra(), json);
        return entity;
    }

    /**
     * Convert a DetalleDeCompra to a JSON which can be sent to the server.
     */
    private convert(detalleDeCompra: DetalleDeCompra): DetalleDeCompra {
        const copy: DetalleDeCompra = Object.assign({}, detalleDeCompra);
        return copy;
    }
}
