/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeCompraDetailComponent } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra-detail.component';
import { DetalleDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.service';
import { DetalleDeCompra } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.model';

describe('Component Tests', () => {

    describe('DetalleDeCompra Management Detail Component', () => {
        let comp: DetalleDeCompraDetailComponent;
        let fixture: ComponentFixture<DetalleDeCompraDetailComponent>;
        let service: DetalleDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeCompraDetailComponent],
                providers: [
                    DetalleDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeCompraDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeCompraDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DetalleDeCompra(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.detalleDeCompra).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
