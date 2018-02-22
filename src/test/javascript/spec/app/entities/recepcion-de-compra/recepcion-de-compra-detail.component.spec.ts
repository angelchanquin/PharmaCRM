/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PharmacrmTestModule } from '../../../test.module';
import { RecepcionDeCompraDetailComponent } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra-detail.component';
import { RecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.service';
import { RecepcionDeCompra } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.model';

describe('Component Tests', () => {

    describe('RecepcionDeCompra Management Detail Component', () => {
        let comp: RecepcionDeCompraDetailComponent;
        let fixture: ComponentFixture<RecepcionDeCompraDetailComponent>;
        let service: RecepcionDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [RecepcionDeCompraDetailComponent],
                providers: [
                    RecepcionDeCompraService
                ]
            })
            .overrideTemplate(RecepcionDeCompraDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecepcionDeCompraDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecepcionDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new RecepcionDeCompra(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.recepcionDeCompra).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
