/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PharmacrmTestModule } from '../../../test.module';
import { ProductoDetailComponent } from '../../../../../../main/webapp/app/entities/producto/producto-detail.component';
import { ProductoService } from '../../../../../../main/webapp/app/entities/producto/producto.service';
import { Producto } from '../../../../../../main/webapp/app/entities/producto/producto.model';

describe('Component Tests', () => {

    describe('Producto Management Detail Component', () => {
        let comp: ProductoDetailComponent;
        let fixture: ComponentFixture<ProductoDetailComponent>;
        let service: ProductoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [ProductoDetailComponent],
                providers: [
                    ProductoService
                ]
            })
            .overrideTemplate(ProductoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Producto(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.producto).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
