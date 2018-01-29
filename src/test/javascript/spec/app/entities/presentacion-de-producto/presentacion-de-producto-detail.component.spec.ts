/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PharmacrmTestModule } from '../../../test.module';
import { PresentacionDeProductoDetailComponent } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto-detail.component';
import { PresentacionDeProductoService } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.service';
import { PresentacionDeProducto } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.model';

describe('Component Tests', () => {

    describe('PresentacionDeProducto Management Detail Component', () => {
        let comp: PresentacionDeProductoDetailComponent;
        let fixture: ComponentFixture<PresentacionDeProductoDetailComponent>;
        let service: PresentacionDeProductoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [PresentacionDeProductoDetailComponent],
                providers: [
                    PresentacionDeProductoService
                ]
            })
            .overrideTemplate(PresentacionDeProductoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PresentacionDeProductoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PresentacionDeProductoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PresentacionDeProducto(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.presentacionDeProducto).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
