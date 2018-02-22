/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PharmacrmTestModule } from '../../../test.module';
import { RecepcionDeCompraComponent } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.component';
import { RecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.service';
import { RecepcionDeCompra } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.model';

describe('Component Tests', () => {

    describe('RecepcionDeCompra Management Component', () => {
        let comp: RecepcionDeCompraComponent;
        let fixture: ComponentFixture<RecepcionDeCompraComponent>;
        let service: RecepcionDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [RecepcionDeCompraComponent],
                providers: [
                    RecepcionDeCompraService
                ]
            })
            .overrideTemplate(RecepcionDeCompraComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecepcionDeCompraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecepcionDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new RecepcionDeCompra(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.recepcionDeCompras[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
