/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeCompraComponent } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.component';
import { DetalleDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.service';
import { DetalleDeCompra } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.model';

describe('Component Tests', () => {

    describe('DetalleDeCompra Management Component', () => {
        let comp: DetalleDeCompraComponent;
        let fixture: ComponentFixture<DetalleDeCompraComponent>;
        let service: DetalleDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeCompraComponent],
                providers: [
                    DetalleDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeCompraComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeCompraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DetalleDeCompra(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.detalleDeCompras[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
