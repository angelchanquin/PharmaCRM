import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DetalleDeRecepcionDeCompra } from './detalle-de-recepcion-de-compra.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DetalleDeRecepcionDeCompraService {

    private resourceUrl =  SERVER_API_URL + 'api/detalle-de-recepcion-de-compras';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/detalle-de-recepcion-de-compras';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(detalleDeRecepcionDeCompra: DetalleDeRecepcionDeCompra): Observable<DetalleDeRecepcionDeCompra> {
        const copy = this.convert(detalleDeRecepcionDeCompra);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(detalleDeRecepcionDeCompra: DetalleDeRecepcionDeCompra): Observable<DetalleDeRecepcionDeCompra> {
        const copy = this.convert(detalleDeRecepcionDeCompra);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DetalleDeRecepcionDeCompra> {
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
     * Convert a returned JSON object to DetalleDeRecepcionDeCompra.
     */
    private convertItemFromServer(json: any): DetalleDeRecepcionDeCompra {
        const entity: DetalleDeRecepcionDeCompra = Object.assign(new DetalleDeRecepcionDeCompra(), json);
        entity.fechaDeVencimiento = this.dateUtils
            .convertLocalDateFromServer(json.fechaDeVencimiento);
        return entity;
    }

    /**
     * Convert a DetalleDeRecepcionDeCompra to a JSON which can be sent to the server.
     */
    private convert(detalleDeRecepcionDeCompra: DetalleDeRecepcionDeCompra): DetalleDeRecepcionDeCompra {
        const copy: DetalleDeRecepcionDeCompra = Object.assign({}, detalleDeRecepcionDeCompra);
        copy.fechaDeVencimiento = this.dateUtils
            .convertLocalDateToServer(detalleDeRecepcionDeCompra.fechaDeVencimiento);
        return copy;
    }
}
