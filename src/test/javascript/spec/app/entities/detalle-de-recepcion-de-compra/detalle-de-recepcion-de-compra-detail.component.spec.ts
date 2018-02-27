/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeRecepcionDeCompraDetailComponent } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra-detail.component';
import { DetalleDeRecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.service';
import { DetalleDeRecepcionDeCompra } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.model';

describe('Component Tests', () => {

    describe('DetalleDeRecepcionDeCompra Management Detail Component', () => {
        let comp: DetalleDeRecepcionDeCompraDetailComponent;
        let fixture: ComponentFixture<DetalleDeRecepcionDeCompraDetailComponent>;
        let service: DetalleDeRecepcionDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeRecepcionDeCompraDetailComponent],
                providers: [
                    DetalleDeRecepcionDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeRecepcionDeCompraDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeRecepcionDeCompraDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeRecepcionDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DetalleDeRecepcionDeCompra(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.detalleDeRecepcionDeCompra).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
