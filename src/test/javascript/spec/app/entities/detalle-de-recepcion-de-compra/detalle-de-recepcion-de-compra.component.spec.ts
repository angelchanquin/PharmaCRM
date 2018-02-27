/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeRecepcionDeCompraComponent } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.component';
import { DetalleDeRecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.service';
import { DetalleDeRecepcionDeCompra } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.model';

describe('Component Tests', () => {

    describe('DetalleDeRecepcionDeCompra Management Component', () => {
        let comp: DetalleDeRecepcionDeCompraComponent;
        let fixture: ComponentFixture<DetalleDeRecepcionDeCompraComponent>;
        let service: DetalleDeRecepcionDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeRecepcionDeCompraComponent],
                providers: [
                    DetalleDeRecepcionDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeRecepcionDeCompraComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeRecepcionDeCompraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeRecepcionDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DetalleDeRecepcionDeCompra(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.detalleDeRecepcionDeCompras[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
