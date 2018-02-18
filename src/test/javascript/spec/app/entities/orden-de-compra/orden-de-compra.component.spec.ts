/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PharmacrmTestModule } from '../../../test.module';
import { OrdenDeCompraComponent } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.component';
import { OrdenDeCompraService } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.service';
import { OrdenDeCompra } from '../../../../../../main/webapp/app/entities/orden-de-compra/orden-de-compra.model';

describe('Component Tests', () => {

    describe('OrdenDeCompra Management Component', () => {
        let comp: OrdenDeCompraComponent;
        let fixture: ComponentFixture<OrdenDeCompraComponent>;
        let service: OrdenDeCompraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [OrdenDeCompraComponent],
                providers: [
                    OrdenDeCompraService
                ]
            })
            .overrideTemplate(OrdenDeCompraComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdenDeCompraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenDeCompraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new OrdenDeCompra(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ordenDeCompras[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
