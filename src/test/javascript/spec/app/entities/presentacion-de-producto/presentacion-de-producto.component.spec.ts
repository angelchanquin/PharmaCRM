/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PharmacrmTestModule } from '../../../test.module';
import { PresentacionDeProductoComponent } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.component';
import { PresentacionDeProductoService } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.service';
import { PresentacionDeProducto } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.model';

describe('Component Tests', () => {

    describe('PresentacionDeProducto Management Component', () => {
        let comp: PresentacionDeProductoComponent;
        let fixture: ComponentFixture<PresentacionDeProductoComponent>;
        let service: PresentacionDeProductoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [PresentacionDeProductoComponent],
                providers: [
                    PresentacionDeProductoService
                ]
            })
            .overrideTemplate(PresentacionDeProductoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PresentacionDeProductoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PresentacionDeProductoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PresentacionDeProducto(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.presentacionDeProductos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
