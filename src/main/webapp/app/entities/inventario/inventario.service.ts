import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Inventario } from './inventario.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InventarioService {

    private resourceUrl =  SERVER_API_URL + 'api/inventarios';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/inventarios';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(inventario: Inventario): Observable<Inventario> {
        const copy = this.convert(inventario);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(inventario: Inventario): Observable<Inventario> {
        const copy = this.convert(inventario);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Inventario> {
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
     * Convert a returned JSON object to Inventario.
     */
    private convertItemFromServer(json: any): Inventario {
        const entity: Inventario = Object.assign(new Inventario(), json);
        entity.fecha = this.dateUtils
            .convertLocalDateFromServer(json.fecha);
        return entity;
    }

    /**
     * Convert a Inventario to a JSON which can be sent to the server.
     */
    private convert(inventario: Inventario): Inventario {
        const copy: Inventario = Object.assign({}, inventario);
        copy.fecha = this.dateUtils
            .convertLocalDateToServer(inventario.fecha);
        return copy;
    }
}
