import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OrdenDeCompra } from './orden-de-compra.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OrdenDeCompraService {

    private resourceUrl =  SERVER_API_URL + 'api/orden-de-compras';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/orden-de-compras';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ordenDeCompra: OrdenDeCompra): Observable<OrdenDeCompra> {
        const copy = this.convert(ordenDeCompra);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ordenDeCompra: OrdenDeCompra): Observable<OrdenDeCompra> {
        const copy = this.convert(ordenDeCompra);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OrdenDeCompra> {
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
     * Convert a returned JSON object to OrdenDeCompra.
     */
    private convertItemFromServer(json: any): OrdenDeCompra {
        const entity: OrdenDeCompra = Object.assign(new OrdenDeCompra(), json);
        entity.fecha = this.dateUtils
            .convertLocalDateFromServer(json.fecha);
        entity.fechaDeEntregaEsparada = this.dateUtils
            .convertLocalDateFromServer(json.fechaDeEntregaEsparada);
        return entity;
    }

    /**
     * Convert a OrdenDeCompra to a JSON which can be sent to the server.
     */
    private convert(ordenDeCompra: OrdenDeCompra): OrdenDeCompra {
        const copy: OrdenDeCompra = Object.assign({}, ordenDeCompra);
        copy.fecha = this.dateUtils
            .convertLocalDateToServer(ordenDeCompra.fecha);
        copy.fechaDeEntregaEsparada = this.dateUtils
            .convertLocalDateToServer(ordenDeCompra.fechaDeEntregaEsparada);
        return copy;
    }
}
