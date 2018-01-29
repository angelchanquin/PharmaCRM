/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PharmacrmTestModule } from '../../../test.module';
import { ProductoComponent } from '../../../../../../main/webapp/app/entities/producto/producto.component';
import { ProductoService } from '../../../../../../main/webapp/app/entities/producto/producto.service';
import { Producto } from '../../../../../../main/webapp/app/entities/producto/producto.model';

describe('Component Tests', () => {

    describe('Producto Management Component', () => {
        let comp: ProductoComponent;
        let fixture: ComponentFixture<ProductoComponent>;
        let service: ProductoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [ProductoComponent],
                providers: [
                    ProductoService
                ]
            })
            .overrideTemplate(ProductoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Producto(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.productos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
