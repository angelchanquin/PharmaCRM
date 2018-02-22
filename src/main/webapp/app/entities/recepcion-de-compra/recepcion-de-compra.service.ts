import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RecepcionDeCompra } from './recepcion-de-compra.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RecepcionDeCompraService {

    private resourceUrl =  SERVER_API_URL + 'api/recepcion-de-compras';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/recepcion-de-compras';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(recepcionDeCompra: RecepcionDeCompra): Observable<RecepcionDeCompra> {
        const copy = this.convert(recepcionDeCompra);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(recepcionDeCompra: RecepcionDeCompra): Observable<RecepcionDeCompra> {
        const copy = this.convert(recepcionDeCompra);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RecepcionDeCompra> {
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
     * Convert a returned JSON object to RecepcionDeCompra.
     */
    private convertItemFromServer(json: any): RecepcionDeCompra {
        const entity: RecepcionDeCompra = Object.assign(new RecepcionDeCompra(), json);
        entity.fechaDeRecepcion = this.dateUtils
            .convertLocalDateFromServer(json.fechaDeRecepcion);
        return entity;
    }

    /**
     * Convert a RecepcionDeCompra to a JSON which can be sent to the server.
     */
    private convert(recepcionDeCompra: RecepcionDeCompra): RecepcionDeCompra {
        const copy: RecepcionDeCompra = Object.assign({}, recepcionDeCompra);
        copy.fechaDeRecepcion = this.dateUtils
            .convertLocalDateToServer(recepcionDeCompra.fechaDeRecepcion);
        return copy;
    }
}
