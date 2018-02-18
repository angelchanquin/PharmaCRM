/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PharmacrmTestModule } from '../../../test.module';
import { OrdenDeCompraDetailComponent } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra-detail.component';
import { OrdenDeCompraService } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.service';
import { OrdenDeCompra } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.model';

describe('Component Tests', () => {

    describe('OrdenDeCompra Management Detail Component', () => {
        let comp: OrdenDeCompraDetailComponent;
        let fixture: ComponentFixture<OrdenDeCompraDetailComponent>;
        let service: OrdenDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [OrdenDeCompraDetailComponent],
                providers: [
                    OrdenDeCompraService
                ]
            })
            .overrideTemplate(OrdenDeCompraDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenDeCompraDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new OrdenDeCompra(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ordenDeCompra).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
